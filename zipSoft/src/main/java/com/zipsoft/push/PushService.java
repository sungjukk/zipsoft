package com.zipsoft.push;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.zipsoft.push.dto.PushDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PushService {
	
	public void sendPush(PushDto dto) {
		MulticastMessage message = MulticastMessage.builder().putAllData(dto.getMap()).addAllTokens(dto.getTokenList()).build();
		try {
			BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
