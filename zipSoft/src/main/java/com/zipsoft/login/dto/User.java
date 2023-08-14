package com.zipsoft.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private long id;
	private String userId;
	private String password;
	private String email;
	private String name;
}
