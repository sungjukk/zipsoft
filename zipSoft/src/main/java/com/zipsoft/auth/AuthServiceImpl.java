package com.zipsoft.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zipsoft.auth.dto.LoginDto;
import com.zipsoft.auth.dto.User;
import com.zipsoft.config.CacheKeys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authManager;
	private final AuthMapper loginMapper;
	
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

	@Override
	@Caching(evict = {
			@CacheEvict(value=CacheKeys.loadUserById, key="#id")
	})
	public void removeUserCache(long id) {
		log.info("userId : " + id + " 캐시 삭제 완료");
	}
	
	

}
