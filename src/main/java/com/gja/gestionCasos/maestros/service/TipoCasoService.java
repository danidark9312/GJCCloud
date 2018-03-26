package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.filters.TipoCasoFiltro;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface TipoCasoService {

	public List<TipoCaso> obtenerTipoCaso()throws DAOException, BusinessException;
	public Long getCountFilter(TipoCasoFiltro tipoCaso) throws DAOException;
	public List<TipoCaso> getTipoCasoByFilter(TipoCasoFiltro tipoCaso)throws DAOException,BusinessException;
	public TipoCaso guardarTipoCaso(TipoCaso tipoCaso)throws DAOException, BusinessException;
	public int eliminarTipoCaso(TipoCaso tipoCaso)throws DAOException,BusinessException;
	public TipoCaso findByPK(TipoCaso tipoCaso) throws DAOException,BusinessException;
	public Long getCountByActividad(ActividadTipoCaso actividadTipoCaso) throws DAOException,BusinessException;
	public boolean existNameTipoCaso(TipoCaso tipoCaso) throws DAOException,BusinessException;
	public Integer consultarCodigoTipoCaso(TipoCaso tipoCaso) throws DAOException,	BusinessException; 
	public TipoCaso obtenerActividades(TipoCaso tipoCaso) throws DAOException, BusinessException;
	public JSONObject getJsonMostrarTiposCasos(List<TipoCaso> tiposCasos,int sEcho,int cantidad) throws BusinessException;

}
