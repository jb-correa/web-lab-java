package com.weblab.app.repositorios;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weblab.app.entidades.Practica;

@Repository
public interface PracticaRepositorio extends JpaRepository<Practica, String>{
	
		
	@Query (value = "SELECT * FROM Practica p ORDER BY p.nombre", nativeQuery=true)
	List<Practica> verPracticasDisponibles();
	
	@Query (value = "SELECT a FROM Practica a WHERE a.codigo = :codigo")
	Practica mostrarPracticaXCodigo(@Param("codigo")String codigo);
	
	@Query (value ="SELECT a FROM Practica a WHERE a.nombre = :nombre", nativeQuery=true)
	List<Practica> mostrarPracticaXnombre(@Param("nombre")String nombre);
	
	@Query (value ="SELECT a FROM Practica a WHERE a.metodo = :metodo", nativeQuery=true)
	List<Practica> mostrarPracticaXmetodo(@Param("metodo")String metodo);
	
	@Query (value ="SELECT a FROM Practica a JOIN a.usuario u WHERE u.dni LIKE :dni", nativeQuery=true)
	List<Practica> mostrarPracticaPorUsuario(@Param("dni")String dni);

}