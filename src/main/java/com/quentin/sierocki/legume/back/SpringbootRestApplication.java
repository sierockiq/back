package com.quentin.sierocki.legume.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.quentin.sierocki.legume.back.domain.repository")
@EnableAspectJAutoProxy
public class SpringbootRestApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootRestApplication.class, args);
	}

}
