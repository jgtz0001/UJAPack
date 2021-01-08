/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.beans.LimpiadoBaseDeDatos;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.beans.ServicioUjaPack;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import es.ujaen.dae.ujapack.excepciones.PuntoDeControlEquivocado;
import es.ujaen.dae.ujapack.repositorios.RepositorioPuntoDeControl;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author PCJoseGabriel
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjapackApplication.class)
public class UjapackIntegrationTest {

    @Autowired
    ServicioUjaPack serviPack;

    @Autowired
    LimpiadoBaseDeDatos limpiadoBaseDeDatos;

    @Autowired
    RepositorioPuntoDeControl repositorioPuntoDeControl;

    /*
@Test
public void creaEnvio() throws IOException {

Cliente Remitente1 = new Cliente("12323234", "jose", "camara", "jj@gmail.com", "jaen", "Jaén", "Jaén");
Cliente Destinatario0 = new Cliente("12334243", "juan", "pepe","pp@gmail.com", "jaen", "Córdoba", "Córdoba");
Cliente Destinatario1 = new Cliente("12334244", "kiko", "ola", "ola@gmail.com", "jaen", "Madrid", "Madrid");
Cliente Destinatario2 = new Cliente("12334245", "pepe", "perez", "ppp@gmail.com", "jaen", "Toledo", "Toledo");
Cliente Destinatario3 = new Cliente("12334246", "pola", "tere", "hila@gmail.com", "jaen", "Santa Cruz de Tenerife", "Santa Cruz de Tenerife");

Paquete e0 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0);
Paquete e1 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario1);
Paquete e2 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario2);
Paquete e3 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario3);

Assertions.assertEquals(3, e0.getRuta().size());
Assertions.assertEquals(3, e1.getRuta().size());
Assertions.assertEquals(4, e2.getRuta().size());
Assertions.assertEquals(5, e3.getRuta().size());

Assertions.assertEquals(0.004000000189989805, serviPack.calcularImporte(3, 1, 1, 1));

}
//     */
//    @Test
//    public void testAvisaEstado() throws IOException {
//        Cliente Remitente1 = new Cliente("12323234", "jose", "camara", "jj@gmail.com", "jaen", "Jaén", "Jaén");
//        Cliente Destinatario0 = new Cliente("12334243", "juan", "pepe", "pp@gmail.com", "jaen", "Córdoba", "Córdoba");
//
//        Paquete paquet = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0);
//
//        Assertions.assertEquals("EnTransito", paquet.getEstado());
//
//        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(0));
//        serviPack.notificarEntrada(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(0));
//     // serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(1));
//     // serviPack.notificarEntrada(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(1));
//
//        Assertions.assertEquals("EnTransito", paquet.getEstado());
//
//        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(2));
//        serviPack.notificarEntrada(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(2));
//
//        Assertions.assertEquals("EnReparto", paquet.getEstado());
//
//        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(2));
//
//        Assertions.assertEquals("Entregado", paquet.getEstado());
//
//    }

    /*
@Test
public void testRutaIncorrectaPuntoFueraDeRuta() throws IOException {
Cliente Remitente1 =new Cliente("12323234", "jose", "camara", "jj@gmail.com", "jaen", "Jaén", "Jaén");
Cliente Destinatario0 = new Cliente("12334243", "juan", "pepe","pp@gmail.com", "jaen", "Córdoba", "Córdoba");

Paquete paquet = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0);

LocalDateTime fechaSalida = LocalDateTime.now();

PuntoDeControl punto = new PuntoDeControl(5, "CL Cataluña", "Barcelona");

Assertions.assertThrows(PuntoDeControlEquivocado.class, () -> {
serviPack.notificarSalida(paquet.getLocalizador(),fechaSalida, punto);
});
}
     */
 /*
@Test
public void testRutaIncorrectaPuntoDeRutaAtrasado() throws IOException {
Cliente Remitente1 = new Cliente("12323234", "Jose", "Camara", "jj@gmail.com", "Jaén", "Jaén", "Jaén");
Cliente Destinatario0 = new Cliente("12334243", "Juan", "Pepe", "pp@gmail.com", "Córdoba", "Córdoba", "Córdoba");

Paquete paquet = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0);

LocalDateTime fechaSalida = LocalDateTime.now();
PuntoDeControl punto = new PuntoDeControl(16, "Calle Jaén", "Calle Jaén");

Assertions.assertThrows(PuntoDeControlEquivocado.class, () -> {
serviPack.notificarSalida(paquet.getLocalizador(),fechaSalida, punto);
});
}
     */
//    @Test
//    public void testRutaIncorrectaPuntoDeRutaAdelantado() throws IOException {
//        Cliente Remitente1 = new Cliente("12323234", "Jose", "Camara", "jj@gmail.com", "Jaén", "Jaén", "Jaén","clave");
//        Cliente Destinatario0 = new Cliente("12334243", "Juan", "Pepe", "pp@gmail.com", "Córdoba", "Córdoba", "Córdoba","clave");
//
//        Paquete paquet = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0);
//        LocalDateTime fechaSalida = LocalDateTime.now();
//        PuntoDeControl punto = new PuntoDeControl(13, "Calle Córdoba", "Calle Córdoba");
//        //PuntoDeControl punto = repositorioPuntoDeControl.buscarPC(23);
//
//        Assertions.assertThrows(PuntoDeControlEquivocado.class, () -> {
//            serviPack.notificarSalida(paquet.getLocalizador(), fechaSalida, punto);
//        });
//    }
// @BeforeEach
// void limpiadoBaseDeDatos() {
// limpiadoBaseDeDatos.limpiar();
// }
}
