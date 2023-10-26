package com.zipsoft.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.chat.dto.ChatMessageDto;
import com.zipsoft.chat.dto.ChatRoomDetailDto;
import com.zipsoft.chat.dto.ChatRoomDto;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
import com.zipsoft.commons.utils.CustomPageRequest;
import com.zipsoft.model.entity.ChatRoom;
import com.zipsoft.model.entity.ChatRoomMember;
import com.zipsoft.model.mongo.ChatMessage;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {
	
	private final ChatRepository chatRepository;
	
	private final ChatMsgRepository chatMsgRepository;
	
	private final ChatRoomService chatRoomService;
	
	@Override
	public List<ChatRoomDto> list(long userId) {
		List<ChatRoomDto> list = chatRepository.list(userId);
		
		for (ChatRoomDto dto : list) {
			ChatMessage msg = chatMsgRepository.findFirstByChatId(dto.getId());
			dto.setLastMessage(msg.getMessage());
			dto.setSendDate(msg.getSendDate());
			
			List<ChatRoomMemberDto> memberList = chatRoomService.getChatRoomMember(dto.getId());
			
			if (memberList == null) {
				memberList = chatRepository.getChatRoomMemberList(dto.getId(), null);				
			} else { 
				int cnt = memberList.stream().filter(m -> m.getUserId() == userId).map(m -> m.getNoReadCnt()).findFirst().orElse(0);
				dto.setNoReadCnt(cnt);				
			}
			
			dto.setMemberList(memberList);
			dto.setTitle(userId);
		}
		
		return list;
	}
	
	@Override
	public List<ChatRoomMemberDto> getChatRoomMemberList(String chatId, String isActive) {
		return chatRepository.getChatRoomMemberList(chatId, isActive);
	}
	
	@Override
	public void updateChatRoomActive(ChatRoomMemberDto dto) {
		chatRepository.updateEnterChatRoom(dto);
	}

	@Override
	public void updateChatRoomNoReadCnt(String chatId) {
		chatRepository.updateChatRoomNoReadCnt(chatId);
	}

	@Override
	public void insertChatMessage(ChatMessageDto dto) {
		chatMsgRepository.save(dto.convertEntity());
	}

	@Override
	public ChatRoomDetailDto detail(String chatId, long userId) {
		
		ChatRoomDto dto = chatRepository.getChatRoom(chatId);		
		if (dto != null) dto.setMemberList(chatRepository.getChatRoomMemberList(chatId, null));
		dto.setTitle(userId);
		
		return ChatRoomDetailDto.builder().room(dto).list(this.getchatMessageList(chatId, userId, 0)).build();
	}

	@Override
	public List<ChatMessageDto> getchatMessageList(String chatId, long userId, int page) {
		int size = 200;
		CustomPageRequest pagenation = new CustomPageRequest();
		pagenation.setPage(page);
		pagenation.setSize(size);
		List<ChatMessage> messageList = chatMsgRepository.findByChatIdOrderBySendDateDesc(chatId, pagenation.of());
		List<ChatMessageDto> msgDtoList = new ArrayList<ChatMessageDto>();
		List<ChatRoomMemberDto> memberList = chatRoomService.getChatRoomMember(chatId);
		if (memberList == null) {
			memberList = chatRepository.getChatRoomMemberList(chatId, null);
		}
		
		for (int i = 0; i < messageList.size(); i++) {
			int cnt = i + (page * size);
			String userName = "";
			ChatMessage msg = messageList.get(i);
			
			Long readCnt = memberList.stream().filter(m -> m.getUserId() != userId && m.getNoReadCnt() - cnt > 0).count();
			
			ChatMessageDto dto = new ChatMessageDto(msg, userName, readCnt.intValue());
			msgDtoList.add(dto);
			
		}
		
		
		return msgDtoList;
	}

	@Override
	@Transactional
	public String ChatRoomInsert(ChatRoomDto chatRoom, long userId) {
		
		if (chatRoom.getMemberList() != null && chatRoom.getMemberList().size() > 0) {
			List<Long> ids = chatRoom.getMemberList().stream().map(m -> m.getUserId()).collect(Collectors.toList());
			
			String chatId = chatRepository.chatRoomDupCheck(ids);
			if (!"".equals(chatId) && chatId != null) return chatId;
			
			ChatRoom room = ChatRoom.builder().regId(userId).updateId(userId).title(chatRoom.getTitle()).build();
			room = chatRepository.insertChatRoom(room);
			
			for (ChatRoomMemberDto dto : chatRoom.getMemberList()) {
				this.initChatMember(dto, room);
			}
			
			return room.getId();
			
		} else {
			return null;
		}
		
		
	}
	
	private void initChatMember(ChatRoomMemberDto dto, ChatRoom room) {
		dto.setChatId(room.getId());
		chatRepository.insertChatRoomMember(dto.convertEntity());
	}

}
