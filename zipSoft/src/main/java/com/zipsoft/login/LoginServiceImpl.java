package com.zipsoft.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zipsoft.login.dto.LoginDto;
import com.zipsoft.login.dto.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
	
	private final AuthenticationManager authManager;
	private final LoginMapper loginMapper;
	
	@Override
	public User findByUserId(LoginDto dto) {
		return loginMapper.findByUserId(dto.getUserId());
	}

	@Override
	public Authentication authenticate(String id, String password) throws Exception {
		
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(id, password));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
		
	}
	
	

}
