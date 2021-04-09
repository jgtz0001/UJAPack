/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.beans.ServicioUjaPack;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOPaquete;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.excepciones.LocalizadorNoValido;
import es.ujaen.dae.ujapack.excepciones.PaqueteNoRegistrado;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ujapack")
public class ControladorPaquete {

    @Autowired
    ServicioUjaPack serviPack;

    /**
     * Handler para excepciones de violación de restricciones
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlerViolacionRestricciones(ConstraintViolationException e) {
    }

    /**
     * Handler para excepciones de violación de restricciones
     */
    @ExceptionHandler(PaqueteNoRegistrado.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handlerPaqueteNoRegistrado(PaqueteNoRegistrado e) {
    }

    @ExceptionHandler(LocalizadorNoValido.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handlerLocalizadorNoValido(LocalizadorNoValido e) {
    }

    @PostMapping("/paquetes")
    ResponseEntity<DTOPaquete> altaPaquete(@RequestBody DTOPaquete paquete){
        try {
            Paquete paq = serviPack.altaPaquete(paquete.aPaquete());
            return ResponseEntity.status(HttpStatus.CREATED).body(new DTOPaquete(paq));
        } catch (PaqueteNoRegistrado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }  
    
    @GetMapping("/paquetes/{localizador}")
    ResponseEntity<DTOPaquete> verPaquete(@PathVariable String localizador) {
        Optional<Paquete> paquete = serviPack.verPaquetes(localizador);
        return paquete.map(p -> ResponseEntity.ok(new DTOPaquete(p)))
                .orElse(ResponseEntity.notFound().build());
    }

}
