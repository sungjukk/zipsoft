package com.zipsoft.exception;

import org.springframework.messaging.MessagingException;

public class CustomAuthException extends MessagingException {
	
	
	public CustomAuthException(String message) {
        super(message); // RuntimeException 클래스의 생성자를 호출합니다.
    }
}
