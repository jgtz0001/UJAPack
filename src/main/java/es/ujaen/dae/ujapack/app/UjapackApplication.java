package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.beans.ServicioUjaPack;
import es.ujaen.dae.ujapack.entidades.Cliente;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "es.ujaen.dae.ujapack.app")
public class UjapackApplication {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        ServicioUjaPack uja = new ServicioUjaPack();
        uja.leerJson();
//crear dos cliente.
        Cliente cli1 = new Cliente("", "", "", "", "", "Sevilla", "Sevilla");
        Cliente cli2 = new Cliente("", "", "", "", "", "Toledo", "Toledo");
        ArrayList<String> ruta = new ArrayList<String>();
        ruta = uja.altaEnvio(1, 1, 1, cli1, cli2);
        if (ruta.isEmpty()) {
            System.out.println("Ruta no valida");
        } else {
            for (int i = 0; i < ruta.size(); i++) {
                System.out.println(ruta.get(i));
            }
        }

    }
}
