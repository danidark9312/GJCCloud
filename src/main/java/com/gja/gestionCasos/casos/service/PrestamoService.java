package com.gja.gestionCasos.casos.service;


import java.util.List;

import com.gja.gestionCasos.casos.entities.ParametroReportePrestamo;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.PrestamoReporte;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;



public interface PrestamoService {

	public Prestamo obtenerPrestamo(Prestamo  prestamo) throws DAOException,BusinessException;
	public Prestamo actualizarPrestamo(Prestamo prestamo) throws DAOException,BusinessException;
	List<PrestamoReporte> loadPrestamoReporte(ParametroReportePrestamo parametroReportePrestamo)
			throws DAOException, BusinessException;
}
