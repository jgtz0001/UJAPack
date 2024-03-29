/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.beans;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.Paquete.Estado;
import es.ujaen.dae.ujapack.repositorios.RepositorioPuntoDeControl;
import java.io.IOException;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author PCJoseGabriel
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjapackApplication.class)
public class ServicioUjapackTest {

    @Autowired
    ServicioUjaPack serviPack;

    @Autowired
    LimpiadoBaseDeDatos limpiadoBaseDeDatos;

    @Autowired
    RepositorioPuntoDeControl repositorioPuntoDeControl;

    @Test
    public void TestAccesoServicioUjaPack() {
        Assertions.assertThat(serviPack).isNotNull();
    }

    @Test

    public void TestPruebaEnvios() throws IOException {

        Cliente Remitente1 = new Cliente("12323234", "jose", "camara", "jj@gmail.com", "jaen", "Jaén", "Jaén");
        serviPack.altaCliente(Remitente1);
        Cliente Destinatario0 = new Cliente("12334243", "juan", "pepe", "pp@gmail.com", "jaen", "Córdoba", "Córdoba");
        serviPack.altaCliente(Destinatario0);
        Cliente Destinatario1 = new Cliente("12334244", "kiko", "ola", "ola@gmail.com", "jaen", "Madrid", "Madrid");
        serviPack.altaCliente(Destinatario1);
        Cliente Destinatario2 = new Cliente("12334245", "pepe", "perez", "ppp@gmail.com", "jaen", "Toledo", "Toledo");
        serviPack.altaCliente(Destinatario2);
        Cliente Destinatario3 = new Cliente("12334246", "pola", "tere", "hila@gmail.com", "jaen", "Santa Cruz de Tenerife", "Santa Cruz de Tenerife");
        serviPack.altaCliente(Destinatario3);

        Paquete e0 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0, 0);
        Paquete e1 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario1, 0);
        Paquete e2 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario2, 0);
        Paquete e3 = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario3, 0);

        Assertions.assertThat(serviPack.comprobarNPuntosRuta(3, e0.getRuta().size()));
        Assertions.assertThat(serviPack.comprobarNPuntosRuta(4, e1.getRuta().size()));
        Assertions.assertThat(serviPack.comprobarNPuntosRuta(3, e2.getRuta().size()));
        Assertions.assertThat(serviPack.comprobarNPuntosRuta(5, e3.getRuta().size()));

        Assertions.assertThat(serviPack.comprobarImporte(0.004000000189989805, serviPack.calcularImporte(3, 1, 1, 1)));

    }

    @Test

    public void TestComprobarEstados() throws IOException {
        Cliente Remitente1 = new Cliente("12323234", "jose", "camara", "jj@gmail.com", "jaen", "Jaén", "Jaén");
        serviPack.altaCliente(Remitente1);
        Cliente Destinatario0 = new Cliente("12334243", "juan", "pepe", "pp@gmail.com", "jaen", "Córdoba", "Córdoba");
        serviPack.altaCliente(Destinatario0);

        Paquete paquet = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0, 0);

        Assertions.assertThat(paquet.getEstado().equals(Estado.EnTransito));

        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(0).getId());
        serviPack.notificarEntrada(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(1).getId());

        Assertions.assertThat(paquet.getEstado().equals(Estado.EnTransito));

        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(1).getId());
        serviPack.notificarEntrada(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(2).getId());

        Assertions.assertThat(paquet.getEstado().equals(Estado.EnReparto));

        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(2).getId());

        Assertions.assertThat(paquet.getEstado().equals(Estado.Entregado));
    }

    @Test

    public void TestRutaIncorrectaPuntoFueraDeRuta() throws IOException {
        Cliente Remitente1 = new Cliente("12323234", "jose", "camara", "jj@gmail.com", "jaen", "Jaén", "Jaén");
        serviPack.altaCliente(Remitente1);
        Cliente Destinatario0 = new Cliente("12334243", "juan", "pepe", "pp@gmail.com", "jaen", "Córdoba", "Córdoba");
        serviPack.altaCliente(Destinatario0);

        Paquete paquet = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0, 0);

        LocalDateTime fechaSalida = LocalDateTime.now();

        Assertions.assertThatThrownBy(() -> {
            serviPack.notificarSalida(paquet.getLocalizador(), fechaSalida, 5);
        });
    }

    @Test

    public void TestRutaIncorrectaPuntoDeRutaAtrasado() throws IOException {
        Cliente Remitente1 = new Cliente("12323234", "Jose", "Camara", "jj@gmail.com", "Jaén", "Jaén", "Jaén");
        serviPack.altaCliente(Remitente1);
        Cliente Destinatario0 = new Cliente("12334243", "Juan", "Pepe", "pp@gmail.com", "Córdoba", "Córdoba", "Córdoba");
        serviPack.altaCliente(Destinatario0);

        Paquete paquet = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0, 0);

        LocalDateTime fechaSalida = LocalDateTime.now();
        Assertions.assertThatThrownBy(() -> {
            serviPack.notificarSalida(paquet.getLocalizador(), fechaSalida, 1);
        });
    }

    @Test

    public void TestRutaIncorrectaPuntoDeRutaAdelantado() throws IOException {
        Cliente Remitente1 = new Cliente("12323234", "Jose", "Camara", "jj@gmail.com", "Jaén", "Jaén", "Jaén");
        serviPack.altaCliente(Remitente1);
        Cliente Destinatario0 = new Cliente("12334243", "Juan", "Pepe", "pp@gmail.com", "Córdoba", "Córdoba", "Córdoba");
        serviPack.altaCliente(Destinatario0);

        Paquete paquet = serviPack.altaEnvio(1, 1, 1, Remitente1, Destinatario0, 0);
        LocalDateTime fechaSalida = LocalDateTime.now();

        Assertions.assertThatThrownBy(() -> {
            serviPack.notificarSalida(paquet.getLocalizador(), fechaSalida, 13);
        });
    }

    @BeforeEach
    void limpiadoBaseDeDatos() {
        limpiadoBaseDeDatos.limpiar();
    }
}
