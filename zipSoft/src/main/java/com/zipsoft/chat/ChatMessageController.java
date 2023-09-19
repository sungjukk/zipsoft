package com.zipsoft.chat;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.chat.dto.ChatDto;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
import com.zipsoft.commons.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatMessageController {
	
	private final SimpMessageSendingOperations template;
	
	private final ChatService chatService;
	
	@MessageMapping("/chat/send")
	public void enterUser(@Payload ChatDto dto, Message<?> message) {
		
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) SimpMessageHeaderAccessor.getUser(message.getHeaders());
		UserPrincipal prin = (UserPrincipal) upat.getPrincipal();
		
		List<ChatRoomMemberDto> mberList = chatService.getChatRoomMemberList(dto.getId(), "N");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		dto.setSendDt(now.format(dtf));
		
		if (mberList != null && mberList.size() > 0) {
			
			chatService.updateChatRoomNoReadCnt(dto.getId());
			
			for (ChatRoomMemberDto mber : mberList) {
				template.convertAndSend("/topic/" + mber.getUserId(), dto);
			}
		}
		
		
		
		template.convertAndSend("/topic/chat/" + dto.getId(), dto);
	}
	
}
