package com.zipsoft.chat;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipsoft.chat.dto.ChatRoomDto;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
import com.zipsoft.model.entity.ChatRoom;
import com.zipsoft.model.entity.ChatRoomMember;
import com.zipsoft.model.entity.QChatRoom;
import com.zipsoft.model.entity.QChatRoomMember;
import com.zipsoft.model.entity.QUser;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	private final EntityManager entityManager;
	
	private QChatRoom chatRoom = QChatRoom.chatRoom;
	private QChatRoomMember chatRoomMember = QChatRoomMember.chatRoomMember;
	private QUser user = QUser.user;
	
	@Transactional
	public void updateEnterChatRoom(ChatRoomMemberDto dto) {
		
		
		
		BooleanBuilder builder = new BooleanBuilder();
		
		builder.and(chatRoomMember.userId.eq(dto.getUserId()));
		
		if (dto.getChatId() != null && !"".equals(dto.getChatId())) {
			builder.and(chatRoomMember.chatRoom.id.eq(dto.getChatId()));
		}
		
		
		jpaQueryFactory.update(chatRoomMember)
					   .set(chatRoomMember.isFirst, dto.getIsFirst())
					   .set(chatRoomMember.noReadCnt, 0)
					   .where(builder)
					   .execute();
	}
	
	public List<ChatRoomMemberDto> getChatRoomMemberList(String chatId, String isActive) {
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(chatRoomMember.chatRoom.id.eq(chatId));
		if (isActive != null && !"".equals(isActive)) builder.and(chatRoomMember.isFirst.eq(isActive));
		
		return jpaQueryFactory.select(Projections.fields( ChatRoomMemberDto.class, 
														  chatRoomMember.id
														  ,ExpressionUtils.as(chatRoomMember.chatRoom.id, "chatId")
														  ,chatRoomMember.noReadCnt
														  ,user.userName
														  ,chatRoomMember.isFirst
														  ,chatRoomMember.userId
														  ,user.deviceToken))
				              .from(chatRoomMember)
				              .join(user).on(chatRoomMember.userId.eq(user.id))
				              .where(builder)
				              .fetch();
	}
	
	@Transactional
	public void updateChatRoomNoReadCnt(String id) {
		jpaQueryFactory.update(chatRoomMember)
					   .set(chatRoomMember.noReadCnt, chatRoomMember.noReadCnt.add(1))
					   .where(chatRoomMember.chatRoom.id.eq(id)
							   .and(chatRoomMember.isFirst.eq("N"))
							 )
					   .execute();
	}

	public ChatRoomDto getChatRoom(String chatId) {
		
		ChatRoomDto dto = jpaQueryFactory.select(Projections.fields(ChatRoomDto.class,
																	chatRoom.id
																	,chatRoom.title))
				                         .from(chatRoom)
				                         .where(chatRoom.id.eq(chatId))
				                         .fetchOne();
		
		return dto;
	}
	
	@Transactional
	public void updateMemberList(List<ChatRoomMemberDto> list) {
		for (ChatRoomMemberDto dto : list) {
			entityManager.merge(dto.convertEntity());
		}
		
		entityManager.flush();
	}
	
	public ChatRoom insertChatRoom(ChatRoom room) {
		entityManager.persist(room);
		entityManager.flush();
		return room;
	}
	
	public void insertChatRoomMember(ChatRoomMember member) {
		entityManager.persist(member);
		entityManager.flush();
	}
	
	public String chatRoomDupCheck(List<Long> ids) {
		
		List<String> chatIds =jpaQueryFactory.select(chatRoomMember.chatRoom.id)
											 .from(chatRoomMember)
											 .where(chatRoomMember.userId.in(ids))
											 .groupBy(chatRoomMember.chatRoom.id)
											 .having(chatRoomMember.chatRoom.id.count().eq((long) ids.size())).fetch();
		
		if (chatIds == null || chatIds.isEmpty()) return null;
		
		String chatId = jpaQueryFactory.select(chatRoomMember.chatRoom.id)
									   .from(chatRoomMember)
									   .where(chatRoomMember.chatRoom.id.in(chatIds))
									   .groupBy(chatRoomMember.chatRoom.id)
									   .having(chatRoomMember.chatRoom.id.count().eq((long) ids.size())).fetchOne();
		
		
		return chatId;
		
	}
	
	public List<ChatRoomDto> list(long userId) {
		List<ChatRoomDto> list = jpaQueryFactory.select(Projections.fields(ChatRoomDto.class
																			,chatRoom.id
																			,chatRoomMember.noReadCnt))
												.from(chatRoom)
												.innerJoin(chatRoomMember)
												.on(chatRoom.id.eq(chatRoomMember.chatRoom.id), chatRoomMember.userId.eq(userId))
												.fetch();
		
		
		return list;
	}
}
