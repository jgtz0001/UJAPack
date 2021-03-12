/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.beans;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import es.ujaen.dae.ujapack.entidades.CentroDeLogistica;
import es.ujaen.dae.ujapack.entidades.Oficina;
import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
import es.ujaen.dae.ujapack.repositorios.RepositorioCentroDeLogistica;
import es.ujaen.dae.ujapack.repositorios.RepositorioCliente;
import es.ujaen.dae.ujapack.repositorios.RepositorioOficina;
import es.ujaen.dae.ujapack.repositorios.RepositorioPaquete;
import es.ujaen.dae.ujapack.repositorios.RepositorioPuntoDeControl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author PCJoseGabriel
 */
@Service
@Validated
public class ServicioJson {
    
    @Autowired
    RepositorioCliente repositorioClientes;

    @Autowired
    RepositorioPuntoDeControl repositorioPuntoDeControl;

    @Autowired
    RepositorioCentroDeLogistica repositorioCentroDeLogistica;

    @Autowired
    RepositorioPaquete repositorioPaquete;
    
    @Autowired
    RepositorioOficina repositorioOficina;
    
    
    public ServicioJson (){
        
    }
    @PostConstruct
    public void rellenarJson() {
        try {
            leerJson();
            
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
    
    
    /*Función que se encarga de leer un Json y añadir los datos a la base de datos. */
    private void leerJson() throws IOException {
        String jsonStr = Files.readString(new File("redujapack.json").toPath());
        JsonObject raiz = new Gson().fromJson(jsonStr, JsonObject.class);
        List<CompletarPuntosDeControl> ARellenar = new ArrayList<CompletarPuntosDeControl>();
        List<CentroDeLogistica> centrosBD = new ArrayList<CentroDeLogistica>();
        for (int i = 1; i <= raiz.size(); i++) {
            JsonObject centro1 = raiz.getAsJsonObject(String.valueOf(i));
            int id = i;
            String nombre = centro1.get("nombre").getAsString();
            String localizacion = centro1.get("localización").getAsString();

            JsonArray provincias = centro1.getAsJsonArray("provincias");
            JsonArray conexiones = centro1.getAsJsonArray("conexiones");

            List<String> listdata = new ArrayList<String>();;
            List<Integer> listdata2 = new ArrayList<Integer>();;
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
            
            Oficina OF = new Oficina (id,nombre,localizacion);
            repositorioOficina.guardar(OF);

            CentroDeLogistica centroNuevo = new CentroDeLogistica(id, nombre, localizacion, listdata, listdata2);
            centrosBD.add(centroNuevo);

        }
        for (int i = 0; i < centrosBD.size(); i++) {
            repositorioCentroDeLogistica.guardar(centrosBD.get(i));
        }

        int id = 11;
        for (int i = 0; i < ARellenar.size(); i++) {
            List<String> provinciasAIncluir = ARellenar.get(i).getProvincias();
            for (int j = 0; j < provinciasAIncluir.size(); j++) {
                if (!provinciasAIncluir.get(j).equals(ARellenar.get(i).getNombrePadre())) {
                    Oficina OF = new Oficina (id,("Calle " + provinciasAIncluir.get(j)), provinciasAIncluir.get(j));
                    PuntoDeControl punto = new PuntoDeControl(id, ("Calle " + provinciasAIncluir.get(j)), provinciasAIncluir.get(j), ARellenar.get(i).getIdPadre());
                    repositorioPuntoDeControl.guardar(punto);
                    repositorioOficina.guardar(OF);
                    id++;
                }
            }
        }
    }
    class CompletarPuntosDeControl {

        private int idPadre;
        private String NombrePadre;
        private List<String> provincias;

        public CompletarPuntosDeControl(int idPadre, List<String> provincias, String NombrePadre) {
            this.idPadre = idPadre;
            this.provincias = provincias;
            this.NombrePadre = NombrePadre;
        }

        /**
         * @return the NombrePadre
         */
        public String getNombrePadre() {
            return NombrePadre;
        }

        /**
         * @return the provincias
         */
        public List<String> getProvincias() {
            return provincias;
        }

        /**
         * @return the idPadre
         */
        public int getIdPadre() {
            return idPadre;
        }
     }
    
    
}
