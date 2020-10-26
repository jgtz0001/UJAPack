package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.interfaces.ServicioUjaPack;
import java.io.*;
import java.text.ParseException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "es.ujaen.dae.ujapack.app")
public class UjapackApplication {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

        ServicioUjaPack uja = new ServicioUjaPack();
        System.out.println("Guardando datos...");
        uja.leerJson();
        System.out.println("Datos guardados correctamente.");
    }
}