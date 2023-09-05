package com.zipsoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.zipsoft.commons.payload.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
	
	@ExceptionHandler({NoHandlerFoundException.class})
	public ApiResponse notFoundException(Exception e) {
		log.error(e.getMessage());
		return ApiResponse.fail(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler({MaxUploadSizeExceededException.class, MultipartException.class})
	public ApiResponse maxSizeException(Exception e) { 
		return ApiResponse.fail(HttpStatus.PAYLOAD_TOO_LARGE, e.getMessage());
	}
	
	@ExceptionHandler({BadRequest.class, RuntimeException.class})
	public ApiResponse RuntimeException(Exception e) {
		log.error(e.getMessage());
		return ApiResponse.fail(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	@ExceptionHandler({Exception.class})
	public ApiResponse serverException(Exception e) {
		log.error(e.getMessage());
		return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	
}
