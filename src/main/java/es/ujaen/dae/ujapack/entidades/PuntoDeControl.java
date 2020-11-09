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
    String nombre;
    String localizacion;
    ArrayList<String> provincia;

    public PuntoDeControl(){}
    
    public PuntoDeControl(int id, String nombre, String localizacion, ArrayList<String> provincia) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.provincia = provincia;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the provincia
     */
    public ArrayList<String> getProvincia() {
        return provincia;
    }

    /**
     * @return the localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }
}
