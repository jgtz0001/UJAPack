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
    public int numPuntosControl;
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
        this.numPuntosControl = ruta.size();
        this.estado = estado.EnTransito;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.ruta = ruta;
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
            throw new PuntoDeControlEquivocado(); // El punto de control no correspondería con la ruta.
        }

        if (!tama.equals(numPuntosControl)) {
            if (!ruta.get(pasanPaquetes.size()).localizacion.equals(punto.localizacion)) {
                throw new PuntoDeControlEquivocado(); // El punto de control no correspondería con la ruta.
            }
        }

    }

    void controlaExcepcionesEntrada(PuntoDeControl punto) {
        for (int i = 0; i < pasanPaquetes.size(); i++) {
            if (pasanPaquetes.get(i).pasoControl.localizacion.equals(punto.localizacion))//Aquí encontraria un punto de control repetido, por lo que no valdría.
            {
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
            throw new PuntoDeControlEquivocado(); // El punto de control no correspondería con la ruta.
        }

        if (!ruta.get(pasanPaquetes.size()).localizacion.equals(punto.localizacion)) {
            throw new PuntoDeControlEquivocado(); // El punto de control proporcionado esta adelantado con la ruta.
        }
    }

    public void notificaEntrada(LocalDateTime fechaEntrada, PuntoDeControl punto) {
        controlaExcepcionesEntrada(punto);

        PasoPorPuntoDeControl nuevo = new PasoPorPuntoDeControl(punto, fechaEntrada);
        pasanPaquetes.add(nuevo);
    }

    public void notificaSalida(LocalDateTime fechaSalida, PuntoDeControl punto) {
        controlaExcepcionesSalida(punto);     // Con esto te aseguras de que el punto de control es el correcto.
        Integer tama = pasanPaquetes.size();

        if (tama.equals(numPuntosControl)) {
            if (estado.equals(estado.EnReparto)) {
                estado = estado.Entregado;
            }

        } else {
            if (tama.equals(numPuntosControl - 1)) {
                estado = estado.EnReparto;
            }
//            if (this.pasanPaquetes.get(tama - 1).getFechaLlegada().isAfter(fechaSalida)) {
//                throw new FechaIncorrecta();
//            }
            this.pasanPaquetes.get(tama - 1).setFechaLlegada(fechaSalida);
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
    public ArrayList<PuntoDeControl> getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(ArrayList<PuntoDeControl> ruta) {
        this.ruta = ruta;
        //this.numPuntosControl = ruta.size();
    }

    /**
     * @return the localizador
     */
    public int getLocalizador() {
        return localizador;
    }

}
