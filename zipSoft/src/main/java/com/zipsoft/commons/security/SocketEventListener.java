package com.zipsoft.commons.security;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.zipsoft.chat.ChatService;
import com.zipsoft.chat.dto.ChatRoomMemberDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SocketEventListener {
	
	private final ChatService chatService;
	
	@EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) SimpMessageHeaderAccessor.getUser(event.getMessage().getHeaders());
		UserPrincipal prin = (UserPrincipal) upat.getPrincipal();
		ChatRoomMemberDto dto = ChatRoomMemberDto.builder()
												 .userId(prin.getUserId())
												 .isActive("N")
												 .build();
		
		chatService.updateChatRoomActive(dto);
    }
	
}
