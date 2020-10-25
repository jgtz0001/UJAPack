/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.interfaces;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import java.util.Set;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 *
 * @author Pablo
 */
public class ServicioUjaPack {

    ArrayList<PuntoDeControl> puntosDeControl;
    HashMap<Integer, Paquete> paquetes;
    ArrayList<Cliente> clientes;

    void altaEnvio(float peso, float anchura, Cliente remitente, Cliente destinatario) {
        Random rand = new Random();
        Integer localizador = rand.nextInt();

        while (paquetes.containsKey(rand)) {
            localizador = rand.nextInt();
        }

//habria que meter la función calcularpuntoscontrol
        Paquete paquet = new Paquete(localizador, "Preparado", (float) 20.4, (float) 10, (float) 10);
        paquetes.put(localizador, paquet);
    }

    String verEstado(int localizador) {
        if (!paquetes.containsKey(localizador)) {
            throw new IllegalArgumentException("Este localizador: " + localizador + " no existe");
        }
        return paquetes.get(localizador).getEstado();
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
        float importe = (peso *altura * anchura * numPuntosControl / 1000);
        return importe;
    }

    List<PuntoDeControl> calcularRutaPaquete(String localidadRem, String localidadDest) {
        return null;
    }

    public void anadirJSON(String file) throws IOException {
//JSONObject myObject = new JSONObject();
        HashMap nodos = new HashMap<>();
        Map<Integer, ArrayList<Integer>> conexionesTemp = new HashMap<>();
        int contadorProvincias = 100;

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        StringBuilder strb = new StringBuilder();
        String strAux = null;

        while ((strAux = br.readLine()) != null) {
            strb.append(strAux);
        }

        String jsonStr = strb.toString();
        Gson gson = new Gson();
        JsonObject raiz = gson.fromJson(jsonStr, JsonObject.class);
        Set<String> centrosLogSetStr = raiz.keySet();

        for (String centroStr : centrosLogSetStr) {
            JsonObject centroJson = raiz.getAsJsonObject(centroStr);
            

            int id = Integer.parseInt(centroStr);
            String nombre = centroJson.get("nombre").getAsString();
            String localizacion=centroJson.get("localizacion").getAsString();
            CentroDeLogistica c=new CentroDeLogistica(id,nombre,localizacion,"");
            Nodo centroNodo = new Nodo(id, nombre);

            nodos.put(id, centroNodo);
            
            
            JsonArray provincias = centroJson.getAsJsonArray("provincias");

            for (JsonElement provincia : provincias) {
                Nodo nodoProvincia = new Nodo(contadorProvincias++, provincia.getAsString());
                nodoProvincia.setConexiones(centroNodo.conexiones);
                centroNodo.setConexiones(nodoProvincia.conexiones);
                nodos.put(nodoProvincia.getId(), nodoProvincia);
            }
            
            JsonArray conexiones=centroJson.getAsJsonArray("conexiones");
            ArrayList<Integer>conexTemp=new ArrayList<>();
            
            for (JsonElement conexion: conexiones){
                conexTemp.add(Integer.parseInt(conexion.getAsString()));
            }
        }
    }
    
  

    public class Nodo {

        private int id;
        private String nombre;
        private Map<Integer, Nodo> conexiones;

        public Nodo(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
            conexiones = new HashMap<>();
        }

        public void setConexiones(Map<Integer, Nodo> conexiones) {
            this.conexiones = conexiones;
        }
        public int getId(){ 
            return id;
        }
        
         public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
    }

}
