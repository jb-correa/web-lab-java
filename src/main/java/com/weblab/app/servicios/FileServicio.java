package com.weblab.app.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.weblab.app.entidades.Foto;
import com.weblab.app.entidades.Pdf;
import com.weblab.app.errores.ErrorServicio;
import com.weblab.app.models.FileModel;
import com.weblab.app.repositorios.PdfRepositorio;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class FileServicio {

	private static final Font FUENTE_CONTENIDO = FontFactory.getFont("Verdana", 9, BaseColor.GRAY);
	
	private static final Font FUENTE_TITULO = FontFactory.getFont("Verdana", 12, Font.UNDERLINE, BaseColor.BLACK);
	private static final Font FUENTE_DATOS = FontFactory.getFont("Verdana", 12, Font.BOLD, BaseColor.BLACK);
	
	
	@Autowired
	private AnalisisServicio analisisServicio;
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private PracticaServicio practicaServicio;
	

	@Autowired
    private FileServicio fileServicio;
	
	@Autowired
    private PdfRepositorio pdfRepositorio;
	
	String nombre;
	String apellido;
	String dni;
	Date fechaNac;
	String numAfiliado;
	Date fecha;
	Document document;
	FileOutputStream pdfFile;

	// CONSTRUCTOR PARAMETRIZADO
	public FileServicio(String nombre, String apellido, Date fechaNac) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaNac =fechaNac;
		this.numAfiliado = numAfiliado;
		this.fecha = fecha;
		document = new Document();
	}

	// CONSTRUCTOR VACÍO
	public FileServicio() {

	}


	//PDF SHEET CREATOR
	public void crearPlantilla() throws MalformedURLException, IOException {
		try {
			pdfFile = new FileOutputStream( nombre + " " + apellido + ".pdf");
			PdfWriter.getInstance(document, pdfFile);
			document.open();
			
			Image imagen = Image.getInstance("C:/Users/Fran/Desktop/web-lab/web-lab/Documents/workspace-spring-tool-suite-4-4.11.1.RELEASE/web-lab/Encabezado.png");  
			imagen.scalePercent(75f);
			imagen.setAlignment(Element.ALIGN_LEFT);
			document.add(imagen);
			
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			
			if (numAfiliado.equals(null)) {
				numAfiliado=" --- ";
			}
			
			
			document.add(new Paragraph(("Paciente: " ),FUENTE_TITULO));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Nombre: " + (nombre), FUENTE_DATOS));
			document.add(new Paragraph("Apellido: " + (apellido), FUENTE_DATOS));
			document.add(new Paragraph("DNI: " + (dni), FUENTE_DATOS));
			document.add(new Paragraph("Fecha de Nacimiento: " + (fechaNac), FUENTE_DATOS));
			document.add(new Paragraph("Número de Afiliado: " + (numAfiliado), FUENTE_DATOS));
			

			document.add(Chunk.NEWLINE);

			Paragraph texto = new Paragraph("Muchas gracias por hacerse atender en nuestros laboratorios."
					+ "Les brindamos sus resultados de la orden: "+ dni);
			texto.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(texto);
			
			
//			for (Element element : texto) {}
			

			document.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (DocumentException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	

	
	
	//GUARDAR PDF EN BASE DE DATOS
	@Transactional
    public Pdf savePdf(MultipartFile archivo) throws ErrorServicio{
		
        if (archivo != null) {
        	
            try {
                Pdf pdf = new Pdf();
                pdf.setMime(archivo.getContentType());
                pdf.setNombre(archivo.getName());
                pdf.setContenido(archivo.getBytes());
                
                return pdfRepositorio.save(pdf);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

	
}

