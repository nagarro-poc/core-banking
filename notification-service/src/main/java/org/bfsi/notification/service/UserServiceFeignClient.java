package org.bfsi.notification.service;

import org.bfsi.notification.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "api/v1/users/")
public interface UserServiceFeignClient {

    @GetMapping(value = "/{id}")
    public UserModel getUserDetails(@PathVariable Long id);

}