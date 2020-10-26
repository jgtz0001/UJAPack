/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.beans;

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
import java.util.LinkedList;
import java.util.Queue;

public class ServicioUjaPack {

    private ArrayList<PuntoDeControl> puntosDeControl;
    private HashMap<Integer, Paquete> paquetes;
    private ArrayList<Cliente> clientes;
    private static final long LIMIT = 10000000000L;
    private static long last = 0;

    ServicioUjaPack() {
        puntosDeControl = new ArrayList<PuntoDeControl>();
        paquetes = new HashMap<Integer, Paquete>();
        clientes = new ArrayList<Cliente>();
    }

    public boolean buscaPorDni(String dni) {
        for (Cliente cli : clientes) {
            if (cli.getDni() == dni) {
                return true;
            }
        }
        return false;
    }

    public static long getID() {
        // 10 digits.
        long id = System.currentTimeMillis() % LIMIT;
        if (id <= last) {
            id = (last + 1) % LIMIT;
        }
        return last = id;
    }

    public void altaEnvio(float peso, float anchura, Cliente remitente, Cliente destinatario) {
        if (buscaPorDni(destinatario.getDni()) == false) {
            clientes.add(destinatario);
        }

        Integer localizador = (int) getID();
        while (!paquetes.containsKey(localizador)) {
            localizador = (int) getID();
        }

        Paquete paquet = new Paquete(localizador, (float) 20.4, (float) 10, (float) 10);
        paquetes.put(localizador, paquet);
    }

    String verEstado(int localizador) {
        if (!paquetes.containsKey(localizador)) {
            throw new IllegalArgumentException("Este localizador: " + localizador + " no existe");
        }
        return paquetes.get(localizador).getEstado().toString();
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
        for (Paquete value : paquetes.values()) {
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

    /*ArrayList<PuntoDeControl> calcularRutaPaquete(String localidadRem, String localidadDest) {
        int idRem=0, idDest;
        for (int i = 1; i < puntosDeControl.size(); i++) {
            if (puntosDeControl.get(i).getProvincia().contains(localidadRem));
                idRem = puntosDeControl.get(i).getId();
            if (puntosDeControl.get(i).getProvincia().contains(localidadDest));
                idDest = puntosDeControl.get(i).getId();
        }        
        ArrayList sol=new ArrayList();
        boolean [] visitado =new boolean[10];
        sol.add(idRem);
        return null;
    }
    
    ArrayList ruta(int idRem, int idDest, ArrayList<PuntoDeControl>p, ArrayList sol){
        
        if(p.get(idRem).getConexiones().contains(idDest)){
             sol.add(idDest);
             return sol;
        }
            
        if (sol.add(p.get(idRem).getConexiones().size()>=2)){
            ArrayList p1 = new ArrayList();
            ArrayList p2 = new ArrayList();
            p1 = ruta(idRem, idDest, p, sol);
            p2 = ruta(idRem, idDest, p, sol);
            if ()
        }

        for (int i = 0 ; i < p.get(idRem).getConexiones().size(); i++){
            sol.add(p.get(idRem).getConexiones().get(i));
        }
        
        
        if (prof.contains(idRem))
        
        return null;
    }*/
    public void leerJson() throws IOException {
        String jsonStr = Files.readString(new File("redujapack.json").toPath());
        JsonObject raiz = new Gson().fromJson(jsonStr, JsonObject.class);

        for (int i = 1; i <= raiz.size(); i++) {
            JsonObject centro1 = raiz.getAsJsonObject(String.valueOf(i));
            int id = i;
            String nombre = centro1.get("nombre").getAsString();
            String localizacion = centro1.get("localización").getAsString();

            //System.out.println(centro1.getAsJsonArray("conexiones"));
            JsonArray provincias = centro1.getAsJsonArray("provincias");
            JsonArray conexiones = centro1.getAsJsonArray("conexiones");

            ArrayList<String> listdata = new ArrayList<String>();
            //ArrayList listdata2 = new ArrayList();
            for (int j = 0; j < provincias.size(); j++) {
                listdata.add(provincias.get(j).getAsString());
            }
//            for (int j = 0; j < conexiones.size(); j++) {
//                listdata2.add(conexiones.get(j));
//            }
            PuntoDeControl punto = new PuntoDeControl(id, nombre, localizacion, listdata);
            puntosDeControl.add(punto);
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
