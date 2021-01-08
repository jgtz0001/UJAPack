/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.controladoresREST;

import es.ujaen.dae.ujapack.beans.ServicioUjaPack;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOCliente;
import es.ujaen.dae.ujapack.controladoresREST.DTOs.DTOPaquete;
import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.excepciones.ClienteNoRegistrado;
import es.ujaen.dae.ujapack.excepciones.PaqueteNoRegistrado;
import java.util.Optional;
import javax.annotation.PostConstruct;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ujapack")
public class ControladorPaquete {
    @Autowired
    ServicioUjaPack serviPack;
    
     /** Handler para excepciones de violación de restricciones */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlerViolacionRestricciones(ConstraintViolationException e) {
        // return ResponseEntity.badRequest().body(e.getMessage());
    }

     /** Handler para excepciones de violación de restricciones */
    @ExceptionHandler(ClienteNoRegistrado.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlerPaqueteNoRegistrado(PaqueteNoRegistrado e) {
    }
  
    
//pasar atributos de paquete
    @PostMapping("/paquete/{localizador}")
    ResponseEntity<DTOPaquete> altaPaquete(@RequestBody DTOPaquete paquete,@RequestBody DTOCliente remitente, @RequestBody DTOCliente destinatario){
        try{
            Paquete paq = serviPack.altaPaquete(paquete.aPaquete(),remitente.aCliente(),destinatario.aCliente());
            //Paquete paq = serviPack.altaEnvio(0, 0, 0, remitente, destinatario);
            return ResponseEntity.status(HttpStatus.CREATED).body(new DTOPaquete(paq));
        }
        catch(PaqueteNoRegistrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }        
    }
    
     


}
