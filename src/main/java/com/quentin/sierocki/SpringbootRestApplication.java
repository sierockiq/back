package com.quentin.sierocki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.quentin.sierocki.domain.repository")
public class SpringbootRestApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootRestApplication.class, args);
	}

}
