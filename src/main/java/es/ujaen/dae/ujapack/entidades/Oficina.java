/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jenar
 */
public class Oficina extends PuntoDeControl{
    List<CentroDeLogistica> listaLogística;
    
    Oficina(int id, String nombre, String localizacion, ArrayList<String> provincia, ArrayList conexiones){
        super(id, nombre, localizacion, provincia, conexiones);
    }
    
}