package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

public interface ActividadTipoCasoRepository {

	public List<ActividadTipoCaso> obtenerActividadesTipoCaso(Integer codigoTipoCaso) throws DAOException,BusinessException;
	
	
}