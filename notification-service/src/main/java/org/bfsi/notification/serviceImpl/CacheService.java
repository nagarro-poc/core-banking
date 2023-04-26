package org.bfsi.notification.serviceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.bfsi.notification.model.UserModel;
import org.bfsi.notification.service.UserServiceFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    Logger logger = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    UserServiceFeignClient userServiceFeignClient;

    @Cacheable(value = "user", key = "#id")
    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public UserModel callUserModelFeign(Long id) {
        logger.info("Fetching User using feign:" + id);
        return userServiceFeignClient.getUserDetails(id);
    }

    public UserModel userFallback(Exception e) {
        logger.info("UserFallback:" + e.toString());

        return new UserModel();
    }
}
