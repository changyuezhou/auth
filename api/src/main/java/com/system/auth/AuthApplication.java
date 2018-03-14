package com.system.auth;

import com.system.auth.util.LoggerInitialFactory;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.system.auth.dao"})
public class AuthApplication {
	public static void main(String[] args) {
		Logger logger = LoggerInitialFactory.getLogger();
		logger.debug("#################################################### Start");
		SpringApplication.run(AuthApplication.class, args);
	}
}
