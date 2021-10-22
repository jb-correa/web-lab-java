package com.weblab.app.servicios;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.weblab.app.entidades.Analisis;
import com.weblab.app.entidades.Practica;
import com.weblab.app.errores.ErrorServicio;

@Service
public class AnalisisServicio {
	
	
	

	@Transactional
	public void cargarPractica(List<Practica> practicas) throws ErrorServicio{
		
		List<Practica> practicasConResultado=new ArrayList();
		
		for(int i=0;i<practicas.size();i++) {
			if(practicas.get(i).getResultado()!=null) {
				
				practicasConResultado.add(practicas.get(i));
			}
			
		}
		
		Analisis analisis=new Analisis();
		analisis.setPracticas(practicasConResultado);
		
		
		
		
		
		
	}
}
