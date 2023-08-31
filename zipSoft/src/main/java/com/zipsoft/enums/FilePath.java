package com.zipsoft.enums;

import lombok.Getter;

@Getter
public enum FilePath {
	BOARD("/board","게시판 저장 위치");
	
	private String path;
	private String description;
	
	FilePath(String path, String description) {
		this.path = path;
		this.description = description;
	}
}
