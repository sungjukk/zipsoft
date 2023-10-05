package com.zipsoft.friend.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.zipsoft.board.dto.BoardFileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {
	
	private long id;
	private long userId;
	private String userName;
	private long friendId;
	private String friendName;
	private String comment;
	private String acceptYn;
	
}
