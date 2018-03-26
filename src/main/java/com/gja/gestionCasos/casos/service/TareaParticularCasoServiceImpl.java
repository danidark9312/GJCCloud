package com.gja.gestionCasos.casos.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.AlertaTareaParticular;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.ActividadCasoRepository;
import com.gja.gestionCasos.actividades.repository.AlertaTareaParticularRepository;
import com.gja.gestionCasos.actividades.repository.TareaParticularCasoRepository;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Service("tareaParticularCasoService")
public class TareaParticularCasoServiceImpl implements TareaParticularCasoService{

	@Autowired
	private ActividadCasoRepository actividadCasoRepository;
	
	@Autowired
	private TareaParticularCasoRepository tareaParticularCasoRepository; 
	
	@Autowired
	private AlertaTareaParticularRepository alertaTareaParticularRepository;
	
	public TareaParticularCaso guardarTareaparticularCaso(
			TareaParticularCaso tareaParticularCaso) throws DAOException,BusinessException{
		ActividadCaso actividad = null;
		actividad=tareaParticularCaso.getActividadParticular();
		tareaParticularCaso.setActividadParticular(null);
		actividad = actividadCasoRepository.guardarActividadCaso(actividad);
		tareaParticularCaso.setActividadParticular(actividad);
		return tareaParticularCaso;
	}
	
	@Transactional
	public TareaParticularCaso eliminarTareaParticularCaso(
			TareaParticularCaso tareaParticularCaso) throws DAOException,
			BusinessException {
		tareaParticularCaso = tareaParticularCasoRepository.consultarTareaParticular(tareaParticularCaso.getCodigoTarea());
		tareaParticularCaso.setSnActiva(Parametros.getCodigoTareaActivoNo());
		tareaParticularCaso = tareaParticularCasoRepository.guardarTareaParticularCaso(tareaParticularCaso);
		Hibernate.initialize(tareaParticularCaso.getActividadParticular());
		return tareaParticularCaso;
	}

	public  List<TareaActividad> obtenerCodigoTarea(TareaActividad tareaActividad) throws DAOException,
	BusinessException {
		 List<TareaActividad> tipoCasoByPK = tareaParticularCasoRepository.obtenerCodigoTareaActividad(tareaActividad);
		return tipoCasoByPK;
	}
	
	public  List<Actividad> obtenerCodigoActividad(Actividad actividad) throws DAOException,
	BusinessException {
		 List<Actividad> tipoCasoByPK = tareaParticularCasoRepository.obtenerCodigoActividad(actividad);
		return tipoCasoByPK;
	}
	
	@Transactional
	public TareaParticularCaso activarTareaParticularCaso(TareaParticularCaso tareaParticularCaso, boolean isActivarActividad) throws DAOException,
			BusinessException {
		tareaParticularCaso = tareaParticularCasoRepository.consultarTareaParticular(tareaParticularCaso.getCodigoTarea());
		if (tareaParticularCaso.getActividadParticular().getSnActiva() != null && tareaParticularCaso.getActividadParticular().getSnActiva().equals(Parametros.getCodigoActividadActivoSi())) {			
			tareaParticularCaso.setSnActiva(Parametros.getCodigoTareaActivoSi());
			tareaParticularCaso = tareaParticularCasoRepository.guardarTareaParticularCaso(tareaParticularCaso);
		} else {
			if (isActivarActividad) {
				tareaParticularCaso.setSnActiva(Parametros.getCodigoTareaActivoSi());
				tareaParticularCaso.getActividadParticular().setSnActiva(Parametros.getCodigoActividadActivoSi());
				tareaParticularCaso = tareaParticularCasoRepository.guardarTareaParticularCaso(tareaParticularCaso);
			} else {
				throw new BusinessException("La tarea pertenece a una actividad inactiva, debe activar la actividad para continuar.");
			}
		}
		return tareaParticularCaso;
	}
	
	@Transactional
	public void borradoFisicoTarea(TareaParticularCaso tareaParticularCaso) throws DAOException, BusinessException {
		tareaParticularCaso = tareaParticularCasoRepository.consultarTareaParticular(tareaParticularCaso.getCodigoTarea());
		tareaParticularCasoRepository.borradoFisicoTarea(tareaParticularCaso);
	}
	
