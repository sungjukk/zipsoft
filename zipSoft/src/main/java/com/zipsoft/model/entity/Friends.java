package com.zipsoft.model.entity;

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
@Table(name="TB_FRIENDS")
public class Friends extends Base {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "friend_id")
	private User friendUser;
	
	@Column(columnDefinition = "varchar(1) default 'N'")
	private String acceptYn;
	
	@Column 
    private long regId; // reg_id 등록자 아이디 VARCHAR(24) NOT NULL
	
	@Column 
    private long updateId; // reg_id 등록자 아이디 VARCHAR(24) NOT NULL
}
