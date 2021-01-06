/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author jenar
 */
@Entity
public class CentroDeLogistica extends PuntoDeControl implements Serializable {
    
    @Column
    private ArrayList<Integer> conexiones;
    
    @Column
    private ArrayList<String> provincias;

    @ManyToMany(mappedBy = "listaLogistica")
    public List<Oficina> listaOficinas;
    
    

    public CentroDeLogistica(int id,String nombre, String localizacion, ArrayList<String> provincias, ArrayList<Integer> conexiones) {
        super(id,nombre, localizacion);
        this.conexiones = conexiones;
        this.provincias = provincias;
        
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
        return provincias;
    }
    
}
