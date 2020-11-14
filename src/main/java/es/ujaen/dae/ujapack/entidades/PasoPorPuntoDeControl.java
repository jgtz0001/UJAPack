/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Pablo
 */
    @Entity
public class PasoPorPuntoDeControl implements Serializable{
    @Id
    @NotBlank
    private PuntoDeControl pasoControl;
    @NotBlank
    private LocalDateTime fechaLlegada;
    @NotBlank
    private LocalDateTime fechaSalida;
   
    
    PasoPorPuntoDeControl(PuntoDeControl p){
        fechaLlegada = LocalDateTime.now();
        pasoControl = p;
    }
    
    void salida(){
        fechaSalida = LocalDateTime.now();
    }

}
