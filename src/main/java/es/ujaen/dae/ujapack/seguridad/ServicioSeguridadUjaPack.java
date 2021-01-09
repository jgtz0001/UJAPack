/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Pablo
 */

@Configuration
public class ServicioSeguridadUjaPack extends WebSecurityConfigurerAdapter{
   
    @Autowired
    ServicioDatosCliente servicioDatosCliente;
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(servicioDatosCliente)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        
        httpSecurity.httpBasic();
        
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/clientes").anonymous();
        // Permitir el acceso de un cliente sólo a sus recursos asociados 
        httpSecurity.authorizeRequests().antMatchers("/ujapack/clientes/{dni}/**")
                .access("hasRole('CLIENTE') and #dni == principal.username");
    }

}
