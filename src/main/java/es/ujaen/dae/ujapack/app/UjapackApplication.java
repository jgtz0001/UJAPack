package es.ujaen.dae.ujapack.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"es.ujaen.dae.ujapack.beans",
    "es.ujaen.dae.ujapack.repositorios",
    "es.ujaen.dae.ujapack.controladoresREST",
    "es.ujaen.dae.ujapack.seguridad"
})

@EntityScan(basePackages = "es.ujaen.dae.ujapack.entidades")
public class UjapackApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(UjapackApplication.class, args);

    }
}
