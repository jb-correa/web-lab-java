package com.weblab.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.weblab.app.servicios.UsuarioServicio;

@SpringBootApplication
public class WebLabApplication {
	
	UsuarioServicio usuarioServicio;

	public static void main(String[] args) {
		SpringApplication.run(WebLabApplication.class, args);
	
	}
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
		
	}

}
