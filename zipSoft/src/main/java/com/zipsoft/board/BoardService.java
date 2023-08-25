package com.zipsoft.board;

import java.util.List;

import com.zipsoft.board.dto.BoardDto;

public interface BoardService {
	
	public List<BoardDto> getBoardList();
	
	public void insertBoard(BoardDto board);
	
}
