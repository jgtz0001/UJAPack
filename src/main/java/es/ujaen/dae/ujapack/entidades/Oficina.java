/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author jenar
 */
@Entity
public class Oficina extends PuntoDeControl implements Serializable {

    @OneToOne
    CentroDeLogistica centroLog;

   public  Oficina(int id, String nombre, String localizacion) {
        super(id, nombre, localizacion);
        this.nombre = nombre;
        this.localizacion = localizacion;
    }

    public Oficina() {
    }

    public CentroDeLogistica getCentroLog() {
        return centroLog;
    }
    
    
  }
  
