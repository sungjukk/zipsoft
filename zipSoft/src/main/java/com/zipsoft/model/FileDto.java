package com.zipsoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
	
	private String orgFileName;
	
	private String fileName;
	
	private String path;
	
	private int size;
	
	private String ext;
	
}
