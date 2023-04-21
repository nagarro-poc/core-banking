package org.bfsi.user.serviceImpl;

import java.util.Optional;

import org.bfsi.user.entity.User;
import org.bfsi.user.respository.UserRepository;
import org.bfsi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void saveUser(User user) {
		userRepository.save(user);

	}

	@Override
	public Optional<User> getUser(long id) {
		
		return userRepository.findById(id);
	}

	@Override
	public User updateUser(User user) {

		return userRepository.saveAndFlush(user);
	}

}
