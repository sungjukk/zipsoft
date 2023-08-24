package com.zipsoft.board;

import org.apache.ibatis.annotations.Mapper;

import com.zipsoft.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	void insertBoard(BoardDto board);
	
}
