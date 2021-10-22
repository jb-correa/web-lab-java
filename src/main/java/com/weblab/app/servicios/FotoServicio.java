package com.weblab.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.weblab.app.entidades.Foto;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.repositorios.FotoRepositorio;

@Service
public class FotoServicio {

	@Autowired
    private FotoRepositorio fotoRepositorio;

	@Transactional
    public Foto GuardarFoto(MultipartFile archivo) throws ErrorServicio{
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

	@Transactional
    public Foto ModificarFoto(String id, MultipartFile archivo) throws ErrorServicio{
        if (archivo != null) {
           try {
               Foto foto = new Foto();
               
               if (id != null) {
                   Optional<Foto> respuesta = fotoRepositorio.findById(id);
                   if (respuesta.isPresent()) {
                       foto = respuesta.get();
                   }
               }
               foto.setMime(archivo.getContentType());
               foto.setNombre(archivo.getName());
               foto.setContenido(archivo.getBytes());
               return fotoRepositorio.save(foto);
           } catch (Exception e) {
               System.err.println(e.getMessage());
           }
       }
       return null;
   }
    
    
    
    @Transactional(readOnly = true)
    public Foto getOne(String id) {
        return fotoRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Foto> listarTodos() {
        return fotoRepositorio.findAll();
    }

    
    
}
