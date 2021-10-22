package com.weblab.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weblab.app.entidades.Os;
import com.weblab.app.entidades.Practica;

@Repository
public interface OsRepositorio extends JpaRepository<Os, String>{

	@Query (value ="SELECT a from Os a")
	List<Os> OsDisponibles();
	
	@Query (value ="SELECT a FROM Os a WHERE a.osNumber = :osNumber")
	Os mostrarOs(@Param("osNumber")String osNumber);	
}