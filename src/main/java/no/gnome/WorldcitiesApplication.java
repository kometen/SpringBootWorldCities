package no.gnome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WorldcitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldcitiesApplication.class, args);
	}
}
