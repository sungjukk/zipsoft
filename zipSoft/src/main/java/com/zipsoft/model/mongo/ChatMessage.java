package com.zipsoft.model.mongo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "CHAT_MESSAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
	
	@Id
	private String id;
	private String chatId;
	private long userId;
	private String message;
	private String sendDate;
	
	
}
