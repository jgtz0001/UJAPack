/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jenar
 */
public class Paquete {
    private int localizador;
    private int numPuntosControl;
    private String estado;
    private float importe;
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaSalida;
    private float peso;
    private float altura;
    private ArrayList<PasoPorPuntoDeControl> pasanPaquetes;
    private Cliente remitente;
    private Cliente destinatario;
    //Envio
    //Aqui habría que tener algo para no repetir los codigos de paquetes no?
    

    Paquete(int localizador, int numPuntosControl, String estado, float importe,
            LocalDateTime fechaLlegada,LocalDateTime fechaSalida,float peso, float altura) {
        this.localizador = localizador;
        this.numPuntosControl = numPuntosControl;
        this.estado = estado;
        this.importe = importe;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.peso = peso;
        this.altura = altura;
        destinatario = new Cliente();
        remitente = new Cliente();
        //pasanPaquetes = new List<PasoPorPuntoDeControl>();
    }
    
    public Paquete(int localizador, String estado, float importe, float peso, float altura) {
        this.localizador = localizador;
        this.numPuntosControl = 0;
        this.estado = estado;
        this.importe = importe;
        this.fechaLlegada = null;
        this.fechaSalida = null;
        this.peso = peso;
        this.altura = altura;
    }    
    
    public static boolean checkLocalizador (int localizador){
        if( Integer.toString(localizador).length()== 10)
            return true;
        return false;
    }
     public static boolean checkEnvio(String dni1, String dni2 ,String dir1, String dir2, String loc1, String loc2){
       
       if((dni1 != dni2) || (dir1!= dir2)|| ( loc1!= loc2 ))
           return true;
       
       return false;
   }
    
    public static boolean checkRepiteLocalizador (int localizador1, int localizador2){        
        
        if( localizador1 == localizador2)
            return true;
        
        return false;
    }
    //Esto hay que cambiarlo pero no se que hacia esta función.
    //Envio getEnvio(){    }
   
    public int getLocalizador() {
        return localizador;
    }

    public void setLocalizador(int localizador) {
        this.localizador = localizador;
    }

    public int getNumPuntosControl() {
        return numPuntosControl;
    }

    public void setNumPuntosControl(int numPuntosControl) {
        this.numPuntosControl = numPuntosControl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    /**
     * @return the pasanPaquetes
     */
    public ArrayList<PasoPorPuntoDeControl> getPasanPaquetes() {
        return pasanPaquetes;
    }

    /**
     * @param pasanPaquetes the pasanPaquetes to set
     */
    public void setPasanPaquetes(ArrayList<PasoPorPuntoDeControl> pasanPaquetes) {
        this.pasanPaquetes = pasanPaquetes;
    }

    /**
     * @return the remitente
     */
    public Cliente getRemitente() {
        return remitente;
    }

    /**
     * @param remitente the remitente to set
     */
    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    /**
     * @return the destinatario
     */
    public Cliente getDestinatario() {
        return destinatario;
    }

    /**
     * @param destinatario the destinatario to set
     */
    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }
    
    
}
