/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author jenar
 */
@Entity
public class Paquete implements Serializable {
    
    @Id
    @Size(min = 10, max = 10)
    private int localizador;
    @NotBlank
    private int numPuntosControl;
    @NotBlank
    private Estado estado;
    @NotBlank
    private float importe;
    @NotBlank
    private float peso;
    @NotBlank
    private float altura;
    @NotBlank
    private ArrayList<PasoPorPuntoDeControl> pasanPaquetes;
    @NotBlank
    private ArrayList<String> ruta;
    @NotBlank
    private Cliente remitente;
    @NotBlank
    private Cliente destinatario;
    
 

    public enum Estado {
        En_transito,
        En_reparto,
        Entregado,
        Extraviado;
    }

    public Paquete(int localizador, float importe, float peso, float altura, PuntoDeControl p) {
        this.localizador = localizador;
        this.numPuntosControl = 0;
        this.estado = estado.En_transito;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.pasanPaquetes = new ArrayList<PasoPorPuntoDeControl>();
        PasoPorPuntoDeControl primero = new PasoPorPuntoDeControl(p);
        pasanPaquetes.add(primero);
    }

    public static boolean checkLocalizador(int localizador) {
        if (Integer.toString(localizador).length() == 10) {
            return true;
        }
        return false;
    }

    public static boolean checkEnvio(String dni1, String dni2, String dir1, String dir2, String loc1, String loc2) {

        if ((dni1 != dni2) || (dir1 != dir2) || (loc1 != loc2)) {
            return true;
        }

        return false;
    }

    public static boolean checkRepiteLocalizador(int localizador1, int localizador2) {

        if (localizador1 == localizador2) {
            return true;
        }

        return false;
    }

    public static boolean testRepiteEnvio(ArrayList<String> ruta1, ArrayList<String> ruta2, int localizador1, int localizador2) {
        if ((ruta1 == ruta2) && (localizador1 == localizador2)) {
            return false;
        }
        return true;
    }

    public void envia(LocalDateTime fechaSalida, PuntoDeControl punto) {
        Integer tama = pasanPaquetes.size();
        if (estado.equals(estado.En_transito)) {
            estado = estado.En_reparto;
        }

        if (tama.equals(numPuntosControl)) {
            if (estado.equals(estado.En_reparto)) {
                estado = Estado.Entregado;
            }
        } else {
            this.pasanPaquetes.get(tama - 1).setFechaSalida(fechaSalida);
            PasoPorPuntoDeControl nuevo = new PasoPorPuntoDeControl(punto);
            pasanPaquetes.add(nuevo);
        }
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

    public Estado getEstado() {
        return estado;
    }

    /**
     * @return the ruta
     */
    public ArrayList<String> getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(ArrayList<String> ruta) {
        this.ruta = ruta;
        this.numPuntosControl = ruta.size();
    }

}
