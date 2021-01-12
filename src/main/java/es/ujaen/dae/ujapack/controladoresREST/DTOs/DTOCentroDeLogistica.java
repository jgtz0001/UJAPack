/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import java.util.List;
import org.springframework.hateoas.Link;



/**
 *
 * @author Pablo
 */
public class DTOCentroDeLogistica {

    int id;

    String nombre;

    String localizacion;
    
   
    private List<Link> conexiones;

    private List<Link> provincias;

    private List<Link> listaOficinas;

    public DTOCentroDeLogistica(int id, String nombre, String localizacion) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;

    }

    public DTOCentroDeLogistica(CentroDeLogistica centroDeLogistica) {
        this.id = centroDeLogistica.getId();
        this.nombre = centroDeLogistica.getNombre();
        this.localizacion = centroDeLogistica.getLocalizacion();
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

    /**
     * @return the conexiones
     */
    public List<Link> getConexiones() {
        return conexiones;
    }

    /**
     * @return the provincias
     */
    public List<Link> getProvincias() {
        return provincias;
    }

    /**
     * @return the listaOficinas
     */
    public List<Link> getListaOficinas() {
        return listaOficinas;
    }
    
}
