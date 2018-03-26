/**
 * 
 */
package com.gja.gestionCasos.permisos.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.repository.MaestroRolesRepositoryImpl;
import com.gja.gestionCasos.permisos.repository.AccionesRepositoryImpl;
import com.gja.gestionCasos.permisos.repository.OpcionesRepositoryImpl;
import com.sf.roles.Opcion;
import com.sf.roles.Rol;
import com.sf.roles.RolAccionOpcionPK;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author Usuario
 *
 */
@Service("opcionesService")
@Transactional(readOnly = true)
public class OpcionesServiceImpl implements OpcionesService {

	@Autowired
	AccionesRepositoryImpl accionesRepository;
	@Autowired
	OpcionesRepositoryImpl opcionesRepository;
	@Autowired
	MaestroRolesRepositoryImpl maestroRolRepository;

	public static final Logger LOG = Logger.getLogger(OpcionesServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gja.gestionCasos.permisos.service.OpcionesService#asignOpcionesRol
	 * (com.sf.roles.Rol, java.util.List)
	 */
	public boolean asignOpcionesRol(Rol rol, List<Opcion> opciones) throws BusinessException, DAOException {
		RolAccionOpcionPK opcionAccionPk = new RolAccionOpcionPK();
		boolean res = true;

		if (!maestroRolRepository.existNameRol(rol.getDescripcion())) {
			for (Opcion opcion : opciones) {
				asignOpcionPorRol(rol, opcion, opcionAccionPk);
			}
		} else
			res = false;
		

		return res;
	}

	private boolean asignOpcionPorRol(Rol rol, Opcion opcion,
			RolAccionOpcionPK opcionesAccionesPk) throws BusinessException, DAOException {
		RolAccionOpcionPK opcionAccionPk = new RolAccionOpcionPK();

		if (opcionesRepository.existsOpcion(opcion)){
			opcionAccionPk.setCodigoOpcion(opcion.getCodigo());
			opcionAccionPk.setCodigoRol(rol.getCodigo());
			
			accionesRepository.setOpciones(opcionAccionPk);
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gja.gestionCasos.permisos.service.OpcionesService#getOpciones()
	 */
	public List<Opcion> getOpciones() throws BusinessException, DAOException {

		return opcionesRepository.getOpciones();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gja.gestionCasos.permisos.service.OpcionesService#getOpciones(com
	 * .sf.roles.Rol)
	 */
	public List<Opcion> getOpciones(Rol rol) throws BusinessException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gja.gestionCasos.permisos.service.OpcionesService#setOpciones(com
	 * .sf.roles.Opcion)
	 */
	public boolean setOpciones(Opcion opcion) throws BusinessException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gja.gestionCasos.permisos.service.OpcionesService#unasignOpcionesRol
	 * (com.sf.roles.Rol, java.util.List)
	 */
	public boolean unasignOpcionesRol(Rol rol, List<Opcion> opciones) throws BusinessException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Opcion getOpciones(String opcion) throws BusinessException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCodigoOpcion(String url) throws BusinessException, DAOException, NoResultException {
		
		return opcionesRepository.getCodigoOpcion(url);
	}

}
