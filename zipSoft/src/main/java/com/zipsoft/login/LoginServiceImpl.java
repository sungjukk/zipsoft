package com.zipsoft.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zipsoft.login.dto.LoginDto;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public LoginDto findUserId(LoginDto dto) {
		return loginMapper.findUserId(dto);
	}

}
