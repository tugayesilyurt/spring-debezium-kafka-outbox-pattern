package com.debezium.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.debezium.user.request.CreateUserRequest;
import com.debezium.user.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping()
	public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest){
		
		userService.createUser(createUserRequest);
		return new ResponseEntity<Boolean>(Boolean.TRUE,HttpStatus.CREATED);
		
	}

}
