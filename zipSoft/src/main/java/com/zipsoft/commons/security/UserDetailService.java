package com.zipsoft.commons.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zipsoft.auth.AuthMapper;
import com.zipsoft.auth.dto.User;
import com.zipsoft.config.CacheKeys;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
	
	private final AuthMapper loginMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = loginMapper.findByUserId(username);
		
		if (user == null) throw new UsernameNotFoundException("회원을 찾을 수 없습니다.");
		
		return new UserPrincipal(user);
	}
	
	@Cacheable(value = CacheKeys.loadUserById, key = "#id", unless = "#result == null")
	public UserDetails loadUserById(long id) {
		User user = loginMapper.findById(id);
		
		if (user == null) return null;
		
		return new UserPrincipal(user);
		
	}

}
