package com.deals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.deals", "it.ozimov.springboot"})
@SpringBootApplication
public class BestDealsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BestDealsApplication.class, args);
	}
	
}
