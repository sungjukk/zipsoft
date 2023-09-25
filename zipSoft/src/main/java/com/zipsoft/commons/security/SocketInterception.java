package com.zipsoft.commons.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.zipsoft.chat.ChatRoomService;
import com.zipsoft.chat.ChatService;
import com.zipsoft.chat.dto.ChatRoomMemberDto;
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
	
	private final ChatService chatService;
	
	private final ChatRoomService chatRoomServive;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		
		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			String token = this.getJwtFromRequest(accessor);
			String result = tokenProvider.validateToken(token);
			
			if ("SUCC".equals(result)) {
				long userId = tokenProvider.getUserNoFromJWT(token);
				
				UserDetails userDetails = userDetailService.loadUserById(userId);
				Authentication auth =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				accessor.setUser(auth);
			} else if ("ExpiredToken".equals(result)) {
				throw new CustomAuthException("expired");
			}
			
		} else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
			
			String subUrl = String.valueOf(message.getHeaders().get("simpDestination"));
			this.updateChatActive(subUrl, "Y", message);
			
		} 
		
		return message;
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())) {
			String subUrl = String.valueOf(message.getHeaders().get("simpSubscriptionId"));
			chatRoomServive.leaveChatRoom(message);
		} 
	}
	
	private String getJwtFromRequest(StompHeaderAccessor acc) {
        String bearerToken = acc.getFirstNativeHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.TOKEN_GRANT)) {
            return bearerToken.substring(7);
        }

        return null;
    }
	
	private void updateChatActive(String url, String isActive, Message<?> message) {
		if (url.indexOf("/topic/chat/") > -1) {
			String chatId = url.replaceAll("/topic/chat/", "");
			UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) SimpMessageHeaderAccessor.getUser(message.getHeaders());
			UserPrincipal prin = (UserPrincipal) upat.getPrincipal();
			
			chatRoomServive.enterChatRoom(chatId, prin.getUserId());
		}
	}
	
}
