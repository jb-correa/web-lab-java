package com.weblab.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weblab.app.entidades.File;
import com.weblab.app.entidades.Foto;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String>{
    
    @Query("SELECT a from Foto a ORDER BY a.nombre")
	public List<Foto> verFotos();
}

