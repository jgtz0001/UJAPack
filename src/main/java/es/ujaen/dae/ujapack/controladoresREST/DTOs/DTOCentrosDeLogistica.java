/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST.DTOs;

import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import es.ujaen.dae.ujapack.entidades.Oficina;
import java.util.List;

/**
 *
 * @author PCJoseGabriel
 */
public class DTOCentrosDeLogistica {
     
    private List<Integer> conexiones;

    private List<String> provincias;

    private List<Oficina> listaOficinas;

    
    public DTOCentrosDeLogistica (){}

       public DTOCentrosDeLogistica ( List<Integer> conexiones,List<String> provincias, List<Oficina> listaOficinas){
       
         this.conexiones=conexiones;
         this.listaOficinas=listaOficinas;
         this.provincias=provincias;
       
       }
       public DTOCentrosDeLogistica (CentroDeLogistica centrologistica){
           
         this.conexiones = centrologistica.getConexiones();
         this.listaOficinas = centrologistica.getListaOficinas();
         this.provincias = centrologistica.getProvincias();
       }
       
    public List<Integer> getConexiones() {
        return conexiones;
    }

    public List<String> getProvincias() {
        return provincias;
    }

    public List<Oficina> getListaOficinas() {
        return listaOficinas;
    }
    
}
