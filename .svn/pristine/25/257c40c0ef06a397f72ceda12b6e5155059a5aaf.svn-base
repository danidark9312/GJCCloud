package com.gja.gestionCasos.maestros.service;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.sf.roles.RolesUsuarios;
import com.sf.social.signinmvc.user.model.ChangePassword;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface MaestroUsuariosService {

	List<User> getUsuarios(String activo, String rol, String sSearch, int displayStart, int displayLength) throws BusinessException, DAOException;
	
	List<RolesUsuarios> getRolesUsuarios() throws BusinessException, DAOException;
	
	List<EstadoUsuario> getEstados() throws BusinessException, DAOException;

	User saveUsuario(User usuario, RolesUsuarios rolesUsuarios) throws BusinessException, DAOException;
	
	User saveUsuario(User usuario, String contrasenaNueva) throws BusinessException, DAOException;

	User findByPK(User usuario) throws BusinessException, DAOException;

	boolean existIdUsuario(User usuario) throws BusinessException, DAOException;

	boolean existCorreoUsuario(User usuario) throws BusinessException, DAOException;

	
	ChangePassword existCorreoUsuarioToken(User usuario) throws BusinessException, DAOException;

	User saveNewUsuario(User usuario, RolesUsuarios rolesUsuarios) throws BusinessException, DAOException;
	
	public String obtenerIdPorEmail(String email) throws BusinessException, DAOException;

	Long getCountUsuario(String activo, String rol, String sSearch) throws BusinessException, DAOException;
}
