package com.happy3w.etl.copydb.copydb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
public class CopyDBApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(CopyDBApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(Arrays.asList(args));
		System.out.println(System.getenv("spring.profiles.active"));
		System.out.println(System.getProperty("spring.profiles.active"));
		System.out.println(Arrays.asList(environment.getActiveProfiles()));
		System.out.println(environment.getProperty("action.export2.name"));
	}
}
