package com.zipsoft.board;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zipsoft.auth.AuthMapper;
import com.zipsoft.auth.AuthServiceImpl;
import com.zipsoft.board.dto.BoardCommentDto;
import com.zipsoft.board.dto.BoardCommentInterface;
import com.zipsoft.board.dto.BoardDto;
import com.zipsoft.board.dto.BoardFileDto;
import com.zipsoft.board.dto.SearchDto;
import com.zipsoft.commons.utils.FileUtil;
import com.zipsoft.enums.FilePath;
import com.zipsoft.model.FileDto;
import com.zipsoft.model.entity.Board;
import com.zipsoft.model.entity.BoardComment;
import com.zipsoft.model.entity.BoardFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepository;
	
	private final BoardMapper boardMapper;
	
	@Override
	public Page<BoardDto> getBoardList(SearchDto search) {
		return boardRepository.list(search);
	}
	
	@Override
	public void insertBoard(BoardDto board) {
		Board b = Board.builder().subject(board.getSubject())
								 .content(board.getContent())
								 .regId(board.getRegId())
								 .updateId(board.getRegId())
								 .build();
		
		if (board.getFiles() != null && board.getFiles().size() > 0) {
			List<BoardFile> list = new ArrayList<BoardFile>();
			for (MultipartFile m : board.getFiles()) {
				
				FileDto file = FileUtil.upload(m, FilePath.BOARD);
				if (file != null) {
					BoardFile boardFile = new BoardFile(file);
					boardFile.setRegId(board.getRegId());
					boardFile.setUpdateId(board.getRegId());
					list.add(boardFile);
				}
				
			}
			
			b.setBoardFiles(list);
		}
		
		
		boardRepository.insert(b);
	}

	@Override
	public BoardDto detail(long id) {
		boardRepository.updateBoardView(id);
		BoardDto dto = boardRepository.detail(id);
		if (dto != null) dto.setFileList(boardRepository.detailFileList(id));
		
		return dto;
	}

	@Override
	public BoardFileDto detailFile(long id) {
		return boardRepository.detailFile(id);
	}

	@Override
	public void insertBoardComment(BoardCommentDto dto) {
		BoardComment bc = BoardComment.builder().board(Board.builder().id(dto.getBoardId()).build())
												.comment(dto.getComment())
												.regId(dto.getRegId())
												.updateId(dto.getRegId())
												.parentId(dto.getParentId())
												.build();
		
		boardRepository.insertBoardComment(bc);
		
	}

	@Override
	public List<BoardCommentDto> commentList(long id) {
		return boardMapper.getCommentList(id);
	}

}
