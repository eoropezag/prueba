package com.meep.prueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MeepApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeepApplication.class, args);
	}

}
