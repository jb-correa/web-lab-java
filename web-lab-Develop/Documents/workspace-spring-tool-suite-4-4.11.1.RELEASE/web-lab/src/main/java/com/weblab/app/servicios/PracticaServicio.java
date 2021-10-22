package com.weblab.app.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weblab.app.entidades.Practica;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.models.PracticaModel;
import com.weblab.app.repositorios.PracticaRepositorio;

@Service
public class PracticaServicio {

	@Autowired
	private PracticaRepositorio practicaRepositorio;
	
	

	// METODO CREAR PRACTICAS
	@Transactional
	public void crearPractica(String codigo, String nombre, Double ub, String um, String metodo, String vr)
			throws ErrorServicio {

		validar(codigo, nombre, ub, um, metodo, vr);
		
		/*
		 * MEDIANTE LA CLASE OPTIONAL PODEMOS VER SI COMO RESPUESTA AL ID ME DEVUELVE
		 * UNA PRACTICA ENTONCES LA BUSCAMOS Y LA CREAMOS, SI YA EXISTE LANZA LA
		 * EXCEPCIÓN
		 */
		Practica respuesta = practicaRepositorio.mostrarPracticaXCodigo(codigo);

		if (respuesta == null) {

			Practica practica = new Practica();

			practica.setCodigo(codigo);
			practica.setNombre(nombre);
			practica.setUb(ub);
			practica.setUm(um);
			practica.setMetodo(metodo);
			practica.setVr(vr);

			practicaRepositorio.save(practica);
		} else {
			throw new ErrorServicio("Atención! - Ya existe esa práctica." + " \tIntente con otro diferente.");
		}

	}
	
	

	// METODO EDITAR PRACTICAS
	@Transactional
	public void editarPractica(String codigo, String nombre, Double ub, String um, String metodo, String vr)
			throws ErrorServicio {

		validar(codigo, nombre, ub, um, metodo, vr);

		/*
		 * MEDIANTE LA CLASE OPTIONAL PODEMOS VER SI COMO RESPUESTA AL ID ME DEVUELVE
		 * UNA PRACTICA ENTONCES LA BUSCAMOS Y LA EDITAMOS, SI NO EXISTE LANZA LA
		 * EXCEPCIÓN
		 */
		Practica respuesta = practicaRepositorio.mostrarPracticaXCodigo(codigo);

		if (respuesta != null) {

			respuesta.setCodigo(codigo);
			respuesta.setNombre(nombre);
			respuesta.setUb(ub);
			respuesta.setUm(um);
			respuesta.setMetodo(metodo);
			respuesta.setVr(vr);

			practicaRepositorio.save(respuesta);
		} else {
			throw new ErrorServicio("## Error ## - Practica no encontrada en nuestra base de datos");
		}

	}
	
	

	// METODO ELIMINAR PRACTICAS
	@Transactional
	public void eliminarPractica(String codigo, String nombre, Double ub, String um, String metodo, String vr)
			throws ErrorServicio {

		validar(codigo, nombre, ub, um, metodo, vr);

		/*
		 * MEDIANTE LA CLASE OPTIONAL PODEMOS VER SI COMO RESPUESTA AL ID ME DEVUELVE
		 * UNA PRACTICA ENTONCES LA BUSCAMOS Y LA ELIMINAMOS, SI NO EXISTE LANZA LA
		 * EXCEPCIÓN
		 */
		Practica respuesta = practicaRepositorio.mostrarPracticaXCodigo(codigo);

		if (respuesta != null) {

			practicaRepositorio.delete(respuesta);
		} else {
			throw new ErrorServicio("## Error ## - Practica no encontrada en nuestra base de datos");
		}

	}
	
	
	
	
	//METODO DUDOSO SOBRE CARGAR UN RESULTADO A UNA PRACTICA SEGUN SU ID
	//LO HICE SEGUN SU ID PERO PUEDE SER SEGUN EL CODIGO DE LA PRACTICA
	public void cargarResultado(String idPractica,String resultado)throws ErrorServicio {
		
		
		
		Optional<Practica> respuesta = practicaRepositorio.findById(idPractica);
		
		if (respuesta.isPresent()) {
		
		Practica practicaResul=respuesta.get();
		
		practicaResul.setResultado(resultado);
		
		//NO SE SI ESTÁ BIEN LLAMAR AL REPOSITORIO DE NUEVO
		practicaRepositorio.save(practicaResul);
		
		}else {
			throw new ErrorServicio("## Error ## - Practica no encontrada en nuestra base de datos");
		}
		
	}
	
	

