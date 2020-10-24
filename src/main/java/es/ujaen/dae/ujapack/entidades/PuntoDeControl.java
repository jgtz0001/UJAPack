/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.util.ArrayList;

/**
 *
 * @author Pablo
 */
public class PuntoDeControl {
    private int id;
    private String nombre;
    private String localizacion;
    private String provincia;
    ArrayList conexiones;

    PuntoDeControl(int id, String nombre, String localizacion, String provincia) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.provincia = provincia;
    }
    
    ArrayList calcularPuntosDeControlActual(){
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    

}
