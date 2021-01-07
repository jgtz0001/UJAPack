/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.Paquete;
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

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Paquete buscar(int localizador) {
        return em.find(Paquete.class, localizador);
    }

    @Transactional
    public Optional<Paquete> buscarPaquetes(int localizador) {
        return Optional.ofNullable(em.find(Paquete.class, localizador));
    }

    public void guardar(Paquete paquete) {
        em.persist(paquete);
    }

    @Transactional
    public void actualizarPaquete(Paquete paquete) {
        em.merge(paquete);
    }

}
