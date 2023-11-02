package com.zipsoft.auth;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.model.entity.QUser;
import com.zipsoft.model.entity.QUserThumbnail;
import com.zipsoft.model.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	private final EntityManager entityManager;
	
	QUser user = QUser.user;
	QUserThumbnail userThumbnail = QUserThumbnail.userThumbnail;
	
	public Optional<UserDto> findByUserId(String userId) {
		
		return Optional.ofNullable(jpaQueryFactory
									.select(Projections.fields(UserDto.class, user.id, user.userId, user.userName, user.email, ExpressionUtils.as(userThumbnail.id, "thumbId")))
						            .from(user)
						            .leftJoin(userThumbnail)
						            .on(user.id.eq(userThumbnail.user.id), userThumbnail.useYn.eq("Y"))
						            .where(user.userId.eq(userId))
						            .fetchOne());
		
	}
	
	public Optional<UserDto> findById(long id) {
		
		return Optional.ofNullable(jpaQueryFactory
									.select(Projections.fields(UserDto.class, user.id, user.userId, user.userName, user.email, ExpressionUtils.as(userThumbnail.id, "thumbId")))
						            .from(user)
						            .leftJoin(userThumbnail)
						            .on(user.id.eq(userThumbnail.user.id), userThumbnail.useYn.eq("Y"))
						            .where(user.id.eq(id))
						            .fetchOne());
		
	}
	
	@Transactional
	public long insert(User user) {
		entityManager.persist(user);
		entityManager.flush();
		return user.getId();
	}
	
	@Transactional
	public void updateDeviceToken(long id, String deviceToken) {
		jpaQueryFactory.update(user).set(user.deviceToken, "").where(user.deviceToken.eq(deviceToken)).execute();
		jpaQueryFactory.update(user).set(user.deviceToken, deviceToken).where(user.id.eq(id)).execute();
	}
	
	public void removeDeviceToken(long id) {
		jpaQueryFactory.update(user).set(user.deviceToken, "").where(user.id.eq(id)).execute();
	}
	
}
