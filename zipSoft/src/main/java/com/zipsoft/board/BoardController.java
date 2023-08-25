package com.zipsoft.board;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.board.dto.BoardDto;
import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping
	public ApiResponse list() {
		
		List<BoardDto> list = boardService.getBoardList();
		
		return ApiResponse.OK(list);
	}
	
	@PostMapping
	public ApiResponse insert(@RequestBody BoardDto board, @AuthenticationPrincipal UserPrincipal user) {
		board.setRegId(user.getUserId());
		boardService.insertBoard(board);
		
		return ApiResponse.OK(null);
	}
	
}