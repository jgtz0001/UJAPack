/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jenar
 */
public class Paquete {

    private int localizador;
    private int numPuntosControl;
    private Estado estado;
    private float importe;
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaSalida;
    private float peso;
    private float altura;
    private ArrayList<PasoPorPuntoDeControl> pasanPaquetes;
    private ArrayList<String> ruta;
    private Cliente remitente;
    private Cliente destinatario;


    public enum Estado {
        Transito,
        Reparto,
        Envio;
    }

    public Paquete(int localizador, float importe, float peso, float altura, PuntoDeControl p) {
        this.localizador = localizador;
        this.numPuntosControl = 0;
        this.estado = estado.Transito;
        this.importe = importe;
        this.fechaLlegada = null;
        this.fechaSalida = null;
        this.peso = peso;
        this.altura = altura;
        PasoPorPuntoDeControl primero = new PasoPorPuntoDeControl(p);
    }

    public static boolean checkLocalizador(int localizador) {
        if (Integer.toString(localizador).length() == 10) {
            return true;
        }
        return false;
    }

    public static boolean checkEnvio(String dni1, String dni2, String dir1, String dir2, String loc1, String loc2) {

        if ((dni1 != dni2) || (dir1 != dir2) || (loc1 != loc2)) {
            return true;
        }

        return false;
    }

    public static boolean checkRepiteLocalizador(int localizador1, int localizador2) {

        if (localizador1 == localizador2) {
            return true;
        }

        return false;
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
    public ArrayList<String> getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(ArrayList<String> ruta) {
        this.ruta = ruta;
    }

}
