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
public class Foto {

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
    public Foto(String id, String mime, String nombre, byte[] contenido) {
		this.id = id;
		this.mime = mime;
		this.nombre = nombre;
		this.contenido = contenido;
	}
    
    //CONSTRUCTOR VACÍO
    public Foto() {

	}
    
	
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }



	/**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the mime
     */
    public String getMime() {
        return mime;
    }

    /**
     * @param mime the mime to set
     */
    public void setMime(String mime) {
        this.mime = mime;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the contenido
     */
    public byte[] getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    
}
