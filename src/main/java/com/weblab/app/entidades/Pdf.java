package com.weblab.app.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Pdf {
	
	@Id
//	@GeneratedValue(generator = "uuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
	
    // EL ATRIBUTO MIME DEFINE EL FORMATO DEL ARCHIVO
    private String mime;
    
	private String nombre;
    
	// LA ANOTACIÓN "LOB" IDENTIFICA QUE EL TIPO DE DATO ES PESADO
    // LA ANOTACIÓN "LAZY" INDICA QUE SOLO CARGUE LA FOTO SI YO LLAMO AL CONTEIDO CON UN GET
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

    
    //CONSTRUCTOR PARAMETRIZADO
	public Pdf(String id, String mime, String nombre, byte[] contenido) {
		this.id = id;
		this.mime = mime;
		this.nombre = nombre;
		this.contenido = contenido;
	}
    
	
    //CONSTRUCTOR VACÍO
	public Pdf() {
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMime() {
		return mime;
	}


	public void setMime(String mime) {
		this.mime = mime;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public byte[] getContenido() {
		return contenido;
	}


	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
	

}
