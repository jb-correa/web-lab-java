package com.weblab.app.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


@Data
@Entity
public class Practica {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(unique = true)
	private String codigo;

	private String nombre;

	// UNIDAD BIOQUÍMICA
	private Double ub;

    //UNIDAD DE MEDIDA
	private String um;
	
	//METODO POR EL CUAL SE REALIZA LA PRACTICA
	private String metodo;
	
	//VALOR DE REFERENCIA
	private String vr;
	
	
	private String resultado;



}

