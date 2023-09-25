package com.zipsoft.chat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zipsoft.board.BoardMapper;
import com.zipsoft.board.BoardRepository;
import com.zipsoft.board.BoardServiceImpl;
import com.zipsoft.chat.dto.ChatMessageDto;
import com.zipsoft.chat.dto.ChatRoomDetailDto;
import com.zipsoft.chat.dto.ChatRoomDto;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
import com.zipsoft.commons.utils.CustomPageRequest;
import com.zipsoft.model.mongo.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {
	
	private final ChatRepository chatRepository;
	
	private final ChatMsgRepository chatMsgRepository;
	
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
	public ChatRoomDetailDto detail(String chatId) {
		
		ChatRoomDto dto = chatRepository.getChatRoom(chatId);
		if (dto != null) dto.setMemberList(chatRepository.getChatRoomMemberList(chatId, null));
		
		CustomPageRequest page = new CustomPageRequest();
		page.setPage(0);
		page.setSize(200);
		
		List<ChatMessage> messageList = chatMsgRepository.findByChatIdOrderBySendDateDesc(chatId, page.of());
		
		return ChatRoomDetailDto.builder().room(dto).list(messageList).build();
	}

	@Override
	public List<ChatMessage> getchatMessageList(String chatId) {
		CustomPageRequest page = new CustomPageRequest();
		page.setPage(0);
		page.setSize(200);
		List<ChatMessage> messageList = chatMsgRepository.findByChatIdOrderBySendDateDesc(chatId, page.of());
		
		
		return null;
	}


}
