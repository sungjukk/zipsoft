package com.zipsoft.chat;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
import com.zipsoft.model.entity.QChatRoomMember;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	private final EntityManager entityManager;
	
	private QChatRoomMember chatRoomMember = QChatRoomMember.chatRoomMember;
	
	@Transactional
	public void updateEnterChatRoom(ChatRoomMemberDto dto) {
		
		
		
		BooleanBuilder builder = new BooleanBuilder();
		
		builder.and(chatRoomMember.userId.eq(dto.getUserId()));
		
		if (dto.getChatId() != null && !"".equals(dto.getChatId())) {
			builder.and(chatRoomMember.chatRoom.id.eq(dto.getChatId()));
		}
		
		
		jpaQueryFactory.update(chatRoomMember)
					   .set(chatRoomMember.isActive, dto.getIsActive())
					   .set(chatRoomMember.noReadCnt, 0)
					   .where(builder)
					   .execute();
	}
	
	public List<ChatRoomMemberDto> getChatRoomMemberList(String chatId, String isActive) {
		return jpaQueryFactory.select(Projections.fields( ChatRoomMemberDto.class, 
														  chatRoomMember.id
														  ,ExpressionUtils.as(chatRoomMember.chatRoom.id, "chatId")
														  ,chatRoomMember.noReadCnt
														  ,chatRoomMember.isActive
														  ,chatRoomMember.userId))
				              .from(chatRoomMember)
				              .where(chatRoomMember.chatRoom.id.eq(chatId).and(chatRoomMember.isActive.eq(isActive)))
				              .fetch();
	}
	
	@Transactional
	public void updateChatRoomNoReadCnt(String id) {
		jpaQueryFactory.update(chatRoomMember)
					   .set(chatRoomMember.noReadCnt, chatRoomMember.noReadCnt.add(1))
					   .where(chatRoomMember.chatRoom.id.eq(id)
							   .and(chatRoomMember.isActive.eq("N"))
							 )
					   .execute();
	}

	
}
