/**
 * 
 */
package com.gja.gestionCasos.permisos.service;

import java.util.List;

import com.sf.roles.Rol;
import com.sf.roles.RolAccionOpcion;
import com.sf.roles.RolAccionOpcionPK;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author Usuario
 *
 */
public interface AccionesService {
	
	boolean asignAccionesRol(RolAccionOpcionPK rolAccionPk, List<RolAccionOpcion> acciones) throws BusinessException, DAOException;
	
	void asignAccionesRol(List<RolAccionOpcion> acciones) throws BusinessException, DAOException;
	
	RolAccionOpcion asignAccionesRol(RolAccionOpcion accionesOpcion) throws BusinessException, DAOException;
	
	RolAccionOpcion reasignAccionesRol(RolAccionOpcion accionesOpcion) throws BusinessException, DAOException;
	
	List<String> getAcciones() throws BusinessException, DAOException;
	
	RolAccionOpcion getAcciones(RolAccionOpcionPK rolAccionOpcionPk) throws BusinessException, DAOException;
	
	boolean ifPermisos(Rol rol, String permiso, String url) throws BusinessException, DAOException;
	
	RolAccionOpcion setAcciones(RolAccionOpcion rolAccionOpcion) throws BusinessException, DAOException;
	
	boolean unasignAccionesRol(Rol rol, List<RolAccionOpcion> acciones) throws BusinessException, DAOException;
}
