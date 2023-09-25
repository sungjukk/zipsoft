package com.zipsoft.chat;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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

import com.zipsoft.chat.dto.ChatMessageDto;
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
	
	private final ChatRoomService chatRoomService;
	
	private final ChatRepository chatRepository;
	
	@MessageMapping("/chat/send")
	public void enterUser(@Payload ChatMessageDto dto, Message<?> message) {
		
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) SimpMessageHeaderAccessor.getUser(message.getHeaders());
		UserPrincipal prin = (UserPrincipal) upat.getPrincipal();
		List<ChatRoomMemberDto> mberList = chatRoomService.getChatRoomMember(dto.getId());
		
		if (ChatMessageDto.MessageType.ENTER.toString().equals(dto.getType())) {
			Optional<ChatRoomMemberDto> mberOpt = mberList.stream().filter(l -> l.getUserId() == prin.getUserId() && "Y".equals(l.getIsFirst())).findFirst();
			
			if (!mberOpt.isEmpty()) {
				ChatRoomMemberDto mber = mberOpt.get();
				mber.setIsFirst("N");
				chatRepository.updateEnterChatRoom(mber);
				chatRoomService.setChatRoomMember(dto.getId(), mberList);
				ChatMessageDto msgDto = ChatMessageDto.builder().id(dto.getId()).message(mber.getUserName() + "님이 입장하였습니다.").build();
				chatService.insertChatMessage(msgDto);
				template.convertAndSend("/topic/chat/" + dto.getId(), msgDto);
			}			
			
			return;
		}
		
		
		dto.setUserId(prin.getUserId());
		chatService.insertChatMessage(dto);
		
		if (mberList != null && mberList.size() > 0) {
			
			for (ChatRoomMemberDto mber : mberList) {
				if ("N".equals(mber.getIsActive())) {
					mber.setNoReadCnt(mber.getNoReadCnt() + 1);
					template.convertAndSend("/topic/" + mber.getUserId(), dto);					
				}
			}
			
			chatRoomService.setChatRoomMember(dto.getId(), mberList);
		}
		
		
		
		template.convertAndSend("/topic/chat/" + dto.getId(), dto);
	}
	
	
}
