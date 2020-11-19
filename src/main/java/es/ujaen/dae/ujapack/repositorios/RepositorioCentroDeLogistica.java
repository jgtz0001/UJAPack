/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;

import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import java.util.ArrayList;
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
public class RepositorioCentroDeLogistica {
    
    @PersistenceContext
    EntityManager em;
    
    public Optional <CentroDeLogistica> buscar(ArrayList conexiones){
        return Optional.ofNullable(em.find(CentroDeLogistica.class, conexiones));
    }
    
    public void guardar(CentroDeLogistica centroDeLogistica){
        em.persist(centroDeLogistica);
    }
    
}
