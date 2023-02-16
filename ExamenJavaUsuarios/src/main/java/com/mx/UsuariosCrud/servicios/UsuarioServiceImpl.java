package com.mx.UsuariosCrud.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.UsuariosCrud.entidades.Usuario;
import com.mx.UsuariosCrud.repositorios.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll(String palabraClave) {
		if (palabraClave != null) {
			return usuarioRepository.findAll(palabraClave);
		}
		return (List<Usuario>) usuarioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Usuario> findAllActivo(String activo) {
		if (activo != null) {
			return usuarioRepository.findAll(activo);
		}
		return (List<Usuario>) usuarioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Usuario> findAllInactivo(String inactivo) {
		if (inactivo != null) {
			return usuarioRepository.findAll(inactivo);
		}
		return (List<Usuario>) usuarioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Usuario> findAllRevocado(String revocado) {
		if (revocado != null) {
			return usuarioRepository.findAll(revocado);
		}
		return (List<Usuario>) usuarioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

}
