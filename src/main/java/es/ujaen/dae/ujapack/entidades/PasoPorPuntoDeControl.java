/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PastOrPresent;

/**
 *
 * @author Pablo
 */
@Entity
public class PasoPorPuntoDeControl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    public PuntoDeControl pasoControl;
    @PastOrPresent
    private LocalDateTime fechaLlegada;
    @PastOrPresent
    private LocalDateTime fechaSalida;

    public PasoPorPuntoDeControl() {
    }

    public PasoPorPuntoDeControl(PuntoDeControl p, LocalDateTime fechaEntrada) {
        fechaLlegada = fechaEntrada;
        pasoControl = p;
    }

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

    void salida() {
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

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

}
