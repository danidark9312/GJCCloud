package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface RolesUsuariosRepository {
	
	public List<RolesUsuarios> getRolesUsuarios() throws BusinessException, DAOException;	
	
	RolesUsuarios findByPK(RolesUsuarios rolesUsuarios) throws DAOException;
	
	RolesUsuarios findByCodigoUsuario(String codigoUsuario) throws BusinessException, DAOException;
	
	public boolean existRolesUsuarios(String codigoUsuario, int codigoRol) throws DAOException;
	
	public void borrarRolUsuario(String cedulaUsuario) throws BusinessException, DAOException;

	public List<RolesUsuarios> findByRol(Rol rol) throws BusinessException, DAOException;
	
	public List<Object>  findByNotificacion(Rol rol,TipoCaso tipoCaso) throws BusinessException, DAOException ;
}