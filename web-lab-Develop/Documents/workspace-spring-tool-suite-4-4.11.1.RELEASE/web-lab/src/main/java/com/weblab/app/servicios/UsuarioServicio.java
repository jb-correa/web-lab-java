package com.weblab.app.servicios;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import com.weblab.app.entidades.Foto;
import com.weblab.app.entidades.Os;
import com.weblab.app.entidades.Pdf;
import com.weblab.app.entidades.Usuario;
import com.weblab.app.enumeradores.Rol;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.repositorios.PdfRepositorio;
import com.weblab.app.repositorios.UsuarioRepositorio;
import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class UsuarioServicio implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private FotoServicio fotoServicio;

	@Autowired
	private FileServicio fileServicio;

	@Autowired
	private PdfRepositorio pdfRepositorio;
	
    @Autowired
    private NotificacionService notif;
    
    
    
    

	@Transactional
	public void registrarUsuario(String nombre, String apellido, String dni, Date fechaNac, String telefono,
			String email, String domicilio, String localidad, Os os, String numAfiliado) throws ErrorServicio {

		String clave = dni;

		validar(dni, nombre, apellido, fechaNac, telefono, email, domicilio, localidad, clave);

		Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);

		if (!respuesta.isPresent()) {

			Usuario usuario = new Usuario();

			usuario.setDni(dni);
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setFechaNac(fechaNac);
			usuario.setAlta(new Date());
			usuario.setTelefono(telefono);
			usuario.setEmail(email);
			usuario.setDomicilio(domicilio);
			usuario.setLocalidad(localidad);
			String encriptada = new BCryptPasswordEncoder().encode(clave);
			usuario.setClave(encriptada);
			usuario.setNumAfiliado(numAfiliado);
			usuario.setRol(Rol.USER);
			usuario.setOs(null);

			usuarioRepositorio.save(usuario);
			
			/* LLAMAR MÉTODO ENVIAR CORREO ELECTRÓNICO*/
            notif.enviarMail("Estimado usuario!, éste es un correo de alerta de seguridad para notificarte que "
                    + "\nse ha cargado un nuevo usuario en la base de datos, \nUsuario: ´´ " + usuario.getNombre() + ", " + usuario.getApellido()
                    + ". \nRol de usuario: " + usuario.getRol() + " " + usuario.getApellido() + " , e-mail: " + usuario.getEmail()
                    + ". \nNúmero de afiliado: " + usuario.getNumAfiliado()
                    + ". \nHasta pronto!.", "WEB-LAB! Laboratorio de Mendoza", usuario.getEmail());

		} else {

			throw new ErrorServicio("Atención! - El Usuario que intenta registrar ya se encuentra registrado");

		}

	}

	@Transactional
	public void registrarAdmin(MultipartFile archivo, String nombre, String apellido, String dni, Date fechaNac,
			String telefono, String domicilio, String email, String localidad, String titulo, String descripcion,
			String clave) throws ErrorServicio, MalformedURLException, IOException {

		validar(dni, nombre, apellido, fechaNac, telefono, email, domicilio, localidad, clave);

		// Verificar si usuario YA existe
		Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);

		if (!respuesta.isPresent()) {

			Usuario usuario = new Usuario();

			usuario.setNumAfiliado(null);
			usuario.setOs(null);

			usuario.setDni(dni);
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setFechaNac(fechaNac);
			usuario.setAlta(new Date());
			usuario.setTelefono(telefono);
			usuario.setEmail(email);
			usuario.setDomicilio(domicilio);
			usuario.setLocalidad(localidad);
			String encriptada = new BCryptPasswordEncoder().encode(clave);
			usuario.setClave(encriptada);
			usuario.setRol(Rol.ADMIN);
			usuario.setDescripcion(descripcion);
			usuario.setTitulo(titulo);

			Foto foto = fotoServicio.GuardarFoto(archivo);
			usuario.setFoto(foto);

			FileServicio fileServicio = new FileServicio(usuario.getNombre(),usuario.getApellido(), usuario.getFechaNac());
			fileServicio.crearPlantilla();
			//Pdf pdf = fileServicio.savePdf(archivo);
			//usuario.setPdfs(pdf);

			usuarioRepositorio.save(usuario);
			
			/* LLAMAR MÉTODO ENVIAR CORREO ELECTRÓNICO*/
            notif.enviarMail("Estimado admin!, éste es un correo de alerta de seguridad para notificarte que "
                    + "\nse ha cargado un nuevo usuario en la base de datos, \nUsuario: ´´ " + usuario.getNombre() + ", " + usuario.getApellido()
                    + ". \nRol de usuario: " + usuario.getRol() + " " + usuario.getApellido() + " , e-mail: " + usuario.getEmail()
                    + ". \nNúmero de afiliado: " + usuario.getNumAfiliado()
                    + ". \nHasta pronto!.", "WEB-LAB! Laboratorio de Mendoza", usuario.getEmail());

		} else {

			throw new ErrorServicio("Atención! - El Usuario que intenta registrar ya se encuentra registrado");

		}

	}

	@Transactional
	public void modificarUsuario(MultipartFile archivo, String dni, String nombre, String apellido, Date fechaNac,
			Date alta, Date baja, String telefono, String email, String domicilio, String localidad, String clave,
			String numAfiliado, Os os, String titulo) throws ErrorServicio {

		validar(dni, nombre, apellido, fechaNac, telefono, email, domicilio, localidad, clave);

		// Verificar si usuario existe
		Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);

		if (respuesta.isPresent()) {

			Usuario usuario = new Usuario();

			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setFechaNac(fechaNac);
			usuario.setAlta(alta);
			usuario.setBaja(baja);
			usuario.setTelefono(telefono);
			usuario.setEmail(email);
			usuario.setDomicilio(domicilio);
			usuario.setLocalidad(localidad);
			String encriptada = new BCryptPasswordEncoder().encode(clave);
			usuario.setClave(encriptada);
			usuario.setNumAfiliado(numAfiliado);
			usuario.setRol(Rol.USER);
			usuario.setOs(os);
			usuario.setTitulo(titulo);

			usuarioRepositorio.save(usuario);
			Foto foto = fotoServicio.GuardarFoto(archivo);

			usuario.setFoto(foto);

		} else {

			throw new ErrorServicio("No se encontro el Usuario");

		}

	}

	@Transactional
	public void modificarAdmin(MultipartFile archivo, String nombre, String apellido, String dni, Date fechaNac,
			String telefono, String domicilio, String email, String localidad, String titulo, String descripcion,
			String clave) throws ErrorServicio {

		Usuario usuario = new Usuario();

		validar(dni, nombre, apellido, fechaNac, telefono, email, domicilio, localidad, clave);

		usuario.setNumAfiliado(null);
		usuario.setOs(null);

		usuario.setDni(dni);
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setFechaNac(fechaNac);
		usuario.setTelefono(telefono);
		usuario.setEmail(email);
		usuario.setDomicilio(domicilio);
		usuario.setLocalidad(localidad);
		String encriptada = new BCryptPasswordEncoder().encode(clave);
		usuario.setClave(encriptada);
		usuario.setDescripcion(descripcion);
		usuario.setTitulo(titulo);

		usuarioRepositorio.save(usuario);

	}
	
	
	
	public void cargarAnalisisPorDNI() {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	public void validar(String dni, String nombre, String apellido, Date fechaNac, String telefono, String email,
			String domicilio, String localidad, String clave) throws ErrorServicio {

		if (dni == null || dni.isEmpty() || dni.contains("  ")) {
			throw new ErrorServicio("El dni no puede estar vacio");
		}

		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new ErrorServicio("El nombre no puede estar vacio");
		}

		if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
			throw new ErrorServicio("El apellido no puede estar vacio");
		}

		if (fechaNac == null) {
			throw new ErrorServicio("La fecha de nacimiento no puede estar vacio");
		}

		if (telefono == null || telefono.isEmpty() || telefono.contains("  ")) {
			throw new ErrorServicio("El telefono no puede estar vacio");
		}

		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new ErrorServicio("El email no puede estar vacio");
		}

		if (domicilio == null || domicilio.isEmpty() || domicilio.contains("  ")) {
			throw new ErrorServicio("El domicilo no puede estar vacio");
		}

		if (localidad == null || localidad.isEmpty() || localidad.contains("  ")) {
			throw new ErrorServicio("La localidad no puede estar vacio");
		}

		if ( clave.length() < 8) {
			throw new ErrorServicio("La clave debe tener 8 caracteres");
		}

