/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.interfaces;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.nio.file.Files;


public class ServicioUjaPack {

    private ArrayList<PuntoDeControl> puntosDeControl;
    private HashMap<Integer, Paquete> paquetes;
    private ArrayList<Cliente> clientes;
    
   public ServicioUjaPack() {
        puntosDeControl=new ArrayList<PuntoDeControl>();
        paquetes=new HashMap<Integer, Paquete> ();
        clientes= new ArrayList<Cliente> ();
    }

    void altaEnvio(float peso, float anchura, Cliente remitente, Cliente destinatario) {
        Random rand = new Random();
        Integer localizador = rand.nextInt();

        while (getPaquetes().containsKey(rand)) {
            localizador = rand.nextInt();
        }

//habria que meter la función calcularpuntoscontrol
        Paquete paquet = new Paquete(localizador, "Preparado", (float) 20.4, (float) 10, (float) 10);
        getPaquetes().put(localizador, paquet);
    }

    String verEstado(int localizador) {
        if (!paquetes.containsKey(localizador)) {
            throw new IllegalArgumentException("Este localizador: " + localizador + " no existe");
        }
        return getPaquetes().get(localizador).getEstado();
    }

    String avisaEstado(int localizador, LocalDateTime fechaLlegada, LocalDateTime fechaSalida) {
        if (!paquetes.containsKey(localizador)) {
            throw new IllegalArgumentException("Este localizador: " + localizador + " no existe");
        }
        if (fechaSalida.isBefore(fechaLlegada)) {
            return "El paquete no ha sido enviado desde otro punto logístico.";
        }
        return ("El paquete con localizador: " + localizador + " ha salido a las: " + fechaSalida);
    }

    ArrayList<Paquete> listaPaquetes(String dni) {
        ArrayList<Paquete> lista = new ArrayList();
        for (Paquete value : getPaquetes().values()) {
            if (value.getRemitente().getDni() == dni) {
                lista.add(value);
            }
        }
        return lista;
    }

    float calcularImporte(int numPuntosControl, float peso, float altura, float anchura) {
        float importe = (peso * altura * anchura * numPuntosControl / 1000);
        return importe;
    }

    List<PuntoDeControl> calcularRutaPaquete(String localidadRem, String localidadDest) {
        return null;
    }

    public void leerJson() throws IOException {
        String jsonStr = Files.readString(new File("redujapack.json").toPath());
        JsonObject raiz = new Gson().fromJson(jsonStr, JsonObject.class);
        //System.out.println(raiz.keySet());

        for (int i = 1; i <= raiz.size(); i++) {
            JsonObject centro1 = raiz.getAsJsonObject(String.valueOf(i));
            int id = i;
            String nombre = centro1.get("nombre").getAsString();
            String localizacion = centro1.get("localización").getAsString();

            //System.out.println(centro1.getAsJsonArray("conexiones"));
            JsonArray provincias = centro1.getAsJsonArray("provincias");
            JsonArray conexiones = centro1.getAsJsonArray("conexiones");

            ArrayList<String> listdata = new ArrayList<String>();
            ArrayList listdata2 = new ArrayList();
            for (int j = 0; j < provincias.size(); j++) {
                listdata.add(provincias.get(j).getAsString());
            }
            for (int j = 0; j < conexiones.size(); j++) {
                listdata2.add(conexiones.get(j));
            }
            PuntoDeControl punto = new PuntoDeControl();
            //puntosDeControl.add(punto);
            punto.setId(i);
            punto.setNombre(nombre);
            punto.setLocalizacion(localizacion);
            punto.setProvincia(listdata);
            punto.setConexiones(listdata2);
            puntosDeControl.add(punto);
            
            System.out.println("--------------------------------");
            System.out.println(punto.getId());
            System.out.println(punto.getNombre());
            System.out.println(punto.getLocalizacion());
            System.out.println(punto.getProvincia());
            System.out.println(punto.getConexiones());
            
        }
    }

    /**
     * @return the puntosDeControl
     */
    public ArrayList<PuntoDeControl> getPuntosDeControl() {
        return puntosDeControl;
    }

    /**
     * @param puntosDeControl the puntosDeControl to set
     */
    public void setPuntosDeControl(ArrayList<PuntoDeControl> puntosDeControl) {
        this.puntosDeControl = puntosDeControl;
    }

    /**
     * @return the paquetes
     */
    public HashMap<Integer, Paquete> getPaquetes() {
        return paquetes;
    }

    /**
     * @param paquetes the paquetes to set
     */
    public void setPaquetes(HashMap<Integer, Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    /**
     * @return the clientes
     */
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    /**
     * @param clientes the clientes to set
     */
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
}