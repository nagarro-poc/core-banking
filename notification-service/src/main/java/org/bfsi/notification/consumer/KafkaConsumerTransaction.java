package org.bfsi.notification.consumer;

import org.bfsi.notification.model.KafkaModel;
import org.bfsi.notification.model.UserModel;
import org.bfsi.notification.serviceImpl.CacheService;
import org.bfsi.notification.serviceImpl.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerTransaction {
    Logger logger = LoggerFactory.getLogger(KafkaConsumerTransaction.class);

    @Autowired
    private EmailNotification emailNotification;

    @Autowired
    private CacheService cacheService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public ResponseEntity<Object> consumer(KafkaModel kafkaModel) {

        logger.info("Message Consumed Success: " + kafkaModel.toString());
        String msg = "User:" + kafkaModel.getUserId() + " is:" + kafkaModel.getTransactionType() + " and Balance is:" + kafkaModel.getBalance();
        //fetch user email using user id from Redis cache

        UserModel userModel = cacheService.callUserModelFeign(Long.parseLong(kafkaModel.getUserId()));
        logger.info("UserModel Fetched Success:" + userModel);

        emailNotification.notify(msg);

        return new ResponseEntity<Object>(msg, HttpStatus.OK);

    }

}
