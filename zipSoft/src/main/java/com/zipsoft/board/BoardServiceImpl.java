package com.zipsoft.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.zipsoft.auth.AuthMapper;
import com.zipsoft.auth.AuthServiceImpl;
import com.zipsoft.board.dto.BoardDto;
import com.zipsoft.board.dto.SearchDto;
import com.zipsoft.model.entity.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepository;
	
	@Override
	public Page<BoardDto> getBoardList(SearchDto search) {
		return boardRepository.list(search);
	}
	
	@Override
	public void insertBoard(BoardDto board) {
		Board b = Board.builder().subject(board.getSubject())
								 .content(board.getContent())
								 .regId(board.getRegId())
								 .updateId(board.getRegId())
								 .build();
		
		boardRepository.insert(b);
	}

}
