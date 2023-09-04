package com.zipsoft.board;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.board.dto.BoardDto;
import com.zipsoft.board.dto.BoardFileDto;
import com.zipsoft.board.dto.SearchDto;
import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.security.UserPrincipal;
import com.zipsoft.commons.utils.FileUtil;
import com.zipsoft.enums.FilePath;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping
	public ApiResponse list(SearchDto search) {
		
		Page<BoardDto> list = boardService.getBoardList(search);
		
		return ApiResponse.OK(list);
	}
	
	@PostMapping
	public ApiResponse insert(BoardDto board, @AuthenticationPrincipal UserPrincipal user) {
		board.setRegId(user.getUserId());
		boardService.insertBoard(board);
		
		return ApiResponse.OK(null);
	}
	
	@GetMapping("{id}")
	public ApiResponse detail(@PathVariable("id") long id) {
		
		return ApiResponse.OK(boardService.detail(id));
	}
	
	@GetMapping("/file/{id}")
	public ResponseEntity<Resource> downloadFile(HttpServletRequest request, @PathVariable("id") long id) {
		BoardFileDto dto = boardService.detailFile(id);
		if (dto != null) {
			return FileUtil.download(request, dto.getFileName(), dto.getOrgFileName(), dto.getExt(), FilePath.BOARD);			
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
