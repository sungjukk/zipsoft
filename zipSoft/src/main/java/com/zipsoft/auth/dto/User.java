package com.zipsoft.auth.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	private long id;
	private String userId;
	private String password;
	private String email;
	private String name;
}
