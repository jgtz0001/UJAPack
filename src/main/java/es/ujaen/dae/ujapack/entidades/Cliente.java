/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author zafra
 */
@Entity
public class Cliente implements Serializable {

    @Id
//@Pattern(regexp=exprReg.DNI)
    @Size(min = 9, max = 9)
    private String dni;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellidos;
    @NotBlank
    private String email;
    @NotBlank
    private String direccion;
    @NotBlank
    private String localidad;
    @NotBlank
    private String provincia;

    public Cliente(String dni, String nombre, String apellidos, String email, String direccion, String localidad, String provincia) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Cliente() {

    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @return the localidad
     */
    public String getLocalidad() {
        return localidad;
    }

}
