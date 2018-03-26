/**
 * 
 */
package com.gja.gestionCasos.permisos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.permisos.repository.RolesRepositoryImpl;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author Usuario
 *
 */
@Service("RolesService")
@Transactional(readOnly = true)
public class RolesServiceImpl implements RolesService {

	@Autowired
	RolesRepositoryImpl rolesRepository;
	
	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.service.RolesService#getRoles()
	 */
	public List<Rol> getRoles() throws BusinessException, DAOException {
		
		return rolesRepository.getRoles();
	}
		
	public RolesUsuarios getRolesUsuarios(String idUsuario) throws BusinessException, DAOException {
		RolesUsuarios rolesUsuarios = new RolesUsuarios();
		
		rolesUsuarios = rolesRepository.getRolesUsuarios(idUsuario);
		
		return rolesUsuarios;
	}
	
	public Rol getRol(RolesUsuarios rolesUsuarios) throws BusinessException, DAOException {
		
		return rolesRepository.getRol(rolesUsuarios);
	}

}
