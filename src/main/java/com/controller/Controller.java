package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.request.LoginRequest;
import com.dto.request.RegisterRequest;
import com.dto.response.ResponseRequest;
import com.model.User;
import com.repository.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<ResponseRequest> register(@RequestBody RegisterRequest register) {
		ResponseRequest registerResponse = userService.registor(register);
		return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
	}

	@PostMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp) {
		try {
			userService.verify(email, otp);
			return new ResponseEntity<>("User verified successfully",HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/login")
	public ResponseEntity<?>login (@RequestBody LoginRequest login){
		User user =userService.Login(login.getEmail(),login.getPassword());
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

}
