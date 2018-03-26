package com.gja.gestionCasos.casos.service;

import java.util.List;

import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.entities.RadicadoPK;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface RadicadoService {
	public Radicado obtenerRadicado(RadicadoPK radicadoPK) throws DAOException,BusinessException;
	public List<Radicado> modificarRadicados(List<Radicado> radicados, String numeroRadicadoActualizar) throws DAOException,BusinessException;
	public Radicado eliminarRadicado(Radicado radicado) throws DAOException,BusinessException;
	List<Radicado> findAll() throws DAOException, BusinessException;
	Radicado guardarRadicado(Radicado radicado) throws DAOException, BusinessException;
	Radicado obtenerRadicado(String codigoRadicado) throws DAOException, BusinessException;

}
