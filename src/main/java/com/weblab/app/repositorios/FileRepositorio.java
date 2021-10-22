package com.weblab.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.weblab.app.entidades.File;

@Repository
public interface FileRepositorio extends JpaRepository<File, String>{
	
	@Query(value="SELECT a from Foto a ORDER BY a.nombre", nativeQuery=true)
	List<File> verFotos();

}