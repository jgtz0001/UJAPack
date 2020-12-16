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
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pablo
 */
@RestController
@RequestMapping("/ujapack")
public class ControladorClientes {
    @Autowired
    ServicioUjaPack serviPack;
    
    @PostConstruct
    void controladorArrancado(){
        System.out.println("Controlador REST arrancado");
    }
    
    @PostMapping("/clientes")
    ResponseEntity<DTOPaquete> altaCliente(@RequestBody DTOCliente cliente){
        try{
            serviPack.altaCliente(cliente);
            return ResponseEntity.ok(paquete);
        }
        catch(ClienteNoRegistrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
