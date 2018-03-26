package com.gja.gestionCasos.casos.repository;



import java.util.List;

import com.gja.gestionCasos.casos.entities.ParametroReportePrestamo;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.PrestamoConsolidadoView;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface PrestamoRepository {

	public Prestamo guardarPrestamo(Prestamo prestamo) throws DAOException,BusinessException;
	Prestamo obtenerPrestamo(Prestamo prestamo) throws DAOException, BusinessException;
	List<PrestamoConsolidadoView> loadPrestamoReporte(ParametroReportePrestamo parametroReportePrestamo) throws DAOException, BusinessException;
}
