/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.beans.ServicioUjaPack;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.io.IOException;
import java.time.LocalDateTime;
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
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjapackApplication.class)
public class UjapackIntegrationTest {

    @Autowired
    ServicioUjaPack serviPack;

    @Test
    public void creaEnvio() throws IOException {

        Cliente Remitente1 = new Cliente("12323234", "", "", "", "", "Jaén", "Jaén");
        Cliente Destinatario0 = new Cliente("12334243", "", "", "", "", "Córdoba", "Córdoba");
        Cliente Destinatario1 = new Cliente("12334243", "", "", "", "", "Toledo", "Toledo");
        Cliente Destinatario2 = new Cliente("12334243", "", "", "", "", "Madrid", "Madrid");
        Cliente Destinatario3 = new Cliente("12334243", "", "", "", "", "Santa Cruz de Tenerife", "Santa Cruz de Tenerife");

//        ArrayList<PuntoDeControl> e0 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0);
//        ArrayList<PuntoDeControl> e1 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario1);
//        ArrayList<PuntoDeControl> e2 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario2);
//        ArrayList<PuntoDeControl> e3 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario3);
//
//        Assertions.assertEquals(3, e0.size());
//        Assertions.assertEquals(3, e1.size());
//        Assertions.assertEquals(4, e2.size());
//        Assertions.assertEquals(5, e3.size());
//
//        Assertions.assertEquals(0.004000000189989805, serviPack.calcularImporte(3, 1, 1, 1));

    }

    @Test
    public void testAvisaEstado() throws IOException {
        Cliente Remitente1 = new Cliente("12323234", "", "", "", "", "Jaén", "Jaén");
        Cliente Destinatario0 = new Cliente("12334243", "", "", "", "", "Córdoba", "Córdoba");

//        ArrayList<PuntoDeControl> e0 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0);

//        Paquete paquet = new Paquete(1234567890, (float) 0.004000000189989805, 1, 1, e0);
//
//        Assertions.assertEquals("EnTransito", paquet.getEstado().toString());
//        paquet.envia(LocalDateTime.now(), e0.get(1));
//
//        Assertions.assertNotNull(paquet.getPasanPaquetes().get(0).getFechaLlegada());
//
//        Assertions.assertEquals("EnReparto", paquet.getEstado().toString());
//        paquet.envia(LocalDateTime.now(), p);
//
//        paquet.envia(LocalDateTime.now(), p);
//        Assertions.assertEquals("Entregado", paquet.getEstado().toString());
    }
}
