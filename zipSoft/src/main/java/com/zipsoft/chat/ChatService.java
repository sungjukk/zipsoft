package com.zipsoft.chat;

import java.util.List;

import com.zipsoft.chat.dto.ChatRoomMemberDto;

public interface ChatService {
	
	public List<ChatRoomMemberDto> getChatRoomMemberList(String chatId, String isActive);
	
	public void updateChatRoomActive(ChatRoomMemberDto dto);
	
	public void updateChatRoomNoReadCnt(String chatId);
	
}
