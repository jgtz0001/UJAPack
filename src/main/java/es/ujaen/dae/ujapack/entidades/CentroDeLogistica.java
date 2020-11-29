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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author jenar
 */
@Entity
public class CentroDeLogistica extends PuntoDeControl implements Serializable {

    @Id
    int id;
    @Column(length = 3000) 
    public  Oficina listaOficinas;
    private ArrayList<Integer> conexiones;
   

    public CentroDeLogistica(int id, String nombre, String localizacion, ArrayList<String> provincia, ArrayList<Integer> conexiones) {
        super(id, nombre, localizacion, provincia);
        this.conexiones = conexiones;
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
    public List<String> getProvincias() {
        return provincia;
    }

}
