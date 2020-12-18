/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.Paquete.Estado;


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

    public DTOPaquete(int localizador, int numPuntosControl, String estado, 
                        float importe, float peso, float altura, String remitente, 
                        String destinatario) {
        this.localizador = localizador;
        this.numPuntosControl = numPuntosControl;
        this.estado = estado;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.remitente = remitente;
        this.destinatario = destinatario;
    }

    

    public DTOPaquete(Paquete paquete){
        this.localizador=paquete.getLocalizador();
        this.numPuntosControl=paquete.getControl();
        this.estado=paquete.getEstado();
        this.importe=paquete.getImporte();
        this.peso=paquete.getPeso();
        this.altura=paquete.getAltura();
        this.remitente=paquete.getRemitente().getDni();
        this.destinatario=paquete.getDestinatario().getDni();
        
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
    
    
}
