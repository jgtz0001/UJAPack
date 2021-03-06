/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
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
    private int id;
    @NotBlank
    String nombre;
    @NotBlank
    String localizacion;
    private int idCL;

    public PuntoDeControl() {
    }

    public PuntoDeControl(int id, String nombre, String localizacion, int idCL) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.idCL = idCL;

    }

    public PuntoDeControl(int id, String nombre, String localizacion) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
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
     * @return the localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * @return the idCL
     */
    public int getIdCL() {
        return idCL;
    }
}
