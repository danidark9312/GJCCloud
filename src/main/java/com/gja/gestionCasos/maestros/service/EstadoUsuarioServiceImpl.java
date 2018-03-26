package com.gja.gestionCasos.maestros.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.gja.gestionCasos.maestros.repository.EstadoUsuarioRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("estadoUsuarioService")
@Transactional(readOnly = true)
public class EstadoUsuarioServiceImpl implements EstadoUsuarioService {
	
	@Autowired
	EstadoUsuarioRepositoryImpl estadoUsuarioRepository;

	public List<EstadoUsuario> getEstadosUsuarios() throws BusinessException, DAOException {
		List<EstadoUsuario> estadoUsuario = new ArrayList<EstadoUsuario>();
		estadoUsuario = estadoUsuarioRepository.getEstadosUsuarios();
		return estadoUsuario;
	}

	public EstadoUsuario getEstadoUsuario(String descripcion) throws BusinessException, DAOException {
		EstadoUsuario estadoUsuario = new EstadoUsuario();
		estadoUsuario = estadoUsuarioRepository.getEstadoUsuario(descripcion);
		return estadoUsuario;
	}

	public EstadoUsuario getEstadoUsuario(Integer codigo) throws BusinessException, DAOException {
		EstadoUsuario estadoUsuario = new EstadoUsuario();
		estadoUsuario = estadoUsuarioRepository.getEstadoUsuario(codigo);
		return estadoUsuario;
	}

}
