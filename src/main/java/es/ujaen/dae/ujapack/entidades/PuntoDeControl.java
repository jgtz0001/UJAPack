 /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package es.ujaen.dae.ujapack.entidades;

    import java.util.ArrayList;

    /**
     *
     * @author Pablo
     */
    public class PuntoDeControl {
        private int id;
        private String nombre;
        private String localizacion;
        private ArrayList<String> provincia;
        private ArrayList conexiones;

        public PuntoDeControl(int id, String nombre, String localizacion, ArrayList<String> provincia, ArrayList conexiones) {
            this.id = id;
            this.nombre = nombre;
            this.localizacion = localizacion;
            this.provincia = provincia;
            this.conexiones = conexiones;
        }
        
        ArrayList calcularPuntoControlActual(){
            return null;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getLocalizacion() {
            return localizacion;
        }

        public void setLocalizacion(String localizacion) {
            this.localizacion = localizacion;
        }

        public ArrayList getProvincia() {
            return provincia;
        }

        public void setProvincia(ArrayList provincia) {
            this.provincia = provincia;
        }

        /**
         * @return the conexiones
         */
        public ArrayList getConexiones() {
            return conexiones;
        }

        /**
         * @param conexiones the conexiones to set
         */
        public void setConexiones(ArrayList conexiones) {
            this.conexiones = conexiones;
        }

    }
