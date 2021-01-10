/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.PuntoDeControl;

/**
 *
 * @author Pablo
 */
public class DTOPuntoDeControl {

    int id;

    String nombre;

    String localizacion;

    int idCL;

    public DTOPuntoDeControl(int id, String nombre, String localizacion, int idCL) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.idCL = idCL;
    }

    public DTOPuntoDeControl(PuntoDeControl puntoDeControl) {
        this.id = puntoDeControl.getId();
        this.nombre = puntoDeControl.getNombre();
        this.localizacion = puntoDeControl.getLocalizacion();
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public int getIdCL() {
        return idCL;
    }
   
}
