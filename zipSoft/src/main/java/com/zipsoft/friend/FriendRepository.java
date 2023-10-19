package com.zipsoft.friend;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipsoft.friend.dto.FriendDto;
import com.zipsoft.friend.dto.ProfileDto;
import com.zipsoft.model.entity.Friends;
import com.zipsoft.model.entity.QFriends;
import com.zipsoft.model.entity.QUser;
import com.zipsoft.model.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
	
	public ProfileDto searchFriend(long userId, long searchId) {
		ProfileDto dto = jpaQueryFactory.select(Projections.fields(ProfileDto.class
																   ,user.id
																   ,user.userName
																   ,user.email
																   ,user.userId
																   ,ExpressionUtils.as( new CaseBuilder().when(
																		   JPAExpressions.select(friends.id)
																		   .from(friends)
																		   .where(friends.friendUser.id.eq(user.id), friends.user.id.eq(userId))
																		   .gt((long) 0)
																		   ).then(true).otherwise(false) , "isFriendReq")
										 ))
										 .from(user)
										 .where(user.id.eq(searchId))
										 .fetchOne();
								
		
		return dto;
	}
	
	@Transactional
	public void addFriend(long userId, long friendId) {
		User my = User.builder().id(userId).build();
		User friendUser = User.builder().id(friendId).build();
		
		Friends friendEntity = Friends.builder().user(my).friendUser(friendUser).acceptYn("Y").regId(userId).updateId(userId).build();
		entityManager.persist(friendEntity);
		entityManager.flush();
	}
}
