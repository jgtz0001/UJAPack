/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.entidades;

import es.ujaen.dae.ujapack.excepciones.FechaIncorrecta;
import es.ujaen.dae.ujapack.excepciones.PuntoDeControlEquivocado;
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
    private ArrayList<PuntoDeControl> ruta;
    @NotBlank
    private Cliente remitente;
    @NotBlank
    private Cliente destinatario;

    @OneToMany
    @JoinColumn(name = "pasanPaquetes")
    List<PasoPorPuntoDeControl> Pasanpaquetes;

    public enum Estado {
        EnTransito,
        EnReparto,
        Entregado,
        Extraviado;
    }

    public Paquete(int localizador, float importe, float peso, float altura, ArrayList<PuntoDeControl> ruta) {
        this.localizador = localizador;
        this.numPuntosControl = ruta.size();
        this.estado = estado.EnTransito;
        this.importe = importe;
        this.peso = peso;
        this.altura = altura;
        this.ruta = ruta;
        this.pasanPaquetes = new ArrayList<PasoPorPuntoDeControl>();

        PasoPorPuntoDeControl primero = new PasoPorPuntoDeControl(ruta.get(0), LocalDateTime.now());
        pasanPaquetes.add(primero);
    }

    void controlaExcepcionesSalida(PuntoDeControl punto) {
        boolean esta = false;
        for (int i = 0; i < ruta.size(); i++) {
            if (ruta.get(i).getLocalizacion().equals(punto.localizacion)) {
                esta = true;
                break;
            }
        }
        if (!esta) {
            throw new PuntoDeControlEquivocado(); // El punto de control no correspondería con la ruta.
        }

    }

    void controlaExcepcionesEntrada(PuntoDeControl punto) {
        for (int i = 0; i < pasanPaquetes.size(); i++) {
            if (pasanPaquetes.get(i).pasoControl.localizacion.equals(punto.localizacion))//Aquí encontraria un punto de control repetido, por lo que no valdría.
            {
                throw new PuntoDeControlEquivocado();
            }
        }
        boolean esta = false;
        for (int i = 0; i < ruta.size(); i++) {
            if (ruta.get(i).getLocalizacion().equals(punto.localizacion)) {
                esta = true;
                break;
            }
        }
        if (!esta) {
            throw new PuntoDeControlEquivocado(); // El punto de control no correspondería con la ruta.
        }

        if (!ruta.get(pasanPaquetes.size()).localizacion.equals(punto.localizacion)) {
            throw new PuntoDeControlEquivocado(); // El punto de control proporcionado esta adelantado con la ruta.
        }
    }

    public void notificaEntrada(LocalDateTime fechaEntrada, PuntoDeControl punto) {
        controlaExcepcionesEntrada(punto);

        PasoPorPuntoDeControl nuevo = new PasoPorPuntoDeControl(punto, fechaEntrada);
        pasanPaquetes.add(nuevo);
    }

    public void notificaSalida(LocalDateTime fechaSalida, PuntoDeControl punto) {
        controlaExcepcionesSalida(punto); // Con esto te aseguras de que el punto de control es el correcto.
        Integer tama = pasanPaquetes.size();

        if (tama.equals(numPuntosControl)) {
            if (estado.equals(estado.EnReparto)) {
                estado = estado.Entregado;
            } else {
                estado = estado.EnReparto;
            }
        } else {
            if (this.pasanPaquetes.get(tama - 1).getFechaLlegada().isAfter(fechaSalida)) {
                throw new FechaIncorrecta();
            }
            this.pasanPaquetes.get(tama - 1).setFechaLlegada(fechaSalida);
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

    public String getEstado() {
        return estado.toString();
    }

    /**
     * @return the numPuntosControl
     */
    public int getControl() {
        return numPuntosControl;
    }

    /**
     * @return the ruta
     */
    public ArrayList<PuntoDeControl> getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(ArrayList<PuntoDeControl> ruta) {
        this.ruta = ruta;
//this.numPuntosControl = ruta.size();
    }

    /**
     * @return the localizador
     */
    public int getLocalizador() {
        return localizador;
    }

}
