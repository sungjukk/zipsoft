package com.zipsoft.friend;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {
	
	private final FriendService friendService;
	
	@GetMapping
	public ApiResponse list(@AuthenticationPrincipal UserPrincipal user) {
		return ApiResponse.OK(friendService.list(user.getUserId()));
	}
	
	@GetMapping("/search/{userName}")
	public ApiResponse search(@AuthenticationPrincipal UserPrincipal user, @PathVariable String userName) {
		List<UserDto> userList = friendService.search(userName, user.getUserId());
		return ApiResponse.OK(userList);
	}
	
	@GetMapping("{userId}")
	public ApiResponse detail(@AuthenticationPrincipal UserPrincipal user, @PathVariable long userId) {
		return ApiResponse.OK(null);
	}
	
}
