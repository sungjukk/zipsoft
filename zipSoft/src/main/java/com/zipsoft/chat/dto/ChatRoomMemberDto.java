package com.zipsoft.chat.dto;

import java.io.Serializable;
import java.util.Optional;

import com.zipsoft.model.entity.ChatRoom;
import com.zipsoft.model.entity.ChatRoomMember;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomMemberDto implements Serializable {
	
	private long id;
	
	private String chatId;
	
	private long userId;
	
	private String userName;
	
	private int noReadCnt;
	
	private String isFirst;
	
	private String isActive = "N";
	
	public ChatRoomMember convertEntity() {
		
		if (this.chatId == null || "".equals(this.chatId)) return null;
		
		ChatRoom room = new ChatRoom();
		room.setId(this.chatId);
		
		this.isFirst = Optional.ofNullable(this.isFirst).orElse("Y");
		this.noReadCnt = Optional.ofNullable(this.noReadCnt).orElse(0);
		
		return ChatRoomMember.builder().id(this.id)
									   .chatRoom(room)
									   .userId(this.userId)
									   .isFirst(this.isFirst)
									   .noReadCnt(this.noReadCnt)
									   .build();
	}
	
	
}
