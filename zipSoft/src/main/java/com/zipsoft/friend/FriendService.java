package com.zipsoft.friend;

import java.util.List;

import com.zipsoft.friend.dto.FriendDto;

public interface FriendService {
	
	public List<FriendDto> list(long userId);
	
}
