package com.zipsoft.commons.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieUtil {
	
	public Cookie setCookie(String key, String value, int maxAge) {
		Cookie c = new Cookie(key, value);
		
		if (Constants.REFRESH_TOKEN.equals(key)) {
			c.setHttpOnly(true);		
			c.setSecure(true);
		}
		
		c.setPath("/");
		c.setMaxAge(maxAge);
		
		return c;
		
	}
	
	public Cookie getCookie(HttpServletRequest req, String key) {
		Cookie[] cookies = req.getCookies();
		
		if (cookies == null) return null;
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key)) {
				return cookie;
			}
		}
		
		return null;
	}
	
	public void removeCookie(HttpServletRequest req, HttpServletResponse res, String key) {
		Cookie c = this.getCookie(req, key);
		
		if (c != null) {
			c.setMaxAge(0);
			c.setPath("/");
			res.addCookie(c);
		}
	}
	
}
