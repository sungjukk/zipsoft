package com.zipsoft.board;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.zipsoft.auth.AuthMapper;
import com.zipsoft.auth.AuthServiceImpl;
import com.zipsoft.board.dto.BoardDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;

	@Override
	public void insertBoard(BoardDto board) {
		boardMapper.insertBoard(board);
	}
	

}
