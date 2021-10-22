/*
 */
package com.weblab.app.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weblab.app.entidades.Usuario;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.repositorios.UsuarioRepositorio;
import com.weblab.app.servicios.FotoServicio;
import com.weblab.app.servicios.UsuarioServicio;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@Autowired
	private FotoServicio fotoServicio;
   
    
    /*LO QUE HACE ESTE CONTROLLER ES BUSCAR USUARIO POR ID Y USAR SU FOTO PARA LUEGO LEVANTARLA
     * AL SERVIDOR*/
    @GetMapping("/profilepic/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable String id) throws ErrorServicio{

        try {
            System.out.println("ID: " + id);
//            //Creamos una variable tipo para parsear
//            Integer idstr = Integer.parseInt(id);
            Usuario usuario = usuarioServicio.buscarUsuarioPorID(id);
            if (usuario.getFoto() == null) {
                throw new ErrorServicio("El usuario no tiene Foto Asignada");
            }
            byte[] foto = usuario.getFoto().getContenido();


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
//            return new ResponseEntity<>(foto.getContenido(), headers, HttpStatus.OK);

        } catch (ErrorServicio ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    



}
