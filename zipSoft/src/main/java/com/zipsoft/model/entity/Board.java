package com.zipsoft.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
