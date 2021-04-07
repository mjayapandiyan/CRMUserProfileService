package com.crm.app.user.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.crm.app.user.profile.service.PopulateDataService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CrmApplication implements ApplicationRunner {
	
	@Autowired(required = false)
	private PopulateDataService populateDataService;
	
	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}
	
	/**
	 * By implementing the CommandLineRunner/ApplicationRunner, 
	 * the run() method of the MyRunner class will be executed after the application starts.
	 */

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			log.info("populateDataService instance created and DB script execution done: " +populateDataService.getClass());	
		}catch (Exception e) {
			log.error("Exception Occured in ApplicationRunner : " +e.getMessage());
		}
			
	}
	
}
