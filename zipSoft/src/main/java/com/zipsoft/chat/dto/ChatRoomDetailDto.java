package com.zipsoft.chat.dto;

import java.util.List;

import com.zipsoft.model.mongo.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDetailDto {
	
	private ChatRoomDto room;
	private List<ChatMessageDto> list;
	
}
