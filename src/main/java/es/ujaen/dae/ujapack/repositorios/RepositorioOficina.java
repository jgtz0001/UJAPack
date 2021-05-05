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
    public PuntoDeControl buscarPC(int id) {
        return em.find(PuntoDeControl.class, id);
    }

    @Transactional
    public void guardar(PuntoDeControl puntoDeControl) {
        em.persist(puntoDeControl);
    }

    @Transactional
    public int BuscaIdProvincia(String provincia) {
        PuntoDeControl punto = em.createQuery("select h from PuntoDeControl h WHERE h.localizacion = '" + provincia + "'",
                PuntoDeControl.class).getSingleResult();
        return punto.getId();
    }

    @Transactional
    public int BuscaIdProvinciaCL(int id) {
        PuntoDeControl punto = em.createQuery("select h from PuntoDeControl h WHERE h.id = '" + id + "'",
                PuntoDeControl.class).getSingleResult();
        return punto.getIdCL();
    }

    @Transactional
    public PuntoDeControl getPuntoDeControl(int id) {
        PuntoDeControl punto = em.createQuery("select h from PuntoDeControl h WHERE h.id = '" + id + "'",
                PuntoDeControl.class).getSingleResult();
        return punto;
    }
    
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<Oficina> buscarOficinas(int id) {
        return Optional.ofNullable(em.find(Oficina.class, id));
    }

}
