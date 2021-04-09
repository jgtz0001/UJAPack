/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujapack.beans;

import es.ujaen.dae.ujapack.entidades.Cliente;
import es.ujaen.dae.ujapack.entidades.Paquete;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import java.time.LocalDateTime;
import java.util.List;
import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import es.ujaen.dae.ujapack.excepciones.DNINoValido;
import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import es.ujaen.dae.ujapack.excepciones.LocalizadorNoExiste;
import es.ujaen.dae.ujapack.excepciones.LocalizadorNoValido;
import es.ujaen.dae.ujapack.repositorios.RepositorioCliente;
import es.ujaen.dae.ujapack.repositorios.RepositorioPuntoDeControl;
import es.ujaen.dae.ujapack.repositorios.RepositorioCentroDeLogistica;
import es.ujaen.dae.ujapack.repositorios.RepositorioPaquete;
import java.util.ArrayList;
import java.util.Optional;
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
        private List<Integer> lista;
        List<Integer> conexionesId;

        Nodo(int _id, List<Integer> conexiones) {
            this.id = _id;
            lista = new ArrayList<Integer>();
            lista.add(_id);
            conexionesId = conexiones;
        }

        public List<Integer> getConexionesId() {
            return conexionesId;
        }
    }

    /*
* Constructor de la clase.
     */
    public ServicioUjaPack() {
    }

    /*
* Este metodo genera ids aleatorios de 10 digitos.
* @return el id localizador.
     */
    public static long getID() {
        int localizador = 0;
        Long min = 1000000000L;
        Long max = 9999999999L;

        do {
            localizador = (int) Math.floor(Math.random() * (max - min + 1) + min);
        } while (Integer.toString(localizador).length() != 10);
        return localizador;
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
        List<PuntoDeControl> ruta = new ArrayList<PuntoDeControl>();
        float costeEnvio = 0;

        ruta = calcularRutaPaquete(remitente.getLocalidad(), destinatario.getLocalidad(), idProvinciaRem, idProvinciaDest);
        costeEnvio = calcularImporte(ruta.size(), peso, altura, anchura);

        Paquete paquet = new Paquete(localizador, costeEnvio, peso, anchura, ruta, remitente, destinatario);
        repositorioPaquete.guardar(paquet);
        return paquet;
    }

    private List<Integer> completaRuta(List<Integer> ruta, int idProvinciaRem, int idProvinciaDest) {
        List<Integer> rutaDefinitiva = new ArrayList<Integer>();

        if (!ruta.contains(idProvinciaDest)) {
            rutaDefinitiva.add(idProvinciaDest);
        }

        for (int i = 0; i < ruta.size(); i++) {
            rutaDefinitiva.add(ruta.get(i));
        }

        if (!ruta.contains(idProvinciaRem)) {
            rutaDefinitiva.add(idProvinciaRem);
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
* Obtiene el ID de la Lista de Puntos de control de la ruta.
* @param listaRuta lista de la ruta de un paquete.
* @param localizadorRuta.
* @return iterador.
     */
    int obtenerIdLista(List<PuntoDeControl> listaRuta, int localizadorRuta) {
        for (int iterador = 0; iterador < listaRuta.size(); iterador++) {
            if (listaRuta.get(iterador).getId() == localizadorRuta) {
                return iterador;
            }
        }
        return -1;
    }

    /*
* Avisa del estado y envia el paquete.
* @param localizador Identificador del paquete.
* @param fechaSalida Fecha de Salida del paquete.
* @param punto Punto de control al que llega el paquete.
* @return cadena de caracteres informando al cliente.
     */
    public String notificarSalida(int localizador, LocalDateTime fechaSalida, int punto) {
        Paquete p = repositorioPaquete.buscar(localizador);
        compruebaPaquete(p);
        PuntoDeControl puntos = p.getRuta().get((obtenerIdLista(p.getRuta(), punto)));
        p.notificaSalida(fechaSalida, puntos);
        repositorioPaquete.actualizarPaquete(p);
        return (fechaSalida.toString());
    }

    /*
* Avisa del estado y envia el paquete.
* @param localizador Identificador del paquete.
* @param fechaEntrada Fecha de Entrada del paquete.
* @param punto Punto de control al que llega el paquete.
* @return cadena de caracteres informando al cliente.
     */
    public String notificarEntrada(int localizador, LocalDateTime fechaEntrada, int punto) {
        Paquete p = repositorioPaquete.buscar(localizador);
        compruebaPaquete(p);
        PuntoDeControl puntos = p.getRuta().get((obtenerIdLista(p.getRuta(), punto)));
        p.notificaEntrada(fechaEntrada, puntos);
        repositorioPaquete.actualizarPaquete(p);
        return (fechaEntrada.toString());
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

    public boolean comprobarImporte(double precio1, float precio2) {
        return precio1 == precio2;
    }

    public boolean comprobarNPuntosRuta(int num1, int num2) {
        return num1 == num2;
    }

    /*
* Crea los nodos con las conexiones que se le pasan (id).
* @param lista Lista que contiene los ids por los que pasa el paquete antes de llegar a ese nodo.
* @param id Id el cual se va a añadir al nodo si no esta visitado.
* @param visitados Vector estático booleano que contiene los ya visitados para no meter repetidos.
* @return devuelve un nodo con todos sus atributos completos.
     */
    private Nodo nodoConexiones(List<Integer> lista, int id, boolean[] visitados) {
//        CentroDeLogistica centro = repositorioCentroDeLogistica.BuscarIdCentro(id);//.orElseThrow(IdIncorrecto::new);
//        if (centro == null) {
//            throw new IdIncorrecto();
//        }
        List<Integer> conexiones = repositorioCentroDeLogistica.BuscaIdCL(id);
        Nodo n = new Nodo(id, conexiones);
        for (int j = 0; j < lista.size(); j++) {
            n.lista.add(lista.get(j));
        }
        return n;

    }

    /*
* Cambia el array de enteros a su respectivo nombre del centro logístico.
* @param rutaEnIds List que contiene la ruta en ids.
* @return devuelve la ruta de los centros por los que pasa.
     */
    private List<PuntoDeControl> rutaString(List<Integer> rutaEnIds) {
        List<PuntoDeControl> rutaStr = new ArrayList<PuntoDeControl>();
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
* @param conexiones List que tiene las conexiones del punto de control.
     */
    @Transactional
    private List<PuntoDeControl> busquedaAnchura(Integer origen, int destino, List<Integer> conexiones) {
        boolean[] visitados = new boolean[11];
        boolean[] conexionesVisitadas = new boolean[11];
        List<Nodo> arrayBusquedaNodos = new ArrayList<Nodo>();
        List<Integer> arrayBusquedaIds = new ArrayList<Integer>();
        List<Integer> rutaDefinitiva = new ArrayList<Integer>();

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
        List<Integer> conexionesWhile = new ArrayList<Integer>();
        List<Integer> copiaPrimero = new ArrayList<Integer>();
        while (contador != 10) {
            primero = arrayBusquedaNodos.get(contador);
            conexionesWhile = arrayBusquedaNodos.get(contador).conexionesId;
            for (int i = 0; i < conexionesWhile.size(); i++) {
                if (!visitados[arrayBusquedaNodos.get(contador).id]) {
                    List<Integer> auxiliarNo = arrayBusquedaNodos.get(contador).getConexionesId();

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
                            return rutaString(completaRuta(n.lista, origen, destino));
                        }
                    }
                }
            }
            visitados[arrayBusquedaNodos.get(contador).id] = true;
            contador++;
        }
        return null;
    }

    void compruebaPaquete(Paquete p) {
        for (int i = 0; i < p.getRuta().size(); i++) {
            for (int j = 0; j < p.getRuta().size(); j++) {
                if (i != j) {
                    if (p.getRuta().get(i).getId() == p.getRuta().get(j).getId()) {
                        p.getRuta().remove(j);
                    }
                }
            }
        }

        for (int i = 0; i < p.getPasanPaquetes().size(); i++) {
            for (int j = 0; j < p.getPasanPaquetes().size(); j++) {
                if (i != j) {
                    if (p.getPasanPaquetes().get(i).getId() == p.getPasanPaquetes().get(j).getId()) {
                        p.getPasanPaquetes().remove(j);
                    }
                }
            }
        }
    }

    @Transactional
    public List<PuntoDeControl> calcularRutaPaquete(String localidadRem, String localidadDes, int idProvinciaRem, int idProvinciaDest) {
        List<PuntoDeControl> ruta = new ArrayList<PuntoDeControl>();
        int n = repositorioPuntoDeControl.BuscaIdProvinciaCL(idProvinciaRem);
        CentroDeLogistica c = repositorioCentroDeLogistica.buscarPorId(n);
        List<Integer> conexiones = new ArrayList<Integer>();

        if (idProvinciaRem != 0 && idProvinciaDest != 0) {
            conexiones = repositorioCentroDeLogistica.BuscaIdCL(n);
            ruta = busquedaAnchura(idProvinciaRem, idProvinciaDest, conexiones);
        }
        return ruta;
    }

    public Paquete buscarPaquete(int localizador) {
        if (!repositorioPaquete.buscarPaquetes(localizador).isPresent()) {
            throw new LocalizadorNoExiste();
        }
        return repositorioPaquete.buscar(localizador);
    }

    //Metodos que usan los controladores
    public Optional<Cliente> verCliente(@NotBlank String dni) {
        Optional<Cliente> clienteLogin = repositorioClientes.buscar(dni);

        clienteLogin.ifPresent(c -> c.verDatos().size());
        return clienteLogin;
    }

    public Optional<Paquete> verPaquetes(@NotBlank String localizador) {
        int id = Integer.parseInt(localizador);
        Optional<Paquete> paquete = repositorioPaquete.buscarPaquetes(id);
        return paquete;
    }

    /**
     * Dar de alta cliente y crear una cuenta asociada
     *
     * @param cliente el cliente a dar de alta
     */
    public Cliente altaCliente(@NotNull Cliente cliente) {
        if (repositorioClientes.buscar(cliente.getDni()).isPresent()) {
            throw new DNINoValido();
        }
        if (cliente.getDni().length() != 8) {
            throw new DNINoValido();
        }
        repositorioClientes.guardar(cliente);
        return cliente;
    }

    public Paquete altaPaquete(@NotNull Paquete paquete) {
        if (repositorioPaquete.buscarPaquetes(paquete.getLocalizador()).isPresent()) {
            throw new LocalizadorNoValido();
        }
        if (Integer.toString(paquete.getLocalizador()).length() != 10) {
            throw new LocalizadorNoValido();
        }
        repositorioPaquete.guardar(paquete);
        return paquete;
    }

}
