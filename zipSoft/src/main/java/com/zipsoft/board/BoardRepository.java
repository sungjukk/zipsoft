package com.zipsoft.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zipsoft.board.dto.BoardCommentDto;
import com.zipsoft.board.dto.BoardCommentInterface;
import com.zipsoft.board.dto.BoardDto;
import com.zipsoft.board.dto.BoardFileDto;
import com.zipsoft.board.dto.SearchDto;
import com.zipsoft.model.entity.Board;
import com.zipsoft.model.entity.BoardComment;
import com.zipsoft.model.entity.BoardFile;
import com.zipsoft.model.entity.QBoard;
import com.zipsoft.model.entity.QBoardComment;
import com.zipsoft.model.entity.QBoardFile;
import com.zipsoft.model.entity.QUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	private final EntityManager entityManager;
	
	QBoard board = QBoard.board;
	QBoardFile boardFile = QBoardFile.boardFile;
	QUser user = QUser.user;
	QBoardComment boardComment = QBoardComment.boardComment;
	
	public Page<BoardDto> list(SearchDto search) {
		
		Pageable page = search.of();
		
		List<BoardDto> list = jpaQueryFactory.select(Projections.fields(BoardDto.class, 
							                     board.id,
							                     board.subject,
							                     board.content,
							                     user.userName,
							                     board.viewCnt,
							                     board.updateDt,
							                     ExpressionUtils.as(JPAExpressions.select(boardFile.id.count()).from(boardFile).where(boardFile.board.id.eq(board.id)), "fileCnt"),
							                     ExpressionUtils.as(JPAExpressions.select(boardComment.id.count()).from(boardComment).where(boardComment.board.id.eq(board.id)), "commentCnt")
							                     )
									              )
							               .from(board)
							               .innerJoin(user).on(board.regId.eq(user.id))
							               .orderBy(board.updateDt.desc())
							               .offset(page.getOffset())
							               .limit(page.getPageSize())
							               .fetch();
		
		/* .fetchCount를 없애고 JpaQuery로 남겨둠 */
	    JPAQuery<Board> countQuery = jpaQueryFactory.selectFrom(board)
	    		                                       .innerJoin(user).on(board.regId.eq(user.id));
	    
	    return PageableExecutionUtils.getPage(list, page, () -> countQuery.fetchCount());
		               
	}
	
	@Transactional
	public void insert(Board b) {
		entityManager.persist(b);
		
		if (b.getBoardFiles() != null && b.getBoardFiles().size() > 0) {
			for (BoardFile bf : b.getBoardFiles()) {
				bf.setBoard(b);
				entityManager.persist(bf);
			}
		}
		
		entityManager.flush();
	}
	
	public BoardDto detail(long id) {
		
		return jpaQueryFactory.select(Projections.fields(BoardDto.class, 
							                             board.id
							                             ,board.subject
							                             ,board.content
							                             ,user.userName
							                             ,board.viewCnt
							                             ,board.updateDt
							                             ,board.regId))
							   .from(board)
							   .innerJoin(user).on(board.regId.eq(user.id))
							   .where(board.id.eq(id))
							   .fetchOne();
	}
	
	public List<BoardFileDto> detailFileList(long boardId) {
		
		return jpaQueryFactory.select(Projections.fields(BoardFileDto.class, 
												 		 boardFile.id
												 		 ,boardFile.fileName
												 		 ,boardFile.orgFileName
												 		 ,boardFile.ext
												 		 ,boardFile.fileSize))
					                             .from(boardFile)
					                             .where(boardFile.board.id.eq(boardId))
					                             .fetch();
		
	}
	
	public BoardFileDto detailFile(long id) {
		return jpaQueryFactory.select(Projections.fields(BoardFileDto.class, 
												 		 boardFile.id
												 		 ,boardFile.fileName
												 		 ,boardFile.orgFileName
												 		 ,boardFile.ext
												 		 ,boardFile.fileSize))
										        .from(boardFile)
										        .where(boardFile.id.eq(id))
										        .fetchOne();
	}
	
	@Transactional
	public long deleteFile(long id) {
		return jpaQueryFactory.delete(boardFile).where(boardFile.id.eq(id)).execute();
	}
	
	
	@Transactional
	public void insertBoardComment(BoardComment bc) {
		entityManager.persist(bc);
		entityManager.flush();
	}
	
	@Transactional
	public void updateBoardView(long id) {
		jpaQueryFactory.update(board).set(board.viewCnt, board.viewCnt.add(1)).where(board.id.eq(id)).execute();
	}
	
	@Transactional
	public void editBoard(Board b) {
		
		entityManager.merge(b);
		
		if (b.getBoardFiles() != null && b.getBoardFiles().size() > 0) {
			for (BoardFile bf : b.getBoardFiles()) {
				bf.setBoard(b);
				entityManager.persist(bf);
			}
		}
		
		entityManager.flush();
	}
}
