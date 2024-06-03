package com.repository;

import com.dto.request.RegisterRequest;
import com.dto.response.ResponseRequest;
import com.model.User;

public interface UserService {
	
	ResponseRequest registor(RegisterRequest request);
	
	void verify(String email,String otp);
	
	User Login(String email,String password);

}
