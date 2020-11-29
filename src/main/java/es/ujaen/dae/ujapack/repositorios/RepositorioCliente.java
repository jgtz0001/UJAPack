/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.Cliente;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

/**
 *
 * @author PCJoseGabriel
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioCliente {
    @PersistenceContext
    EntityManager em;
    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Optional <Cliente> buscar(String dni){
        return Optional.ofNullable(em.find(Cliente.class, dni));
    }
    
    @Transactional
    public void guardar(Cliente cliente){
        em.persist(cliente);
    }
}
