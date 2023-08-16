package com.zipsoft.login;

import org.apache.ibatis.annotations.Mapper;

import com.zipsoft.login.dto.LoginDto;
import com.zipsoft.login.dto.User;

@Mapper
public interface LoginMapper {
	
	User findByUserId(String userId);
	
	User findById(long id);
	
}
