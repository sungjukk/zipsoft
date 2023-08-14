package com.zipsoft.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.login.dto.LoginDto;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public ApiResponse login(@RequestBody LoginDto dto) {
		
		System.out.println(dto.getUserId());
		System.out.println(dto.getPassword());
		
		loginService.findUserId(dto);
		
		return null;
	}
	
	
	
}
