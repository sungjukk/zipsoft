package com.zipsoft.chat;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.zipsoft.chat.dto.ChatMessageDto;
import com.zipsoft.chat.dto.ChatRoomDto;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
import com.zipsoft.commons.security.UserPrincipal;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
	
	private final RedisTemplate<String, Object> redisTemplate;
	
	private HashOperations<String, String, ChatRoomDto> opsHashChatRoom;
	
	private HashOperations<String, Long, String> opsHashChatRoomUser;
	
	private static final String CHAT_ROOM = "CHAT_ROOM"; 
	
	private static final String ENTER_CHAT_ROOM = "ENTER_CHAT_ROOM"; 
	
	private final ChatRepository chatRepository;
	
	private final ChatMsgRepository chatMsgRepository;
	
	@PostConstruct
	private void init() {
		opsHashChatRoom = redisTemplate.opsForHash();
		opsHashChatRoomUser = redisTemplate.opsForHash();
	}
	
	public List<ChatRoomMemberDto> getChatRoomMember(String chatId) {
		ChatRoomDto room = opsHashChatRoom.get(CHAT_ROOM, chatId);
		return room.getMemberList();
	}
	
	public void setChatRoomMember(String chatId, List<ChatRoomMemberDto> list) {
		ChatRoomDto room = opsHashChatRoom.get(CHAT_ROOM, chatId);
		room.setMemberList(list);
		opsHashChatRoom.put(CHAT_ROOM, chatId, room);
	}
	
	public void enterChatRoom(String chatId, long userId) {
		ChatRoomDto room = opsHashChatRoom.get(CHAT_ROOM, chatId);
		if (room == null) {
			room = chatRepository.getChatRoom(chatId);
			if (room != null) room.setMemberList(chatRepository.getChatRoomMemberList(chatId, null));
		}
		
		for (ChatRoomMemberDto d : room.getMemberList()) {
			if (d.getUserId() == userId) {
				d.setNoReadCnt(0);
				d.setIsActive("Y");
				break;
			}
		}
		
		opsHashChatRoomUser.put(ENTER_CHAT_ROOM, userId, chatId);
		opsHashChatRoom.put(CHAT_ROOM, room.getId(), room);
		
	}
	
	public void leaveChatRoom(Message<?> message) {
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) SimpMessageHeaderAccessor.getUser(message.getHeaders());
		UserPrincipal prin = (UserPrincipal) upat.getPrincipal();
		
		long userId = prin.getUserId();
		
		String chatId = opsHashChatRoomUser.get(ENTER_CHAT_ROOM, userId);
		
		if ("".equals(chatId) || chatId == null) return;
		
		ChatRoomDto room = opsHashChatRoom.get(CHAT_ROOM, chatId);
		
		if (room != null) {
			for (ChatRoomMemberDto d : room.getMemberList()) {
				if (d.getUserId() == userId) {
					d.setNoReadCnt(0);
					d.setIsActive("N");
				}
			}
			
			long cnt = room.getMemberList().stream().filter(r -> "Y".equals(r.getIsActive())).count();
			if (cnt == 0) {
				chatRepository.updateMemberList(room.getMemberList());
				opsHashChatRoom.delete(CHAT_ROOM, chatId);
				
			} else {
				this.setChatRoomMember(chatId, room.getMemberList());
			}
		}
		
		opsHashChatRoomUser.delete(ENTER_CHAT_ROOM, userId);
		
		
	}
	
	
}
