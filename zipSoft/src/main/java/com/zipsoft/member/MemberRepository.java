package com.zipsoft.member;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipsoft.member.dto.MemberDto;
import com.zipsoft.member.dto.ThumbnailDto;
import com.zipsoft.model.entity.QUser;
import com.zipsoft.model.entity.QUserThumbnail;
import com.zipsoft.model.entity.User;
import com.zipsoft.model.entity.UserThumbnail;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	private final EntityManager entityManager;
	
	QUser user = QUser.user;
	QUserThumbnail userThumbnail = QUserThumbnail.userThumbnail;
	
	public void update(MemberDto member) {
		jpaQueryFactory.update(user)
					   .set(user.userName, member.getUserName())
					   .set(user.email, member.getEmail())
					   .where(user.id.eq(member.getId()))
					   .execute();
	}
	
	public void updateThumbnailNotUsed(long userId, long thumbId) {
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(userThumbnail.user.id.eq(userId));
		if (thumbId > 0) builder.and(userThumbnail.id.ne(thumbId));
		
		//builder.and(userThumbnail.seq.ne(JPAExpressions.select(userThumbnail.seq.max()).from(userThumbnail).where(userThumbnail.user.id.eq(userId))));
		
		jpaQueryFactory.update(userThumbnail)
					   .set(userThumbnail.useYn, "N")
					   .where(builder)
					   .execute();
	}
	
	public void insertThumbnail(UserThumbnail thumbnail) {
		
		int seq = jpaQueryFactory.select(userThumbnail.seq.max().coalesce(0))
								 .from(userThumbnail)
								 .where(userThumbnail.user.id.eq(thumbnail.getUser().getId()))
								 .fetchOne();
		
		thumbnail.setSeq(seq + 1);
		
		entityManager.persist(thumbnail);
		entityManager.flush();
	}
	
	public ThumbnailDto getThumbnail(long id) {
		return jpaQueryFactory.select(Projections.fields(ThumbnailDto.class, userThumbnail.id
																			 ,userThumbnail.fileName
																			 ,userThumbnail.thumbFileName))
				              .from(userThumbnail)
				              .where(userThumbnail.user.id.eq(id), userThumbnail.useYn.eq("Y"))
				              .fetchOne();
	}
	
}
