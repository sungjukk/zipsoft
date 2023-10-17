package com.zipsoft.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class CommonUtil {
	public static final Character[] COMPATIBILITY_CHOSUNGs = {  
				            0x3131, 0x3132, 0x3134, 0x3137, 0x3138,     // ㄱ, ㄲ, ㄴ, ㄷ, ㄸ  
				            0x3139, 0x3141, 0x3142, 0x3143, 0x3145,     // ㄹ, ㅁ, ㅂ, ㅃ, ㅅ  
				            0x3146, 0x3147, 0x3148, 0x3149, 0x314A,     // ㅆ, ㅇ, ㅈ, ㅉ, ㅊ  
				            0x314B, 0x314C, 0x314D, 0x314E              // ㅋ, ㅌ, ㅍ, ㅎ  
				    }; 

	
	
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
	
	public static boolean isChosung(String txt) {
		
		if ("".equals(txt) || txt == null) return false;
		
		List<Character> list =  Arrays.asList(COMPATIBILITY_CHOSUNGs);
		
		
		for (int i = 0; i < txt.length(); i++) {
			char val = txt.charAt(i);
			if (!list.contains(val)) return false;
		}
		
		
		return true;
	}
	
}
