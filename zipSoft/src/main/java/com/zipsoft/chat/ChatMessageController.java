package com.zipsoft.chat;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.chat.dto.ChatDto;
import com.zipsoft.commons.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatMessageController {
	
	private final SimpMessageSendingOperations template;
	
	@MessageMapping("/chat/send")
	public void enterUser(@Payload ChatDto dto, Principal prin) {
		
		System.out.println(prin);
		
		if (ChatDto.MessageType.ENTER.equals(dto.getType())) {
			dto.setMessage(dto.getId()+"님이 입장하였습니다.");
		}
		
		template.convertAndSend("/topic/chat/room" + dto.getId(), dto);
	}
	
}
