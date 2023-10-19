package com.zipsoft.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {
	private long id;
	private String userId;
	private String email;
	private String userName;
	private boolean isFriendReq;
}
