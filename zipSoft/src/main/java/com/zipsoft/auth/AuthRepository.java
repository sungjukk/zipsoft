package com.zipsoft.auth;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.model.entity.QUser;
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
	
	public Optional<UserDto> findByUserId(String userId) {
		
		return Optional.ofNullable(jpaQueryFactory
									.select(Projections.fields(UserDto.class, user.id, user.userId, user.userName, user.email))
						            .from(user)
						            .where(user.userId.eq(userId))
						            .fetchOne());
		
	}
	
	public Optional<UserDto> findById(long id) {
		
		return Optional.ofNullable(jpaQueryFactory
									.select(Projections.fields(UserDto.class, user.id, user.userId, user.userName, user.email))
						            .from(user)
						            .where(user.id.eq(id))
						            .fetchOne());
		
	}
	
	@Transactional
	public long insert(User user) {
		entityManager.persist(user);
		entityManager.flush();
		return user.getId();
	}
	
}
