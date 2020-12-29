/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author jenar
 */
@Entity
public class CentroDeLogistica extends PuntoDeControl implements Serializable {

//    @Id
//    @AttributeOverride(name= "id", column = @Column(name = "id"))
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    int id;

    @Column
    private ArrayList<Integer> conexiones;

    @OneToMany(mappedBy = "listaLogistica")
    public List<Oficina> listaOficinas;

    public CentroDeLogistica(String nombre, String localizacion, ArrayList<String> provincia, ArrayList<Integer> conexiones) {
        super(nombre, localizacion, provincia);
        this.conexiones = conexiones;
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
