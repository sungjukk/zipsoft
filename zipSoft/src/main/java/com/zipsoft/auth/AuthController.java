package com.zipsoft.auth;

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
import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.security.TokenProvider;
import com.zipsoft.commons.security.UserPrincipal;
import com.zipsoft.commons.utils.Constants;
import com.zipsoft.commons.utils.CookieUtil;
import com.zipsoft.model.TokenVo;
import com.zipsoft.model.entity.User;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	private final TokenProvider tokenProvider;
	
	private final CookieUtil cookieUtil;
	
	private final AuthRepository authRepository;
	
	@PostMapping("login")
	public ApiResponse login(HttpServletResponse res, @RequestBody LoginDto dto) {
		
		Authentication auth;
		try {
			auth = authService.authenticate(dto.getUserId(), dto.getPassword());
			UserPrincipal user = (UserPrincipal) auth.getPrincipal();
			
			authService.updateDeviceToken(user.getUserId(), dto.getDeviceToken());
			
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
	
	@GetMapping("republishToken")
	public ApiResponse republicToken(@AuthenticationPrincipal UserPrincipal user) {
		TokenVo tokenVo = TokenVo.builder()
				 .accessToken(tokenProvider.generateToken(user))
				 .grantType(Constants.TOKEN_GRANT)
				 .build();
		return ApiResponse.OK(tokenVo);
	}
	
	@GetMapping("updateToken")
	public ApiResponse updateTokenInfo(@AuthenticationPrincipal UserPrincipal user) {
		
		UserDto dto = authService.findById(user.getUserId());
		
		TokenVo tokenVo = TokenVo.builder()
				 .accessToken(tokenProvider.generateToken(new UserPrincipal(dto)))
				 .grantType(Constants.TOKEN_GRANT)
				 .build();
		return ApiResponse.OK(tokenVo);
	}
	
	@GetMapping("ping")
	public ApiResponse ping(@AuthenticationPrincipal UserPrincipal user) {
		return ApiResponse.OK(null);
	}
	
	@GetMapping("/logout")
	public ApiResponse logout(HttpServletRequest req, HttpServletResponse res) {
		
		cookieUtil.removeCookie(req, res, Constants.REFRESH_TOKEN);
		
		try {
			Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (auth != null) {
				UserPrincipal user = (UserPrincipal) auth;
				authService.removeDeviceToken(user.getUserId());
				authService.removeUserCache(user.getUserId());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return ApiResponse.OK(null);
		
	}
	
	@GetMapping("insert")
	public ApiResponse insert(@RequestBody LoginDto dto) {
		authService.insertUser(dto);
		
		return ApiResponse.OK(null);
	}
	
}
