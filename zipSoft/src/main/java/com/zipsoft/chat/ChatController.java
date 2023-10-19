package com.zipsoft.chat;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.chat.dto.ChatRoomDetailDto;
import com.zipsoft.chat.dto.ChatRoomDto;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
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
	
	@PostMapping("/add")
	public ApiResponse insert(@AuthenticationPrincipal UserPrincipal user, @RequestBody ChatRoomDto dto) {
		
		dto.getMemberList().add(ChatRoomMemberDto.builder().userId(user.getUserId()).build());
		return ApiResponse.OK(chatService.ChatRoomInsert(dto, user.getUserId()));
	}
	
	@GetMapping("{id}")
	public ApiResponse detail(@PathVariable("id") String id, @AuthenticationPrincipal UserPrincipal user) {
		ChatRoomDetailDto dto = chatService.detail(id, user.getUserId());
		
		return ApiResponse.OK(dto);
	}
	
}
