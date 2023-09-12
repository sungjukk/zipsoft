package com.zipsoft.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_BOARD")
public class Board {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 400)
	private String subject;
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	@Column(updatable = false)
	private int viewCnt;
	
	@Column(updatable = false)
	private long regId;
	
	@Column
	private long updateId;
	
	@OneToMany(mappedBy = "board")
	private List<BoardFile> boardFiles;
	
	@OneToMany(mappedBy = "board")
	private List<BoardComment> boardComments;
	
	@Column(name="reg_dt", nullable = false, updatable = false)
	@CreationTimestamp 
	private LocalDateTime regDt; // reg_dt 등록일시 일시 DATETIME NOT NULL
	
	
	@Column(name="update_dt", nullable = false)
	@CreationTimestamp 
	private LocalDateTime updateDt; // reg_dt 등록일시 일시 DATETIME NOT NULL
	
	@PrePersist
    public void prePersist() {
        this.regDt = Optional.ofNullable(this.regDt).orElse(LocalDateTime.now());
        this.updateDt = Optional.ofNullable(this.updateDt).orElse(LocalDateTime.now());
    }
	
	@PreUpdate
    public void preUpdate() {
        this.updateDt = LocalDateTime.now();
    }
}
