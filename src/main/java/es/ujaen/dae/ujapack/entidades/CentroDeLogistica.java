/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author jenar
 */
    @Entity
public class CentroDeLogistica extends PuntoDeControl implements Serializable {

    @NotBlank
    Oficina listaOficinas;
    @NotBlank
    private ArrayList<Integer> conexiones;
    @NotBlank
    private HashMap<Integer, List<String>> ruta;

    public CentroDeLogistica(int id, String nombre, String localizacion, ArrayList<String> provincia, ArrayList<Integer> conexiones) {
        super(id, nombre, localizacion, provincia);
        this.conexiones = conexiones;
        this.ruta = new HashMap<Integer, List<String>>();
        listaOficinas = new Oficina();
    }

    /**
     * @return the conexiones
     */
    public ArrayList<Integer> getConexiones() {
        return conexiones;
    }
    
    /**
     * @return the conexiones
     */
    public ArrayList<String> getProvincias() {
        return provincia;
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
