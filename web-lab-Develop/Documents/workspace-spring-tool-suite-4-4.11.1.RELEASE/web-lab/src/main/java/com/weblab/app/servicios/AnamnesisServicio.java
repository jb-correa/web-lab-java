package com.weblab.app.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weblab.app.entidades.Anamnesis;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.repositorios.AnamnesisRepositorio;






@Service
public class AnamnesisServicio {

	@Autowired 
	private AnamnesisRepositorio anamnesisRepositorio;
	
	
	
	
	@Transactional
	public void crearAnamnesis(String descripcion) throws ErrorServicio{
		
		validar(descripcion);
		
		
		Anamnesis anamnesis=new Anamnesis();
		anamnesis.setDescripcion(descripcion);
		anamnesis.setFecha(new Date());
		
		anamnesisRepositorio.save(anamnesis);
	}
	
	
	@Transactional
	public void editarAnamnesis(String id, String descripcion) throws ErrorServicio {
		
		validar(descripcion);
		
		Optional<Anamnesis> respuesta=anamnesisRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
			Anamnesis anamnesis=respuesta.get();
			
			
			anamnesis.setDescripcion(descripcion);
			anamnesis.setFecha(new Date());
			
			anamnesisRepositorio.save(anamnesis);
		}else {
			throw new  ErrorServicio("La anamnesis solicitada no se encuentra nuestra base de datos.");
		}
		
	}
	
	//ESTE METODO LO HICE POR LAS DUDAS, SI CONSIDERAN QUE NO VA LO ELIMINAN  <3 :)
	
	//@Transactional
	//public void eliminarAnamnesis(String id) throws ErrorServicio {
		
		
		
		//Optional<Anamnesis> respuesta=anamnesisRepositorio.findById(id);
		
		//if(respuesta.isPresent()) {
			//Anamnesis anamnesis=respuesta.get();
			
			//anamnesisRepositorio.delete(anamnesis);
			
		//}else {
			//throw new  ErrorServicio("La anamnesis solicitada no se encuentra nuestra base de datos.");
		//}
		
	//}
	
	

	private void validar (String descripcion) throws ErrorServicio {

		if (descripcion == null || descripcion.isEmpty() ) {
			throw new ErrorServicio("¡ATENCION! Debe agregar una descripción a la anamnesis.");
		}
	
	
	}
	
	
	//METODO QUE MUESTRA TODAS LAS ANAMNESIS ORDENADAS POR FECHA
	public List <Anamnesis> mostrarAnamnesisOrdenadasFecha()throws ErrorServicio{
		
		return anamnesisRepositorio.verAnamnesis();
		
		
	}
	
}
