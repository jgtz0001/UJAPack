/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.interfaces;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.json.JSONObject;

/**
 *
 * @author Pablo
 */
public class ServicioUjaPack {
    ArrayList <PuntoDeControl> puntosDeControl;
    HashMap <Integer, Paquete> paquetes;
    ArrayList <Cliente> clientes;
    
    void altaEnvio(float peso, float anchura, Cliente remitente, Cliente destinatario){
        Random rand = new Random();
        Integer localizador = rand.nextInt();
        
        while (paquetes.containsKey(rand)){
            localizador = rand.nextInt();
        }
        //habria que meter la función calcular importe
        //habria que meter la función calcularpuntoscontrol
        Paquete paquet = new Paquete(localizador, "Preparado", (float)20.4, (float)10, (float)10);
        paquetes.put(localizador,paquet);
    }
    
    String verEstado(int localizador){
        if (!paquetes.containsKey(localizador)){
            throw new IllegalArgumentException ("Este localizador: " + localizador + " no existe");
        }
        return paquetes.get(localizador).getEstado();
    }
    
    //para que se quiere la fecha de llegada?
    String avisaEstado(int localizador, LocalDateTime fechaLlegada, LocalDateTime fechaSalida){
        if(!paquetes.containsKey(localizador))
            throw new IllegalArgumentException("Este localizador: " + localizador + " no existe");
        if(fechaSalida.isBefore(fechaLlegada))
            return "El paquete no ha sido enviado desde otro punto logístico.";
        return ("El paquete con localizador: " + localizador + " ha salido a las: " + fechaSalida);
    }
    
    ArrayList<Paquete> listaPaquetes(String dni){
        ArrayList<Paquete> lista = new ArrayList();
        for (Paquete value : paquetes.values()){
            if (value.getRemitente().getDni() == dni)
                lista.add(value);
        }
        return lista;
    }
    
    float calcularImporte(int numPuntosControl, float peso, float altura, float anchura ){
        float importe = (peso * altura * anchura * numPuntosControl / 1000);
        return importe;
    }
    
    List<PuntoDeControl> calcularRutaPaquete(String localidadRem, String localidadDest){
        return null;
    }
    
    void anadirJSON(){
        //JSONObject myObject = new JSONObject();
    }
}
