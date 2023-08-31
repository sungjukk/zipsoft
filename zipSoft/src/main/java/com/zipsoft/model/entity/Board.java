package com.zipsoft.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "TB_BOARD")
public class Board extends Base {
	
	@Column(nullable = false, length = 400)
	private String subject;
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	@Column
	private int viewCnt;
	
	@Column
	private long regId;
	
	@Column
	private long updateId;
	
	@OneToMany(mappedBy = "board")
	private List<BoardFile> boardFiles;
}
