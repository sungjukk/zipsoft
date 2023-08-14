package com.zipsoft.login;

import com.zipsoft.login.dto.LoginDto;

public interface LoginService {
	
	LoginDto findUserId(LoginDto dto);
	
}
