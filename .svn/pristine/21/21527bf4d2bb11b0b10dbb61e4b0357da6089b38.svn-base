/**
 * 
 */
package com.gja.gestionCasos.permisos.repository;

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
public interface AccionesRepository {

	RolAccionOpcion asignAccionesRol(RolAccionOpcion acciones) throws BusinessException, DAOException;
	
	RolAccionOpcion reasignAccionesRol(RolAccionOpcion acciones) throws BusinessException, DAOException;
	
	List<String> getAcciones() throws BusinessException, DAOException;
	
	List<RolAccionOpcion> getAcciones(Rol rol) throws BusinessException, DAOException;
	
	RolAccionOpcion getAcciones(RolAccionOpcionPK rolAccionOpcionPk) throws BusinessException, DAOException;
	
	RolAccionOpcion setAcciones(RolAccionOpcion rolAccionOpcion) throws BusinessException, DAOException;
	
	RolAccionOpcion setOpciones(RolAccionOpcionPK rolAccionOpcion) throws BusinessException, DAOException;
	
	boolean unasignAccionesRol(Rol rol, List<RolAccionOpcion> acciones) throws BusinessException, DAOException;
}
