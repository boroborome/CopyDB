package com.happy3w.etl.copydb.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class CopyDBApplication {
	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(CopyDBApplication.class, args);
	}

	@Bean
	public CommandLineRunner initCommandLineRunner() {
		return new CopyDbCommandLineRunner(environment);
	}
}
