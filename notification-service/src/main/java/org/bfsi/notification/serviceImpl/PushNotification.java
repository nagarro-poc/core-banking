package org.bfsi.notification.serviceImpl;

import org.bfsi.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushNotification implements NotificationService {
    Logger logger = LoggerFactory.getLogger(PushNotification.class);

    @Override
    public void notify(String message) {
        logger.info("Push Notification from here"+message);
    }
}
