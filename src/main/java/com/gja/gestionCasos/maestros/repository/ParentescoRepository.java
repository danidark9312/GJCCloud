package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.Parentesco;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface ParentescoRepository {

	public List<Parentesco> obtenerParentesco() throws DAOException,BusinessException;
	public List<Parentesco> obtenerCodigoParentesco(Parentesco parentesco) throws DAOException ;

}
