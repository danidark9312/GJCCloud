package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.EntidadFinanciera;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface EntidadFinancieraRepository {

	public List<EntidadFinanciera> obtenerEntidadesFinancieras() throws DAOException,BusinessException;
	
}
