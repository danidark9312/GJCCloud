package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.filters.TipoCasoFiltro;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface TipoCasoRepository {

	public List<TipoCaso> obtenerTipoCaso() throws DAOException, BusinessException;
	public Long getCountFilter(TipoCasoFiltro tipoCaso) throws DAOException;
	public List<TipoCaso> getTipoCasoByFilter(TipoCasoFiltro tipoCaso)
			throws DAOException;
	public TipoCaso findByPK(TipoCaso tipoCaso) throws DAOException;
	public boolean existNameTipoCaso(String nombre) throws DAOException;
	public Integer obtenerCodigoTipoCaso(TipoCaso nombreTipoCaso) throws DAOException;
	public TipoCaso obtenerActividades(TipoCaso tipoCaso) throws DAOException ;
}
