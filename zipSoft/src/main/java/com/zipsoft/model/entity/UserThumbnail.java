package com.zipsoft.model.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import com.zipsoft.model.FileDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="TB_USER_THUMBNAIL")
public class UserThumbnail extends Base {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private int seq;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(nullable = false)
	private String ext;
	
	@Column(nullable = false)
	private int fileSize;
	
	@Column(nullable = false)
	private String orgFileName;
	
	@Column(nullable = false)
	private String fileName;
	
	@Column(nullable = false)
	private String thumbFileName;
	
	@Column(nullable = false)
	private int thumbFileSize;
	
	@Column(length = 1)
    private String useYn;
	
	public static UserThumbnail of(FileDto file, FileDto thumbFile, long userId) {
		return UserThumbnail.builder()
						    .ext(file.getExt())
						    .fileName(file.getFileName())
						    .orgFileName(file.getOrgFileName())
						    .fileSize(file.getSize())
						    .thumbFileName(thumbFile.getFileName())
						    .thumbFileSize(thumbFile.getSize())
						    .useYn("Y")
						    .user(User.builder().id(userId).build())
						    .build();
	}
}
