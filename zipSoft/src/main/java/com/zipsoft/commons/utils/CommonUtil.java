package com.zipsoft.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zipsoft.elasticsearch.dto.ElasticSearchResultDto;

import lombok.RequiredArgsConstructor;

public class CommonUtil {
	
	public static <T> T jsonToDto(String json, Class<T> c) {
		if ("".equals(json) || json == null) return null;
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(json, c);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static <T> T jsonToDtoGeneric(String json, Class<?> c, Class<?> genericClass) {
		if ("".equals(json) || json == null) return null;
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(c, genericClass);
			objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(json, javaType);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static <T> T ObjectToDto(Object obj, Class<T> c) {
		if (obj == null) return null;
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String json = objectMapper.writeValueAsString(obj);
			return objectMapper.readValue(json, c);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
