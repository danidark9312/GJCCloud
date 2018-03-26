/**
 * 
 */
package com.gja.gestionCasos.maestros.service;

import java.util.List;

import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author USUARIO
 *
 */
public interface RolesUsuariosService {
	
	List<RolesUsuarios> getRolesUsuarios() throws BusinessException, DAOException;	
	
	RolesUsuarios findByPK(RolesUsuarios rolesUsuarios) throws DAOException;
	
	RolesUsuarios findByCodigoUsuario(String codigoUsuario) throws BusinessException, DAOException;
	
	boolean existRolesUsuarios(String codigoUsuario, int codigoRol) throws DAOException;
}
