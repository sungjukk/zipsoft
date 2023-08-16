package com.zipsoft.commons.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
	
	private ApiHeader header;
	
	private ApiBody body;
	
	public ApiResponse(ApiHeader header, ApiBody body) {
		this.header = header;
		this.body = body;
	}
	
	public static <T> ApiResponse OK(T data) {
		return new ApiResponse<T>(new ApiHeader(HttpStatus.OK, "SUCCESS"), new ApiBody(data, null));
	}
	
	public static <T> ApiResponse fail(HttpStatus status) {
		return new ApiResponse<T>(new ApiHeader(status, status.name()), new ApiBody(null, null));
	}
	
	public static <T> ApiResponse fail(HttpStatus status, String msg) {
		return new ApiResponse<T>(new ApiHeader(status, msg), new ApiBody(null, null));
	}
	
}
