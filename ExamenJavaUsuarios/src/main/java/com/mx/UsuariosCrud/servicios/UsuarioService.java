package com.mx.UsuariosCrud.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mx.UsuariosCrud.entidades.Usuario;

public interface UsuarioService {

	public void save(Usuario usuario);

	public Usuario findOne(Long id);

	public void delete(Long id);

	public List<Usuario> findAll();

	public Page<Usuario> findAll(Pageable pageable);

	public List<Usuario> findAll(String palabraClave);

	public List<Usuario> findAllActivo(String activo);

	public List<Usuario> findAllInactivo(String inactivo);

	public List<Usuario> findAllRevocado(String revocado);
}
