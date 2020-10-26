/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.util.ArrayList;

/**
 *
 * @author jenar
 */
public class CentroDeLogistica extends PuntoDeControl {

    Oficina listaOficinas;
    private ArrayList<Integer> conexiones;

    public CentroDeLogistica(int id, String nombre, String localizacion, ArrayList<String> provincia, ArrayList<Integer> conexiones) {
        super(id, nombre, localizacion, provincia);
        listaOficinas = new Oficina(id, nombre, localizacion, provincia,conexiones);
        this.conexiones = conexiones;
    }

    /**
     * @return the conexiones
     */
    public ArrayList<Integer> getConexiones() {
        return conexiones;
    }

    
}
