/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author jenar
 */
public class Oficina extends PuntoDeControl implements Serializable{

    CentroDeLogistica listaLogistica;

    Oficina(int id, String nombre, String localizacion, ArrayList<String> provincia, ArrayList<Integer> conexiones) {
        super(id, nombre, localizacion, provincia);
    }
    
    Oficina(){}

}
