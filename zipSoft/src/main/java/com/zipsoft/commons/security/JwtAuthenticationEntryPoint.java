package com.zipsoft.commons.security;

import java.io.IOException;

import org.apache.catalina.connector.Response;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipsoft.commons.payload.ApiResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		
		
		if (HttpStatus.UNAUTHORIZED.value() != response.getStatus()) this.sendError(response, "인증에 실패하였습니다.");
	}
	
	private void sendError(HttpServletResponse res, String msg) throws IOException {
		res.setCharacterEncoding("utf-8");
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		ObjectMapper mapper = new ObjectMapper();
		String txt = mapper.writeValueAsString(ApiResponse.fail(HttpStatus.UNAUTHORIZED, msg));
		
		res.getWriter().write(txt);
		
	}

}
