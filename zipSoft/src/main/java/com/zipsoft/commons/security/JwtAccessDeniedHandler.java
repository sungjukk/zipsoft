package com.zipsoft.commons.security;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		sendError(response, "권한이 없습니다.");
	}
	
	private void sendError(HttpServletResponse res, String msg) throws IOException {
		res.setCharacterEncoding("utf-8");
		res.setStatus(HttpStatus.FORBIDDEN.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		JSONObject obj = new JSONObject();
		obj.put("message", msg);
		res.getWriter().write(obj.toJSONString());
		
	}

}
