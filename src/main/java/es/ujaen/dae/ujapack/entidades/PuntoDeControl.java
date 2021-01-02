/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Pablo
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PuntoDeControl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    String nombre;
    @NotBlank
    String localizacion;
    ArrayList<String> provincia;

    public PuntoDeControl() {
    }

    public PuntoDeControl(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = nombre;
        this.provincia = null;
    }

    public PuntoDeControl(String nombre, String localizacion, ArrayList<String> provincia) {
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
