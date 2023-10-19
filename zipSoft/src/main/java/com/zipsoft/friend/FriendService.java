package com.zipsoft.friend;

import java.util.List;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.friend.dto.FriendDto;
import com.zipsoft.friend.dto.ProfileDto;

public interface FriendService {
	
	public List<FriendDto> list(long userId);
	
	public List<UserDto> search(String userName, long userId);
	
	public ProfileDto userProfile(long userId, long searchId);
	
	public void addFriend(long userId, long friendId);
	
}
