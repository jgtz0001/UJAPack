/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

/**
 *
 * @author zafra
 */
public class Cliente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String direccion;
    private String localidad;

    public Cliente(String dni, String nombre, String apellidos, String email, String direccion, String localidad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.localidad = localidad;
    }
    
    public Cliente(){
        
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    
}
