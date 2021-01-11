/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.Oficina;
/**
 *
 * @author Pablo
 */
public class DTOOficina {
    
    int id;
    
    String nombre;

    public DTOOficina(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public DTOOficina(Oficina oficina) {
        this.id = oficina.getId();
        this.nombre = oficina.getNombre();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
    
}
