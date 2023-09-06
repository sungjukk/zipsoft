package com.zipsoft.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zipsoft.board.dto.BoardCommentDto;
import com.zipsoft.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	List<BoardCommentDto> getCommentList(long id);
	
}
