package org.bfsi.notification.serviceImpl;

import org.bfsi.notification.model.UserModel;
import org.bfsi.notification.service.RedisDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class RedisDataServiceImpl implements RedisDataService {

    Logger logger = LoggerFactory.getLogger(RedisDataService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<UserModel> getCacheList() {
        List<UserModel> listUserModel = new ArrayList<>();

        Set<String> redisKeys = redisTemplate.keys("user*");

        Iterator<String> it = redisKeys.iterator();
        while (it.hasNext()) {
            String key = it.next();

            UserModel us = (UserModel) redisTemplate.opsForValue().get(key);
            logger.info("User Added::" + us.toString());
            listUserModel.add(us);
        }

        return listUserModel;

    }

}
