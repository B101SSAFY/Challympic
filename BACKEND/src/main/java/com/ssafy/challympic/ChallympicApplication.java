package com.ssafy.challympic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChallympicApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location=" +
			"classpath:challympic.yml, " +
			"classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(ChallympicApplication.class)
				.properties(APPLICATION_LOCATIONS)
						.run(args);

//		SpringApplication.run(ChallympicApplication.class, args);
	}

}
