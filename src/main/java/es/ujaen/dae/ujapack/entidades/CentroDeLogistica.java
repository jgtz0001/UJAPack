/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 *
 * @author jenar
 */
@Entity
public class CentroDeLogistica extends PuntoDeControl implements Serializable {

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> conexiones;

    @ElementCollection(targetClass = String.class)
    private List<String> provincias;

    @ManyToMany(mappedBy = "listaLogistica")
    public List<Oficina> listaOficinas;

    public CentroDeLogistica() {
    }

    public CentroDeLogistica(int id, String nombre, String localizacion, List<String> provincias, List<Integer> conexiones) {
        super(id, nombre, localizacion);
        this.conexiones = conexiones;
        this.provincias = provincias;

    }

    /**
     * @return the conexiones
     */
    public List<Integer> getConexiones() {
        return conexiones;
    }

    /**
     * @return the conexiones
     */
    public List<String> getProvincias() {
        return provincias;
    }

}
