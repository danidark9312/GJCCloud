package com.gja.gestionCasos.maestros.service;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.filters.ActividadFiltro;
import com.gja.gestionCasos.filters.GeneralFilter;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface MaestroActividadesService {

	List<Actividad> obtenerActividades()throws DAOException, BusinessException;
	Actividad guardarActividades(Actividad actividad) throws DAOException, BusinessException; 
	Actividad findByPK(Actividad actividad)  throws DAOException, BusinessException;
	int eliminarActividades(Actividad actividad) throws DAOException, BusinessException; 
	Long getCountFilter(ActividadFiltro actividad) throws DAOException;
	public List<Actividad> getActividadByFilter(ActividadFiltro actividad)
			throws DAOException,BusinessException;
	public boolean existNameActividad(Actividad actividad) throws DAOException,	BusinessException;
	
}
