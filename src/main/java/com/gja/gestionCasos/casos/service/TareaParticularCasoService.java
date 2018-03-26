package com.gja.gestionCasos.casos.service;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.AlertaTareaParticular;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface TareaParticularCasoService {

	public TareaParticularCaso guardarTareaparticularCaso(TareaParticularCaso tareaParticularCaso) throws DAOException,BusinessException;
	public List<TareaActividad> obtenerCodigoTarea(TareaActividad tareaActividad) throws DAOException,	BusinessException; 
	public List<Actividad> obtenerCodigoActividad(Actividad actividad) throws DAOException,	BusinessException;
	public TareaParticularCaso eliminarTareaParticularCaso(TareaParticularCaso tareaParticularCaso) throws DAOException,BusinessException;
	public TareaParticularCaso activarTareaParticularCaso(TareaParticularCaso tareaParticularCaso, boolean isActivarTarea) throws DAOException,BusinessException;
	public void borradoFisicoTarea(TareaParticularCaso tareaParticularCaso) throws DAOException,BusinessException;
	public List<TareaParticularCaso> obtenerTareasPendientes() throws DAOException,BusinessException;
	public AlertaTareaParticular obtenerAlertaPorTarea(TareaParticularCaso tarea) throws DAOException, BusinessException;
	public AlertaTareaParticular guardarAlerta(AlertaTareaParticular alertaTarea) throws DAOException, BusinessException;
	public List<TareaParticularCaso> obtenerTareasVencimientoHoy() throws DAOException, BusinessException;
	public void vencerTarea(TareaParticularCaso tarea) throws DAOException, BusinessException;
	public List<Object[]> consultarTareasPorEstado(EquipoCaso equipoCaso) throws DAOException, BusinessException;
	public List<Object[]> consultarPorcentajesTareasPorCasoYEstado(CasoFiltro casoFiltro) throws DAOException, BusinessException;
}
