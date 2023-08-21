package com.zipsoft.auth;

import org.apache.ibatis.annotations.Mapper;

import com.zipsoft.auth.dto.LoginDto;
import com.zipsoft.auth.dto.User;

@Mapper
public interface AuthMapper {
	
	User findByUserId(String userId);
	
	User findById(long id);
	
}
