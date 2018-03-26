package com.gja.gestionCasos.casos.service;

import java.util.List;

import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.casos.entities.Caso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;



public interface BienAfectadoService {

	public List<String> guardarBienAfectado(List<String>  bienesAfectados) throws DAOException,BusinessException;
	public List<BienAfectado> asignarClavePrimaria(List<BienAfectado> bienesAfectados,Caso caso) throws DAOException,BusinessException;
	public BienAfectado eliminarBienAfectado(BienAfectado  bienAfectado) throws DAOException,BusinessException;
	public BienAfectado obtenerBienAfectado(BienAfectado  bienAfectado) throws DAOException,BusinessException;
	public BienAfectado actualizarBienAfectado(BienAfectado  bienAfectado) throws DAOException,BusinessException;
}
