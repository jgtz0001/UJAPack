/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Pablo
 */
@Entity
public class PasoPorPuntoDeControl implements Serializable {
    
    @NotBlank
    public PuntoDeControl pasoControl;
    @Id
    @NotBlank
    private LocalDateTime fechaLlegada;
    @NotBlank
    private LocalDateTime fechaSalida;
    
    @OneToOne
    @JoinColumn(name="fecha_llegada")
    List<PuntoDeControl> PasoControl;
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

   

    public PasoPorPuntoDeControl(PuntoDeControl p) {
        fechaLlegada = LocalDateTime.now();
        pasoControl = p;
    }

    void salida() {
        setFechaSalida(LocalDateTime.now());
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

}
