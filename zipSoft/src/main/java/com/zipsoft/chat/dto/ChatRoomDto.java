package com.zipsoft.chat.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto implements Serializable {
	
	private String id;
	private String title;
	private String lastMessage;
	private int noReadCnt;
	private List<ChatRoomMemberDto> memberList;
	
	public void setTitle(long userId) {
		if (!"".equals(this.title) && this.title != null) return;
		
		String title = "";
		if (this.memberList != null) {
			
			int i = 0;
			for (ChatRoomMemberDto dto : this.memberList) {
				
				if (dto.getUserId() == userId) continue;
				
				if (i >= 3) {
					title += "외 " +  (this.memberList.size() - (i + 1)) + "명";
					break;
				} else if (i == 0) {
					title = dto.getUserName();
				} else {
					title += "," + dto.getUserName();
				}
				i++;
			}
			
			this.title = title;
		}
		
	}
	
	
}
