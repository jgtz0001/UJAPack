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
import es.ujaen.dae.ujapack.excepciones.DNINoEncontrado;
import es.ujaen.dae.ujapack.excepciones.DNINoValido;
import es.ujaen.dae.ujapack.excepciones.IdIncorrecto;
import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import es.ujaen.dae.ujapack.excepciones.LocalizadorNoExiste;
import javax.annotation.PostConstruct;

@Service
@Validated
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

    private final HashMap<Integer, PuntoDeControl> puntosDeControl;
    private final HashMap<Integer, Paquete> paquetes;
    private final HashMap<Integer, Cliente> clientes;
    private static final long LIMIT = 10000000000L;
    private static long last = 0;
    private final HashMap<Integer, CentroDeLogistica> centros;

    /*
* Constructor de la clase.
     */
    public ServicioUjaPack() {
        puntosDeControl = new HashMap<Integer, PuntoDeControl>();
        paquetes = new HashMap<Integer, Paquete>();
        clientes = new HashMap<Integer, Cliente>();
        centros = new HashMap<Integer, CentroDeLogistica>();
        try {
            leerJson();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

    }

    private boolean buscaPorDni(String dni) {
        return clientes.containsKey(dni);
    }

    /*
* Este metodo genera ids aleatorios de 10 digitos.
* @return el id localizador.
     */
    public static long getID() {
        long id = System.currentTimeMillis() % LIMIT;
        if (id <= last) {
            id = (last + 1) % LIMIT;
        }
        return last = id;
    }

    /*
* alta Envio es una función que de manera interna, crea el paquete con todos los atributos de su clase.
* @param peso Corresponde al peso del paquete.
* @param anchoura Medida sobre el paquete.
* @param altura Medida sobre el paquete.
* @param remitente Cliente que manda el paquete.
* @param destinatario Cliente a que va dirigido el paquete.
+ @return Devuelve el paquete.
     */
    public Paquete altaEnvio(float peso, float anchura, float altura, Cliente remitente, Cliente destinatario) {
        if (buscaPorDni(destinatario.getDni()) == false) {
            clientes.put(Integer.parseInt(destinatario.getDni()), destinatario);
        }

        Integer localizador = (int) getID();
        while (paquetes.containsKey(localizador)) {
            localizador = (int) getID();
        }

        Integer idProvinciaRem = obtenerId(remitente.getProvincia());
        Integer idProvinciaDest = obtenerId(destinatario.getProvincia());
        ArrayList<PuntoDeControl> ruta = new ArrayList<PuntoDeControl>();
        float costeEnvio = 0;

        if (idProvinciaDest == 0 || idProvinciaRem == 0) {
            throw new IdIncorrecto();
        }
        ruta = calcularRutaPaquete(remitente.getLocalidad(), destinatario.getLocalidad(), idProvinciaRem, idProvinciaDest);
        costeEnvio = calcularImporte(ruta.size(), peso, altura, anchura);

        ruta = completaRuta(ruta, remitente.getProvincia(), destinatario.getProvincia());

        Paquete paquet = new Paquete(localizador, costeEnvio, peso, anchura, ruta);
        paquetes.put(localizador, paquet);
        return paquet;
    }

    private ArrayList<PuntoDeControl> completaRuta(ArrayList<PuntoDeControl> ruta, String provinciaRem, String provinciaDest) {
        Integer idRem = obtenerId(provinciaRem);
        Integer idDest = obtenerId(provinciaDest);
        PuntoDeControl puntoControlRem = new PuntoDeControl(idRem, provinciaRem);
        PuntoDeControl puntoControlDest = new PuntoDeControl(idDest, provinciaDest);

        ArrayList<PuntoDeControl> rutaDefinitiva = new ArrayList<PuntoDeControl>();
        if (!ruta.get(0).getLocalizacion().equals(provinciaRem)) {
            rutaDefinitiva.add(puntoControlRem);
        }

        for (int i = 0; i < ruta.size(); i++) {
            rutaDefinitiva.add(ruta.get(i));
        }
        if (!ruta.get(ruta.size()-1).getLocalizacion().equals(provinciaDest)) {
            rutaDefinitiva.add(puntoControlDest);
        }
        return rutaDefinitiva;
    }

    /*
* Se utiliza para obtener el id del centro de logística en el que se encuentra a través de la provincia.
* @param provinica Provinica de la que tenemos que encontrar el id.
* @return devuelve el id del centro donde se encuentra la provincia.
     */
    private Integer obtenerId(String provincia) {
        for (HashMap.Entry<Integer, CentroDeLogistica> entry : centros.entrySet()) {
            Integer id = entry.getKey();
            CentroDeLogistica value = entry.getValue();
            if (value.getProvincia().contains(provincia)) {
                return id;
            }
        }
        return null;
    }

    /*
* Avisa del estado al cliente.
* @param localizador Identificador del paquete.
* @return devuelve un string con el estado del paquete.
     */
    public String verEstado(int localizador) {
        if (!paquetes.containsKey(localizador)) {
            throw new LocalizadorNoExiste();
        }
        return paquetes.get(localizador).getEstado().toString();
    }

    /*
    * Avisa del estado y envia el paquete.
    * @param localizador Identificador del paquete.
    * @param fechaSalida Fecha de Salida del paquete.
    * @param punto Punto de control al que llega el paquete.
    * @return cadena de caracteres informando al cliente.
     */
    public String avisaEstado(int localizador, LocalDateTime fechaSalida, PuntoDeControl punto) {
        if (!paquetes.containsKey(localizador)) {
            throw new LocalizadorNoExiste();
        }
        paquetes.get(localizador).notificaSalida(fechaSalida, punto);
        return (fechaSalida + punto.getNombre());
    }

    /*
* Funcion que devuelve todos los paquetes que ha enviado un cliente.
* @param dni DNI del cliente.
* @return devuelve la lista de paquetes que ha enviado el cliente.
     */
    private ArrayList<Paquete> listaPaquetes(String dni) {
        ArrayList<Paquete> lista = new ArrayList();
        if (dni.length() != 8) {
            throw new DNINoValido();
        }
        if (clientes.containsKey(dni)) {
            throw new DNINoEncontrado();
        }
        for (Paquete value : paquetes.values()) {
            if (value.getRemitente().getDni().equals(dni)) {
                lista.add(value);
            }
        }
        return lista;
    }

    /*
* Devuleve el importe al crear un paquete.
* @param numPuntosControl Número de puntos de control por los que pasa el paquete.
* @param peso Peso del paquete.
* @param altura Altura del paquete.
* @param anchura Anchura del paquete.
* @return devuelve el coste de enviar el paquete.
     */
    public float calcularImporte(int numPuntosControl, float peso, float altura, float anchura) {
        float importe = (peso * altura * anchura * (numPuntosControl + 1) / 1000);
        return importe;
    }

    /*
* Función que se encarga de leer un Json y añadir los datos a las estructuras.
     */
    private void leerJson() throws IOException {
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
            puntosDeControl.put(id, punto);

            CentroDeLogistica centroNuevo = new CentroDeLogistica(id, nombre, localizacion, listdata, listdata2);
            centros.put(id, centroNuevo);

        }
    }

    /*
* Crea los nodos con las conexiones que se le pasan (id).
* @param lista Lista que contiene los ids por los que pasa el paquete antes de llegar a ese nodo.
* @param id Id el cual se va a añadir al nodo si no esta visitado.
* @param visitados Vector estático booleano que contiene los ya visitados para no meter repetidos.
* @return devuelve un nodo con todos sus atributos completos.
     */
    private Nodo nodoConexiones(ArrayList<Integer> lista, Integer id, boolean[] visitados) {
        for (HashMap.Entry<Integer, CentroDeLogistica> entry : centros.entrySet()) {
            Integer idMap = entry.getKey();
            if (idMap.equals(id)) {
                CentroDeLogistica value = entry.getValue();
                Nodo n = new Nodo(id, value.getConexiones());
                for (int j = 0; j < lista.size(); j++) {
                    n.lista.add(lista.get(j));
                }
                return n;
            }
        }
        return null;
    }

    /*
* Cambia el array de enteros a su respectivo nombre del centro logístico.
* @param rutaEnIds ArrayList que contiene la ruta en ids.
* @return devuelve la ruta de los centros por los que pasa.
     */
    private ArrayList<PuntoDeControl> rutaString(ArrayList<Integer> rutaEnIds) {
        ArrayList<PuntoDeControl> rutaStr = new ArrayList<PuntoDeControl>();
        for (int i = 0; i < rutaEnIds.size(); i++) {
            rutaStr.add(puntosDeControl.get(rutaEnIds.get(i)));
        }
        Collections.reverse(rutaStr);
        return rutaStr;
    }

    /*
* Algoritmo que encuentra la ruta mas corta recorriendo un grafo establecido por las conexiones de cada centro logístico.
* @param origen Identificador del ponto de control de donde saldría el paquete.
* @param destino Identificador del punto de control a donde llegaría el paquete
* @param conexiones ArrayList que tiene las conexiones del punto de control.
     */
    private ArrayList<PuntoDeControl> busquedaAnchura(Integer origen, Integer destino, ArrayList<Integer> conexiones) {
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
        if (origen.equals(destino)) {
            return rutaString(primero.lista);
        }
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


    /*
* Calcula la ruta del paquete.
* @param localidadRem Localidad del remitente.
* @param localidadDes Localidad del destinatario.
* @param idProvinciaRem Identificador de la provincia donde se encuentra el remitente.
* @param idProvinciaDest Identificador de la provincia del destinatario.
     */
    public ArrayList<PuntoDeControl> calcularRutaPaquete(String localidadRem, String localidadDes, Integer idProvinciaRem, Integer idProvinciaDest) {
        ArrayList<PuntoDeControl> ruta = new ArrayList<PuntoDeControl>();
        if (idProvinciaRem != 0 && idProvinciaDest != 0) {
            ruta = busquedaAnchura(idProvinciaRem, idProvinciaDest, centros.get(idProvinciaRem).getConexiones());
        }
        return ruta;
    }

    /**
     * @return the puntosDeControl
     */
    public HashMap<Integer, PuntoDeControl> getPuntosDeControl() {
        return puntosDeControl;
    }

    /**
     * @return the clientes
     */
    public HashMap<Integer, Cliente> getClientes() {
        return clientes;
    }

}
