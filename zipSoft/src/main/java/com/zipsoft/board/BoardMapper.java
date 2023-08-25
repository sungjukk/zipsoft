package com.zipsoft.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zipsoft.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	void insertBoard(BoardDto board);
	
	List<BoardDto> getBoardList();
}
