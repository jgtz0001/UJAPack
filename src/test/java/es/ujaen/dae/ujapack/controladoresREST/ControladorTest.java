/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.beans.LimpiadoBaseDeDatos;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOCliente;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOPaquete;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Test para controlador REST de clientes
 *
 * @author Pablo
 */
@SpringBootTest(classes = es.ujaen.dae.ujapack.app.UjapackApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControladorTest {

    @LocalServerPort
    int localPort;

    @Autowired
    MappingJackson2HttpMessageConverter springBootJacksonConverter;

    @Autowired
    LimpiadoBaseDeDatos limpiadoBaseDeDatos;

    RestTemplateBuilder restTemplateBuilder;

    /**
     * Crear un TestRestTemplate para las pruebas
     */
    @PostConstruct
    void crearRestTemplateBuilder() {
        restTemplateBuilder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localPort + "/ujapack")
                .additionalMessageConverters(Arrays.asList(springBootJacksonConverter));
    }

    /**
     * Intento de creación de un cliente inválido
     */
    @Test
    public void testAltaClienteInvalido() {
        DTOCliente cliente = new DTOCliente(
                "119956672451",
                "Jenaro",
                "Camara Colmenero",
                "jenaro@gmail.com",
                "Calle La Calle 13",
                "Jamilena",
                "Jaén");

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOCliente> respuesta = restTemplate.postForEntity(
                "/clientes",
                cliente,
                DTOCliente.class
        );

        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void testAltaCliente() {
        DTOCliente cliente = new DTOCliente(
                "11995667",
                "Jenaro",
                "Camara Colmenero",
                "jenaro@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOCliente> respuesta = restTemplate.postForEntity(
                "/clientes",
                cliente,
                DTOCliente.class
        );

        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    public void testAltaPaquete() {
          DTOCliente remitente = new DTOCliente(
                "11995665",
                "Jenaro",
                "Camara Colmenero",
                "jenaroo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");

        DTOCliente destinatario = new DTOCliente(
                "11995668",
                "Jenaro",
                "Camara Colmenero",
                "jenarooo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");
        DTOPaquete paquete = new DTOPaquete(
                1111111118,
                "EnTransito",
                10.0f,
                5.1f,
                3.0f,
                remitente,
                destinatario
        );

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOPaquete> respuesta = restTemplate.postForEntity(
                "/paquetes",
                paquete,
                DTOPaquete.class
        );

        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }
    
        @Test
    public void testAltaPaqueteIncorrecto() {
         DTOCliente remitente = new DTOCliente(
                "11995665",
                "Jenaro",
                "Camara Colmenero",
                "jenaroo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");

        DTOCliente destinatario = new DTOCliente(
                "11995668",
                "Jenaro",
                "Camara Colmenero",
                "jenarooo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");
        DTOPaquete paquete = new DTOPaquete(
                1111,
                "EnTransito",
                14.0f,
                6.4f,
                3.0f,
                remitente,
                destinatario
        );

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOPaquete> respuesta = restTemplate.postForEntity(
                "/paquetes",
                paquete,
                DTOPaquete.class
        );

        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    }
    
    
    @Test
    public void testConsultarEstadoPaquete() {

        DTOCliente remitente = new DTOCliente(
                
                "11995665",
                "Jenaro",
                "Camara Colmenero",
                "jenaroo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");

        DTOCliente destinatario = new DTOCliente(
                
                "11995668",
                "Jenaro",
                "Camara Colmenero",
                "jenarooo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");
        
        DTOPaquete paquete = new DTOPaquete(
                1111111113,
                "EnTransito",
                8,
                5,
                3.2f,
                remitente,
                destinatario
        );

        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOPaquete> respuesta = restTemplate.postForEntity(
                "/paquetes",
                paquete,
                DTOPaquete.class
        );
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        DTOPaquete paqueteEstado = respuesta.getBody();
        Assertions.assertThat(paqueteEstado.getEstado()).isEqualTo((Paquete.Estado.EnTransito).toString());
    }
    
    @Test
    public void testBuscarPaquete() {

        DTOCliente remitente = new DTOCliente(
                "11995668",
                "Jenaro",
                "Camara Colmenero",
                "jenarooo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");

        DTOCliente destinatario = new DTOCliente(
                "11995665",
                "Jenaro",
                "Camara Colmenero",
                "jenaroo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");
        
        DTOPaquete paq = new DTOPaquete(
                1111111112,
                "EnTransito",
                20.0f,
                3.5f,
                9.0f,
                remitente,
                destinatario
        );

       TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
       ResponseEntity<DTOPaquete> respuesta = restTemplate.postForEntity(
                "/paquetes",
                paq,
                DTOPaquete.class
        );
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        DTOPaquete PaqueteCreado = respuesta.getBody();
        TestRestTemplate restTemplateUsuario = new TestRestTemplate(restTemplateBuilder.basicAuthentication("usuario", "usuario"));
        ResponseEntity<DTOPaquete> respuestaEnvio = restTemplateUsuario.getForEntity("/paquetes/{localizador}", DTOPaquete.class, PaqueteCreado.getLocalizador());
        Assertions.assertThat(respuestaEnvio.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
       public void TestComprobarProvinciasIncorrectas() throws IOException {
       DTOCliente remitente = new DTOCliente(
                "11995668",
                "Jenaro",
                "Camara Colmenero",
                "jenarooo@gmail.com",
                "Calle La Calle 13",
                "Jaén",
                "Jaén");
        
          DTOCliente destinatario = new DTOCliente(
                "11995665",
                "Jose Gabriel",
                "De Torre Zafra",
                "zafra@gmail.com",
                "Calle Jaén 13",
                "Madd",
                "Madd");

           DTOPaquete paq = new DTOPaquete(
                1111111115,
                "EnTransito",
                3.0f,
                3.0f,
                9.0f,
                remitente,
                destinatario
        );
             TestRestTemplate restTemplateUsuario = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
              ResponseEntity<DTOPaquete> respuesta = restTemplateUsuario.postForEntity(
                "/paquetes",
                paq,
                DTOPaquete.class
        );
        DTOPaquete PaqueteCreado = respuesta.getBody();
        ResponseEntity<DTOPaquete> respuestaEnvio = restTemplateUsuario.getForEntity("/paquetes/{localizador}", DTOPaquete.class, PaqueteCreado.getLocalizador());
        Assertions.assertThat(respuestaEnvio.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
       }

//       @Test
//     
//    public void TestComprobarEstados() throws IOException {
//       DTOCliente remitente = new DTOCliente(
//                "11995668",
//                "Jenaro",
//                "Camara Colmenero",
//                "jenarooo@gmail.com",
//                "Calle La Calle 13",
//                "Jaén",
//                "Jaén");
//        
//          DTOCliente destinatario = new DTOCliente(
//                "11995665",
//                "Jose Gabriel",
//                "De Torre Zafra",
//                "zafra@gmail.com",
//                "Calle Jaén 13",
//                "Madrid",
//                "Madrid");
//
//           DTOPaquete paq = new DTOPaquete(
//                1111111115,
//                "EnTransito",
//                3.0f,
//                3.0f,
//                9.0f,
//                remitente,
//                destinatario
//        );
//
//        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
//          ResponseEntity<DTOPaquete> respuesta = restTemplate.postForEntity(
//                "/paquetes",
//                paq,
//                DTOPaquete.class
//        );
//        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//        DTOPaquete PaqueteCreado = respuesta.getBody();
//        TestRestTemplate restTemplateUsuario = new TestRestTemplate(restTemplateBuilder.basicAuthentication("usuario", "usuario"));
//        ResponseEntity<DTOPaquete> respuestaEnvio = restTemplateUsuario.getForEntity("/paquetes/{localizador}", DTOPaquete.class, PaqueteCreado.getEstado());
//        Assertions.assertThat(respuestaEnvio.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(paquet.getEstado().equals(Paquete.Estado.EnTransito));
//
//        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(1).getId());
//        serviPack.notificarEntrada(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(1).getId());
//
//        Assertions.assertThat(paquet.getEstado().equals(Paquete.Estado.EnTransito));
//
//        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(2).getId());
//        serviPack.notificarEntrada(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(2).getId());
//
//        Assertions.assertThat(paquet.getEstado().equals(Paquete.Estado.EnReparto));
//
//        serviPack.notificarSalida(paquet.getLocalizador(), LocalDateTime.now(), paquet.getRuta().get(2).getId());
//
//        Assertions.assertThat(paquet.getEstado().equals(Paquete.Estado.Entregado));
//    }


    @BeforeEach
    void limpiadoBaseDeDatos() {
        limpiadoBaseDeDatos.limpiar();
    }
}
