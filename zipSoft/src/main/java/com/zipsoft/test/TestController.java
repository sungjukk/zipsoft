package com.zipsoft.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zipsoft.commons.utils.RedisUtil;

@RestController
public class TestController {
	
	private final RedisUtil redisUtil;
	
	public TestController(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
	
	@GetMapping("/test/redis/set")
	public Map<String, Object> set() {
		Map<String, Object> result = new HashMap<>();
		redisUtil.setData("test", "1231231");
		result.put("result", "succ");
		return result;
	}
	
	@GetMapping("/test/redis/get")
	public Map<String, Object> get() {
		Map<String, Object> result = new HashMap<>();
		System.out.println(redisUtil.getData("test"));
		result.put("result", "succ");
		return result;
	}
	
	@GetMapping("/dfdf")
	public String test() {
		System.out.println("Asdadadsa");
		return "SUCC";
	}
	
}
