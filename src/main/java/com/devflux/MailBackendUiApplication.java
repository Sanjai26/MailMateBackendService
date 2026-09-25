package com.devflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MailBackendUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailBackendUiApplication.class, args);
	}

}
