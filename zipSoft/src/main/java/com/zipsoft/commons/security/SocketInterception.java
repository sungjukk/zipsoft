package com.zipsoft.commons.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.zipsoft.commons.utils.Constants;
import com.zipsoft.exception.CustomAuthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 200)
public class SocketInterception implements ChannelInterceptor {
	
	private final TokenProvider tokenProvider;
	
	private final UserDetailService userDetailService;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		
		if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
			String token = this.getJwtFromRequest(headerAccessor);
			String result = tokenProvider.validateToken(token);
			
			if ("SUCC".equals(result)) {
				long userId = tokenProvider.getUserNoFromJWT(token);
				
				UserDetails userDetails = userDetailService.loadUserById(userId);
				Authentication auth =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				headerAccessor.setUser(auth);
			} else if ("ExpiredToken".equals(result)) {
				throw new CustomAuthException("expired");
			}
			
		}
		
		return message;
	}
	
	private String getJwtFromRequest(StompHeaderAccessor acc) {
        String bearerToken = acc.getFirstNativeHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.TOKEN_GRANT)) {
            return bearerToken.substring(7);
        }

        return null;
    }
	
	
	
}
