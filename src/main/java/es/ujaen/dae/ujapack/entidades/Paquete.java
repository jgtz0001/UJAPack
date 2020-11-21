/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.excepciones.FechaIncorrecta;
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
        En_transito,
        En_reparto,
        Entregado,
        Extraviado;
    }

    public Paquete(int localizador, float importe, float peso, float altura,  ArrayList<PuntoDeControl> ruta) {
        this.localizador = localizador;
        this.numPuntosControl = 0;
        this.estado = estado.En_transito;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.ruta = ruta;
        this.pasanPaquetes = new ArrayList<PasoPorPuntoDeControl>();
        PasoPorPuntoDeControl primero = new PasoPorPuntoDeControl(ruta.get(0));
        pasanPaquetes.add(primero);
    }


    public static boolean checkEnvio(String dni1, String dni2, String dir1, String dir2, String loc1, String loc2) {

        if ((dni1 != dni2) || (dir1 != dir2) || (loc1 != loc2)) {
            return true;
        }

        return false;
    }


    public static boolean testRepiteEnvio(ArrayList<String> ruta1, ArrayList<String> ruta2, int localizador1, int localizador2) {
        if ((ruta1 == ruta2) && (localizador1 == localizador2)) {
            return false;
        }
        return true;
    }

    public void envia(LocalDateTime fechaSalida, PuntoDeControl punto) {
        Integer tama = pasanPaquetes.size();
        if (estado.equals(estado.En_transito)) {
            estado = estado.En_reparto;
        }

        if (tama.equals(numPuntosControl)) {
            if (estado.equals(estado.En_reparto)) {
                estado = Estado.Entregado;
            }
        } else {
            if (this.pasanPaquetes.get(tama-1).getFechaLlegada().isAfter(fechaSalida))
                throw new FechaIncorrecta();
            this.pasanPaquetes.get(tama-1).setFechaSalida(fechaSalida);
            PasoPorPuntoDeControl nuevo = new PasoPorPuntoDeControl(punto);
            pasanPaquetes.add(nuevo);
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
