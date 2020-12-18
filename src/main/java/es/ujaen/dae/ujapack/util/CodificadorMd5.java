/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/**
 * Codificador sencillo para contraseñas basado en Md5 (no seguro)
 * @author ajrueda
 */
public class CodificadorMd5 {

    private CodificadorMd5() {
    }

    public static String codificar(String cadena) {
        String cadenaCodificada = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(cadena.getBytes());
            cadenaCodificada = Base64.getEncoder().withoutPadding().encodeToString(md.digest());
        }
        catch(NoSuchAlgorithmException e) {
            // No debe ocurrir puesto que MD5 es un algoritmo que existe en la
            // implementación Java estándar
        }
        return cadenaCodificada;
    }
}
