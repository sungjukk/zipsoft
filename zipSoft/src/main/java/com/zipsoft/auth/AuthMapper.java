package com.zipsoft.auth;

import org.apache.ibatis.annotations.Mapper;

import com.zipsoft.auth.dto.LoginDto;
import com.zipsoft.auth.dto.UserDto;

@Mapper
public interface AuthMapper {
	
	UserDto findByUserId(String userId);
	
	UserDto findById(long id);
	
}
