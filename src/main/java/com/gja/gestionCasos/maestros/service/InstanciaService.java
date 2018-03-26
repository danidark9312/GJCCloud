package com.gja.gestionCasos.maestros.service;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.Instancia;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface InstanciaService {

	public List<Instancia> obtenerInstancia() throws DAOException,BusinessException;
	Instancia   consultarCodigoInstancia (Instancia instancia)  throws DAOException,BusinessException;
}
