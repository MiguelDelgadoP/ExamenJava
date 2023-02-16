package com.mx.UsuariosCrud.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mx.UsuariosCrud.entidades.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
	@Query("SELECT u FROM USUARIO u WHERE  U.NOMBRE LIKE%?1%"
			+"OR u.APATERNO LIKE%?1%"
			+"OR u.AMATERNO LIKE%?1%"
			+"OR u.ESTATUS LIKE%?A%"
			+"OR u.ESTATUS LIKE%?R%"
			+"OR u.ESTATUS LIKE%?B%")
	public List<Usuario> findAll(String palabraClave);
	

}
