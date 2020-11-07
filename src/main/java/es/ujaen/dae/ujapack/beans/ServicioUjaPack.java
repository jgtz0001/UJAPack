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

public class ServicioUjaPack {

    class Nodo {

        Integer id;
        private ArrayList<Integer> lista;
        ArrayList<Integer> conexionesId;

        Nodo(Integer _id, ArrayList<Integer> conexiones) {
            this.id = _id;
            lista = null;
            lista.add(_id);
            conexionesId = conexiones;
        }
    }

    private ArrayList<PuntoDeControl> puntosDeControl;
    private HashMap<Integer, Paquete> paquetes;
    private ArrayList<Cliente> clientes;
    private static final long LIMIT = 10000000000L;
    private static long last = 0;
    private ArrayList<CentroDeLogistica> centros;

    ServicioUjaPack() {
        puntosDeControl = new ArrayList<PuntoDeControl>();
        paquetes = new HashMap<Integer, Paquete>();
        clientes = new ArrayList<Cliente>();
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

    public void altaEnvio(float peso, float anchura, float altura, Cliente remitente, Cliente destinatario) {
        if (buscaPorDni(destinatario.getDni()) == false) {
            clientes.add(destinatario);
        }

        Integer localizador = (int) getID();
        while (!paquetes.containsKey(localizador)) {
            localizador = (int) getID();
        }

        Integer idProvinciaRem = obtenerId(remitente.getProvincia());
        Integer idProvinciaDest = obtenerId(destinatario.getProvincia());
        ArrayList<String> ruta = new ArrayList<String>();
        float costeEnvio = 0;

        if (idProvinciaDest != 0 && idProvinciaRem != 0) {
            //ruta = busquedaAnchura(idProvinciaRem, idProvinciaDest, centros.get(idProvinciaRem).getConexiones());
            //calcular importe
            //costeEnvio = calcularImporte(ruta.size(), peso, altura, anchura);
            if (remitente.getProvincia() == destinatario.getProvincia()) {
                ruta.add(remitente.getProvincia());
                costeEnvio = calcularImporte(ruta.size(), peso, altura, anchura);
            } else {
                if (idProvinciaDest == idProvinciaRem) {
                    ruta.add(remitente.getProvincia());
                    ruta.add(centros.get(idProvinciaRem).getLocalizacion());
                    ruta.add(destinatario.getProvincia());
                    costeEnvio = calcularImporte(ruta.size(), peso, altura, anchura);
                } else {
                    ruta = busquedaAnchura(idProvinciaRem, idProvinciaDest, centros.get(idProvinciaRem).getConexiones());
                    ruta = rutaFinal(ruta, remitente.getProvincia(), destinatario.getProvincia());
                    costeEnvio = calcularImporte(ruta.size(), peso, altura, anchura);
                }
            }
            PuntoDeControl p = null;//puede petar por no inicializarlo bien
            for (int i = 0; i < puntosDeControl.size(); i++) {
                if (puntosDeControl.get(i).getLocalizacion() == ruta.get(0)) {
                    p = puntosDeControl.get(i);
                    break;
                }
            }
            Paquete paquet = new Paquete(localizador, costeEnvio, peso, anchura, p);
            paquet.setRuta(ruta);
            paquetes.put(localizador, paquet);
            System.out.print("El paquete ha sido creado con exito.");
        } else {
            System.out.print("El paquete no ha podido ser creado.");
            throw new IllegalArgumentException("El id no coincide");
        }
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
        for (int i = 0; i < centros.size(); i++) {
            //localizacion del centro == provincia del cliente donde reside
            if (provincia == centros.get(i).getLocalizacion()) {
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
            PuntoDeControl punto = new PuntoDeControl(id, nombre, localizacion, listdata);
            puntosDeControl.add(punto);

            CentroDeLogistica centroNuevo = new CentroDeLogistica(id, nombre, localizacion, listdata, listdata2);
            centros.add(centroNuevo);
        }
    }

    Nodo nodoConexiones(Integer id) {
        for (int i = 0; i < centros.size(); i++) {
            if (centros.get(i).getId() == id) {
                Nodo n = new Nodo(id, centros.get(i).getConexiones());
                return n;
            }
        }
        return null;
    }

     ArrayList<String> rutaString(ArrayList<Integer> rutaEnIds) {
        ArrayList<String> rutaStr = new ArrayList<String>();
        for (int i = 0; i < rutaEnIds.size(); i++) {
            rutaStr.add(centros.get(i).getLocalizacion());
        }
        return rutaStr;
    }

    ArrayList<String> busquedaAnchura(Integer origen, Integer destino, ArrayList<Integer> conexiones) {
        boolean[] visitados = new boolean[11];
        ArrayList<Nodo> arrayBusquedaNodos = new ArrayList<Nodo>();
        ArrayList<Integer> arrayBusquedaIds = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            visitados[i] = false;
        }

        Integer contador = 0;

        while (contador != 10) { //!arrayBusqueda.contains(destino)
            for (Integer enlace : arrayBusquedaNodos.get(contador).conexionesId) {
                //si el id del nodo esta a false, significa que no esta visitado
                //así nos evitamos una lista infinita
                if (!visitados[arrayBusquedaNodos.get(contador).id]) {
                    //Creamos un nuevo nodo con el id, conectando con el centro logístico en centros, dentro de nodoconexiones
                    //tambien tendria las conexiones del nodo, para seguir buscando si no se encuentra a la primera
                    Nodo n = nodoConexiones(arrayBusquedaNodos.get(contador).id);

                    //Si el nodo se crea a null, significa que algo va mal, el id es incorrecto ()
                    if (n != null) {
                        //esto es solo para el primero, para que se añada al camino
                        if (n.lista.contains(origen)) {
                            n.lista.add(origen);
                            if (arrayBusquedaIds.contains(destino)) {
                                //devuelve la lista del nodo desde el origen hasta el destino, por el camino mas corto.
                                return rutaString(n.lista);
                            }
                        }

                        //se añade el enlace, que es su conexion basicamente.
                        n.lista.add(enlace);
                        //se añade el nodo al arrayBusquedaNodos para que no se quede solo con el primer nodo
                        //por lo que aumenta la lista de nodos a recorrer en caso de que no haya sido el primero en encontrarlo.
                        //sin esto, peta claramente, porque quieres acceder a una posición que no existe
                        arrayBusquedaNodos.add(n);
                        //si al añadir este, es el destino, tendriamos el camino desde el origen, hasta el destino.
                        if (arrayBusquedaIds.contains(destino)) {
                            //devuelve la lista del nodo desde el origen hasta el destino, por el camino mas corto.
                            return rutaString(n.lista);
                        }
                    }
                }
                //visitados[arrayBusquedaNodos.get(contador).id] = true;
            }
            //Al acabar el for, tendríamos el nodo visitado, por lo que lo marcamos
            visitados[arrayBusquedaNodos.get(contador).id] = true;
            contador++; //En el momento que se visiten los 10 nodos del grafo saldriamos del while.
        }
        //devolveriamos un null en caso de que el destino no haya sido encontrado.
        return null;
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
