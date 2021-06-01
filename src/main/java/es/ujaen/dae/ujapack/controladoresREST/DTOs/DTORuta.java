/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo
 */
public class DTORuta {

    private List<DTOPuntoDeControl> ruta;
    //punto de ruta por el que va 
    String estado;

    public DTORuta() {
        this.estado = "EnTransito";
        this.ruta = new ArrayList<>();
    }

    public DTORuta(List<PuntoDeControl> ruta, String estado) {
        this.estado = estado;
        this.ruta = new ArrayList<>();
        for (PuntoDeControl puntoControl : ruta) {
            this.ruta.add(new DTOPuntoDeControl(puntoControl));
        }
    }
   

    public DTORuta(Paquete paquete) {
        this.estado = paquete.getEstado();
        this.ruta = new ArrayList<>();
        for (int i = 0; i < paquete.getRuta().size(); i++){
            this.ruta.add(new DTOPuntoDeControl(paquete.getRuta().get(i)));
        }
    }

    public List<DTOPuntoDeControl> getRuta() {
        return ruta;
    }

    public String getEstado() {
        return estado;
    }

}

