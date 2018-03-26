package com.gja.gestionCasos.actividades.repository;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface TareaParticularCasoRepository {
	
	public TareaParticularCaso consultarTareaParticular(Integer codigoTarea) throws DAOException,BusinessException;
	public TareaParticularCaso guardarTareaParticularCaso(TareaParticularCaso tareaParticularCaso) throws DAOException,BusinessException;
	public List<TareaActividad> obtenerCodigoTareaActividad(TareaActividad tareaActividad) throws DAOException ;
	public List<Actividad>  obtenerCodigoActividad(Actividad Actividad) throws DAOException ;
	public void borradoFisicoTarea(TareaParticularCaso tareaParticularCaso) throws DAOException,BusinessException;
	public List<TareaParticularCaso> obtenerTareasPendientes() throws DAOException,BusinessException;
	public List<TareaParticularCaso> obtenerTareasVencimientoHoy() throws DAOException, BusinessException;
	public Long getCountTareasProximoVencimiento(CasoFiltro caso, String sSearch) throws DAOException, BusinessException;
	public List<Object[]> getTareasProximoVencimiento(CasoFiltro casoFiltro, String sSearch) throws DAOException, BusinessException;
	public List<Object[]> consultarTareasPorEstado(EquipoCaso equipoCaso) throws DAOException, BusinessException;
	public List<Object[]> consultarPorcentajesTareasPorCasoYEstado(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public List<TareaParticularCaso> obtenerTareasPendientesFechaActual() throws DAOException, BusinessException;


}
