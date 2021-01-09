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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import es.ujaen.dae.ujapack.excepciones.DNINoValido;
import es.ujaen.dae.ujapack.excepciones.IdIncorrecto;
import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import es.ujaen.dae.ujapack.excepciones.LocalizadorNoExiste;
import es.ujaen.dae.ujapack.excepciones.LocalizadorNoValido;
import es.ujaen.dae.ujapack.repositorios.RepositorioCliente;
import es.ujaen.dae.ujapack.repositorios.RepositorioPuntoDeControl;
import es.ujaen.dae.ujapack.repositorios.RepositorioCentroDeLogistica;
import es.ujaen.dae.ujapack.repositorios.RepositorioPaquete;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Validated
public class ServicioUjaPack {

    @Autowired
    RepositorioCliente repositorioClientes;

    @Autowired
    RepositorioPuntoDeControl repositorioPuntoDeControl;

    @Autowired
    RepositorioCentroDeLogistica repositorioCentroDeLogistica;

    @Autowired
    RepositorioPaquete repositorioPaquete;

    class Nodo {

        int id;
        private ArrayList<Integer> lista;
        ArrayList<Integer> conexionesId;

        Nodo(int _id, ArrayList<Integer> conexiones) {
            this.id = _id;
            lista = new ArrayList<Integer>();
            lista.add(_id);
            conexionesId = conexiones;
        }

        public ArrayList<Integer> getConexionesId() {
            return conexionesId;
        }
    }

    class CompletarPuntosDeControl {

        private int idPadre;
        private String NombrePadre;
        private ArrayList<String> provincias;

        public CompletarPuntosDeControl(int idPadre, ArrayList<String> provincias, String NombrePadre) {
            this.idPadre = idPadre;
            this.provincias = provincias;
            this.NombrePadre = NombrePadre;
        }

        /**
         * @return the idPadre
         */
        public int getIdPadre() {
            return idPadre;
        }

        /**
         * @return the provincias
         */
        public ArrayList<String> getProvincias() {
            return provincias;
        }

        /**
         * @return the NombrePadre
         */
        public String getNombrePadre() {
            return NombrePadre;
        }

    }

    private static final long LIMIT = 10000000000L;
    private static long last = 0;

    /*
* Constructor de la clase.
     */
    public ServicioUjaPack() {
    }

