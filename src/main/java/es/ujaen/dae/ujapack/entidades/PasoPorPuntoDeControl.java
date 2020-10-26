/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.time.LocalDateTime;

/**
 *
 * @author Pablo
 */
public class PasoPorPuntoDeControl {
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaSalida;
    private PuntoDeControl pasoControl;
    
    PasoPorPuntoDeControl(PuntoDeControl p){
        fechaLlegada = LocalDateTime.now();
        pasoControl = p;
    }

}
