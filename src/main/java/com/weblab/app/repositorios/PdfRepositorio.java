package com.weblab.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.weblab.app.entidades.Pdf;

@Repository
public interface PdfRepositorio extends JpaRepository<Pdf, String>{
	
    @Query("SELECT a from Pdf a ORDER BY a.nombre")
	public List<Pdf> listarPdf();

}
