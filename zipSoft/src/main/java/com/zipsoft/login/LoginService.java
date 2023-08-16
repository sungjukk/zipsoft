package com.zipsoft.login;

import org.springframework.security.core.Authentication;

import com.zipsoft.login.dto.LoginDto;
import com.zipsoft.login.dto.User;

public interface LoginService  {
	
	User findByUserId(LoginDto dto);
	
	Authentication authenticate(String id, String password)  throws Exception;
	
}
