/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Pablo
 */
@Repository
@Transactional
public class RepositorioPuntoDeControl {

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public Optional<PuntoDeControl> buscar(Integer id) {
        return Optional.ofNullable(em.find(PuntoDeControl.class, id));
    }
    
     @Transactional(readOnly = true)
    public PuntoDeControl buscarPC(Integer id) {
        return em.find(PuntoDeControl.class, id);
    }

    @Transactional
    public void guardar(PuntoDeControl puntoDeControl) {
        em.persist(puntoDeControl);
    }
   
    @Transactional        
    public Integer BuscaIdProvincia (String provincia){
    List <PuntoDeControl> puntodecontrol = em.createQuery
    ("select h from PuntoDeControl h where h.provincia = :provincia",
    PuntoDeControl.class).setParameter("provincia", provincia).getResultList();
    return puntodecontrol.get(0).getId();
  }
}
