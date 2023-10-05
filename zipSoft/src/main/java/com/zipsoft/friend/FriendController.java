package com.zipsoft.friend;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.board.BoardController;
import com.zipsoft.board.BoardService;
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
	
}
