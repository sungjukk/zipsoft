package com.zipsoft.chat;

import java.util.List;

import com.zipsoft.chat.dto.ChatMessageDto;
import com.zipsoft.chat.dto.ChatRoomDetailDto;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
import com.zipsoft.model.mongo.ChatMessage;

public interface ChatService {
	
	public List<ChatRoomMemberDto> getChatRoomMemberList(String chatId, String isActive);
	
	public void updateChatRoomActive(ChatRoomMemberDto dto);
	
	public void updateChatRoomNoReadCnt(String chatId);
	
	public void insertChatMessage(ChatMessageDto dto);
	
	public ChatRoomDetailDto detail(String chatId);
	
	public List<ChatMessage> getchatMessageList(String chatId);
	
}
