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
	
	private int result;
	private String msg;
	private T data;
	
	public ApiResponse(int result, String msg, T data) {
		this.result = result;
		this.msg = msg;
		this.data = data;
	}
	
	public static <T> ApiResponse OK(T data) {
		return new ApiResponse<T>(HttpStatus.OK.value(), "success", data);
	}
	
	public static <T> ApiResponse fail(HttpStatus status) {
		return new ApiResponse<T>(status.value(), status.name(), null);
	}
	
	public static <T> ApiResponse fail(HttpStatus status, String msg) {
		return new ApiResponse<T>(status.value(), msg, null);
	}
	
}
