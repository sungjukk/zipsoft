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
public class ThumbnailDto {
	
	private long id;
	private String fileName;
	private String thumbFileName;
	
}
