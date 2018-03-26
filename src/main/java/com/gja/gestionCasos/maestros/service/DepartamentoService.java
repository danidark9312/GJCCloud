package com.gja.gestionCasos.maestros.service;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.Departamento;
import com.gja.gestionCasos.maestros.entities.Pais;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;


public interface DepartamentoService {

	List<Departamento> departamentosPorPais(Pais pais)throws DAOException, BusinessException;
	Departamento consultarCodigoDepartamento (Departamento departamento) throws DAOException, BusinessException;
}
