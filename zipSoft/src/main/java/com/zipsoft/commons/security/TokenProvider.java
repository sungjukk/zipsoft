package com.zipsoft.commons.security;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.zipsoft.commons.utils.Constants;
import com.zipsoft.commons.utils.CookieUtil;
import com.zipsoft.commons.utils.RedisUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider implements InitializingBean {
	
	@Value("${app.jwt.jwtSecret}")
	private String jwtSecret;
	
	@Value("${app.jwt.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	
	@Value("${app.jwt.jwtRefreshExpriationInMs}")
	private int jwtRefreshExpriationInMs;
	
	private final CookieUtil cookieUtil;
	
	private final RedisUtil redisUtil;
	
	private SecretKey key;
	
	
	// 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
	public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
	
	public String generateToken(UserPrincipal user) {
    	Date expiryDate = new Date(new Date().getTime() + (jwtExpirationInMs * 1000));
    	String uuid = UUID.randomUUID().toString();
    	
    	redisUtil.setData(Constants.ACCESS_TOKEN + "_" + user.getUserId(), uuid);
    	
    	return Jwts.builder()
                .setSubject(Long.toString(user.getUserId()))
                .setIssuedAt(new Date())
                .claim("name", user.getUsername())
                .claim("uuid", uuid)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .compact();
    	
	}
	
	public String generateRefreshToken(UserPrincipal user, HttpServletResponse res) {
    	Date expiryDate = new Date(new Date().getTime() + (jwtRefreshExpriationInMs * 1000 * 60));
    	
    	String jwt = Jwts.builder()
				         .setSubject(Long.toString(user.getUserId()))
				         .setIssuedAt(new Date())
				         .claim("name", user.getUsername())
				         .signWith(key, SignatureAlgorithm.HS512)
				         .setExpiration(expiryDate)
				         .compact();
    	
    	redisUtil.setData(Constants.REFRESH_TOKEN + "_" + user.getUserId(), jwt);
    	Cookie c = cookieUtil.setCookie(Constants.REFRESH_TOKEN, jwt, jwtRefreshExpriationInMs * 60);
    	res.addCookie(c);
    	
    	return jwt;
    	
	}
	
	public String validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return "SUCC";
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            return "ExpiredToken";
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return "FAIL";
    }
	
	public long getUserNoFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
        		            .setSigningKey(key)
        		            .build()
        		            .parseClaimsJws(token)
        		            .getBody();

        return Long.valueOf(claims.getSubject());
    }
	
	
	private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
	
}