	@Transactional
	public List<TareaParticularCaso> obtenerTareasPendientes() throws DAOException, BusinessException {
		List<TareaParticularCaso> tareasPendientes = tareaParticularCasoRepository.obtenerTareasPendientes();
		for (TareaParticularCaso tarea:tareasPendientes) {
			Hibernate.initialize(tarea.getActividadParticular());
			Hibernate.initialize(tarea.getResponsablesTareas());
			for (ResponsableTarea responsable:tarea.getResponsablesTareas()) {
				Hibernate.initialize(responsable.getCasosEquiposCaso().getEquipoCaso().getUsuario());
			}
		}
		return tareasPendientes;
	}
	
	@Transactional
	public AlertaTareaParticular guardarAlerta(AlertaTareaParticular alertaTarea)
			throws DAOException, BusinessException {
		alertaTarea = alertaTareaParticularRepository.guardarAlerta(alertaTarea);
		return alertaTarea;
	}
	
	
	public AlertaTareaParticular obtenerAlertaPorTarea(TareaParticularCaso tarea)
			throws DAOException, BusinessException {
		AlertaTareaParticular alertaTarea = alertaTareaParticularRepository.obtenerAlertaPorTarea(tarea);
		return alertaTarea;
	}
	
	@Transactional
	public List<TareaParticularCaso> obtenerTareasVencimientoHoy() throws DAOException, BusinessException {
		List<TareaParticularCaso> tareasPendientes = tareaParticularCasoRepository.obtenerTareasVencimientoHoy();
		for (TareaParticularCaso tarea:tareasPendientes) {
			Hibernate.initialize(tarea.getActividadParticular());
			Hibernate.initialize(tarea.getResponsablesTareas());
			for (ResponsableTarea responsable:tarea.getResponsablesTareas()) {
				Hibernate.initialize(responsable.getCasosEquiposCaso().getEquipoCaso().getUsuario());
			}
		}
		return tareasPendientes;
	}
	
	@Override
	@Transactional
	public void vencerTarea(TareaParticularCaso tarea) throws DAOException, BusinessException {
		tarea.setFinalizada(Parametros.getEstadoActividadVencida());
		tareaParticularCasoRepository.guardarTareaParticularCaso(tarea);
	}
	
	
	@Override
	public List<Object[]> consultarTareasPorEstado(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		List<Object[]> tareasPorEstado = tareaParticularCasoRepository.consultarTareasPorEstado(equipoCaso);
		
		for (Object[] obj:tareasPorEstado) {
			
			if (Parametros.getEstadoActividadVencida().equals(obj[1].toString())) {
				obj[1] = "Vencidas";
//				actividadesPorEstado.add(obj);
				continue;
			}
			if (Parametros.getActividadFinalizada().equals(obj[1].toString())) {
				obj[1] = "Finalizadas";
//				actividadesPorEstado.add(obj);
				continue;
			}
			if (Parametros.getActividadPendiente().equals(obj[1].toString())) {
				obj[1] = "Pendientes";
//				actividadesPorEstado.add(obj);
				continue;
			}
		}
		return tareasPorEstado;
	}
	
	
	@Override
	public List<Object[]> consultarPorcentajesTareasPorCasoYEstado(CasoFiltro casoFiltro) throws DAOException, BusinessException {		
		List<Object[]> tareasPorEstado = tareaParticularCasoRepository.consultarPorcentajesTareasPorCasoYEstado(casoFiltro);
		
		for (Object[] obj:tareasPorEstado) {
			if (Parametros.getEstadoActividadVencida().equals(obj[1].toString())) {
				obj[1] = "Vencidas";
//				actividadesPorEstado.add(obj);
				continue;
			}
			if (Parametros.getActividadFinalizada().equals(obj[1].toString())) {
				obj[1] = "Finalizadas";
//				actividadesPorEstado.add(obj);
				continue;
			}
			if (Parametros.getActividadPendiente().equals(obj[1].toString())) {
				obj[1] = "Pendientes";
//				actividadesPorEstado.add(obj);
				continue;
			}
		}
		return tareasPorEstado;
	}
}
