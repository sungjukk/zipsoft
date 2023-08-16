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

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		this.sendError(response, "인증에 실패하였습니다.");
	}
	
	private void sendError(HttpServletResponse res, String msg) throws IOException {
		res.setCharacterEncoding("utf-8");
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		JSONObject obj = new JSONObject();
		obj.put("message", msg);
		res.getWriter().write(obj.toJSONString());
		
	}

}
