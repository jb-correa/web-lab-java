package com.weblab.app.controladores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.weblab.app.entidades.Foto;
import com.weblab.app.entidades.Os;
import com.weblab.app.entidades.Practica;
import com.weblab.app.entidades.Usuario;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.repositorios.FotoRepositorio;
import com.weblab.app.repositorios.OsRepositorio;
import com.weblab.app.repositorios.PdfRepositorio;
import com.weblab.app.repositorios.PracticaRepositorio;
import com.weblab.app.repositorios.UsuarioRepositorio;
import com.weblab.app.servicios.FileServicio;
import com.weblab.app.servicios.OsServicio;
import com.weblab.app.servicios.PracticaServicio;
import com.weblab.app.servicios.UsuarioServicio;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

	@Autowired
	private UsuarioServicio usService;

	@Autowired
	private PracticaRepositorio pracRepo;

	@Autowired
	private UsuarioRepositorio usRepo;

	@Autowired
	private OsRepositorio osRepo;

	@Autowired
	private OsServicio osServicio;

	@Autowired
	private FotoRepositorio fotoRepositorio;

	@Autowired
	private FileServicio fileServicio;

	@Autowired
	private PdfRepositorio pdfRepositorio;

	@Autowired
	private PracticaServicio prServicio;

	@GetMapping("/alta-us")
	public String altaUs(ModelMap modelo) {
		List<Os> oss = osRepo.OsDisponibles();
		modelo.addAttribute("oss", oss);
		return "carga-us.html";
	}

