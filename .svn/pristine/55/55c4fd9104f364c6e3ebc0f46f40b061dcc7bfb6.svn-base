package com.gja.gestionCasos.maestros.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.maestros.repository.MaestroActividadesRepository;
import com.gja.gestionCasos.maestros.repository.MaestroRolesRepositoryImpl;
import com.gja.gestionCasos.maestros.repository.MaestroTareaActividadRepositoryImpl;
import com.sf.roles.EstadoRol;
import com.sf.roles.Rol;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;


@Service("maestroRolesService")
@Transactional(readOnly = true)
public class MaestroRolesServiceImpl implements MaestroRolesService {
	@Autowired
	MaestroRolesRepositoryImpl maestroRolRepository;
	@Autowired
	MaestroActividadesRepository maestroActividadesRepository;
	@Autowired
	MaestroTareaActividadRepositoryImpl maestroTareaActividadRepository; 
			
	public List<Rol> getRoles(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws DAOException, BusinessException {
		
		return maestroRolRepository.getRoles(activo, busquedaDescripcion, displayStart, displayLength);
	}
	
	public Long getCountRoles(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws DAOException, BusinessException  {
		
		return maestroRolRepository.getCountRoles(activo, busquedaDescripcion, displayStart, displayLength);
	}
	
	public List<Rol> getAllRoles(String activo, String busquedaDescripcion) throws DAOException, BusinessException {
		
		return maestroRolRepository.getAllRoles(activo, busquedaDescripcion);
	}
	
	@Transactional
	public Rol saveRol(Rol rol) throws DAOException, BusinessException {

		if(rol.getCodigo() == null || rol.getCodigo() == -1){
			rol.setCodigo(null);
			rol = maestroRolRepository.persist(rol);
		}else{
			rol = maestroRolRepository.merge(rol);
		}

		return rol;
	}

	public boolean existNameRol(Rol rol) throws DAOException, BusinessException {
		
		return  maestroRolRepository.existNameRol(rol.getDescripcion());
	}
	
	public Rol findByPK(Rol rol) throws DAOException, BusinessException {
		
		return maestroRolRepository.findByPK(rol);
	}
	
	public Rol findByIdRol(Integer codigoRol) throws BusinessException, DAOException {

		return maestroRolRepository.findByIdRol(codigoRol);
	}
	
	/**
	 * TODO revisar si en las historias si hay o hubo que implementar logico
	 * o no el borrar rol
	 */
	@Transactional
	public int deleteRol(Rol rol)
		throws DAOException, BusinessException {
		@SuppressWarnings("unused")
		List<TareaActividad> respaldoTareaActividad = new  ArrayList<TareaActividad>();
		
		@SuppressWarnings("unused")
		TareaActividad tareaActividad = new TareaActividad();
	
		return 1;
	}
	
	public List<EstadoRol> getEstados() throws DAOException, BusinessException {
		
		return maestroRolRepository.getEstados();
	}

	public Long cantidad(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws BusinessException, DAOException {
		// TODO Auto-generated method stub
		return maestroRolRepository.getCountRoles(activo, busquedaDescripcion, displayStart, displayLength);
	}

		
}
