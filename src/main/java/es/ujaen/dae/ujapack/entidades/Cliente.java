/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author zafra
 */
@Entity
public class Cliente implements Serializable {

    @Id
    @Size(min = 8, max = 8)
    private String dni;
    @NotBlank
    @NotEmpty
    private String nombre;
    @NotBlank
    @NotEmpty
    private String apellidos;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @NotEmpty
    private String direccion;
    @NotBlank
    @NotEmpty
    private String localidad;
    @NotBlank
    @NotEmpty
    private String provincia;
    
    @OneToMany
    @JoinColumn(name="remitente_dni")
    List<Paquete> PaqueteRemitente;
    
    @OneToMany
    @JoinColumn(name="destinatario_dni")
    List<Paquete> PaqueteDestinatario;

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
