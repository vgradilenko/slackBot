package com.mev.jbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "com.mev.jbot"})
public class JbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JbotApplication.class, args);
	}
}
