/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.beans.ServicioUjaPack;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author PCJoseGabriel
 */
@SpringBootTest(classes = {UjapackApplication.class})
public class UjapackIntegrationTest {

    @Autowired
    UjapackApplication ujaPack;
    ServicioUjaPack serviPack;

    public UjapackIntegrationTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testLocalizador() {
        int localizadorCheck1 = 123456789;
        int localizadorCheck2 = 1234567891;

        Assertions.assertTrue(Paquete.checkLocalizador(localizadorCheck2));
        Assertions.assertFalse(Paquete.checkLocalizador(localizadorCheck1));

    }

    @Test
    public void testRepiteLocalizador() {
        int localizadorCheck1 = 1234567891;
        int localizadorCheck2 = 1234567891;
        int localizadorCheck3 = 1234515261;

        Assertions.assertTrue(Paquete.checkRepiteLocalizador(localizadorCheck2, localizadorCheck2));

        Assertions.assertFalse(Paquete.checkRepiteLocalizador(localizadorCheck1, localizadorCheck3));

    }

    @Test
    public void testEnvio() {
        String dni1 = "26054589";
        String dni2 = "75896325";
        String dni3 = "75896325";
        String dir1 = "Av Jaen";
        String dir2 = "Calle Barcelona";
        String dir3 = "Calle Barcelona";
        String loc1 = "Jaen";
        String loc2 = "Madrid";
        String loc3 = "Madrid";

        Assertions.assertTrue(Paquete.checkEnvio(dni1, dni2, dir1, dir2, loc1, loc2));
        Assertions.assertFalse(Paquete.checkEnvio(dni2, dni3, dir2, dir3, loc2, loc3));
    }

    @Test
    public void testRutas() {

        int localizador1 = 1234567891;
        int localizador2 = 1234515261;

        ArrayList<String> rutas1 = new ArrayList<String>();
        rutas1.add("Lucena");
        rutas1.add("Cordoba");
        rutas1.add("Sevilla");
        rutas1.add("Toledo");
        rutas1.add("Madrid");

        ArrayList<String> rutas2 = new ArrayList<String>();
        rutas2.add("Lucena");
        rutas2.add("Cordoba");
        rutas2.add("Sevilla");
        rutas2.add("Toledo");
        rutas2.add("Madrid");
        rutas2.add("Getafe");

        Assertions.assertTrue(Paquete.testRepiteEnvio(rutas1, rutas2, localizador1, localizador2));
        Assertions.assertFalse(Paquete.testRepiteEnvio(rutas1, rutas1, localizador1, localizador1));

    }

    @Test
    public void creaEnvio() throws IOException {

        Cliente Remitente1 = new Cliente("", "", "", "", "", "Jaén", "Jaén");
        Cliente Destinatario1 = new Cliente("", "", "", "", "", "Córdoba", "Córdoba");

        ArrayList<String> e1 = new ArrayList<String>();

     
        e1 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario1);
        Assertions.assertEquals(3, e1.size());

        Assertions.assertEquals(0.003, serviPack.calcularImporte(3, 1, 1, 1));

    }

    @Test
    public void testRutasJ() {
        ArrayList<String> localizaciones = new ArrayList<String>();
        localizaciones.add("Sevilla");
        localizaciones.add("Toledo");
        localizaciones.add("Valencia");
        localizaciones.add("Valladolid");
        localizaciones.add("Barcelona");
        localizaciones.add("Zaragoza");
        localizaciones.add("Vitoria");
        localizaciones.add("Orense");
        localizaciones.add("Madrid");
        localizaciones.add("Santa Cruz de Tenerife");
        ArrayList<Integer> conex = new ArrayList<Integer>();
        conex.add(2);
        conex.add(3);

        Assertions.assertTrue(serviPack.testRutaJ(localizaciones, conex));

    }

}
