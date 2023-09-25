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
	
	public String getTitle() {
		
		if (!"".equals(this.title) && this.title != null) return this.title;
		if (this.memberList == null) return this.title;
		if (this.memberList.size() <=0 || this.memberList.isEmpty()) return this.title;
		
		String title = "";
		for (int i = 0; i < this.memberList.size(); i++) {
			ChatRoomMemberDto dto = this.memberList.get(i);
			
			if (i >= 3) {
				title += "외 " +  (this.memberList.size() - (i + 1)) + "명";
				break;
			} else if (i == 0) {
				title = dto.getUserName();
			} else {
				title += "," + dto.getUserName();
			}
			
		}
		
		return title;
	}
	
}
