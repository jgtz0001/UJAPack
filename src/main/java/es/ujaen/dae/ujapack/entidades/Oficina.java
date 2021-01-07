/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author jenar
 */
@Entity
public class Oficina extends PuntoDeControl implements Serializable {

    @ManyToMany
    List<CentroDeLogistica> listaLogistica;

    Oficina(int id,String nombre, String localizacion, ArrayList<Integer> conexiones) {
        super(id,nombre, localizacion);
    }

    Oficina() {
    }

}
