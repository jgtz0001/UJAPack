/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.beans.LimpiadoBaseDeDatos;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOCliente;
import java.time.LocalDate;
import java.util.List;
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
public class ControladorClientesTest {

    @LocalServerPort
    int localPort;

    @Autowired
    MappingJackson2HttpMessageConverter springBootJacksonConverter;

    @Autowired
    LimpiadoBaseDeDatos limpiadoBaseDeDatos;

    TestRestTemplate restTemplate;

    /**
     * Crear un TestRestTemplate para las pruebas
     */
    @PostConstruct
    void crearRestTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localPort + "/ujapack")
                .additionalMessageConverters(List.of(springBootJacksonConverter));

        restTemplate = new TestRestTemplate(restTemplateBuilder);
    }

//    /**
//     * Intento de creación de un cliente inválido
//     */
//    @Test
//    public void testAltaClienteInvalido() {
//        DTOCliente cliente = new DTOCliente(
//                "11995667D",
//                "Jenaro",
//                "Camara Colmenero",
//                "jenarogmail.com",
//                "Calle La Calle 13",
//                "Jamilena",
//                "Jaén",                
//                "clave");
//
//        ResponseEntity<DTOCliente> respuesta = restTemplate.postForEntity(
//                "/clientes",
//                cliente,
//                DTOCliente.class
//        );
//
//        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }

//    @Test
//    public void testAltaCliente() {
//
//    }

    @BeforeEach
    void limpiadoBaseDeDatos() {
        limpiadoBaseDeDatos.limpiar();
    }
}
