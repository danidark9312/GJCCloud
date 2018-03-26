package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.sf.roles.EstadoRol;
import com.sf.roles.Rol;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface MaestroRolesRepository {
	
	public List<Rol> getRoles(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws BusinessException, DAOException;	
	
	Rol findByPK(Rol rol) throws DAOException;
	
	Rol findByIdRol(Integer codigoRol) throws BusinessException, DAOException;
	
	public boolean existNameRol(String nombre) throws DAOException;
	
	public List<EstadoRol> getEstados() throws BusinessException, DAOException;
	
	public Long getCountRoles(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws BusinessException, DAOException;
		
}