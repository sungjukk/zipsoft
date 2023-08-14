package com.zipsoft.commons.security;

import java.nio.file.attribute.UserPrincipal;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider implements InitializingBean {
	
	@Value("${app.jwt.jwtSecret}")
	private String jwtSecret;
	
	@Value("${app.jwt.jwtExpirationInMs}")
	private long jwtExpirationInMs;
	
	@Value("${app.jwt.jwtRefreshExpriationInMs}")
	private long jwtRefreshExpriationInMs;
	
	private SecretKey key;
	
	// 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
	public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
	
	public String generateToken() {
    	Date expiryDate = new Date(new Date().getTime() + jwtExpirationInMs);
    	
    	
    	return Jwts.builder()
                .setSubject(Long.toString(1))
                .setIssuedAt(new Date())
                .claim("name", "tester")
                .claim("email", "tester@tester.com")
                .claim("uuid", "123123123")
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .compact();
    	
	}
	
	public String generateRefreshToken() {
    	Date expiryDate = new Date(new Date().getTime() + jwtRefreshExpriationInMs);
    	
    	
    	return Jwts.builder()
                .setSubject(Long.toString(1))
                .setIssuedAt(new Date())
                .claim("name", "tester")
                .claim("email", "tester@tester.com")
                .claim("uuid", "123123123")
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .compact();
    	
	}
	
}
