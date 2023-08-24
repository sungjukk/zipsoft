package com.zipsoft.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	
	private long id;
	private String subject;
	private String content;
	private String userName;
	private int viewCnt;
	private long regId;
	private String regDate;
	private String updateDate;
	
}
