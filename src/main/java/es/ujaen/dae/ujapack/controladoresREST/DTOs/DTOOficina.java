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

    int idCentroLog;
    String localizacion;

    public DTOOficina() {
    }

    public DTOOficina(int centro, String localizacion) {
        this.idCentroLog = centro;
        this.localizacion = localizacion;
    }

    public DTOOficina(Oficina oficina) {
        this.idCentroLog = oficina.getCentroLog().getId();
        this.localizacion = oficina.getLocalizacion();
    }

    public int getIdCentroLog() {
        return idCentroLog;
    }

}
