package com.gja.gestionCasos.maestros.service;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.TipoCuenta;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface TipoCuentaService {

	public List<TipoCuenta> obtenerTipoCuenta() throws DAOException,BusinessException;
	
	
}
