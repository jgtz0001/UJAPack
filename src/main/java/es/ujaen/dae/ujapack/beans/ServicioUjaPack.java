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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import java.io.File;
import java.nio.file.Files;
import java.util.Collections;

public class ServicioUjaPack {

    class Nodo {

        Integer id;
        private ArrayList<Integer> lista;
        ArrayList<Integer> conexionesId;

        Nodo(Integer _id, ArrayList<Integer> conexiones) {
            this.id = _id;
            lista = new ArrayList<Integer>();
            lista.add(_id);
            conexionesId = conexiones;
        }

        public ArrayList<Integer> getConexionesId() {
            return conexionesId;
        }
    }

    private ArrayList<PuntoDeControl> puntosDeControl;
    private HashMap<Integer, Paquete> paquetes;
    private ArrayList<Cliente> clientes;
    private static final long LIMIT = 10000000000L;
    private static long last = 0;
    private ArrayList<CentroDeLogistica> centros;

    public ServicioUjaPack() {
        puntosDeControl = new ArrayList<PuntoDeControl>();
        paquetes = new HashMap<Integer, Paquete>();
        clientes = new ArrayList<Cliente>();
        centros = new ArrayList<CentroDeLogistica>();
    }

    private boolean buscaPorDni(String dni) {
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

    public ArrayList<String> altaEnvio(float peso, float anchura, float altura, Cliente remitente, Cliente destinatario) {
        if (buscaPorDni(destinatario.getDni()) == false) {
            clientes.add(destinatario);
        }

        Integer localizador = (int) getID();
        while (paquetes.containsKey(localizador)) {
            localizador = (int) getID();
        }

        Integer idProvinciaRem = obtenerId(remitente.getProvincia());
        Integer idProvinciaDest = obtenerId(destinatario.getProvincia());
        ArrayList<String> ruta = new ArrayList<String>();
        float costeEnvio = 0;

        if (idProvinciaDest != 0 && idProvinciaRem != 0) {
            ruta = calcularRutaPaquete(remitente.getLocalidad(), destinatario.getLocalidad());
            costeEnvio = calcularImporte(ruta.size(), peso, altura, anchura);

            PuntoDeControl p = new PuntoDeControl();
            for (int i = 0; i < puntosDeControl.size(); i++) {
                if (puntosDeControl.get(i).getLocalizacion() == ruta.get(0)) {
                    p = puntosDeControl.get(i);
                    break;
                }
            }
            Paquete paquet = new Paquete(localizador, costeEnvio, peso, anchura, p);
            paquet.setRuta(ruta);
            paquetes.put(localizador, paquet);
            System.out.println("El paquete ha sido creado con exito.");
        } else {
            System.out.println("El paquete no ha podido ser creado.");
            throw new IllegalArgumentException("El id no coincide");
        }
        return ruta;
    }

    ArrayList<String> rutaFinal(ArrayList<String> rutaIncompleta, String provinciaRem, String provinciaDest) {
        ArrayList<String> rutaCompleta = new ArrayList<String>();
        rutaCompleta.add(provinciaRem);
        for (int i = 0; i < rutaIncompleta.size(); i++) {
            rutaCompleta.add(rutaIncompleta.get(i));
        }
        rutaCompleta.add(provinciaDest);
        return rutaCompleta;
    }

    Integer obtenerId(String provincia) {
        ArrayList<String> localizacion = new ArrayList<String>();
        for (int i = 0; i < centros.size(); i++) {
            localizacion = centros.get(i).getProvincia();
            if (localizacion.contains(provincia)) {
                return centros.get(i).getId();
            }
        }

        return null;
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

    public void leerJson() throws IOException {
        String jsonStr = Files.readString(new File("redujapack.json").toPath());
        JsonObject raiz = new Gson().fromJson(jsonStr, JsonObject.class);

        for (int i = 1; i <= raiz.size(); i++) {
            JsonObject centro1 = raiz.getAsJsonObject(String.valueOf(i));
            int id = i;
            String nombre = centro1.get("nombre").getAsString();
            String localizacion = centro1.get("localización").getAsString();

            JsonArray provincias = centro1.getAsJsonArray("provincias");
            JsonArray conexiones = centro1.getAsJsonArray("conexiones");

            ArrayList<String> listdata = new ArrayList<String>();
            ArrayList listdata2 = new ArrayList();
            for (int j = 0; j < provincias.size(); j++) {
                listdata.add(provincias.get(j).getAsString());
            }
            for (int j = 0; j < conexiones.size(); j++) {
                listdata2.add(conexiones.get(j).getAsInt());
            }
            PuntoDeControl punto = new PuntoDeControl(id, nombre, localizacion, listdata);
            puntosDeControl.add(punto);

            CentroDeLogistica centroNuevo = new CentroDeLogistica(id, nombre, localizacion, listdata, listdata2);
            centros.add(centroNuevo);

        }
    }

    Nodo nodoConexiones(ArrayList<Integer> lista, Integer id, boolean[] visitados) {
        for (int i = 0; i < centros.size(); i++) {
            if (centros.get(i).getId() == id) {
                Nodo n = new Nodo(id, centros.get(i).getConexiones());
                for (int j=0; j<lista.size(); j++){
                    n.lista.add(lista.get(j));
                } 

                return n;
            }
        }
        return null;
    }

    ArrayList<String> rutaString(ArrayList<Integer> rutaEnIds) {
        ArrayList<String> rutaStr = new ArrayList<String>();
        for (int i = 0; i < rutaEnIds.size(); i++) {
            rutaStr.add(centros.get(rutaEnIds.get(i)-1).getLocalizacion());
        }
        Collections.reverse(rutaStr);
        return rutaStr;
    }

    ArrayList<String> busquedaAnchura(Integer origen, Integer destino, ArrayList<Integer> conexiones) {
        boolean[] visitados = new boolean[11];
        boolean[] conexionesVisitadas = new boolean[11];
        ArrayList<Nodo> arrayBusquedaNodos = new ArrayList<Nodo>();
        ArrayList<Integer> arrayBusquedaIds = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            visitados[i] = false;
            conexionesVisitadas[i] = false;
        }

        Integer contador = 0;

        Nodo primero = new Nodo(origen, conexiones);
        arrayBusquedaNodos.add(primero);
        ArrayList<Integer> conexionesWhile = new ArrayList<Integer>();
        ArrayList<Integer> copiaPrimero = new ArrayList<Integer>();
        while (contador != 10) { //!arrayBusqueda.contains(destino)
            primero = arrayBusquedaNodos.get(contador);
            conexionesWhile = arrayBusquedaNodos.get(contador).conexionesId;
            for (int i = 0; i < conexionesWhile.size(); i++) {
                if (!visitados[arrayBusquedaNodos.get(contador).id]) {
                    ArrayList<Integer> auxiliarNo = arrayBusquedaNodos.get(contador).getConexionesId();

                    copiaPrimero = primero.lista;
                    Nodo n = nodoConexiones(copiaPrimero, auxiliarNo.get(i), conexionesVisitadas);
                    if (n != null) {
                        if (!visitados[auxiliarNo.get(i)]) {
                            if (!conexionesVisitadas[auxiliarNo.get(i)]) {
                                arrayBusquedaIds.add(auxiliarNo.get(i));
                                conexionesVisitadas[auxiliarNo.get(i)] = true;
                            }
                            arrayBusquedaNodos.add(n);
                        }
                        if (n.lista.contains(destino)) {
                            return rutaString(n.lista);
                        }
                    }
                }
            }
            visitados[arrayBusquedaNodos.get(contador).id] = true;
            contador++;
        }
        return null;
    }

    ArrayList<String> calcularRutaPaquete(String localidadRem, String localidadDes) {
        Integer idRem = 0;
        Integer idDest = 0;
        ArrayList<String> ruta = new ArrayList<String>();
        for (int i = 0; i < puntosDeControl.size(); i++) {
            if (idRem != 0 && idDest != 0) {
                break;
            } else {
                if (puntosDeControl.get(i).getProvincia().contains(localidadRem)) {
                    idRem = puntosDeControl.get(i).getId();
                }
                if (puntosDeControl.get(i).getProvincia().contains(localidadDes)) {
                    idDest = puntosDeControl.get(i).getId();
                }
            }
        }

        if (idRem != 0 && idDest != 0) {
            ruta = busquedaAnchura(idRem, idDest, centros.get(idRem - 1).getConexiones());
            if (!ruta.contains(localidadDes))
                ruta.add(localidadDes);
            if (!ruta.contains(localidadRem))
                ruta = añade(localidadRem, ruta);
        }
        return ruta;
    }

    ArrayList<String> añade (String localidadRem, ArrayList<String> ruta){
        ArrayList<String> rutaFinal =  new ArrayList<String>();
        rutaFinal.add(localidadRem);
        for (int i=0; i< ruta.size(); i++){
            rutaFinal.add(ruta.get(i));
        }
        return rutaFinal;
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
