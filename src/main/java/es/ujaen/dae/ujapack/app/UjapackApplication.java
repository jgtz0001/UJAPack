package es.ujaen.dae.ujapack.app;

import java.io.*;
import java.text.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages ={ "es.ujaen.dae.ujapack.beans","es.ujaen.dae.ujapack.repositorios"})
@EntityScan(basePackages = "es.ujaen.dae.ujapack.entidades")
public class UjapackApplication {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        SpringApplication servidor = new SpringApplication(UjapackApplication.class);
        ApplicationContext context = servidor.run(args);

    }
}
