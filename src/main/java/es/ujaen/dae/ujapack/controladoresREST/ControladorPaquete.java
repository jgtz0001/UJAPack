/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.beans.ServicioUjaPack;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOCentrosDeLogistica;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOCliente;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOOficina;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOPaquete;
import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Oficina;
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
    ResponseEntity<DTOPaquete> altaPaquete(@RequestBody DTOPaquete paquete) {
        try {
            Cliente rem = serviPack.altaCliente((paquete.getRem()).aCliente());
            Cliente dest = serviPack.altaCliente((paquete.getDest()).aCliente());
            Paquete paquet = serviPack.altaEnvio(1, 1, 1, rem, dest, paquete.getLocalizador());
           
            return ResponseEntity.status(HttpStatus.CREATED).body(new DTOPaquete(paquet));
        } catch (PaqueteNoRegistrado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/paquetesClientes")
    ResponseEntity<DTOPaquete> altaPaqueteClientes(@RequestBody DTOPaquete paqueteDto) {
        try {
            Cliente rem = serviPack.altaCliente((paqueteDto.getRem()).aCliente());
            Cliente dest = serviPack.altaCliente((paqueteDto.getDest()).aCliente());
            Paquete paquete = serviPack.altaEnvio(1, 1, 1, rem, dest, paqueteDto.getLocalizador());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(new DTOPaquete(paquete));
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

    @GetMapping("/centro/{id}")
    ResponseEntity<DTOCentrosDeLogistica> verCentro(@PathVariable String id) {
        Optional<CentroDeLogistica> centro = serviPack.verCentros(id);
        return centro.map(p -> ResponseEntity.ok(new DTOCentrosDeLogistica(p)))
                .orElse(ResponseEntity.notFound().build());
    }
    
     @GetMapping("/oficina/{id}")
    ResponseEntity<DTOOficina> verOficina(@PathVariable String id) {
        Optional<Oficina> oficina = serviPack.verOficinas(id);
        return oficina.map(p -> ResponseEntity.ok(new DTOOficina(p)))
                .orElse(ResponseEntity.notFound().build());
    }
 
}
