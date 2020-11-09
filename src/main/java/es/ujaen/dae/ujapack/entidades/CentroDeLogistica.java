/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jenar
 */
public class CentroDeLogistica extends PuntoDeControl {

    //Oficina listaOficinas;
    private ArrayList<Integer> conexiones;
    private HashMap<Integer, List<String>> ruta;

    public CentroDeLogistica(int id, String nombre, String localizacion, ArrayList<String> provincia, ArrayList<Integer> conexiones) {
        super(id, nombre, localizacion, provincia);
        this.conexiones = conexiones;
        this.ruta = new HashMap<Integer, List<String>>();
    }

    /**
     * @return the conexiones
     */
    public ArrayList<Integer> getConexiones() {
        return conexiones;
    }

    /**
     * @return the ruta
     */
    public HashMap<Integer, List<String>> getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(HashMap<Integer, List<String>> ruta) {
        this.ruta = ruta;
    }

}
