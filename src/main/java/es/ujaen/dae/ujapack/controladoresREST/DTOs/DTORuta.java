/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo
 */
public class DTORuta {
     private List<DTOPuntoDeControl> ruta;
     
      public DTORuta(){
        this.ruta = new ArrayList<>();
    }
      
    public DTORuta(List<PuntoDeControl> ruta){
        this.ruta = new ArrayList<>();
        for(PuntoDeControl puntoControl : ruta){
            this.ruta.add(new DTOPuntoDeControl(puntoControl));
        }
    }
    
    public List<DTOPuntoDeControl> getRuta() {
        return ruta;
    }
    
}
