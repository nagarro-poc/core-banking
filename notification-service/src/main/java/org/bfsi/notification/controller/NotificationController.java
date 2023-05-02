package org.bfsi.notification.controller;

import org.bfsi.notification.model.UserModel;
import org.bfsi.notification.service.RedisDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private RedisDataService redisDataService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        logger.info("Info level log message");
        logger.debug("Debug level log message");
        logger.error("Error level log message");
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    @GetMapping("/cached-user")
    public ResponseEntity<List<UserModel>> notifyTransactions() {
        logger.info("In cache Controller:");
        return new ResponseEntity<List<UserModel>>(redisDataService.getCacheList(), HttpStatus.OK);

    }
}
