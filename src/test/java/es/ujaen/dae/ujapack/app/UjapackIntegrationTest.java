/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.app;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.interfaces.ServicioUjaPack;
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
@SpringBootTest(classes={UjapackApplication.class})
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

    /**
     * Test of main method, of class UjapackApplication.
     */
    /*@Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        UjapackApplication.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
    @Test
    public void testLocalizador(){
      int localizadorCheck1= 123456789;
      int localizadorCheck2= 1234567891;
       
     
      Assertions.assertTrue(Paquete.checkLocalizador(localizadorCheck2));
      Assertions.assertFalse(Paquete.checkLocalizador(localizadorCheck1));
       
    }
@Test
    public void testRepiteLocalizador(){
      int localizadorCheck1= 1234567891;
      int localizadorCheck2= 1234567891;
      int localizadorCheck3= 1234515261;
    
     
     
      Assertions.assertTrue(Paquete.checkRepiteLocalizador(localizadorCheck2,localizadorCheck2));
            
      Assertions.assertFalse(Paquete.checkRepiteLocalizador(localizadorCheck1,localizadorCheck3));

       
    }
    @Test
    public void TestEnvio(){
      String dni1 = "26054589";
      String dni2 = "75896325";
      String dni3 = "75896325";
      String dir1 = "Av Jaen";
      String dir2 = "Calle Barcelona";
      String dir3 = "Calle Barcelona";
      String loc1 = "Jaen";
      String loc2 = "Madrid";
      String loc3 = "Madrid";
        
         Assertions.assertTrue(Paquete.checkEnvio(dni1,dni2,dir1,dir2,loc1,loc2));
         Assertions.assertFalse(Paquete.checkEnvio(dni2,dni3,dir2,dir3,loc2,loc3));
    }
}
