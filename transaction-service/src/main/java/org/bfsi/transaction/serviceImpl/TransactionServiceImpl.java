package org.bfsi.transaction.serviceImpl;

import org.bfsi.transaction.model.AccountEntityModel;
import org.bfsi.transaction.entity.TransactionEntity;
import org.bfsi.transaction.model.TransactionStatus;
import org.bfsi.transaction.model.TransactionType;
import org.bfsi.transaction.repository.TransactionRepository;
import org.bfsi.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Value("${account.service.url}")
    private String accountServiceUrl;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TransactionEntity debit(Long accountId, BigDecimal amount) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        HttpEntity<AccountEntityModel> entity = new HttpEntity<AccountEntityModel>(headers);

        AccountEntityModel ae = restTemplate.getForObject(accountServiceUrl + accountId, AccountEntityModel.class);

        TransactionEntity te = new TransactionEntity();

        if ((ae.getBalance().subtract(amount)).compareTo(BigDecimal.ZERO) < 0) {
            te.setTransactionStatus(TransactionStatus.FAIL);
        } else {
            te.setTransactionStatus(TransactionStatus.SUCCESS);
            ae.setBalance(ae.getBalance().subtract(amount));
        }

        restTemplate.put(accountServiceUrl, ae);

        te.setTransactionType(TransactionType.DEBIT);
        te.setAccountId(accountId);
        te.setTransactionAmount(amount);

        transactionRepository.save(te);

        sendMessage("AccountId: " + accountId + " is Debited and now Balance is: " + ae.getBalance());

        return te;
    }

    @Override
    public TransactionEntity credit(Long accountId, BigDecimal amount) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        HttpEntity<AccountEntityModel> entity = new HttpEntity<AccountEntityModel>(headers);

        AccountEntityModel ae = restTemplate.getForObject(accountServiceUrl + accountId, AccountEntityModel.class);

        ae.setBalance(ae.getBalance().add(amount));

        restTemplate.put(accountServiceUrl, ae);

        TransactionEntity te = new TransactionEntity();
        te.setTransactionType(TransactionType.CREDIT);
        te.setAccountId(accountId);
        te.setTransactionAmount(amount);
        te.setTransactionStatus(TransactionStatus.SUCCESS);

        transactionRepository.save(te);

        sendMessage("AccountId: " + accountId + " is Credited with amount: " + amount);

        return te;
    }

    private void sendMessage(String message) {
        kafkaTemplate.send(topicName, message);
    }
}


