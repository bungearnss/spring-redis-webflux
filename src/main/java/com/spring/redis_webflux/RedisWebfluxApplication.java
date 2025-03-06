package com.spring.redis_webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisWebfluxApplication.class, args);
	}

}
