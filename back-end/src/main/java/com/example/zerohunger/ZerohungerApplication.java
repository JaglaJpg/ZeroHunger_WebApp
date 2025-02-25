package com.example.zerohunger;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

//This just runs the application

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "com.example.zerohunger.Entity")
public class ZerohungerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZerohungerApplication.class, args);
	}
	


}