//		if (numAfiliado == null || numAfiliado.isEmpty() || numAfiliado.contains("  ")) {
//			throw new ErrorServicio("El numero de Afiliado no puede estar vacio");
//		}

	}

	// METODO DE CONSULTA: MOSTRAR USUARIOS POR DNI
	public Usuario buscarUsuarioPorID(String id) throws ErrorServicio {

		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

		if (respuesta.isPresent()) {
			Usuario buscarid = respuesta.get();
			return buscarid;
		} else {
			throw new ErrorServicio("Atención! - El Usuario que intenta encontrar no existe en la Base de Datos");
		}
	}

	// METODO DE CONSULTA: Mostrar Usuarios
	public List<Usuario> mostrarUsuarios() {
		return usuarioRepositorio.mostrarUsuariosDisponibles();
	}

	// METODO DE CONSULTA: FILTRAR POR MULTIPLE PARAMETROS
	public List<Usuario> listAll(String dni) throws ErrorServicio {

		if (dni != null) {
			return usuarioRepositorio.findAll(dni);
		} else

			return usuarioRepositorio.findAll();

	}

	// METODO ADMINISTRAR/OTORGAR PERMISOS
	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
		Usuario us = usuarioRepositorio.mostrarUsuariosDni(dni);
		if (us != null) {
			List<GrantedAuthority> permissions = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + us.getRol().toString());
			permissions.add(p);
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuario", us);
			return new org.springframework.security.core.userdetails.User(us.getDni(), us.getClave(), permissions);
		} else {

			return null;
		}
	}

//	@Override
//	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
//		Usuario us = usuarioRepositorio.mostrarUsuariosDni(dni);
//		if (us != null) {
//			List<SimpleGrantedAuthority> permisos = new ArrayList<>();
//			SimpleGrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + us.getRol().toString());
//			permisos.add(p1);
//			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//			HttpSession session = attr.getRequest().getSession(true);
//			session.setAttribute("usuario", us);
//			return new org.springframework.security.core.userdetails.User(us.getDni(), us.getClave(), permisos);
//		} else {
//
//			return null;
//		}
//	}
}

