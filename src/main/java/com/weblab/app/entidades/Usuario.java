package com.weblab.app.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.weblab.app.enumeradores.Rol;

import lombok.Data;

@Data
@Entity
public class Usuario {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	
	@Column(unique = true)
	private String dni;

	private String nombre;
	private String apellido;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date fechaNac;

	@Temporal(TemporalType.DATE)
	private Date alta;

	@Temporal(TemporalType.DATE)
	private Date baja;
	private String telefono;
	private String email;
	private String domicilio;
	private String localidad;
	private String clave;
	private String numAfiliado;

	@Enumerated(EnumType.STRING)
	private Rol rol;

	@ManyToOne
	private Os os;

	@OneToMany
	private List<File> files;

	@OneToMany
	private List<Analisis> analisis;

	@OneToMany
	private List<Anamnesis> anamnesis;
	
	@OneToOne
	private Foto foto;
	
	private String descripcion;
	
	private String titulo;
	
	@OneToMany
	private List<Pdf> pdfs;

}
