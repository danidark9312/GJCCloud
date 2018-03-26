package com.gja.gestionCasos.actividades.service;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.ListaActividades;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;


public interface ActividadCasoService {

	public ActividadCaso guardarActividadCaso(ActividadCaso actividadCaso, List<CasoEquipoCaso> demandantes) throws DAOException,BusinessException;
	public List<ActividadCaso>  getActividadByCaso(Integer codigoCaso) throws DAOException, BusinessException;
	public List<ActividadTipoCaso> obtenerActividadesTipoCaso(Integer codigoTipoCaso)throws DAOException, BusinessException;
	public ActividadCaso modificarActividadCaso(ActividadCaso actividadCaso) throws DAOException,BusinessException;
	public ActividadCaso eliminarActividadCaso(ActividadCaso actividadCaso) throws DAOException,BusinessException;
	public ActividadCaso activarActividadCaso(ActividadCaso actividadCaso) throws DAOException,BusinessException;
	public List<ActividadCaso> obtenerActividadesInactivas(CasoFiltro casoFiltro) throws DAOException,BusinessException;
	public List<ActividadCaso> obtenerTotalActividadesInactivas(CasoFiltro casoFiltro) throws DAOException,BusinessException;
	public void borradoFisicoActividad(ActividadCaso actividadCaso) throws DAOException,BusinessException;
	public List<Actividad> obtenerEstadosProcesales() throws DAOException, BusinessException;
	public List<ActividadCaso> obtenerActividadesPendientes() throws DAOException, BusinessException;
	public List<ActividadCaso> obtenerActividadesVencidas() throws DAOException, BusinessException;
	public void vencerActividad(ActividadCaso actividadCaso) throws DAOException, BusinessException;
	
	public ActividadCaso guardarActividadCasoOtroResponsable(ActividadCaso actividadCaso) throws DAOException, BusinessException;
	public EquipoCaso guardarEquipoCasoOtroResponsable(EquipoCaso equipoCaso) throws DAOException, BusinessException;
	public CasoEquipoCaso guardarCasoEquipoCasoOtroResponsable(CasoEquipoCaso casoEquipoCaso) throws DAOException, BusinessException;
	public TareaParticularCaso guardarTareaParticularOtroResponsable(TareaParticularCaso tareaParticularCasos) throws DAOException, BusinessException;
	int updateActividadesCasoOrden(ListaActividades listaActividades) throws DAOException, BusinessException;
}
