package com.zipsoft.chat.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.zipsoft.board.dto.BoardFileDto;
import com.zipsoft.model.mongo.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
	
	public enum MessageType {
        ENTER, TALK
    }
	
	private String type;	//타입
	private String id;		//채팅방
	private String message;	// 메세지
	private long userId;	//보내는ID
	private String userName;//보내는이름
	private String sendDate;  //보낸날짜
	
	public ChatMessage convertEntity() {
		if (this.sendDate == null || "".equals(this.sendDate)) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
			this.sendDate = now.format(dtf);
		}
		
		ChatMessage chat = ChatMessage.builder().chatId(this.id)
												.message(this.message)
												.userId(this.userId)
												.sendDate(this.sendDate)
												.build();
		
		return chat;
	}
	
}
