package com.crm.app.user.profile.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfig {
	
	private String driverClassName;
	private String username;
	private String password;
	private String url;
	
	@Autowired
	private Environment environment;
	
	@Profile("dev")
	@Bean
	public String devDBConfiguration() {
		log.info("Loading Dev DB configuration properties");
		log.info("driverClassName :" +driverClassName);
		log.info("DB URL :" +url);
		return "**** DEV Environment ****";
	}
	
	@Profile("prod")
	@Bean
	public String prodDBConfiguration() {
		log.info("Loading Prod DB configuration properties");
		log.info("driverClassName :" +driverClassName);
		log.info("DB URL :" +url);
		return "**** PROD Environment ****";
	}
	
	@PostConstruct
	  void postConstruct(){
	    String[] activeProfiles = environment.getActiveProfiles();
	    log.info("active profiles: {}", Arrays.toString(activeProfiles));
	  }

}
