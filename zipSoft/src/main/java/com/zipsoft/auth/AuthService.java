package com.zipsoft.auth;

import org.springframework.security.core.Authentication;

import com.zipsoft.auth.dto.LoginDto;
import com.zipsoft.auth.dto.UserDto;

public interface AuthService  {
	
	UserDto findByUserId(LoginDto dto);
	
	Authentication authenticate(String id, String password)  throws Exception;
	
	void removeUserCache(long id);
	
	void insertUser(LoginDto dto);
}
