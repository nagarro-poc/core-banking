package org.bfsi.transaction.serviceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.bfsi.transaction.entity.TransactionEntity;
import org.bfsi.transaction.model.*;
import org.bfsi.transaction.repository.TransactionRepository;
import org.bfsi.transaction.service.AccountServiceFeignClient;
import org.bfsi.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, KafkaModel> kafkaTemplate;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountServiceFeignClient accountServiceFeignClient;

    @Override
    @CircuitBreaker(name = "accountService", fallbackMethod = "accountFallback")
    //@RateLimiter(name = "accountRateLimiter", fallbackMethod = "accountFallback")
    public TransactionEntity debit(TransactionDTO transactionDTO) {

        Long accountId = transactionDTO.getAccountId();
        BigDecimal amount = transactionDTO.getAmount();

        AccountEntityModel ae = accountServiceFeignClient.getAccountDetails(accountId);

        logger.info("Account Data Retrieved for Debit:" + ae.toString());

        TransactionEntity te = new TransactionEntity();

        if ((ae.getBalance().subtract(amount)).compareTo(BigDecimal.ZERO) < 0) {
            te.setTransactionStatus(TransactionStatus.FAIL);
        } else {
            te.setTransactionStatus(TransactionStatus.SUCCESS);
            ae.setBalance(ae.getBalance().subtract(amount));
        }

        accountServiceFeignClient.updateAccountEntity(ae);

        te.setTransactionType(TransactionType.DEBIT);
        te.setAccountId(accountId);
        te.setTransactionAmount(amount);

        transactionRepository.save(te);

        KafkaModel kafkaModel = new KafkaModel();
        kafkaModel.setTransactionId(te.getId());
        kafkaModel.setAccountNumber(ae.getAccountNumber());
        kafkaModel.setBalance(ae.getBalance());
        kafkaModel.setUserId(ae.getUserId());
        kafkaModel.setTransactionType(te.getTransactionType());
        kafkaModel.setAccountId(accountId);

        sendMessage(kafkaModel);

        return te;
    }

    @Override
    @CircuitBreaker(name = "accountService", fallbackMethod = "accountFallback")
    public TransactionEntity credit(TransactionDTO transactionDTO) {

        Long accountId = transactionDTO.getAccountId();
        BigDecimal amount = transactionDTO.getAmount();

        AccountEntityModel ae = accountServiceFeignClient.getAccountDetails(accountId);

        logger.info("Account Data Retrieved for Credit:" + ae.toString());

        ae.setBalance(ae.getBalance().add(amount));

        accountServiceFeignClient.updateAccountEntity(ae);

        TransactionEntity te = new TransactionEntity();
        te.setTransactionType(TransactionType.CREDIT);
        te.setAccountId(accountId);
        te.setTransactionAmount(amount);
        te.setTransactionStatus(TransactionStatus.SUCCESS);

        transactionRepository.save(te);

        KafkaModel kafkaModel = new KafkaModel();
        kafkaModel.setTransactionId(te.getId());
        kafkaModel.setAccountNumber(ae.getAccountNumber());
        kafkaModel.setBalance(ae.getBalance());
        kafkaModel.setUserId(ae.getUserId());
        kafkaModel.setTransactionType(te.getTransactionType());
        kafkaModel.setAccountId(accountId);

        sendMessage(kafkaModel);

        return te;
    }

    private void sendMessage(KafkaModel kafkaModel) {

        logger.info("Going to Produced Kafka Msg: " + kafkaModel.toString());
        kafkaTemplate.send(topicName, kafkaModel);

    }

    public TransactionEntity accountFallback(Exception e) {
        logger.info(e.toString());
        TransactionEntity te = new TransactionEntity();
        te.setTransactionStatus(TransactionStatus.FAIL);
        return te;
    }

}


