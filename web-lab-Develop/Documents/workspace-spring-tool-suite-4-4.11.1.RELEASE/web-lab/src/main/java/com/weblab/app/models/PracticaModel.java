package com.weblab.app.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class PracticaModel implements Serializable{

	private static final long serialVersionUID = 6522896498689132123L;
	
    private String id;
	
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
