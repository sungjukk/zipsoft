package com.zipsoft.login;

import org.apache.ibatis.annotations.Mapper;

import com.zipsoft.login.dto.LoginDto;

@Mapper
public interface LoginMapper {
	
	LoginDto findUserId(LoginDto dto);
	
}
