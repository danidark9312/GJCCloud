package com.gja.gestionCasos.maestros.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.repository.CasoRepositoryImpl;
import com.gja.gestionCasos.filters.ActividadFiltro;
import com.gja.gestionCasos.filters.GeneralFilter;
import com.gja.gestionCasos.maestros.entities.Instancia;
import com.gja.gestionCasos.maestros.repository.InstanciaRepository;
import com.gja.gestionCasos.maestros.repository.MaestroActividadRepositoryImpl;
import com.gja.gestionCasos.maestros.repository.MaestroActividadesRepository;
import com.gja.gestionCasos.maestros.repository.MaestroTareaActividadRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;


@Service("maestroActividadService")
@Transactional(readOnly = true)
public class MaestroActividadesServiceImpl implements MaestroActividadesService {
	@Autowired
	MaestroActividadRepositoryImpl maestroActividadRepository;
	private static final Logger LOG = Logger
			.getLogger(MaestroActividadesServiceImpl.class);
	@Autowired
	MaestroActividadesRepository maestroActividadesRepository;
	@Autowired
	MaestroTareaActividadRepositoryImpl maestroTareaActividadRepository; 
	
	public Long getCountFilter(ActividadFiltro actividad) throws DAOException {
		Long cantidad = maestroActividadesRepository.getCountFilter(actividad);
		return cantidad;
	}
	
	public List<Actividad> getActividadByFilter(ActividadFiltro actividad)
			throws DAOException,BusinessException {
				List<Actividad> actividades = maestroActividadesRepository.getActividadByFilter(actividad);
				return actividades;
	}
	
	
	public List<Actividad> obtenerActividades() throws DAOException,
			BusinessException {
		List<Actividad> actividades = maestroActividadesRepository.obtenerActividades();
		return actividades;
	}
	@Transactional
	public Actividad guardarActividades(Actividad actividad)
			throws DAOException, BusinessException {
		boolean guardarListado=false;    
		List<TareaActividad> tareas=actividad.getTareaActividadList();
		    
			if (actividad.getCdactividad()==null ){
				
				actividad.setTareaActividadList(null);
				guardarListado=true;
			}
			actividad=maestroActividadRepository.merge(actividad);
			//actividad.setTareaActividadList(tareas);
			if (guardarListado ){
				for (TareaActividad objTareaActividad: tareas){
					objTareaActividad.setCdactividad(actividad);
					maestroTareaActividadRepository.merge(objTareaActividad);
				}
			}
		
			
			return actividad;
	}
	
	public Actividad findByPK(Actividad actividad) throws DAOException,
			BusinessException {
		Actividad actividadFindByPK = maestroActividadesRepository.findByPK(actividad);;
		return actividadFindByPK;
	}
	@Transactional
	public int eliminarActividades(Actividad actividad)
		throws DAOException, BusinessException {
		List<TareaActividad> respaldoTareaActividad = new  ArrayList<TareaActividad>();
		respaldoTareaActividad = actividad.getTareaActividadList();
		TareaActividad tareaActividad = new TareaActividad();
//		for(TareaActividad objTareaActividad: respaldoTareaActividad){
//			TareaActividad tareaActividadEliminar = new TareaActividad();
//			tarea.setCdtarea(objTareaActividad.getCdtarea().getCdtarea());
//			tareaActividadEliminar.setCdtareaactividad(objTareaActividad.getCdtareaactividad());
//			tareaActividadEliminar= maestroTareaActividadRepository.findByPK(tareaActividadEliminar);
//			maestroTareaActividadRepository.remove(tareaActividadEliminar);
//			tarea=maestroTareaRepository.findByPK(tarea);
//			maestroTareaRepository.remove(tarea);
//			objTareaActividad.setCdtarea(null);
//		}
		
		actividad.setTareaActividadList(null);
		actividad.setActividadTipoCasoList(null);
		Actividad actividadEliminar = new Actividad();
		actividadEliminar.setCdactividad(actividad.getCdactividad());
		actividadEliminar=	maestroActividadRepository.findByPK(actividadEliminar);
		actividadEliminar.setIsactivo("N");
		maestroActividadRepository.merge(actividadEliminar);
		
		return 1;
	}
	
	public boolean existNameActividad(Actividad actividad) throws DAOException,
	BusinessException {
		boolean existe = maestroActividadesRepository.existNameActividad(actividad.getDsactividad());
		return existe;
	}


}
