/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.beans.LimpiadoBaseDeDatos;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOCliente;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOPaquete;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTORuta;
import es.ujaen.dae.ujapack.entidades.Paquete;
import java.io.IOException;
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
    public void TestComprobarProvinciasCorrectas() throws IOException {
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
                "Madrid",
                "Madrid");

        DTOPaquete paq = new DTOPaquete(
                1111111115,
                "EnTransito",
                3.0f,
                3.0f,
                9.0f,
                remitente,
                destinatario
        );
        TestRestTemplate restTemplate = new TestRestTemplate(restTemplateBuilder.basicAuthentication("admin", "admin"));
        ResponseEntity<DTOCliente> respuesta = restTemplate.postForEntity(
                "/clientes",
                destinatario,
                DTOCliente.class
        );

        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
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

    @Test
    public void testConsultarEstadoEnvio() {
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
        DTOPaquete envioEstado = respuesta.getBody();
        Assertions.assertThat(envioEstado.getEstado()).isEqualTo(Paquete.Estado.EnTransito.toString());
    }

    /*
    * Actualiza el punto de control, indicando la salida de este.
     */
    @Test
    public void testActualizarPasoPuntoControlCentro() {
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
                "Córdoba",
                "Córdoba");

        DTOPaquete paq = new DTOPaquete(
                1111111113,
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

        DTOPaquete paqueteCreado = respuesta.getBody();
        DTORuta rutaEnvioCreado = new DTORuta(paqueteCreado.getRuta(), paqueteCreado.getEstado());

        ResponseEntity<Void> respuesta2 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarcentrologistico/{idCentro}",
                "salida", Void.class, paqueteCreado.getLocalizador(), rutaEnvioCreado.getRuta().get(0).getId()
        );

        Assertions.assertThat(respuesta2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /*
    * Actualizará la llegada del paquete al centro logístico.
     */
    @Test
    public void testActualizarLlegadaPuntoControlCentro() {
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
                "Córdoba",
                "Córdoba");

        DTOPaquete paq = new DTOPaquete(
                1111111113,
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

        DTOPaquete paqueteCreado = respuesta.getBody();

        ResponseEntity<Void> respuesta2 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarcentrologistico/{idCentro}",
                "salida", Void.class, paqueteCreado.getLocalizador(), 16
        );

        Assertions.assertThat(respuesta2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Void> respuesta3 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarsalidacentrologistico/{idCentro}",
                "entrada", Void.class, paqueteCreado.getLocalizador(), 1
        );

        Assertions.assertThat(respuesta3.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /*
    * Este test, quiere actualizar la llegada a un punto de control,
    * sin embargo este todavia no ha salido del anterior, lo que generará un error.
     */
    @Test
    public void testActualizarLlegadaPuntoControlCentroIncorrecto() {
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
                "Córdoba",
                "Córdoba");

        DTOPaquete paq = new DTOPaquete(
                1111111113,
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

        DTOPaquete paqueteCreado = respuesta.getBody();

        ResponseEntity<Void> respuesta2 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarsalidacentrologistico/{idCentro}",
                "salida", Void.class, paqueteCreado.getLocalizador(), 1
        );

        Assertions.assertThat(respuesta2.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    /*
    * Actualiza el punto de control, indicando la salida de este, sin embargo
    * El punto de control es incorrecto, lo que generará un error.
     */
    @Test
    public void testActualizarPasoPuntoControlCentroIncorrecto() {
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
                "Córdoba",
                "Córdoba");

        DTOPaquete paq = new DTOPaquete(
                1111111113,
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

        DTOPaquete paqueteCreado = respuesta.getBody();

        ResponseEntity<Void> respuesta2 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarcentrologistico/{idCentro}",
                "salida", Void.class, paqueteCreado.getLocalizador(), 1
        );

        Assertions.assertThat(respuesta2.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    /*
    * Este test realizara la ruta completa y actualizara el estado del paquete
     */
    @Test
    public void testRealizarRutaCompleta() {
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
                "Córdoba",
                "Córdoba");

        DTOPaquete paq = new DTOPaquete(
                1111111113,
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

        DTOPaquete paqueteCreado = respuesta.getBody();

        ResponseEntity<Void> respuesta2 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarcentrologistico/{idCentro}",
                "salida", Void.class, paqueteCreado.getLocalizador(), 16
        );

        Assertions.assertThat(respuesta2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Void> respuesta3 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarsalidacentrologistico/{idCentro}",
                "entrada", Void.class, paqueteCreado.getLocalizador(), 1
        );

        Assertions.assertThat(respuesta3.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Void> respuesta4 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarcentrologistico/{idCentro}",
                "salida", Void.class, paqueteCreado.getLocalizador(), 1
        );

        Assertions.assertThat(respuesta4.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Void> respuesta5 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarsalidacentrologistico/{idCentro}",
                "entrada", Void.class, paqueteCreado.getLocalizador(), 13
        );

        Assertions.assertThat(respuesta5.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Void> respuesta6 = restTemplate.postForEntity(
                "/paquetes/{localizador}/notificarcentrologistico/{idCentro}",
                "salida", Void.class, paqueteCreado.getLocalizador(), 13
        );

        Assertions.assertThat(respuesta6.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @BeforeEach
    void limpiadoBaseDeDatos() {
        limpiadoBaseDeDatos.limpiar();
    }
}
