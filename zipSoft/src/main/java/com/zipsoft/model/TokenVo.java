package com.zipsoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenVo {
	
	private String grantType;
	private String accessToken;
	private String refreshToken;
	
}
