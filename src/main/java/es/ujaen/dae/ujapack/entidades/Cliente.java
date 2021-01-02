/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.util.CodificadorMd5;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    private String nombre;
    @NotBlank
    private String apellidos;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String direccion;
    @NotBlank
    private String localidad;
    @NotBlank
    private String provincia;
    @NotBlank
    private String clave;
    
    @OneToMany(mappedBy="remitente")
    List<Paquete> PaquetesRemitente;
    
    @OneToMany(mappedBy="destinatario")
    List<Paquete> PaquetesDestinatario;

    public Cliente(String dni, String nombre, String apellidos, String email, String direccion, String localidad, String provincia, String clave) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.clave=clave;
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

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getClave() {
        return clave;
    }
    
    
    /**
     * Compara la clave con la del cliente, codificándola en Md5
     * @param clave
     * @return 
     */
    public boolean claveValida(String clave) {
        return this.clave.equals(CodificadorMd5.codificar(clave));        
    }
    
    /**
     * Devolver cuentas del usuario
     * @return la lista de cuentas
     */
    public List<Paquete> verPaquetes() {
        return Collections.unmodifiableList(PaquetesRemitente);
    }
    
    


}
