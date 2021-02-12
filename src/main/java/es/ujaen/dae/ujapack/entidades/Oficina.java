/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 *
 * @author jenar
 */
@Entity
public class Oficina extends PuntoDeControl implements Serializable {

    @ManyToMany(fetch = FetchType.EAGER)
    List<CentroDeLogistica> listaLogistica;

   public  Oficina(int id, String nombre, String localizacion) {
        super(id, nombre, localizacion);
        this.nombre = nombre;
        this.localizacion = localizacion;
    }

    public Oficina() {
    }
  }
  
