package org.bfsi.notification.service;

import org.bfsi.notification.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RedisDataService {
    public List<UserModel> getCacheList();

}
