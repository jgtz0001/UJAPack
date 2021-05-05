/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.Paquete;
import static es.ujaen.dae.ujapack.entidades.Paquete.Estado.EnTransito;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.util.List;

/**
 *
 * @author Pablo
 */
public class DTOPaquete {

    int localizador;
    int numPuntosControl;
    String estado;
    float importe;
    float peso;
    float altura;
    String remitente;
    String destinatario;
    List<PuntoDeControl> ruta;

    private DTOCliente rem;
    
    private DTOCliente dest;
    
    public DTOPaquete() {
    }

    public DTOPaquete(int localizador, String estado, float importe, float peso, float altura, DTOCliente remitente, DTOCliente desti) {
        this.localizador = localizador;
        this.estado = estado;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.rem = remitente;
        this.dest = desti;
        this.remitente = remitente.nombre;
        this.destinatario = desti.nombre;
    }

    public DTOPaquete(DTOCliente rem, DTOCliente dest) {
        this.remitente = rem.nombre;
        this.destinatario = dest.nombre;
        this.rem = rem;
        this.dest = dest;
        this.estado = EnTransito.toString();
    }

    public DTOPaquete(Paquete paquete) {
        this.localizador = paquete.getLocalizador();
        this.numPuntosControl = paquete.getControl();
        this.estado = paquete.getEstado();
        this.importe = paquete.getImporte();
        this.peso = paquete.getPeso();
        this.altura = paquete.getAltura();
        if (paquete.getRemitente() != null) {
            this.remitente = paquete.getRemitente().getDni();
        } else {
            this.remitente = "";
        }
        if (paquete.getDestinatario() != null) {
            this.destinatario = paquete.getDestinatario().getDni();
        } else {
            this.destinatario = "";
        }

        this.ruta = paquete.getRuta();

    }

    public int getLocalizador() {
        return localizador;
    }

    public int getNumPuntosControl() {
        return numPuntosControl;
    }

    public String getEstado() {
        return estado.toString();
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

    public String getRemitente() {
        return remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public Paquete aPaquete() {
        return new Paquete(localizador, importe, peso, altura);
    }

    public Paquete bPaquete() {
        return new Paquete(localizador, importe, peso, altura, ruta);
    }

    /**
     * @return the rem
     */
    public DTOCliente getRem() {
        return rem;
    }

    /**
     * @return the dest
     */
    public DTOCliente getDest() {
        return dest;
    }

}
