/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.app.LimpiadoBaseDeDatos;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOCliente;
import java.util.List;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Test para controlador REST de clientes
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
        
    /** Crear un TestRestTemplate para las pruebas */
    @PostConstruct
    void crearRestTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localPort + "/ujapack")
                .additionalMessageConverters(List.of(springBootJacksonConverter));
        
        restTemplate = new TestRestTemplate(restTemplateBuilder);
    }    
    


    @Test
    public void testAltaCliente() {
        
    }
    
    @BeforeEach
    void limpiadoBaseDeDatos() {
        limpiadoBaseDeDatos.limpiar();
    }
}
