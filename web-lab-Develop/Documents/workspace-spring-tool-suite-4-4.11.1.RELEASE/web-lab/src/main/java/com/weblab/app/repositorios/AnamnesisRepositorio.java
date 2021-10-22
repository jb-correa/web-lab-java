package com.weblab.app.repositorios;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weblab.app.entidades.Anamnesis;

@Repository
public interface AnamnesisRepositorio extends JpaRepository<Anamnesis, String>{

	@Query (value ="SELECT a from Anamnesis a ORDER BY fecha", nativeQuery=true)
	List<Anamnesis> verAnamnesis();
	
	@Query (value ="SELECT a from Anamnesis a WHERE a.descripcion = :descripcion", nativeQuery=true)
	List<Anamnesis> buscarDescripcion(@Param("descripcion")String descripcion);
	
}

