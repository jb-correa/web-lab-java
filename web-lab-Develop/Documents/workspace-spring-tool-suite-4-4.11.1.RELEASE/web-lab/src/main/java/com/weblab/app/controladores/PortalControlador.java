package com.weblab.app.controladores;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.weblab.app.entidades.Usuario;
import com.weblab.app.repositorios.UsuarioRepositorio;



@Controller
@RequestMapping("/")
public class PortalControlador {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
    @GetMapping("/")
    public String index(ModelMap modelo) {
    List <Usuario> usuarios = usuarioRepositorio.verAdmin();
    	modelo.addAttribute("usuarios", usuarios);
        return "index.html";
    }
    
    
    @GetMapping("/login")
    public String login() {
        return "login.html";
    } 


}
