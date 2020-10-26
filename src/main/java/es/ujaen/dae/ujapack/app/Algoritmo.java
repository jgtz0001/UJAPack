///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package es.ujaen.dae.ujapack.app;
//
//import es.ujaen.dae.ujapack.entidades.PuntoDeControl;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
///**
// *
// * @author jenar
// */
//public class Algoritmo {
//    
//    
//
//    public boolean[] visitiadoAnchura = new boolean[10];
//    public boolean[] visitiadoProfunidad = new boolean[10];
//
//    public class Grafo {
//
//        public int[][] g = {{2, 1, 0, 1, 0},
//        {1, 2, 1, 0, 0},
//        {0, 1, 2, 1, 0},
//        {1, 0, 1, 2, 1},
//        {0, 0, 0, 1, 2}};
//
//        public Grafo() {
//        }
//
//        public int[][] getG() {
//            return g;
//        }
//    }
//    
//    class casilla {
//
//        /**
//         * @return the camino
//         */
//        public ArrayList getCamino() {
//            return camino;
//        }
//
//        /**
//         * @param camino the camino to set
//         */
//        public void setCamino(ArrayList camino) {
//            this.camino = camino;
//        }
//
//        /**
//         * @return the coste
//         */
//        public int getCoste() {
//            return coste;
//        }
//
//        /**
//         * @param coste the coste to set
//         */
//        public void setCoste(int coste) {
//            this.coste = coste;
//        }
//
//        /**
//         * @return the adyacentes
//         */
//
//        private ArrayList camino;
//        private int coste;
//
//        casilla() {
//            //camino = new List<String>();
//            coste = -1;
//        }
//
//        casilla(int tama) {
//            //camino = new List<String>();
//            coste = tama;
//        }
//    }
//
//    //Vector<Vector<casilla>> matriz;
//    casilla[][] matriz;
//
//    Algoritmo() {
//        //matriz = new Vector<Vector<casilla>>(10);
//        
//    }
//
//    void rellenaMatriz(int tamanio, ArrayList<PuntoDeControl> puntosDeControl) {
//        casilla c = new casilla();
//        casilla relleno = new casilla(0);
//        //rellenamos la matriz de adyacencias con 0
//        for (int i = 0; i < tamanio; i++) {
//            for (int j = 0; j < tamanio; j++) {
//                matriz[i][j] = relleno;
//            }
//        }
//        //rellenamos la diagonal -1 ya que no sirve
//        for (int i = 0; i < tamanio; i++) {
//            matriz[i][i] = c;
//        }
//        
//        //esta funcion, marca los adyacentes con 1 y añade la trayectoria de ida y vuelta a seguir
//        casilla rellenoAdyacentesIda = new casilla(1);
//        casilla rellenoAdyacentesVuelta = new casilla(1);
//        for(int i = 0; i < 10; i++){
//            ArrayList p = puntosDeControl.get(i).getConexiones();
//            for (int j=0;j<p.size();j++){
//                ArrayList trayectoEnIdsIda = new ArrayList();
//                ArrayList trayectoEnIdsVuelta = new ArrayList();
//                
//                trayectoEnIdsIda.add(i); trayectoEnIdsIda.add(j);
//                trayectoEnIdsVuelta.add(j); trayectoEnIdsVuelta.add(i);
//                
//                rellenoAdyacentesIda.setCamino(trayectoEnIdsIda);
//                matriz[i][(int)p.get(j)] = rellenoAdyacentesIda;
//                
//                rellenoAdyacentesVuelta.setCamino(trayectoEnIdsVuelta);
//                matriz[j][(int)p.get(i)] = rellenoAdyacentesVuelta;
//            }
//        }
//        
//    }
//
//    public ArrayList<Integer> recorridoProfunidad(int nodoI) {
//        //Lista donde guardo los nodos recorridos
//        ArrayList<Integer> recorridos = new ArrayList<Integer>();
//        visitiadoProfunidad[nodoI] = true;//El nodo inicial ya está visitado
//        //Cola de visitas de los nodos adyacentes
//        ArrayList<Integer> cola = new ArrayList<Integer>();
//        recorridos.add(nodoI);//Listo el nodo como ya recorrido
//        cola.add(nodoI);//Se agrega el nodo a la cola de visitas
//        while (!cola.isEmpty()) {//Hasta que visite todos los nodos
//            int j = cola.remove(0);//Se saca el primer nodo de la cola
//            //Se busca en la matriz que representa el grafo los nodos adyacentes
//            for (int i = 0; i < matriz.length; i++) {
//                //Si es un nodo adyacente y no está visitado entonces
//                if (matriz[j][i].getCoste() == 1 && !visitiadoProfunidad[i]) {
//                    cola.add(i);//Se agrega a la cola de visita
//                    //Se recorren los hijos del nodo actual de visita y se agrega el recorrido al la lista
//                    recorridos.addAll(recorridoProfunidad(i));
//                    visitiadoProfunidad[i] = true;//Se marca como visitado
//                }
//            }
//        }
//        return recorridos;//Se devuelve el recorrido del grafo en profundidad
//    }
//
//}