//	// LEVANTAR MOSTRAR DATOS USUARIOS
//	@GetMapping("/usuarios")
//	public String verUsuarios(ModelMap modelo) {
//
//		List<Usuario> usuarios = usService.mostrarUsuarios();
//		modelo.put("usuarios", usuarios);
//
//		return "usuarios.html";
//	}

	@PostMapping("/carga-us")
	public String cargaUs(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String dni, @RequestParam String fechaNac, @RequestParam String telefono,
			@RequestParam String email, @RequestParam String domicilio, @RequestParam String localidad, String osNumber,
			String numAfiliado) throws ErrorServicio, ParseException {

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = formato.parse(fechaNac);

		System.out.println();
		Os os = osRepo.mostrarOs(osNumber);
		try {
			// System.out.println(fechaNac.toString());
			usService.registrarUsuario(nombre, apellido, dni, date1, telefono, email, domicilio, localidad, os,
					numAfiliado);
			modelo.put("exito", "Los datos fuéron enviados exitosamente! " + "\nMuchas Gracias!");
		} catch (ErrorServicio ex) {
			// TODO Auto-generated catch block

			modelo.put("error", ex.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("dni", dni);
			modelo.put("fechaNac", date1); // No lo coloca
			modelo.put("telefono", telefono);
			modelo.put("domicilio", domicilio);
			modelo.put("email", email);
			modelo.put("localidad", localidad);
			modelo.put("numAfiliado", numAfiliado);
			return "carga-us.html";

		}
		return "exito.html";

	}

	@GetMapping("/alta-admin")
	public String altaAdmin(ModelMap modelo) {

//		List<Foto> fotos = fotoRepositorio.findAll();
//		modelo.put("fotos", fotos);

		return "carga-admin.html";
	}

	@PostMapping("/carga-admin")
	public String cargaAdmin(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam String dni, @RequestParam String fechaNac,
			@RequestParam String telefono, @RequestParam String domicilio, @RequestParam String email,
			@RequestParam String localidad, @RequestParam String titulo, @RequestParam String descripcion,
			@RequestParam String clave) throws Exception, Exception {

		// System.out.println("---" +fechaNac);

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = formato.parse(fechaNac);

		try {
			System.out.println(fechaNac.toString());
			usService.registrarAdmin(archivo, nombre, apellido, dni, date1, telefono, domicilio, email, localidad,
					titulo, descripcion, clave);
			modelo.put("exito", "Los datos fuéron enviados exitosamente! " + "\nMuchas Gracias!");
		} catch (ErrorServicio ex) {
			modelo.put("error", ex.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("dni", dni);
			modelo.put("fechaNac", fechaNac);
			modelo.put("telefono", telefono);
			modelo.put("domicilio", domicilio);
			modelo.put("email", email);
			modelo.put("localidad", localidad);
			modelo.put("titulo", titulo);
			modelo.put("descripcion", descripcion);

			return "carga-admin.html";
		}
		return "exito.html";
	}

	@GetMapping("/carga-resultado")
	public String cargaRtdo(ModelMap modelo) {
		// retornar un objeto usuario pasando por parametro el dni del usuario

		List<Practica> practicas = pracRepo.verPracticasDisponibles();
		modelo.put("practicas", practicas);

		return "carga-resultados.html";
	}

	@PostMapping("/alta-rtdos")
	public String altaRtdos(ModelMap modelo, @RequestParam String dni, @RequestParam String idPractica,
			@RequestParam Double resultado) {
		Usuario us = usRepo.mostrarUsuariosDni(dni);
		// CREAR UN MÉTODO DENTRO DE USUARIO QUE CARGUE UN ELEMENTO AL LIST DE ANALISIS
		// PARA ESO NECESITO UN METODO EN ANALISIS SERVICE QUE COPIE UNA PRACTICA YA
		// CARGADA
		// NECESITO UN METODO EN PRACTICA SERVICE QUE LE CARGUE EL RESULTADO Y ESTA SEA
		// LA QUE SE COPIE EN ANALISIS

		return "carga-resultados.html";
	}

	// EDITAR USAURIO (OPCIÓN 1) "AUTOFILL"
	@GetMapping("/modificar-datos")
	public String modificarDatos(HttpSession session, ModelMap modelo) {

		Usuario us = (Usuario) session.getAttribute("usuario");

		// Inyectar datos de usuario (Autocompletar datos)
		
		modelo.addAttribute("id", us.getId());
		modelo.addAttribute("nombre", us.getNombre());
		modelo.addAttribute("apellido", us.getApellido());
		modelo.addAttribute("dni", us.getDni());
		modelo.addAttribute("fechaNac", us.getFechaNac()); // NO LLEGA LA FECHA
		modelo.addAttribute("telefono", us.getTelefono());
		modelo.addAttribute("domicilio", us.getDomicilio());
		modelo.addAttribute("email", us.getEmail());
		modelo.addAttribute("localidad", us.getLocalidad());
		modelo.addAttribute("titulo", us.getTitulo());
		modelo.addAttribute("descripcion", us.getDescripcion());

		return "modificar-admin.html";
	}

	@PostMapping("/modificar")
	public String modificar(HttpSession session, ModelMap modelo, MultipartFile archivo, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam String dni, @RequestParam String fechaNac,
			@RequestParam String telefono, @RequestParam String domicilio, @RequestParam String email,
			@RequestParam String localidad, @RequestParam String titulo, @RequestParam String descripcion,
			@RequestParam String clave) throws ParseException {

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = formato.parse(fechaNac);

		try {
			usService.modificarAdmin(archivo, nombre, apellido, dni, date1, telefono, domicilio, email, localidad,
					titulo, descripcion, clave);
			modelo.put("exito", "Los datos fuéron enviados exitosamente! " + "\nMuchas Gracias!");

			// session.setAttribute("usuario", poner elusuariommodificado);

			session.setAttribute("nombre", nombre);
			session.setAttribute("apellido", apellido);
			session.setAttribute("dni", dni);
			session.setAttribute("fechaNac", fechaNac); // No llega la fecha
			session.setAttribute("telefono", telefono);
			session.setAttribute("domicilio", domicilio);
			session.setAttribute("email", email);
			session.setAttribute("localidad", localidad);
			session.setAttribute("titulo", titulo);
			session.setAttribute("descripcion", descripcion);
			// session.setAttribute("archivo", archivo); //No se puede cambiar la imagen

		} catch (ErrorServicio ex) {
			modelo.put("error", ex.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("dni", dni);
			modelo.put("fechaNac", fechaNac); // Borra la fecha
			modelo.put("telefono", telefono);
			modelo.put("domicilio", domicilio);
			modelo.put("email", email);
			modelo.put("localidad", localidad);
			modelo.put("titulo", titulo);
			modelo.put("descripcion", descripcion);

			return "modificar-admin.html";
//			e.printStackTrace();
		}
		return "exito.html";
	}

	@GetMapping("/alta-os")
	public String altaOs(ModelMap modelo) {
		return "carga-os.html";
	}

	@PostMapping("/carga-os")
	public String cargaOs(ModelMap modelo, @RequestParam String osNumber, @RequestParam String nombre)
			throws Exception, Exception {

		try {
			osServicio.crearOs(osNumber, nombre);
			modelo.put("exito", "Los datos fuéron enviados exitosamente! " + "\nMuchas Gracias!");
		} catch (ErrorServicio ex) {
			modelo.put("error", ex.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("osNumber", osNumber);

			return "carga-os.html";
		}
		return "exito.html";
	}

	@GetMapping("/alta-practica")
	public String altaPr(ModelMap modelo) {
		return "carga-practica.html";
	}

	@PostMapping("/carga-practica")
	public String cargaPr(ModelMap modelo, @RequestParam String codigo, @RequestParam String nombre,
			@RequestParam String ub, @RequestParam String um, @RequestParam String metodo, @RequestParam String vr)
			throws Exception, Exception {

		Double ub1 = Double.parseDouble(ub);

		try {
			prServicio.crearPractica(codigo, nombre, ub1, um, metodo, vr);
			modelo.put("exito", "Los datos fuéron enviados exitosamente! " + "\nMuchas Gracias!");
		} catch (ErrorServicio ex) {
			modelo.put("error", ex.getMessage());
			modelo.put("codigo", codigo);
			modelo.put("nombre", nombre);
			modelo.put("ub", ub);
			modelo.put("um", um);
			modelo.put("metodo", metodo);
			modelo.put("vr", vr);

			return "carga-practica.html";
		}
		return "exito.html";
	}

}