	// VALIDAR SI USAURIO INGRESA DATOS ERRONEOS
	private void validar(String codigo, String nombre, Double ub, String um, String metodo, String vr)
			throws ErrorServicio {

		if (codigo == null || codigo.isEmpty() || codigo == null) {
			throw new ErrorServicio("Atención! - No se pueden ingresar datos nulos");
		}

		if (nombre == null || nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio("Atención! - No se pueden ingresar datos nulos, debe ingresar un nombre");
		}

		if (ub == null || ub == null) {
			throw new ErrorServicio(
					"Atención! - No se pueden ingresar datos nulos, debe ingresar una unidad UB valida");
		}

//		if (ub <= 10) {
//			throw new ErrorServicio("Atención! - Debe ingresar un UB mayor a 10");
//		}

		if (um == null || um == null) {
			throw new ErrorServicio(
					"Atención! - No se pueden ingresar datos nulos, debe ingresar una unidad de medida valida");
		}

		if (metodo == null || metodo.isEmpty() || metodo == null) {
			throw new ErrorServicio(
					"Atención! - No se pueden ingresar datos nulos, debe ingresar un metodo de práctica valido");
		}

		if (vr == null || vr.isEmpty()) {
			throw new ErrorServicio("Atención! - No se pueden ingresar datos nulos, debe ingresar una vr valida");
		}

	}
	
	
//	private void validar(PracticaModel m)
//			throws ErrorServicio {
//
//		if (m.getCodigo() == null || m.getCodigo().isEmpty()) {
//			throw new ErrorServicio("Atención! - No se pueden ingresar datos nulos");
//		}
//
//		if (m.getNombre() == null || m.getNombre().isEmpty()) {
//			throw new ErrorServicio("Atención! - No se pueden ingresar datos nulos, debe ingresar un nombre");
//		}
//
//		if (m.getUb() == null || m.getUb() == null) {
//			throw new ErrorServicio(
//					"Atención! - No se pueden ingresar datos nulos, debe ingresar una unidad UB valida");
//		}
//
//		if (m.getUb() <= 10) {
//			throw new ErrorServicio("Atención! - Debe ingresar un UB mayor a 10");
//		}
//
//		if (m.getUm() == null || m.getUm() == null) {
//			throw new ErrorServicio(
//					"Atención! - No se pueden ingresar datos nulos, debe ingresar una unidad de medida valida");
//		}
//
//		if (m.getMetodo() == null || m.getMetodo().isEmpty()) {
//			throw new ErrorServicio(
//					"Atención! - No se pueden ingresar datos nulos, debe ingresar un metodo de práctica valido");
//		}
//
//		if (m.getVr() == null || m.getVr().isEmpty()) {
//			throw new ErrorServicio("Atención! - No se pueden ingresar datos nulos, debe ingresar una vr valida");
//		}
//
//	}

	
	
	// METODO MOSTRAR PRACTICAS POR NOMBRE
	public List<Practica> mostrarPracticaXnombre(String nombre) throws ErrorServicio {

		List<Practica> respuesta = practicaRepositorio.mostrarPracticaXnombre(nombre);

		if (!respuesta.isEmpty()) {
			List<Practica> buscarNombre = respuesta;
			return buscarNombre;
		} else {
			throw new ErrorServicio(
					"No se encontró la practica en la base de datos." + "\nRevise los datos e intente nuevamente");
		}
	}

	
	
	// METODO MOSTRAR PRACTICAS
	public List<Practica> mostrarPracticas(String var) {
		return practicaRepositorio.verPracticasDisponibles();
	}
	
	
	
	// METODO MOSTRAR PRACTICAS POR USUARIO
	public List<Practica> mostrarPracticasPorUsuario(String dni) throws ErrorServicio {

		List<Practica> respuesta = practicaRepositorio.mostrarPracticaPorUsuario(dni);

		if (!respuesta.isEmpty()) {
			List<Practica> buscarPorDNI = respuesta;
			return buscarPorDNI;
		} else {
			throw new ErrorServicio(
					"No se encontró la practica en la base de datos." + "\nRevise los datos e intente nuevamente");
		}
	}

}


