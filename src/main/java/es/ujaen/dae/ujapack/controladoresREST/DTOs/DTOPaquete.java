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
    String estado;
    float importe;
    float peso;
    float altura;

    private DTOCliente rem;

    private DTOCliente dest;

    private List<PuntoDeControl> ruta;

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
    }

    public DTOPaquete(int localizador, String estado, float importe, float peso, float altura, DTOCliente remitente, DTOCliente desti, List<PuntoDeControl> ruta) {
        this.localizador = localizador;
        this.estado = estado;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.rem = remitente;
        this.dest = desti;
        this.ruta = ruta;
    }

    public DTOPaquete(DTOCliente rem, DTOCliente dest) {
        this.rem = rem;
        this.dest = dest;
        this.estado = EnTransito.toString();
    }

    public DTOPaquete(Paquete paquete) {
        this.localizador = paquete.getLocalizador();
        this.estado = paquete.getEstado();
        this.importe = paquete.getImporte();
        this.peso = paquete.getPeso();
        this.altura = paquete.getAltura();
        this.rem = new DTOCliente(paquete.getRemitente());
        this.dest = new DTOCliente(paquete.getDestinatario());
        this.ruta = paquete.getRuta();
    }

    public int getLocalizador() {
        return localizador;
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

    public Paquete aPaquete() {
        return new Paquete(localizador, importe, peso, altura);
    }

    public Paquete bPaquete() {
        return new Paquete(localizador, importe, peso, altura);
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

    public List<PuntoDeControl> getRuta() {
        return ruta;
    }

}
