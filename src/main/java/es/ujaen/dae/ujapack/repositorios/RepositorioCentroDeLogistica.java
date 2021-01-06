/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

/**
 *
 * @author Pablo
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioCentroDeLogistica {

    @PersistenceContext
    EntityManager em;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public CentroDeLogistica buscarPorId(int id) {
        return em.find(CentroDeLogistica.class, id);
    }

    @Transactional
    public void guardar(CentroDeLogistica centroDeLogistica) {
        em.persist(centroDeLogistica);
    }
    
    @Transactional
    public ArrayList<Integer> BuscaIdCL(int id) {
        List<CentroDeLogistica> puntos = em.createQuery("select h from CentroDeLogistica h WHERE h.id = '" + id + "'",
                CentroDeLogistica.class).getResultList();
        return puntos.get(0).getConexiones();
    }

}
