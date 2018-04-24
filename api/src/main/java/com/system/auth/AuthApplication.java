package com.system.auth;

import com.system.auth.auth.Auth;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan({"com.system.auth.dao"})
/*
@EnableAutoConfiguration
@Configuration
public class AuthApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder application) {
		return application.sources(AuthApplication.class);
	}
}
*/

public class AuthApplication {
	public static void main(String[] args) {
		Auth.initialAuth("77e2edcc9b40441200e31dc57dbb8829", "06848368e2647bf5ec493160c423d453",
				"192.168.56.101", "8088", "/api/auth/access_token",
				1000, 600,
				"open_id", "access_token", "user_name");
		SpringApplication.run(AuthApplication.class, args);
	}
}
