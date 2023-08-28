package com.zipsoft.commons.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zipsoft.auth.AuthMapper;
import com.zipsoft.auth.AuthRepository;
import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.config.CacheKeys;
import com.zipsoft.model.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
	
	private final AuthRepository authRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UserDto> user = authRepository.findByUserId(username);
		
		if (user.isEmpty()) throw new UsernameNotFoundException("회원을 찾을 수 없습니다.");
		
		return new UserPrincipal(user.get());
	}
	
	@Cacheable(value = CacheKeys.loadUserById, key = "#id", unless = "#result == null")
	public UserDetails loadUserById(long id) {
		Optional<UserDto> user = authRepository.findById(id);
		
		if (user.isEmpty()) return null;
		
		return new UserPrincipal(user.get());
		
	}

}
