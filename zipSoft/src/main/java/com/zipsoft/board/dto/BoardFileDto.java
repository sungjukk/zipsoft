package com.zipsoft.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFileDto {
	
	private long id;
	private String ext;
	private int fileSize;
	private String orgFileName;
	private String fileName;
	
}
