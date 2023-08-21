package com.zipsoft.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.commons.payload.ApiResponse;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	
	@GetMapping("/error")
	public ApiResponse handleError() {
		
		return ApiResponse.fail(HttpStatus.NOT_FOUND);
		
	}
}
