/**
 * 
 */
package com.gja.gestionCasos.permisos.service;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.repository.MaestroRolesRepositoryImpl;
import com.gja.gestionCasos.permisos.repository.AccionesRepositoryImpl;
import com.gja.gestionCasos.permisos.repository.OpcionesRepository;
import com.sf.roles.Opcion;
import com.sf.roles.Rol;
import com.sf.roles.RolAccionOpcion;
import com.sf.roles.RolAccionOpcionPK;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author Usuario
 *
 */
@Service("accionesService")
@Transactional(readOnly = true)
public class AccionesServiceImpl implements AccionesService {

	@Autowired
	OpcionesRepository opcionesRepository;
	@Autowired
	AccionesRepositoryImpl accionesRepository;
	@Autowired
	MaestroRolesRepositoryImpl maestroRolRepository;
	
	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.service.AccionesService#asignAccionesRol(com.sf.roles.Rol, java.util.List)
	 */
	public boolean asignAccionesRol(RolAccionOpcionPK rolAccionOpcionPk, List<RolAccionOpcion> acciones) throws BusinessException,
			DAOException {
		for (RolAccionOpcion rolAccionOpcion : acciones) {
			rolAccionOpcion = accionesRepository.asignAccionesRol(rolAccionOpcion);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.service.AccionesService#asignAccionesRol(com.sf.roles.Rol, java.util.List)
	 */
	public void asignAccionesRol(List<RolAccionOpcion> acciones) throws BusinessException,
			DAOException {
		for (RolAccionOpcion rolAccionOpcion : acciones) {
			rolAccionOpcion = accionesRepository.asignAccionesRol(rolAccionOpcion);
		}
	}
	
	@Transactional
	public RolAccionOpcion asignAccionesRol(RolAccionOpcion accionesOpcion) throws BusinessException, DAOException {
		accionesOpcion = accionesRepository.persist(accionesOpcion);
		
		return accionesOpcion;
	}
	
	@Transactional
	public RolAccionOpcion reasignAccionesRol(RolAccionOpcion accionesOpcion) throws BusinessException, DAOException {
		accionesOpcion = accionesRepository.merge(accionesOpcion);
		
		return accionesOpcion;
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.service.AccionesService#getAcciones()
	 */
	public List<String> getAcciones() throws BusinessException, DAOException {
		
		return accionesRepository.getAcciones();
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.service.AccionesService#getAcciones(com.sf.roles.Rol)
	 */
	public RolAccionOpcion getAcciones(RolAccionOpcionPK rolAccionOpcionPk) throws BusinessException, DAOException {
		
		return accionesRepository.getAcciones(rolAccionOpcionPk);
	}

	public boolean ifPermisos(Rol rol, String permiso, String url) throws BusinessException, DAOException {
		boolean encuentraPermiso = false;
		RolAccionOpcionPK rolAccionOpcionPk = new RolAccionOpcionPK();
		RolAccionOpcion rolAccionOpcion = new RolAccionOpcion();
		String permisos[] = new String[]{"lectura", "eliminar", "escritura", "modificar"};
				
		for (Opcion opcion : opcionesRepository.getOpciones()) {
			if(!url.isEmpty() && url.equalsIgnoreCase(opcion.getUrl())){
				rolAccionOpcionPk.setCodigoOpcion(opcion.getCodigo());
				rolAccionOpcionPk.setCodigoRol(rol.getCodigo());
			}
		}
		
		for (String permisoAplicacion : permisos) {
			if(permisoAplicacion.equalsIgnoreCase(permiso)){
				if("S".equalsIgnoreCase(rolAccionOpcion.getSnCrear())){
					encuentraPermiso = true;
				}
			}
		}
		
		for (Field field : RolAccionOpcion.class.getDeclaredFields()) {
			System.out.println("Field:\t" + field.getName());
		}
		
		return encuentraPermiso;
	}
	
	@SuppressWarnings("unused")
	private List<Object> getPermisosPorRolOpcion(Rol rol, String opcion){
		return null;
	}
	
	@SuppressWarnings("unused")
	private List<Object> getOpcinoesPorRol(Rol rol){
		return null;
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.service.AccionesService#setAcciones(com.sf.roles.RolAccionOpcion)
	 */
	public RolAccionOpcion setAcciones(RolAccionOpcion rolAccionOpcion) throws BusinessException, DAOException {
		
		return accionesRepository.setAcciones(rolAccionOpcion);
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.service.AccionesService#unasignAccionesRol(com.sf.roles.Rol, java.util.List)
	 */
	public boolean unasignAccionesRol(Rol rol, List<RolAccionOpcion> acciones) throws BusinessException,
			DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	
}
	