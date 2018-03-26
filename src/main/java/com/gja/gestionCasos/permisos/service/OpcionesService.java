/**
 * 
 */
package com.gja.gestionCasos.permisos.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.sf.roles.Opcion;
import com.sf.roles.Rol;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author Usuario
 *
 */
public interface OpcionesService {
		
	boolean asignOpcionesRol(Rol rol, List<Opcion> opciones) throws BusinessException, DAOException;
	
	String getCodigoOpcion(String url) throws BusinessException, DAOException, NoResultException;
	
	List<Opcion> getOpciones() throws BusinessException, DAOException;
	
	List<Opcion> getOpciones(Rol rol) throws BusinessException, DAOException;
	
	Opcion getOpciones(String opcion) throws BusinessException, DAOException;
		
	boolean setOpciones(Opcion opcion) throws BusinessException, DAOException;
	
	boolean unasignOpcionesRol(Rol rol, List<Opcion> opciones) throws BusinessException, DAOException;
}
