package com.miage.bibliothequeApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.miage.bibliothequeApp.repository")
public class BibliothequeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliothequeAppApplication.class, args);
	}

}
