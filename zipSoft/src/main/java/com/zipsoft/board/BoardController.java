package com.zipsoft.board;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.board.dto.BoardCommentDto;
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
	
	@PostMapping("{id}")
	public ApiResponse edit(BoardDto board, @AuthenticationPrincipal UserPrincipal user, @PathVariable("id") long id) {
		board.setId(id);
		board.setUpdateId(user.getUserId());
		
		boardService.edit(board);
		return ApiResponse.OK(null);
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
	
	@DeleteMapping("/file/{id}")
	public ApiResponse deleteFile(@PathVariable("id") long id) {
		boardService.deleteBoardFile(id);
		
		return ApiResponse.OK(null);
	}
	
	@PostMapping("/{id}/comment")
	public ApiResponse insertBoardComment(@PathVariable("id") long id, @RequestBody BoardCommentDto dto, @AuthenticationPrincipal UserPrincipal user) {
		dto.setBoardId(id);
		dto.setRegId(user.getUserId());
		
		boardService.insertBoardComment(dto);
		return ApiResponse.OK(null);
	}
	
	@GetMapping("/{id}/comment")
	public ApiResponse commentList(@PathVariable("id") long id) {
		List<BoardCommentDto> list = boardService.commentList(id);
		
		return ApiResponse.OK(list);
	}
}
