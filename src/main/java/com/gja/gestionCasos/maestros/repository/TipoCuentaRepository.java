package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.TipoCuenta;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface TipoCuentaRepository {
	
	public List<TipoCuenta> obtenerTipoCuenta() throws BusinessException,DAOException;

}
