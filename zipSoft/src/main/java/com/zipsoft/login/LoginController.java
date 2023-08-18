package com.zipsoft.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.security.TokenProvider;
import com.zipsoft.commons.security.UserPrincipal;
import com.zipsoft.commons.utils.Constants;
import com.zipsoft.commons.utils.CookieUtil;
import com.zipsoft.login.dto.LoginDto;
import com.zipsoft.model.TokenVo;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
	
	private final LoginService loginService;
	
	private final TokenProvider tokenProvider;
	
	private final CookieUtil cookieUtil;
	
	@PostMapping
	public ApiResponse login(HttpServletResponse res, @RequestBody LoginDto dto) {
		
		Authentication auth;
		try {
			auth = loginService.authenticate(dto.getUserId(), dto.getPassword());
			UserPrincipal user = (UserPrincipal) auth.getPrincipal();
			
			TokenVo tokenVo = TokenVo.builder()
									 .accessToken(tokenProvider.generateToken(user))
									 .refreshToken(tokenProvider.generateRefreshToken(user, res))
									 .grantType(Constants.TOKEN_GRANT)
									 .build();
			
			return ApiResponse.OK(tokenVo);
			
		} catch (UsernameNotFoundException | BadCredentialsException e) {
			return ApiResponse.fail(HttpStatus.UNAUTHORIZED, e.getMessage());
		} catch (Exception e) {
			return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
	}
	
	
}
