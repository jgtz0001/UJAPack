/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author jenar
 */
@Entity
public class Oficina extends PuntoDeControl implements Serializable {

//   @Id
//   //@GeneratedValue(strategy=GenerationType.TABLE)
//   int id;
    @ManyToOne
    CentroDeLogistica listaLogistica;

    Oficina(String nombre, String localizacion, ArrayList<String> provincia, ArrayList<Integer> conexiones) {
        super(nombre, localizacion, provincia);
    }

    Oficina() {
    }

}
