package com.myrestapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.model.User;
import com.myrestapp.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/user")
	public void postUser(@RequestBody User user) {
		String encPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encPassword);
		userRepository.save(user);
	}

	
}
