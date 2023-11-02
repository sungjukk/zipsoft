package com.zipsoft.member.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	
	private long id;
	private String userId;
	private String userName;
	private String email;
	private long thumbId;
	private MultipartFile file;
	
}
