/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
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
public class RepositorioPuntoDeControl {

    @PersistenceContext
    EntityManager em;

    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PuntoDeControl buscarPC(int id) {
        return em.find(PuntoDeControl.class, id);
    }

    @Transactional
    public void guardar(PuntoDeControl puntoDeControl) {
        em.persist(puntoDeControl);
    }
   
    @Transactional        
    public int BuscaIdProvincia (String provincia){
    List <PuntoDeControl> puntodecontrol = em.createQuery
    ("select h from PuntoDeControl h where h.provincia = :provincia",
    PuntoDeControl.class).setParameter("provincia", provincia).getResultList();
    return puntodecontrol.get(0).getId();
  }
}
