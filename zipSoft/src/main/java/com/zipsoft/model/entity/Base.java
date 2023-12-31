package com.zipsoft.model.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Base {
	
	@Column(name="reg_dt", nullable = false, updatable = false)
	@CreationTimestamp 
	private LocalDateTime regDt; // reg_dt 등록일시 일시 DATETIME NOT NULL
	
	
	@Column(name="update_dt", nullable = false, updatable = false)
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
