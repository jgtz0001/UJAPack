/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.beans;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author PCJoseGabriel
 */
@Service
public class LimpiadoBaseDeDatos {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

    /**
     * Lista de entidades a borrar.
     */
    final String[] tablas = {
        "centro_de_logistica_conexiones",
        "centro_de_logistica_provincia",
        "centro_de_logistica",
        "CentroDeLogistica",
        "Cliente",
        "Oficina",
        "Paquete",
        "PasoPorPuntoDeControl",
        "PuntoDeControl"
    };

    final String deleteFrom = "delete from ";

   
    /** Realizar borrado */
    public void limpiar() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            for (String tabla : tablas) {
                em.createQuery(deleteFrom + tabla).executeUpdate();
            }
        });
    }


}

