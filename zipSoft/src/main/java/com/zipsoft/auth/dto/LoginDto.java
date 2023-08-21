package com.zipsoft.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
	
	private long id;
	private String userId;
	private String password;
	private String email;
	private String name;
	
}
