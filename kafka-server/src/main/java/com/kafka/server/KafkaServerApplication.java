package com.kafka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KafkaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaServerApplication.class, args);
	}

}
