package es.ujaen.dae.ujapack.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="es.ujaen.dae.ujapack.app")
public class UjapackApplication {

	public static void main(String[] args) {
		SpringApplication.run(UjapackApplication.class, args);
	}

}
