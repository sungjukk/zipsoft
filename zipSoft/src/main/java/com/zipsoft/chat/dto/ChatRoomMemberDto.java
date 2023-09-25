package com.zipsoft.chat.dto;

import java.io.Serializable;

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
	
	private String isActive;
	
}
