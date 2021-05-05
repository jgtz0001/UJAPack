/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.Oficina;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.util.Optional;
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
public class RepositorioOficina {

    @PersistenceContext
    EntityManager em;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Oficina buscarOficina(int id) {
        return em.find(Oficina.class, id);
    }

    @Transactional
    public void guardar(Oficina oficina) {
        em.persist(oficina);
    }

    @Transactional
    public PuntoDeControl getOficina(int id) {
        Oficina oficina = em.createQuery("select h from Oficina h WHERE h.id = '" + id + "'",
                Oficina.class).getSingleResult();
        return oficina;
    }
    
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<Oficina> buscarOficinas(int id) {
        return Optional.ofNullable(em.find(Oficina.class, id));
    }

}
