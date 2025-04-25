package br.com.ifpe.oxefood_api_luana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OxefoodApiLuanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OxefoodApiLuanaApplication.class, args);
	}

}
