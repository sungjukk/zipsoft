package com.zipsoft.chat.dto;

import com.zipsoft.model.entity.ChatRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomMemberDto {
	
	private long id;
	
	private String chatId;
	
	private long userId;
	
	private int noReadCnt;
	
	private String isActive;
	
}
