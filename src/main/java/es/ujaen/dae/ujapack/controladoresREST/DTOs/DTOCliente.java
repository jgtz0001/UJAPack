/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.Cliente;


/**
 *
 * @author Pablo
 */
public class DTOCliente {
   
    String dni;
    String nombre;
    String apellidos;
    String email;
    String direccion;
    String localidad;
    String provincia;
    String clave;

    public DTOCliente(String dni, String nombre, String apellidos, String email, String direccion, String localidad, String provincia, String clave) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.clave=clave;
    }
    
    public DTOCliente(Cliente cliente) {
        this.dni = cliente.getDni();
        this.nombre = cliente.getNombre();
        this.apellidos = cliente.getApellidos();
        this.email = cliente.getEmail();
        this.direccion = cliente.getDireccion();
        this.localidad = cliente.getLocalidad();
        this.provincia = cliente.getProvincia();
        this.clave=cliente.getClave();
    }

    public String getDni() {
        return dni;
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

    public String getLocalidad() {
        return localidad;
    }

    public String getProvincia() {
        return provincia;
    }
    
    public String getClave() {
        return clave;
    }
    
    public Cliente aCliente(){
        return new Cliente(dni, nombre, apellidos, email, direccion, localidad, provincia,clave);
    }
    
    
    
}
