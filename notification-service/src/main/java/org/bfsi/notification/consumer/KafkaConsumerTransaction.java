package org.bfsi.notification.consumer;

import org.bfsi.notification.service.NotificationService;
import org.bfsi.notification.serviceImpl.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerTransaction {
    Logger logger = LoggerFactory.getLogger(KafkaConsumerTransaction.class);

    @Autowired
    private EmailNotification emailNotification;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumer(String msg) {
        logger.info("Message Consumed Success: " + msg);
        emailNotification.notify(msg);
    }

}
