package com.zipsoft.chat.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.zipsoft.board.dto.BoardFileDto;

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
public class ChatDto {
	
	public enum MessageType {
        ENTER, TALK
    }
	
	private String type;	//타입
	private String id;		//채팅방
	private String message;	// 메세지
	private long userId;	//보내는사람
	
}
