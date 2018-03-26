package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.Departamento;
import com.gja.gestionCasos.maestros.entities.Pais;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface DepartamentoRepository {

	List<Departamento> obtenerDepartamentoPorPaises(Pais pais) throws BusinessException, DAOException;
	Departamento consultarCodigoDepartamento (Departamento departamento) throws BusinessException, DAOException;
}
