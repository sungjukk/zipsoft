package com.zipsoft.commons.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipsoft.commons.payload.ApiResponse;
import com.zipsoft.commons.utils.Constants;
import com.zipsoft.commons.utils.CookieUtil;
import com.zipsoft.commons.utils.RedisUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private CookieUtil cookieUtil;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
		
		boolean isAuth = false;
		long userId = 0;
		String errorMsg = "";
		
		
		if ("/auth/republishToken".equals(req.getRequestURI())) {
			Cookie cookie = cookieUtil.getCookie(req, Constants.REFRESH_TOKEN);
			if (cookie == null) {
				errorMsg = "refresh_token_not_fonud";
			} else {
				String refreshToken = cookie.getValue();
				if (!"".equals(refreshToken) && refreshToken != null) {
					
					String msg = tokenProvider.validateToken(refreshToken);
					
					if (!"SUCC".equals(msg)) {
						errorMsg = "refresh_token_not_fonud";				
					};
					
					userId = tokenProvider.getUserNoFromJWT(refreshToken);
					String saveRefreshToken = redisUtil.getData(Constants.REFRESH_TOKEN + "_" + userId);
					
					if (refreshToken.equals(saveRefreshToken)) {
						isAuth = true;
					} else {
						errorMsg = "refresh_token_expired";
					}
				} else {
					errorMsg = "refresh_token_not_fonud";
				}
			}
			
			
			if (!"".equals(errorMsg)) {
				setErrorResponse(req,res,errorMsg);
				return;
			}
			
		} else {
			String jwt = this.getJwtFromRequest(req);
			
			if (jwt != null) {
				String result = tokenProvider.validateToken(jwt);
				
				if ("SUCC".equals(result)) {
					isAuth = true; 
					userId = tokenProvider.getUserNoFromJWT(jwt);
				} else if ("/auth/logout".equals(req.getRequestURI())) {
					
				} else if ("ExpiredToken".equals(result)) {
					setErrorResponse(req,res,"expired");
					return;
				}

			}
		}
		
		if (isAuth) {
			
			UserDetails userDetails = userDetailService.loadUserById(userId);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

            SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		
		filterChain.doFilter(req, res);
	}
	
	private String getJwtFromRequest(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.TOKEN_GRANT)) {
            return bearerToken.substring(7);
        }

        return null;
    }
	
	
	public void setErrorResponse(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        
		res.setCharacterEncoding("utf-8");
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		ObjectMapper mapper = new ObjectMapper();
		String txt = mapper.writeValueAsString(ApiResponse.fail(HttpStatus.UNAUTHORIZED, msg));
		
		res.getWriter().write(txt);
    }
	
}
