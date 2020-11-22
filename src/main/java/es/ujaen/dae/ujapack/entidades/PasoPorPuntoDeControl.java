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
public class PasoPorPuntoDeControl{

    /**
     * @return the fechaLlegada
     */
    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    /**
     * @return the fechaSalida
     */
    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public PuntoDeControl pasoControl;
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaSalida;
   
    
    public PasoPorPuntoDeControl(PuntoDeControl p, LocalDateTime fechaEntrada){
        fechaLlegada = fechaEntrada;
        pasoControl = p;
    }
    
    void salida(){
        setFechaSalida(LocalDateTime.now());
    }

    /**
     * @return the pasoControl
     */
    public PuntoDeControl getPasoControl() {
        return pasoControl;
    }

    /**
     * @param pasoControl the pasoControl to set
     */
    public void setPasoControl(PuntoDeControl pasoControl) {
        this.pasoControl = pasoControl;
    }

    /**
     * @param fechaLlegada the fechaLlegada to set
     */
    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    /**
     * @param fechaSalida the fechaSalida to set
     */
    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

}
