/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.util.List;
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
public class RepositorioPaquete {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Paquete buscar(int localizador) {
        return em.find(Paquete.class, localizador);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<Paquete> buscarPaquetes(int localizador) {
        return Optional.ofNullable(em.find(Paquete.class, localizador));
    }
    
    @Transactional
    public List<PuntoDeControl> buscarRutaPaquetes(int localizador) {
        Paquete paquete = em.createQuery("select h from Paquete h WHERE h.id = '" + localizador + "'",
            Paquete.class).getSingleResult();
        return paquete.getRuta();
    }
    
    public void guardar(Paquete paquete) {
        em.persist(paquete);
    }

    
    public void actualizarPaquete(Paquete paquete) {
        em.merge(paquete);
    }
    
    

}
