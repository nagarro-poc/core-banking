package org.bfsi.notification.service;

import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    public void notify(String message);

}
