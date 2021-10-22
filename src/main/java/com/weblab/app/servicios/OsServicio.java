package com.weblab.app.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weblab.app.entidades.Os;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.repositorios.OsRepositorio;

@Service
public class OsServicio {

	@Autowired
	private OsRepositorio osRepositorio;

	// METODO CREAR OBRA SOCIAL
	
	@Transactional
	public void crearOs(String osNumber, String nombre) throws ErrorServicio {

		validar(osNumber, nombre);

		Optional<Os> respuesta = osRepositorio.findById(osNumber);

		if (!respuesta.isPresent()) {

			Os os = new Os();

			os.setOsNumber(osNumber);
			os.setNombre(nombre);

			osRepositorio.save(os);
		} else {
			throw new ErrorServicio("Atención! - Ya existe esa Obra Social.");
		}

	}

	// METODO EDITAR OBRA SOCIAL
	
	@Transactional
	public void editarOs(String osNumber, String nombre) throws ErrorServicio {

		validar(osNumber, nombre);

		Optional<Os> respuesta = osRepositorio.findById(osNumber);

		if (!respuesta.isPresent()) {

			Os os = new Os();

			os.setOsNumber(osNumber);
			os.setNombre(nombre);

			osRepositorio.save(os);
		} else {
			throw new ErrorServicio("## Error ## - Obra Social no encontrada en la base de datos");
		}

	}

	// VALIDAR

	private void validar(String osNumber, String nombre) throws ErrorServicio {

		if (osNumber == null || osNumber.isEmpty()) {
			throw new ErrorServicio("Atención! - No se pueden ingresar datos nulos");
		}

		if (nombre == null || nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio("Atención! - No se pueden ingresar datos nulos, debe ingresar un nombre");
		}

	}

	// METODO MOSTRAR OBRAS SOCIALES

	public List<Os> OsDisponibles() throws ErrorServicio {
		return osRepositorio.OsDisponibles();

	}

	// METODO MOSTRAR OBRAS SOCIALES POR ID

	public Os mostrarOsId(String osNumber) throws ErrorServicio {
		Os respuesta = osRepositorio.mostrarOs(osNumber);

		if (respuesta != null) {
			Os mostrarOsId = respuesta;
			return mostrarOsId;
		} else {
			throw new ErrorServicio(
					"No se encontró la Obra Social en la base de datos." + "\nRevise los datos e intente nuevamente");
		}

	}
	
}
