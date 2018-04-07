package com.gja.gestionCasos.actividades.repository;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface ActividadCasoRepository {
	
	public ActividadCaso guardarActividadCaso(ActividadCaso actividadCaso) throws DAOException,BusinessException;
	public List<ActividadCaso> getActividadByCaso(Integer codigoCaso) throws DAOException;
	public ActividadCaso consultarActividad(Integer codigoCaso) throws DAOException,BusinessException;
	public List<ActividadCaso> obtenerFiltroActividadesInactivas(CasoFiltro casoFiltro) throws DAOException,BusinessException;
	public List<ActividadCaso> obtenerTotalActividadesInactivas(CasoFiltro casoFiltro) throws DAOException,BusinessException;
	public void borradoFisicoActividad(ActividadCaso actividadCaso) throws DAOException,BusinessException;
	public List<Actividad> obtenerEstadosProcesales() throws DAOException, BusinessException;
	public List<ActividadCaso> obtenerActividadesCaso(CasoFiltro casoFiltro) throws DAOException;
	public List<ActividadCaso> obtenerActividadesPendientes() throws DAOException, BusinessException;
	public List<ActividadCaso> obtenerActividadesVencidas() throws DAOException, BusinessException;
	int updateActividadCasoOrden(ActividadCaso actividadCaso) throws DAOException;
	List<ActividadCaso> getActividadPendienteByCaso(Integer codigoCaso) throws DAOException;
	List<ActividadCaso> getActividadCompladaByCaso(Integer codigoCaso) throws DAOException;
}
