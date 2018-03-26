package com.gja.gestionCasos.reportes.service;

import java.util.List;

import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface ReportesService {

	public List<Caso> encontrarCasoPorFiltro(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public Long getCountFilter(CasoFiltro caso) throws DAOException, BusinessException;
	public List<Object[]> obtenerAudienciasExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public List<Object[]> obtenerCaducidadCasos(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public List<Object[]> obtenerCaducidadCasosExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public Long getCountCaducidadCasos(CasoFiltro caso) throws DAOException, BusinessException;
	public List<Object[]> obtenerDocumentosPendientes(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public List<Object[]> obtenerDocumentosPendientesExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public Long getCountDocumentosPendientes(CasoFiltro caso) throws DAOException, BusinessException;
	public Long getCountTareasProximoVencimiento(CasoFiltro caso, String sSearch) throws DAOException, BusinessException;
	public List<Object[]> getTareasProximoVencimiento(CasoFiltro casoFiltro, String sSearch) throws DAOException, BusinessException;
}
