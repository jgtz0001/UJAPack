/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.Paquete.Estado;


/**
 *
 * @author Pablo
 */
public class DTOPaquete {
    
    int localizador;
    int numPuntosControl;
    Estado estado;
    float importe;
    float peso;
    float altura;
    String remitente;
    String destinatario;

    public DTOPaquete(int localizador, int numPuntosControl, Estado estado, float importe, float peso, float altura, String remitente, String destinatario) {
        this.localizador = localizador;
        this.numPuntosControl = numPuntosControl;
        this.estado = estado;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.remitente = remitente;
        this.destinatario = destinatario;
    }

    public int getLocalizador() {
        return localizador;
    }

    public int getNumPuntosControl() {
        return numPuntosControl;
    }

    public Estado getEstado() {
        return estado;
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
    
    
}
