package com.zipsoft.friend;

import java.util.List;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.friend.dto.FriendDto;

public interface FriendService {
	
	public List<FriendDto> list(long userId);
	
	public List<UserDto> search(String userName, long userId);
	
	public FriendDto detail(long userId);
	
}
