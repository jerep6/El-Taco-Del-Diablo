package com.eltacodeldiablo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig {

	@Bean
	public MongoClient mongo() throws Exception {
		return new MongoClient("192.168.1.50");
	}

}
