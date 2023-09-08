package com.zipsoft.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	
	private int no;
	private long id;
	private String subject;
	private String content;
	private String userName;
	private int viewCnt;
	private long regId;
	private long updateId;
	private long fileCnt;
	private long commentCnt;
	private LocalDateTime regDt;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime updateDt;
	
	private List<MultipartFile> files;
	
	private List<BoardFileDto> fileList;
}
