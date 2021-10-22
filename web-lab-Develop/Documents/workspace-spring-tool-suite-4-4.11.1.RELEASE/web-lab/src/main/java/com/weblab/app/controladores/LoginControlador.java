package com.weblab.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.weblab.app.entidades.Usuario;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.repositorios.UsuarioRepositorio;
import com.weblab.app.servicios.UsuarioServicio;


@Controller
@RequestMapping("/login")
public class LoginControlador {
	
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
	@Autowired
	private UsuarioServicio usService;
    
    
    
	
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }
    
    
    
    
//    @GetMapping("/usuarios")
//    public String verUsuarios(ModelMap modelo) {
//
//        List<Usuario> usuarios = usService.mostrarUsuarios();
//        modelo.put("usuarios", usuarios);
//
//        return "usuarios.html";
//    }
    
    
 
    
    
    
  //CONTROLADOR PARA EL BUSCADOR GENERICO
  @PostMapping("/searcheverything")
  public String searchEverything(ModelMap modelo, @RequestParam String dni) {

      System.out.println("dni Usuario: " + dni);

      try {
          List<Usuario> usuarios = usuarioServicio.listAll(dni);
          modelo.addAttribute("usuarios", usuarios); 

      } catch (ErrorServicio ex) {
          modelo.put("error", ex.getMessage());
          modelo.put("dni", dni);

          return "inicio.html";
      }

      return "inicio.html";
  }

}
