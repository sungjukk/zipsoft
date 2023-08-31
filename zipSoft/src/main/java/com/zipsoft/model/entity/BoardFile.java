package com.zipsoft.model.entity;

import com.zipsoft.model.FileDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name="TB_BOARD_FILE")
public class BoardFile extends Base {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String ext;
	
	@Column(nullable = false)
	private int fileSize;
	
	@Column(nullable = false)
	private String orgFileName;
	
	@Column(nullable = false)
	private String fileName;
	
	@Column
	private long regId;
	
	@Column
	private long updateId;
	
	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;
	
	public BoardFile(FileDto dto) {
		this.ext = dto.getExt();
		this.fileSize = dto.getSize();
		this.orgFileName = dto.getOrgFileName();
		this.fileName = dto.getFileName();
	}
}
