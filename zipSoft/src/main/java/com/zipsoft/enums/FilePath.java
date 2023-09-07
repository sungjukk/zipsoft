package com.zipsoft.enums;

import lombok.Getter;

@Getter
public enum FilePath {
	BOARD("/board","게시판 저장 경로"),
	EDITOR_IMAGE("/images", "에디터 이미지 업로드 경로");
	
	private String path;
	private String description;
	
	FilePath(String path, String description) {
		this.path = path;
		this.description = description;
	}
}