    @PostConstruct
    public void rellenarJson() {
        try {
            leerJson();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @Transactional(readOnly = true)
    private boolean buscaPorDni(String dni) {
        return repositorioClientes.buscar(dni).isPresent();
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

    /**
     * Realiza un login de un cliente
     *
     * @param dni el DNI del cliente
     * @param clave la clave de acceso
     * @return el objeto de la clase Cliente asociado
     */
    @Transactional
    public Optional<Cliente> loginCliente(@NotBlank String dni, @NotBlank String clave) {
        Optional<Cliente> clienteLogin = repositorioClientes.buscar(dni)
                .filter((cliente) -> cliente.claveValida(clave));

        // Asegurarnos de que se devuelve el cliente con los datos precargados
        clienteLogin.ifPresent(c -> c.verDatos().size());
        return clienteLogin;
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

        int localizador = (int) getID();
        while (repositorioPaquete.buscarPaquetes(localizador).isPresent()) {
            localizador = (int) getID();
        }

        int idProvinciaRem = obtenerIdProvincia(remitente.getProvincia());
        int idProvinciaDest = obtenerIdProvincia(destinatario.getProvincia());
        ArrayList<PuntoDeControl> ruta = new ArrayList<PuntoDeControl>();
        float costeEnvio = 0;

        ruta = calcularRutaPaquete(remitente.getLocalidad(), destinatario.getLocalidad(), idProvinciaRem, idProvinciaDest);
        costeEnvio = calcularImporte(ruta.size(), peso, altura, anchura);

        Paquete paquet = new Paquete(localizador, costeEnvio, peso, anchura, ruta, remitente, destinatario);
        repositorioPaquete.guardar(paquet);
        return paquet;
    }

    private ArrayList<Integer> completaRuta(ArrayList<Integer> ruta, int idProvinciaRem, int idProvinciaDest) {
        ArrayList<Integer> rutaDefinitiva = new ArrayList<>();

        if (!ruta.contains(idProvinciaRem)) {
            rutaDefinitiva.add(idProvinciaRem);
        }

        for (int i = 0; i < ruta.size(); i++) {
            rutaDefinitiva.add(ruta.get(i));
        }

        if (!ruta.contains(idProvinciaDest)) {
            rutaDefinitiva.add(idProvinciaDest);
        }
        return rutaDefinitiva;
    }

    /*
* Se utiliza para obtener el id del centro de logística en el que se encuentra a través de la provincia.
* @param provinica Provinica de la que tenemos que encontrar el id.
* @return devuelve el id del centro donde se encuentra la provincia.
     */
    private int obtenerIdProvincia(String provincia) {
        return repositorioPuntoDeControl.BuscaIdProvincia(provincia);
    }

    /*
* Avisa del estado al cliente.
* @param localizador Identificador del paquete.
* @return devuelve un string con el estado del paquete.
     */
    public String verEstado(int localizador) {
        Paquete p = repositorioPaquete.buscar(localizador);
        if (p == null) {
            throw new LocalizadorNoExiste();
        }
        return p.getEstado();
    }

    /*
* Avisa del estado y envia el paquete.
* @param localizador Identificador del paquete.
* @param fechaSalida Fecha de Salida del paquete.
* @param punto Punto de control al que llega el paquete.
* @return cadena de caracteres informando al cliente.
     */
    public String notificarSalida(int localizador, LocalDateTime fechaSalida, PuntoDeControl punto) {
        Paquete p = repositorioPaquete.buscar(localizador);
        if (p == null) {
            throw new LocalizadorNoExiste();
        }
        p.notificaSalida(fechaSalida, punto);
        repositorioPaquete.actualizarPaquete(p);

        return (fechaSalida + punto.getNombre());
    }

    /*
* Avisa del estado y envia el paquete.
* @param localizador Identificador del paquete.
* @param fechaEntrada Fecha de Entrada del paquete.
* @param punto Punto de control al que llega el paquete.
* @return cadena de caracteres informando al cliente.
     */
    //hay que actualizar 
    public String notificarEntrada(int localizador, LocalDateTime fechaEntrada, PuntoDeControl punto) {
        Paquete p = repositorioPaquete.buscar(localizador);
        if (p == null) {
            throw new LocalizadorNoExiste();
        }
        p.notificaEntrada(fechaEntrada, punto);
        repositorioPaquete.actualizarPaquete(p);
        return (fechaEntrada + punto.getNombre());
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
* Función que se encarga de leer un Json y añadir los datos a la base de datos.
     */
    @Transactional
    private void leerJson() throws IOException {
        String jsonStr = Files.readString(new File("redujapack.json").toPath());
        JsonObject raiz = new Gson().fromJson(jsonStr, JsonObject.class);
        ArrayList<CompletarPuntosDeControl> ARellenar = new ArrayList<CompletarPuntosDeControl>();
        ArrayList<CentroDeLogistica> centrosBD = new ArrayList<CentroDeLogistica>();
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

            CompletarPuntosDeControl nuevoPuntoDeControl = new CompletarPuntosDeControl(id, listdata, localizacion);
            ARellenar.add(nuevoPuntoDeControl);

            for (int j = 0; j < conexiones.size(); j++) {
                listdata2.add(conexiones.get(j).getAsInt());
            }
            PuntoDeControl punto = new PuntoDeControl(id, nombre, localizacion, id);
            repositorioPuntoDeControl.guardar(punto);

            CentroDeLogistica centroNuevo = new CentroDeLogistica(id, nombre, localizacion, listdata, listdata2);
            centrosBD.add(centroNuevo);

        }
        for (int i = 0; i < centrosBD.size(); i++) {
            repositorioCentroDeLogistica.guardar(centrosBD.get(i));
        }

        int id = 11;
        for (int i = 0; i < ARellenar.size(); i++) {
            ArrayList<String> provinciasAIncluir = ARellenar.get(i).getProvincias();
            for (int j = 0; j < provinciasAIncluir.size(); j++) {
                if (!provinciasAIncluir.get(j).equals(ARellenar.get(i).getNombrePadre())) {
                    PuntoDeControl punto = new PuntoDeControl(id, ("Calle " + provinciasAIncluir.get(j)), provinciasAIncluir.get(j), ARellenar.get(i).idPadre);
                    repositorioPuntoDeControl.guardar(punto);
                    id++;
                }
            }
        }
    }

    /*
* Crea los nodos con las conexiones que se le pasan (id).
* @param lista Lista que contiene los ids por los que pasa el paquete antes de llegar a ese nodo.
* @param id Id el cual se va a añadir al nodo si no esta visitado.
* @param visitados Vector estático booleano que contiene los ya visitados para no meter repetidos.
* @return devuelve un nodo con todos sus atributos completos.
     */
    private Nodo nodoConexiones(ArrayList<Integer> lista, int id, boolean[] visitados) {
        CentroDeLogistica centro = repositorioCentroDeLogistica.buscarPorId(id);
        if (centro == null) {
            throw new IdIncorrecto();
        }
        Nodo n = new Nodo(id, centro.getConexiones());
        for (int j = 0; j < lista.size(); j++) {
            n.lista.add(lista.get(j));
        }
        return n;

    }

    /*
* Cambia el array de enteros a su respectivo nombre del centro logístico.
* @param rutaEnIds ArrayList que contiene la ruta en ids.
* @return devuelve la ruta de los centros por los que pasa.
     */
    private ArrayList<PuntoDeControl> rutaString(ArrayList<Integer> rutaEnIds) {
        ArrayList<PuntoDeControl> rutaStr = new ArrayList<PuntoDeControl>();
        for (int i = 0; i < rutaEnIds.size(); i++) {
            rutaStr.add(repositorioPuntoDeControl.getPuntoDeControl(rutaEnIds.get(i)));
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
    private ArrayList<PuntoDeControl> busquedaAnchura(Integer origen, int destino, ArrayList<Integer> conexiones) {
        boolean[] visitados = new boolean[11];
        boolean[] conexionesVisitadas = new boolean[11];
        ArrayList<Nodo> arrayBusquedaNodos = new ArrayList<Nodo>();
        ArrayList<Integer> arrayBusquedaIds = new ArrayList<Integer>();
        ArrayList<Integer> rutaDefinitiva = new ArrayList<Integer>();

        int origenCentroLogistico = repositorioPuntoDeControl.BuscaIdProvinciaCL(origen);
        int destinoCentroLogistico = repositorioPuntoDeControl.BuscaIdProvinciaCL(destino);

        for (int i = 0; i < 10; i++) {
            visitados[i] = false;
            conexionesVisitadas[i] = false;
        }

        int contador = 0;

        Nodo primero = new Nodo(origenCentroLogistico, conexiones);
        if (origenCentroLogistico == destinoCentroLogistico) {
            rutaDefinitiva.add(origenCentroLogistico);
            rutaDefinitiva = completaRuta(rutaDefinitiva, origen, destino);
            return rutaString(rutaDefinitiva);
        }
        arrayBusquedaNodos.add(primero);
        ArrayList<Integer> conexionesWhile = new ArrayList<Integer>();
        ArrayList<Integer> copiaPrimero = new ArrayList<Integer>();
        while (contador != 10) { //!arrayBusqueda.contains(destino)
            primero = arrayBusquedaNodos.get(contador);
            conexionesWhile = arrayBusquedaNodos.get(contador).conexionesId;
            for (int i = 0; i < conexionesWhile.size(); i++) {
                if (!visitados[contador]) {
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
                        if (n.lista.contains(destinoCentroLogistico)) {
                            //completar ruta
                            completaRuta(n.lista, origen, destino);
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
    public ArrayList<PuntoDeControl> calcularRutaPaquete(String localidadRem, String localidadDes, int idProvinciaRem, int idProvinciaDest) {
        ArrayList<PuntoDeControl> ruta = new ArrayList<PuntoDeControl>();
        if (idProvinciaRem != 0 && idProvinciaDest != 0) {
            int jola = repositorioPuntoDeControl.BuscaIdProvinciaCL(idProvinciaRem);
            ArrayList<Integer> conexion = new ArrayList<Integer>();
            conexion.add(2);
            conexion.add(3);

// ruta = busquedaAnchura(idProvinciaRem, idProvinciaDest, repositorioCentroDeLogistica.BuscaIdCL(jola));
            ruta = busquedaAnchura(idProvinciaRem, idProvinciaDest, conexion);

        }
        return ruta;
    }

    /**
     * Realiza un login de un cliente
     *
     * @param dni el DNI del cliente
     * @param clave la clave de acceso
     * @return el objeto de la clase Cliente asociado
     */
    @Transactional
    public Optional<Cliente> verCliente(@NotBlank String dni) {
        Optional<Cliente> clienteLogin = repositorioClientes.buscar(dni);

        // Asegurarnos de que se devuelve el cliente con los datos precargados
        clienteLogin.ifPresent(c -> c.verDatos().size());
        return clienteLogin;
    }
    
        @Transactional
    public Optional<Paquete> verPaquetes(@NotBlank Integer localizador) {
        Optional<Paquete> paquete = repositorioPaquete.buscarPaquetes(localizador);//.orElseThrow(LocalizadorNoValido::new);

//        paquete.ifPresent(p -> p.);
        return paquete;
    }

    /**
     * Dar de alta cliente y crear una cuenta asociada
     *
     * @param cliente el cliente a dar de alta
     * @return el paquete asociado al cliente
     */
    public Cliente altaCliente(@NotNull @Valid Cliente cliente) {
        if (!repositorioClientes.buscar(cliente.getDni()).isPresent()) {
            throw new DNINoValido();
        }
        repositorioClientes.guardar(cliente);

        return cliente;
    }

    public Paquete altaPaquete(@NotNull Paquete paquete, @NotNull @Valid Cliente remitente, @NotNull @Valid Cliente destinatario) {
        if (!repositorioPaquete.buscarPaquetes(paquete.getLocalizador()).isPresent()) {
            throw new LocalizadorNoValido();
        }

        // Crear y registrar paquete
        Paquete paquet = altaEnvio(1, 1, 1, remitente, destinatario);
        repositorioPaquete.guardar(paquete);

        return paquete;
    }

    public Paquete buscarPaquete(int localizador) {
        if (!repositorioPaquete.buscarPaquetes(localizador).isPresent()) {
            throw new LocalizadorNoExiste();
        }
        return repositorioPaquete.buscar(localizador);
    }
}
