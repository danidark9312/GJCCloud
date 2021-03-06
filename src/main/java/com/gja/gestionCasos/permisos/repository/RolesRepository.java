/**
 * 
 */
package com.gja.gestionCasos.permisos.repository;

import java.util.List;

import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;

/**
 * @author Usuario
 *
 */
public interface RolesRepository {

	public Rol getRol(RolesUsuarios rolesUsuarios) throws BusinessException, DAOException;
	
	public List<Rol> getRoles() throws BusinessException, DAOException;
	
	public RolesUsuarios getRolesUsuarios(String idRol) throws BusinessException, DAOException;
	
	
}
