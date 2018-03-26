package com.gja.gestionCasos.casos.repository;

import com.gja.gestionCasos.casos.entities.Celular;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface CelularRepository {

	public Celular guardarCelular(Celular celular) throws DAOException,BusinessException;
	
}
