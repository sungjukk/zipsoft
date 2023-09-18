package com.zipsoft.chat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zipsoft.board.BoardMapper;
import com.zipsoft.board.BoardRepository;
import com.zipsoft.board.BoardServiceImpl;
import com.zipsoft.chat.dto.ChatRoomMemberDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {
	
	private final ChatRepository chatRepository;
	
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


}
