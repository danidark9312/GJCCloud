package com.gja.gestionCasos.casos.repository;



import java.util.List;

import com.gja.gestionCasos.casos.entities.Abono;
import com.gja.gestionCasos.casos.entities.ArchivoAbono;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface AbonoRepository {

	public Abono guardarAbono(Abono abono) throws DAOException,BusinessException;

	List<Abono> findAbonosByPrestamo(Prestamo prestamo) throws DAOException, BusinessException;

	Abono findUltimoAbono(Prestamo prestamo) throws DAOException, BusinessException;

	void deleteAbono(Abono abono) throws DAOException, BusinessException;

	Double getTotalPagado(Prestamo prestamo) throws DAOException, BusinessException;

	Double getInteresesPagados(Prestamo prestamo) throws DAOException, BusinessException;

	ArchivoAbono guardarArchivoAbono(ArchivoAbono archivoAbono) throws DAOException, BusinessException;
}
