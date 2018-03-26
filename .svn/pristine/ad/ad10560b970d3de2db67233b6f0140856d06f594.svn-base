package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface MaestroUsuariosRepository {
	
	boolean existIdUsuario(String idUsuario) throws DAOException;
	
	boolean existCorreoUsuario(String email) throws DAOException;
	
	User findByPK(User usuario) throws DAOException; 	
	
	List<EstadoUsuario> getEstados() throws BusinessException, DAOException;
	 
	List<User> getUsuarios(String activo, String rol, String sSearch, int displayStart, int displayLength) throws BusinessException, DAOException;
	
	public String obtenerIdPorEmail(String email) throws BusinessException, DAOException;
	
	public Long getCountUsuario(String activo, String rol, String sSearch) throws BusinessException, DAOException;
		
}