/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.excepciones.PuntoDeControlEquivocado;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author jenar
 */
@Entity
public class Paquete implements Serializable {

    @Id
    @Column(length = 10)
    private int localizador;
    @Positive
    private int numPuntosControl;
    @Enumerated(EnumType.ORDINAL)
    private Estado estado;
    @PositiveOrZero
    private float importe;
    @Positive
    private float peso;
    @Positive
    private float altura;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
//    @Fetch(value = FetchMode.SELECT)
    private List<PasoPorPuntoDeControl> pasanPaquetes;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<PuntoDeControl> ruta;

    @ManyToOne
    private Cliente remitente;

    @ManyToOne
    private Cliente destinatario;

    public enum Estado {
        EnTransito,
        EnReparto,
        Entregado,
        Extraviado;
    }

    public Paquete() {

    }

    public Paquete(int localizador, float importe, float peso, float altura) {
        this.localizador = localizador;
        this.estado = Estado.EnTransito;
        this.importe = importe;
        this.numPuntosControl = 1;
        this.peso = peso;
        this.altura = altura;
    }

    public Paquete(int localizador, float importe, float peso, float altura, List<PuntoDeControl> ruta) {
        this.localizador = localizador;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.ruta = ruta;
    }

    public Paquete(int localizador, float importe, float peso, float altura, List<PuntoDeControl> ruta, Cliente remitente, Cliente destinatario) {
        this.localizador = localizador;
        this.numPuntosControl = ruta.size();
        this.estado = estado.EnTransito;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.ruta = ruta;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.pasanPaquetes = new ArrayList<PasoPorPuntoDeControl>();

        PasoPorPuntoDeControl primero = new PasoPorPuntoDeControl(ruta.get(0), LocalDateTime.now());
        pasanPaquetes.add(primero);
    }

    void controlaExcepcionesSalida(PuntoDeControl punto) {
        boolean esta = false;
        Integer tama = pasanPaquetes.size();
        for (int i = 0; i < ruta.size(); i++) {
            if (ruta.get(i).getLocalizacion().equals(punto.localizacion)) {
                esta = true;
                break;
            }
        }
        if (!esta) {
            throw new PuntoDeControlEquivocado();
        }

        if (!tama.equals(numPuntosControl)) {
            if (!ruta.get(pasanPaquetes.size()).localizacion.equals(punto.localizacion)) {
                throw new PuntoDeControlEquivocado();
            }
        }

    }

    void controlaExcepcionesEntrada(PuntoDeControl punto) {
        for (int i = 0; i < pasanPaquetes.size(); i++) {
            if (pasanPaquetes.get(i).pasoControl.localizacion.equals(punto.localizacion)) {
                throw new PuntoDeControlEquivocado();
            }
        }
        boolean esta = false;
        for (int i = 0; i < ruta.size(); i++) {
            if (ruta.get(i).getLocalizacion().equals(punto.localizacion)) {
                esta = true;
                break;
            }
        }
        if (!esta) {
            throw new PuntoDeControlEquivocado();
        }

        if (!ruta.get(pasanPaquetes.size()).localizacion.equals(punto.localizacion)) {
            throw new PuntoDeControlEquivocado();
        }
    }

    public void notificaEntrada(LocalDateTime fechaEntrada, PuntoDeControl punto) {
        controlaExcepcionesEntrada(punto);

        PasoPorPuntoDeControl nuevo = new PasoPorPuntoDeControl(punto, fechaEntrada);
        pasanPaquetes.add(nuevo);
    }

    public void notificaSalida(LocalDateTime fechaSalida, PuntoDeControl punto) {
        controlaExcepcionesSalida(punto);
        Integer tama = pasanPaquetes.size();

        if (tama.equals(numPuntosControl)) {
            if (estado.equals(estado.EnReparto)) {
                estado = estado.Entregado;
            }

        } else {
            if (tama.equals(numPuntosControl - 1)) {
                estado = estado.EnReparto;
            }
            this.pasanPaquetes.get(tama - 1).setFechaLlegada(fechaSalida);
        }
    }

    /**
     * @return the pasanPaquetes
     */
    public List<PasoPorPuntoDeControl> getPasanPaquetes() {
        return pasanPaquetes;
    }

    /**
     * @param pasanPaquetes the pasanPaquetes to set
     */
    public void setPasanPaquetes(List<PasoPorPuntoDeControl> pasanPaquetes) {
        this.pasanPaquetes = pasanPaquetes;
    }

    /**
     * @return the remitente
     */
    public Cliente getRemitente() {
        return remitente;
    }

    public String getEstado() {
        return estado.toString();
    }

    /**
     * @return the numPuntosControl
     */
    public int getControl() {
        return numPuntosControl;
    }

    /**
     * @return the ruta
     */
    public List<PuntoDeControl> getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(List<PuntoDeControl> ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the localizador
     */
    public int getLocalizador() {
        return localizador;
    }

    public float getImporte() {
        return importe;
    }

    public float getPeso() {
        return peso;
    }

    public float getAltura() {
        return altura;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

}
