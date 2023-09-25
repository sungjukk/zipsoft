package com.zipsoft.chat;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.board.BoardController;
import com.zipsoft.board.BoardService;
import com.zipsoft.chat.dto.ChatRoomDetailDto;
import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
	
	private final ChatService chatService;
	
	@GetMapping("{id}")
	public ApiResponse detail(@PathVariable("id") String id, @AuthenticationPrincipal UserPrincipal user) {
		ChatRoomDetailDto dto = chatService.detail(id);
		
		return ApiResponse.OK(dto);
	}
	
}
