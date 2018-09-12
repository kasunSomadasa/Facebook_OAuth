package com.kasun.facebook.facebookauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FacebookauthApplication extends SpringBootServletInitializer{

/*
	public static void main(String[] args) {
		SpringApplication.run(FacebookauthApplication.class, args);
	}
	*/
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FacebookauthApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(FacebookauthApplication.class, args);
	}
}
