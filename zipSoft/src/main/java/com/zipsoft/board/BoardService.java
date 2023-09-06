package com.zipsoft.board;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zipsoft.board.dto.BoardCommentDto;
import com.zipsoft.board.dto.BoardDto;
import com.zipsoft.board.dto.BoardFileDto;
import com.zipsoft.board.dto.SearchDto;

public interface BoardService {
	
	public Page<BoardDto> getBoardList(SearchDto search);
	
	public void insertBoard(BoardDto board);
	
	public BoardDto detail(long id);
	
	public BoardFileDto detailFile(long id);
	
	public void insertBoardComment(BoardCommentDto dto);
	
	public List<BoardCommentDto> commentList(long id);
}
