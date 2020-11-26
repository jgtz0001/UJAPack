/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.repositorios;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
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
    public Optional <PuntoDeControl> buscar(int id){
        return Optional.ofNullable(em.find(PuntoDeControl.class, id));
    }
    
    @Transactional
    public void guardar(PuntoDeControl puntoDeControl){
        em.persist(puntoDeControl);
    }
    
}
