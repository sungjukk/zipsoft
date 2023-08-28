package com.zipsoft.model.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
@Table(name="TB_USER")
public class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(length = 1)
    private String useYn;
	
	@Column 
    private long regId; // reg_id 등록자 아이디 VARCHAR(24) NOT NULL
	
	@Column(name="reg_dt", nullable = false, updatable = false)
	@CreationTimestamp 
	private LocalDateTime regDt; // reg_dt 등록일시 일시 DATETIME NOT NULL
	
	@Column 
    private long updateId; // reg_id 등록자 아이디 VARCHAR(24) NOT NULL
	
	@Column(name="update_dt", nullable = false, updatable = false)
	@CreationTimestamp 
	private LocalDateTime updateDt; // reg_dt 등록일시 일시 DATETIME NOT NULL
	
	
	@PrePersist
    public void prePersist() {
		this.regId = this.id;
        this.regDt = Optional.ofNullable(this.regDt).orElse(LocalDateTime.now());
        this.updateDt = Optional.ofNullable(this.updateDt).orElse(LocalDateTime.now());
        this.useYn = Optional.ofNullable(this.useYn).orElse("Y");
    }
}
