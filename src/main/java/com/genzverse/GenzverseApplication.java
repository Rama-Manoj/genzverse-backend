package com.genzverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync	
@EnableScheduling	
public class GenzverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenzverseApplication.class, args);
	}

}
