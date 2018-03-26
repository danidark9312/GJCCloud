package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.filters.ActividadFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface MaestroActividadesRepository {
	public List<Actividad> obtenerActividades() throws DAOException,BusinessException;	
	Actividad findByPK(Actividad actividad) throws DAOException;
	Long getCountFilter(ActividadFiltro actividad) throws DAOException;
	List<Actividad> getActividadByFilter(ActividadFiltro actividad)throws DAOException;
	public boolean existNameActividad(String nombre) throws DAOException;
}
