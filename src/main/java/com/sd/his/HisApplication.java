package com.sd.his;

import com.sd.his.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class HisApplication  {
	private final Logger logger = LoggerFactory.getLogger(HisApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(HisApplication.class, args);
	}


}
