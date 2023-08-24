package com.zipsoft.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.auth.dto.LoginDto;
import com.zipsoft.board.dto.BoardDto;
import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.security.TokenProvider;
import com.zipsoft.commons.security.UserPrincipal;
import com.zipsoft.commons.utils.Constants;
import com.zipsoft.commons.utils.CookieUtil;
import com.zipsoft.model.TokenVo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping
	public ApiResponse list() {
		
		return ApiResponse.OK(null);
	}
	
	@PostMapping
	public ApiResponse insert(@RequestBody BoardDto board) {
		board.setRegId(0);
		boardService.insertBoard(board);
		
		return ApiResponse.OK(null);
	}
	
}
