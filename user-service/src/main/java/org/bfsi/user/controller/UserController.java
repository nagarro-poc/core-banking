package org.bfsi.user.controller;

import org.bfsi.user.entity.User;
import org.bfsi.user.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	UserServiceImpl	 userService;
    @GetMapping()
    public String health(){
        return "OK";
    }
    @PostMapping("/saveuser")
    public User saveUser(@RequestBody User user) {
    	userService.saveUser(user);
    	return user;
    }
    @GetMapping("/getuser")
    public User getUser(@RequestParam String id){
        return userService.getUser(Long.parseLong(id)).get();
    }
    
    @PostMapping("/updateuser")
    public User updateUser(@RequestBody User user) {
    	userService.updateUser(user);
    	return user;
    }
}
