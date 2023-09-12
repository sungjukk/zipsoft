package com.zipsoft.commons.security;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	private final long MAX_AGE_SEC = 3600;
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        converter.setObjectMapper(mapper);
        return converter;
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")
			    .allowedOrigins("http://localhost:3000", "http://192.168.0.12")
			    .allowCredentials(true)
			    .allowedMethods("HEAD","OPTIONS","GET","POST","PUT","PATCH","DELETE")
			    .exposedHeaders(HttpHeaders.CONTENT_DISPOSITION, HttpHeaders.CONTENT_LENGTH)
			    .maxAge(MAX_AGE_SEC);
		
	}
	
	
	
}
