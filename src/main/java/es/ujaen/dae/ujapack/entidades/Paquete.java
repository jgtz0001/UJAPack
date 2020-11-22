/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.excepciones.FechaIncorrecta;
import es.ujaen.dae.ujapack.excepciones.PuntoDeControlEquivocado;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author jenar
 */
public class Paquete implements Serializable {

    private int localizador;
    private int numPuntosControl;
    private Estado estado;
    private float importe;
    private float peso;
    private float altura;
    private ArrayList<PasoPorPuntoDeControl> pasanPaquetes;
    private ArrayList<PuntoDeControl> ruta;
    private Cliente remitente;
    private Cliente destinatario;

    public enum Estado {
        EnTransito,
        EnReparto,
        Entregado,
        Extraviado;
    }

    public Paquete(int localizador, float importe, float peso, float altura, ArrayList<PuntoDeControl> ruta) {
        this.localizador = localizador;
        this.numPuntosControl = 0;
        this.estado = estado.EnTransito;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.ruta = ruta;
        this.pasanPaquetes = new ArrayList<PasoPorPuntoDeControl>();

        PasoPorPuntoDeControl primero = new PasoPorPuntoDeControl(ruta.get(0), LocalDateTime.now());
        pasanPaquetes.add(primero);
    }

    public void notificaSalida(LocalDateTime fechaSalida, PuntoDeControl punto) {
        for (int i = 0; i < pasanPaquetes.size(); i++) {
            if (pasanPaquetes.get(i).pasoControl.equals(punto))//Aquí encontraria un punto de control repetido, por lo que no valdría.
            {
                throw new PuntoDeControlEquivocado();
            }
        }
        if (!ruta.contains(punto)) {
            throw new PuntoDeControlEquivocado(); // El punto de control no correspondería con la ruta.
        }
        PasoPorPuntoDeControl nuevo = new PasoPorPuntoDeControl(punto, fechaSalida);
        pasanPaquetes.add(nuevo);
    }

    public void notificaEntrada(LocalDateTime fechaEntrada) {
        Integer tama = pasanPaquetes.size();
        if (estado.equals(estado.EnTransito)) {
            estado = estado.EnReparto;
        }

        if (tama.equals(numPuntosControl)) {
            if (estado.equals(estado.EnReparto)) {
                estado = Estado.Entregado;
            }
        } else {
            if (this.pasanPaquetes.get(tama - 1).getFechaLlegada().isAfter(fechaEntrada)) {
                throw new FechaIncorrecta();
            }
            this.pasanPaquetes.get(tama - 1).setFechaSalida(fechaEntrada);
        }
    }

    /**
     * @return the pasanPaquetes
     */
    public ArrayList<PasoPorPuntoDeControl> getPasanPaquetes() {
        return pasanPaquetes;
    }

    /**
     * @param pasanPaquetes the pasanPaquetes to set
     */
    public void setPasanPaquetes(ArrayList<PasoPorPuntoDeControl> pasanPaquetes) {
        this.pasanPaquetes = pasanPaquetes;
    }

    /**
     * @return the remitente
     */
    public Cliente getRemitente() {
        return remitente;
    }

    public Estado getEstado() {
        return estado;
    }

    /**
     * @return the ruta
     */
    public ArrayList<PuntoDeControl> getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(ArrayList<PuntoDeControl> ruta) {
        this.ruta = ruta;
        this.numPuntosControl = ruta.size();
    }

}
