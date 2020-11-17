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

/**
 *
 * @author PCJoseGabriel
 */
@Repository
@Transactional
public class RepositorioCliente {
    @PersistenceContext
    EntityManager em;
    
    public Optional <Cliente> buscar(String dni){
        return Optional.ofNullable(em.find(Cliente.class, dni));
    }
    
    public void guardar(Cliente cliente){
        em.persist(cliente);
    }
}
