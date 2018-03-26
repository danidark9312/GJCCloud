package com.gja.gestionCasos.casos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.casos.entities.Abono;
import com.gja.gestionCasos.casos.entities.ArchivoAbono;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.repository.AbonoRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("abonoService")
@Transactional(readOnly = true)
public class AbonoServiceImpl implements AbonoService {
	
	@Autowired
	AbonoRepositoryImpl abonoRepositoryImpl;
	

	@Transactional
	public Abono guardarAbono(Abono abono) throws DAOException, BusinessException{
		return  abonoRepositoryImpl.guardarAbono(abono);
		
	}
	@Transactional
	public ArchivoAbono guardarArchivoAbono(ArchivoAbono archivoAbono) throws DAOException, BusinessException{
		return  abonoRepositoryImpl.guardarArchivoAbono(archivoAbono);
		
	}
	
	public List<Abono> findAbonosByPrestamo(Prestamo prestamo) throws DAOException, BusinessException{
		return  abonoRepositoryImpl.findAbonosByPrestamo(prestamo);
		
	}
	
	public Double getTotalPagado(Prestamo prestamo) throws DAOException, BusinessException{
		return abonoRepositoryImpl.getTotalPagado(prestamo);
		
	}
	public Double getInteresesPagados(Prestamo prestamo) throws DAOException, BusinessException{
		return abonoRepositoryImpl.getInteresesPagados(prestamo);
		
	}
	
	@Transactional
	public void deleteAbono(Abono abono) throws DAOException, BusinessException{
		abonoRepositoryImpl.deleteAbono(abono);
	}
	public Abono findUltimoAbono(Prestamo prestamo) throws DAOException, BusinessException{
		return  abonoRepositoryImpl.findUltimoAbono(prestamo);
		
	}


	
}
