/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import es.ujaen.dae.ujapack.entidades.Oficina;

/**
 *
 * @author Pablo
 */
public class DTOOficina {

    CentroDeLogistica centroLog;

    public DTOOficina() {
    }

    public DTOOficina(CentroDeLogistica centro) {
        this.centroLog = centro;
    }
    
    public DTOOficina(Oficina oficina) {
        this.centroLog = oficina.getCentroLog();
    }

    public CentroDeLogistica getCentroLog() {
        return centroLog;
    }

    
}
