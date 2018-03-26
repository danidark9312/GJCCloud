/**
 * 
 */
package com.gja.gestionCasos.permisos.repository;

import java.util.List;

import javax.persistence.Query;

import org.exolab.castor.mapping.xml.Sql;
import org.springframework.stereotype.Repository;

import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

/**
 * @author Usuario
 *
 */
@Repository("RolesRepository")
public class RolesRepositoryImpl extends AbstractRepository<Rol> implements RolesRepository {

	
	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.repository.RolesRepository#getRoles()
	 */
	@SuppressWarnings("unchecked")
	public List<Rol> getRoles() throws BusinessException, DAOException {		
		List<Rol> roles = null;
		
		roles = entityManager.createQuery("SELECT r FROM Rol r ORDER BY r.descripcion").getResultList();

		return roles;
	}
	
	public Rol getRol(RolesUsuarios rolesUsuarios) throws BusinessException, DAOException {
		Rol rol = new Rol();
		
		rol.setCodigo(rolesUsuarios.getRolesUsuariosPK().getCodigoRol());
		
		rol = (Rol) entityManager.createQuery("SELECT r FROM Rol r WHERE r.codigo = :pCodigoRol")
				.setParameter("pCodigoRol", rol.getCodigo())
				.getSingleResult();
		
		return rol;
	}
	
	public RolesUsuarios getRolesUsuarios(String idUsuario) throws BusinessException, DAOException {
		RolesUsuarios rolesUsuarios = new RolesUsuarios();
		
		rolesUsuarios = (RolesUsuarios) entityManager.createQuery("SELECT r FROM RolesUsuarios r WHERE r.rolesUsuariosPK.codigoUsuario = :pCodigoUsuario")
				.setParameter("pCodigoUsuario", idUsuario)
				.getSingleResult();
		
		return rolesUsuarios;
	}
}
