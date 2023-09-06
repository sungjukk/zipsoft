package com.zipsoft.board.dto;

import java.time.LocalDateTime;

public interface BoardCommentInterface {
	Long getId();
	
	Long getBoardId();
	
	String getComment();
	
	String getParentId();
	
	LocalDateTime getRegDt();
	
	Long getRegId();
	
	String getUserName();
}
