package com.zipsoft.commons.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zipsoft.login.LoginMapper;
import com.zipsoft.login.dto.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
	
	private final LoginMapper loginMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = loginMapper.findByUserId(username);
		
		if (user == null) throw new UsernameNotFoundException("회원을 찾을 수 없습니다.");
		
		return new UserPrincipal(user);
	}
	
	@Cacheable(value = "loadUserById", key = "#id", unless = "#result == null")
	public UserDetails loadUserById(long id) {
		User user = loginMapper.findById(id);
		
		if (user == null) return null;
		
		return new UserPrincipal(user);
		
	}

}
