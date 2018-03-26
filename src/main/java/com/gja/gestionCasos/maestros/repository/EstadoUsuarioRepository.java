package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface EstadoUsuarioRepository {
	
	List<EstadoUsuario> getEstadosUsuarios() throws BusinessException, DAOException;
		
	EstadoUsuario getEstadoUsuario(String descripcion) throws BusinessException, DAOException;
	
	EstadoUsuario getEstadoUsuario(Integer codigo) throws BusinessException, DAOException;
}
