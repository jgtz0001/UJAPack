/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Pablo
 */
public class PaqueteTest {
    
    public PaqueteTest(){
    }
    
    @Test
    void testValidaPaquete() {
        
        Paquete paquete = new Paquete(12345678, 25.0f, 2.0f, 1.0f);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        Set<ConstraintViolation<Paquete>> violations = validator.validate(paquete);
        
        Assertions.assertThat(violations).isEmpty();
    }
    
    @Test
    void testNoValidaRutaPaquete() {
        
        Paquete paquete = new Paquete(12345678, 25.0f, 2.0f, 1.0f);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        Set<ConstraintViolation<Paquete>> violations = validator.validate(paquete);
        
        Assertions.assertThat(violations).isEmpty();
    }
}
