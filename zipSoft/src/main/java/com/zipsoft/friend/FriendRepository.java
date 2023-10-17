package com.zipsoft.friend;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipsoft.friend.dto.FriendDto;
import com.zipsoft.model.entity.QFriends;
import com.zipsoft.model.entity.QUser;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FriendRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	private final EntityManager entityManager;
	
	QFriends friends = QFriends.friends;
	QUser user = QUser.user;
	
	public List<FriendDto> list(long userId) {
		List<FriendDto> list = jpaQueryFactory.select(Projections.fields(FriendDto.class
																		 ,friends.id
																		 ,friends.acceptYn
																		 ,ExpressionUtils.as(friends.user.id,"userId")
																		 ,ExpressionUtils.as(friends.friendUser.id,"friendId")
																		 ,ExpressionUtils.as(user.userName,"friendName")
											   ))
											  .from(friends)
											  .innerJoin(user)
											  .on(friends.friendUser.id.eq(user.id))
											  .where(friends.user.id.eq(userId))
											  .orderBy(user.userName.asc())
											  .fetch();
		
		return list;
	}
	
	
}
