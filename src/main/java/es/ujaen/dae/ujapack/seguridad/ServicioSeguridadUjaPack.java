/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujapack.seguridad;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Pablo
 */
@Configuration
public class ServicioSeguridadUjaPack extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("usuario").roles("USUARIO").password("{noop}usuario")
                .and()
                .withUser("admin").roles("ADMIN", "USUARIO").password("{noop}admin");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/paquetes/").hasRole("ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/clientes/").hasRole("ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/ujapack/clientes/**").hasAnyRole("USUARIO", "ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/ujapack/paquetes/{localizador}").hasAnyRole("USUARIO", "ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/ujapack/centro/**").hasAnyRole("USUARIO", "ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/ujapack/oficina/**").hasAnyRole("USUARIO", "ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/ujapack/paqueteruta/{localizador}").hasAnyRole("USUARIO", "ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/paquetes/{localizador}/notificarcentrologistico/{idCentro}").hasRole("ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/ujapack/paquetes/{localizador}/notificarsalidacentrologistico/{idCentro}").hasRole("ADMIN");    
    }
}
