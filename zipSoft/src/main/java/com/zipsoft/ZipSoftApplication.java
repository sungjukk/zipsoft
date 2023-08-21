package com.zipsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ZipSoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipSoftApplication.class, args);
	}

}
