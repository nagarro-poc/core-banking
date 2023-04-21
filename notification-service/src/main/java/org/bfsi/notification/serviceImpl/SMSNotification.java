package org.bfsi.notification.serviceImpl;

import org.bfsi.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SMSNotification implements NotificationService {
    Logger logger = LoggerFactory.getLogger(SMSNotification.class);

    @Override
    public void notify(String message) {
        logger.info("SMS Notification from here:"+message);
    }
}
