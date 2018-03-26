package com.gja.gestionCasos.casos.repository;

import java.util.List;

import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.entities.RadicadoPK;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface RadicadoRepository {

	public Radicado obtenerRadicado(RadicadoPK radicadoPK) throws DAOException,BusinessException;
	public Radicado guardarRadicado(Radicado radicado) throws DAOException,BusinessException;
	public void eliminarRadicado(Radicado radicado) throws DAOException,BusinessException;
	List<Radicado> findAll() throws DAOException, BusinessException;
	Radicado obtenerRadicado(String codigo) throws DAOException, BusinessException;
	
}
