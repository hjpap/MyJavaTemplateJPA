package com.wei.zuba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@SpringBootApplication
@ComponentScan
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableAutoConfiguration
@ImportResource("classpath:/application-security.xml")
public class ZubaWebApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ZubaWebApplication.class, args);
	}
	
}
