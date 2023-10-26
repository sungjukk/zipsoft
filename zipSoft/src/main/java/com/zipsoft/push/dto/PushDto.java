package com.zipsoft.push.dto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushDto<T> {
	
	private List<String> tokenList;
	private T pushDto;
	
	public Map<String, String> getMap() {
		Map<String, String> result = new HashMap<String, String>();
		if (this.pushDto == null) return result;
		
		
		for (Field field : pushDto.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object obj = field.get(this.pushDto);
				result.put(field.getName(), String.valueOf(obj));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
