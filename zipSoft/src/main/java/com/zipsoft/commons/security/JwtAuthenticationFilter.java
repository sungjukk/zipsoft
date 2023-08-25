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
		
		String jwt = this.getJwtFromRequest(req);
		Cookie c = cookieUtil.getCookie(req, Constants.REFRESH_TOKEN);
		if (c != null)
		System.out.println(c.getValue());
		
		if (jwt != null) {
			String result = tokenProvider.validateToken(jwt);
			
			if ("SUCC".equals(result)) {
				isAuth = true; 
				userId = tokenProvider.getUserNoFromJWT(jwt);
			} else if ("ExpiredToken".equals(result)) {
				if ("/auth/republishToken".equals(req.getRequestURI())) {
					userId = this.refreshTokenCheck(req, res);
					isAuth = userId != 0;
				} else {
					setErrorResponse(req,res);
					return;
				}
			}

			
			
			if (isAuth) {
				
				UserDetails userDetails = userDetailService.loadUserById(userId);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
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
	
	private long refreshTokenCheck(HttpServletRequest req, HttpServletResponse res) {
		Cookie cookie = cookieUtil.getCookie(req, Constants.REFRESH_TOKEN);
		
		if (cookie == null) return 0;
		
		String refreshToken = cookie.getValue();
		
		if (!"".equals(refreshToken) && refreshToken != null) {
			
			if (!"SUCC".equals(tokenProvider.validateToken(refreshToken))) return 0;
			
			long userId = tokenProvider.getUserNoFromJWT(refreshToken);
			String saveRefreshToken = redisUtil.getData(Constants.REFRESH_TOKEN + "_" + userId);
			
			if (refreshToken.equals(saveRefreshToken) || saveRefreshToken == null || "".equals(saveRefreshToken)) {
				return userId;
			}
		}
		
		return 0;
	}
	
	public void setErrorResponse(HttpServletRequest req, HttpServletResponse res) throws IOException {
        
		res.setCharacterEncoding("utf-8");
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		ObjectMapper mapper = new ObjectMapper();
		String txt = mapper.writeValueAsString(ApiResponse.fail(HttpStatus.UNAUTHORIZED, "expired"));
		
		res.getWriter().write(txt);
    }
	

}
