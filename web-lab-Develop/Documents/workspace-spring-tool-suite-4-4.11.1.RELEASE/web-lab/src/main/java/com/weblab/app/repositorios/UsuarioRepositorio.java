package com.weblab.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.weblab.app.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{

    //METODO DE CONSULTA: Mostrar Usuarios
    @Query("SELECT a FROM Usuario a ORDER BY a.apellido")
    public List<Usuario> mostrarUsuariosDisponibles();
	
//	@Query (value="SELECT a from Usuario a WHERE a.email = :email", nativeQuery=true)
//	Usuario mostrarUsuariosMail(@Param("email")String email);
    @Query("SELECT a FROM Usuario a WHERE a.email = :email")
    public Usuario mostrarUsuariosMail(@Param("email") String email);
	
	@Query ("SELECT a from Usuario a WHERE a.dni = :dni")
	public Usuario mostrarUsuariosDni(@Param("dni")String dni);
	
	@Query (value="SELECT a from Usuario a WHERE a.numAfiliado = :numAfiliado", nativeQuery=true)
	public Usuario mostrarUsuariosAfiliado(@Param("numAfiliado")String numAfiliado);
	
	@Query (value="SELECT a from Usuario a WHERE a.rol LIKE 'ADMIN'")
	public List <Usuario> verAdmin();
	
	
    //CONSULTA SQL: BUSCADOR GENÉRICO DE MULTIPLES PARAMETROS
	
//    @Query(value = "SELECT * FROM Usuario u, Anamnesis a, os o, Practica p WHERE u.nombre LIKE %?1%"
//    		+ " OR u.dni LIKE %?1% OR u.apellido LIKE %?1% OR u.fecha_nac LIKE %?1% OR a.descripcion IN"
//    		+ " (SELECT descripcion FROM anamnesis a WHERE a.id = u.id AND a.descripcion LIKE %?1%)"
//    		+ " OR o.nombre IN (SELECT nombre FROM os o WHERE o.id = u.id AND o.nombre LIKE %?1%)"
//    		+ " GROUP BY o.nombre OR a.descripcion OR u.nombre OR u.id OR u.apellido LIMIT 10", nativeQuery=true)
	
	@Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:q% "
			+ "OR u.apellido LIKE %:q% OR "
			+ "u.dni LIKE %:q% OR u.domicilio LIKE %:q% "
			+ "OR u.email LIKE %:q% OR u.rol LIKE %:q% "
			+ "OR u.telefono LIKE %:q% OR u.titulo LIKE %:q% ")
	public List<Usuario> findAll(String q);

	
}
