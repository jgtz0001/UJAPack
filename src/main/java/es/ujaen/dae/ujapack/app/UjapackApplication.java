package es.ujaen.dae.ujapack.app;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import es.ujaen.dae.ujapack.interfaces.ServicioUjaPack;
import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.boot.SpringApplication;
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