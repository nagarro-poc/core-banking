package org.bfsi.user.service;

import java.util.Optional;

import org.bfsi.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	void saveUser(User user);
	Optional<User> getUser(long id);
	User updateUser(User user);
	
}
