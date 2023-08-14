package com.zipsoft.commons.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiHeader {
	
	private HttpStatus resultCode;
	private String codeName;
	
}
