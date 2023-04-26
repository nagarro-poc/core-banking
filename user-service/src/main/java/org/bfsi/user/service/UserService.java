package org.bfsi.user.service;

import org.bfsi.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User saveUser(User user);

    User getUser(long id);

    List<User> getList();

    User updateUser(User user);

    void deleteUser(Long id);


}
