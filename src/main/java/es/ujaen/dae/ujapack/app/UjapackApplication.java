package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.beans.ServicioUjaPack;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        ServicioUjaPack uja = new ServicioUjaPack();
        //crear dos cliente.
        Cliente cli1 = new Cliente("12323234", "", "", "", "", "Jaén", "Jaén");//localidad y luego provincia!!
        Cliente cli2 = new Cliente("12334243", "", "", "", "", "Córdoba", "Córdoba");
        Paquete paquet = uja.altaEnvio(1, 1, 1, cli1, cli2);
        List<PuntoDeControl> ruta = paquet.getRuta();
        PuntoDeControl punto = new PuntoDeControl(1, "Vitoria", "Vitoria", null);
        paquet.notificaSalida(LocalDateTime.now(), punto);
        if (ruta.isEmpty()) {
            System.out.println("Ruta no valida");
        } else {
            for (int i = 0; i < ruta.size(); i++) {
                System.out.println(ruta.get(i).getLocalizacion());
            }
        }
    }
}
