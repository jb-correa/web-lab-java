package com.weblab.app.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weblab.app.entidades.Usuario;
import com.weblab.app.repositorios.UsuarioRepositorio;

@Controller
@RequestMapping("/user")
public class UsuarioControlador {
	
	@Autowired
	private UsuarioRepositorio usRepo;
	
    @GetMapping("/modificar-datos")
    public String modificarDatos(){
        return "modificar-user.html";
    }   

}

