package com.syy.spring.cloud.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MicroWeatherRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroWeatherRedisApplication.class, args);
	}
}
