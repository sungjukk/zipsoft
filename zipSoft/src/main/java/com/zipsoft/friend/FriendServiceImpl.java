package com.zipsoft.friend;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zipsoft.friend.dto.FriendDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
	
	private final FriendRepository friendRepository;
	
	@Override
	public List<FriendDto> list(long userId) {
		return friendRepository.list(userId);
	}

}
