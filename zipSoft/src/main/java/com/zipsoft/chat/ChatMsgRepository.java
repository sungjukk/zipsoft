package com.zipsoft.chat;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zipsoft.model.mongo.ChatMessage;

@Repository
public interface ChatMsgRepository extends MongoRepository<ChatMessage, String> {
	
	List<ChatMessage> findByChatIdOrderBySendDateDesc(String chatId, Pageable page);
	
}
