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
    private ArrayList<String> provincia;

    public PuntoDeControl(int id, String nombre, String localizacion, ArrayList<String> provincia) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.provincia = provincia;
    }

//        ArrayList calcularPuntoControlActual(){
//            return null;
//        }
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

}
