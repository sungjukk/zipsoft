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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zipsoft.auth.dto.LoginDto;
import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.config.CacheKeys;
import com.zipsoft.model.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authManager;
	private final AuthMapper loginMapper;
	private final AuthRepository authRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDto findByUserId(LoginDto dto) {
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

	@Override
	public void insertUser(LoginDto dto) {
		User user = User.builder()
						.userId(dto.getUserId())
						.password(passwordEncoder.encode(dto.getPassword()))
						.userName("tester")
						.email("test@test.com")
						.build();
		authRepository.insert(user);
	}

	@Override
	public void updateDeviceToken(long id, String deviceToken) {
		authRepository.updateDeviceToken(id, deviceToken);
	}

	@Override
	public void removeDeviceToken(long id) {
		authRepository.removeDeviceToken(id);
	}
	
	

}
