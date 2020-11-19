/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author Pablo
 */
@Entity
public class PuntoDeControl implements Serializable {

    @Id
    @Max(10)
    private int id;
    @NotEmpty
    String nombre;
    @NotEmpty
    String localizacion;
    @NotBlank
    ArrayList<String> provincia;
    
  

    public PuntoDeControl() {
    }

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
