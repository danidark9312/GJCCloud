/**
 * 
 */
package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.repository.RolesUsuariosRepository;
import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author USUARIO
 *
 */
@Service("RolesUsuariosService")
@Transactional(readOnly = true)
public class RolesUsuarioServiceImpl implements RolesUsuariosService {

	@Autowired
	RolesUsuariosRepository rolesUsuariosRepository;
	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.maestros.service.RolesUsuariosService#getRolesUsuarios()
	 */
	public List<RolesUsuarios> getRolesUsuarios() throws BusinessException, DAOException {

		return rolesUsuariosRepository.getRolesUsuarios();
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.maestros.service.RolesUsuariosService#findByPK(com.sf.roles.RolesUsuarios)
	 */
	public RolesUsuarios findByPK(RolesUsuarios rolesUsuarios) throws DAOException {
		
		return rolesUsuariosRepository.findByPK(rolesUsuarios);
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.maestros.service.RolesUsuariosService#findByCodigoUsuario(java.lang.String)
	 */
	public RolesUsuarios findByCodigoUsuario(String codigoUsuario) throws BusinessException, DAOException {
		
		return rolesUsuariosRepository.findByCodigoUsuario(codigoUsuario);
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.maestros.service.RolesUsuariosService#existRolesUsuarios(java.lang.String, int)
	 */
	public boolean existRolesUsuarios(String codigoUsuario, int codigoRol) throws DAOException {
		 
		return rolesUsuariosRepository.existRolesUsuarios(codigoUsuario, codigoRol);
	}

}
