package de.daniu.home.ablesung;

import de.daniu.home.aspects.logging.LoggingAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(LoggingAspect.class)
public class AblesungApplication {

	public static void main(String[] args) {
		SpringApplication.run(AblesungApplication.class, args);
	}
}
