package org.bfsi.transaction.serviceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.bfsi.transaction.entity.TransactionEntity;
import org.bfsi.transaction.model.AccountEntityModel;
import org.bfsi.transaction.model.TransactionStatus;
import org.bfsi.transaction.model.TransactionType;
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
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountServiceFeignClient accountServiceFeignClient;

    @Override
    @CircuitBreaker(name = "accountService", fallbackMethod = "accountFallback")
    //@RateLimiter(name = "accountRateLimiter", fallbackMethod = "accountFallback")
    public TransactionEntity debit(Long accountId, BigDecimal amount) {

        AccountEntityModel ae = accountServiceFeignClient.getAccountDetails(accountId);

        logger.info("Account Data Retrieved for Debit:" + ae.getBalance());

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

        sendMessage("AccountId: " + accountId + " is Debited and now Balance is: " + ae.getBalance());

        return te;
    }

    @Override
    @CircuitBreaker(name = "accountService", fallbackMethod = "accountFallback")
    public TransactionEntity credit(Long accountId, BigDecimal amount) {

        AccountEntityModel ae = accountServiceFeignClient.getAccountDetails(accountId);

        logger.info("Account Data Retrieved for Credit:" + ae.getBalance());

        ae.setBalance(ae.getBalance().add(amount));

        accountServiceFeignClient.updateAccountEntity(ae);

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

        logger.info("Going to Produced Kafka Msg: " + message);
        kafkaTemplate.send(topicName, message);
    }

    public TransactionEntity accountFallback(Exception e) {
        TransactionEntity te = new TransactionEntity();
        te.setTransactionStatus(TransactionStatus.FAIL);
        return te;
    }

}


