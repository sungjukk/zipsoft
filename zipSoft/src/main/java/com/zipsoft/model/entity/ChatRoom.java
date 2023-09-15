package com.zipsoft.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Table(name = "TB_CHAT_ROOM")
public class ChatRoom extends Base {
	
	@Id
	@Column(name="id")
	private String id;
	
	private String title;
	
	private long regId;
	
	private long updateId;
	
	@OneToMany(mappedBy = "chatRoom")
	private List<ChatRoomMember> chatRoomMember;
	
	@PrePersist
	public void prePersist() {
		this.id = Optional.ofNullable(this.id).orElse(UUID.randomUUID().toString());
	}
	
}
