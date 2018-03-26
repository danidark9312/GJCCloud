/**
 * 
 */
package com.gja.gestionCasos.permisos.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sf.roles.Rol;
import com.sf.roles.RolAccionOpcion;
import com.sf.roles.RolAccionOpcionPK;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

/**
 * @author Usuario
 *
 */
@Repository("AccionesRepositoryImpl")
public class AccionesRepositoryImpl extends AbstractRepository<RolAccionOpcion> implements AccionesRepository {

	public static final Logger LOG = Logger.getLogger(AccionesRepositoryImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gja.gestionCasos.permisos.repository.AccionesRepository#asignAccionesRol
	 * (com.sf.roles.Rol, java.util.List)
	 */
	public RolAccionOpcion asignAccionesRol(RolAccionOpcion acciones) throws BusinessException, DAOException {
		acciones = this.merge(acciones);
		
		return acciones;
	}
	
	public RolAccionOpcion reasignAccionesRol(RolAccionOpcion acciones) throws BusinessException, DAOException {
		acciones = entityManager.merge(acciones);
		
		return acciones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gja.gestionCasos.permisos.repository.AccionesRepository#getAcciones()
	 */
	public List<String> getAcciones() throws BusinessException, DAOException {
		List<String> listaAcciones = new ArrayList<String>();
		//TODO Opciones quemadas revisar como obtenerlas a traves de reflexion
		listaAcciones.add("snCrear");
		listaAcciones.add("snEliminar");
		listaAcciones.add("snEscritura");
		listaAcciones.add("snLectura");

		return listaAcciones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gja.gestionCasos.permisos.repository.AccionesRepository#getAcciones
	 * (com.sf.roles.Rol)
	 */
	public List<RolAccionOpcion> getAcciones(Rol rol) throws BusinessException, DAOException {
		//TODO REVISAR SI SE NECESITA O NO METODO
		return null;
	}

	/**
	 * @exception NoResultException es omitida (si no hay resultado no dispara excepcion) 
	 */
	public RolAccionOpcion getAcciones(RolAccionOpcionPK rolAccionOpcionPk) throws BusinessException,
		DAOException {
		RolAccionOpcion acciones = new RolAccionOpcion();
		
		try {
		acciones = (RolAccionOpcion) entityManager.createQuery("SELECT r FROM RolAccionOpcion r WHERE r.rolAccionOpcionPK.codigoRol = :pRol AND r.rolAccionOpcionPK.codigoOpcion = :pOpcion")
				.setParameter("pRol", rolAccionOpcionPk.getCodigoRol())
				.setParameter("pOpcion", rolAccionOpcionPk.getCodigoOpcion())
				.getSingleResult();
		}catch(NoResultException e) {
			/**
			 * Omite la excepcion, se necesita continuar en srevicio y controlador cuando este no encuentra resultados
			 */
		}

		return acciones;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gja.gestionCasos.permisos.repository.AccionesRepository#setAcciones
	 * (com.sf.roles.RolAccionOpcion)
	 */
	public RolAccionOpcion setAcciones(RolAccionOpcion rolAccionOpcion) throws BusinessException, DAOException {
		
		rolAccionOpcion = entityManager.merge(rolAccionOpcion);
		
		return rolAccionOpcion;
	}

	public RolAccionOpcion setOpciones(RolAccionOpcionPK rolAccionOpcionPk) throws BusinessException,
			DAOException {
		RolAccionOpcion rolAccionOpcion = new RolAccionOpcion();

		rolAccionOpcion.setRolAccionOpcionPK(rolAccionOpcionPk);

		rolAccionOpcion = entityManager.merge(rolAccionOpcion);

		return rolAccionOpcion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gja.gestionCasos.permisos.repository.AccionesRepository#
	 * unasignAccionesRol(com.sf.roles.Rol, java.util.List)
	 */
	public boolean unasignAccionesRol(Rol rol, List<RolAccionOpcion> acciones) throws BusinessException,
			DAOException {
		//TODO REVISAR SI SE NECESITA O NO METODO
		return false;
	}
	
}
