package com.weblab.app.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class File {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String uri;
	
	private String fileName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date editado;

	private boolean activo;
}
