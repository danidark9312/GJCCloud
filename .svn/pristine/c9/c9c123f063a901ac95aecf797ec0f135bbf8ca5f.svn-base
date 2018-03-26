package com.gja.gestionCasos.maestros.service;

import java.util.List;

import com.sf.roles.EstadoRol;
import com.sf.roles.Rol;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface MaestroRolesService {

	List<Rol> getRoles(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws BusinessException, DAOException;
	
	List<Rol> getAllRoles(String activo, String busquedaDescripcion) throws BusinessException, DAOException;
	
	Long getCountRoles(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws BusinessException, DAOException;
	
	List<EstadoRol> getEstados() throws BusinessException, DAOException;
	
	Rol saveRol(Rol rol) throws BusinessException, DAOException;

	Rol findByIdRol(Integer codigoRol) throws BusinessException, DAOException;
	
	Rol findByPK(Rol rol) throws BusinessException, DAOException;

	int deleteRol(Rol rol) throws BusinessException, DAOException;

	public boolean existNameRol(Rol rol) throws BusinessException, DAOException;

}
