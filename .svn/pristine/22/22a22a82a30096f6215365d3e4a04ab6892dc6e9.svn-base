package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.ClaseBien;
import com.gja.gestionCasos.maestros.entities.Departamento;
import com.gja.gestionCasos.maestros.repository.ClaseBienRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/*ClaseBienServiceImpl: Clase para realizar los servicios o logica del bloque clase bienes
* @author: Cristian Mauricio Arenas-Soluciones Futuras  
* @version: Nuevo Caso
* @since: 1.0 
* @FechaCreacion: 14/04/2015 */


@Service("claseBienService")
@Transactional(readOnly = true)
public class ClaseBienServiceImpl implements ClaseBienService {

	private static final Logger LOG = Logger.getLogger(PaisServiceImpl.class);	
	@Autowired
	ClaseBienRepository claseBienRepository; 
	public List<ClaseBien> obtenerClaseBien() throws DAOException,BusinessException
	{
		List<ClaseBien> clasesBienes=null;
		clasesBienes = claseBienRepository.obtenerClaseBien();
		return clasesBienes;
	}
	

	public ClaseBien  consultarCodigoClaseBien(ClaseBien claseBien) throws DAOException, BusinessException{
		ClaseBien claseBienByPK = null;
		claseBienByPK =  claseBienRepository.consultarCodigoClaseBien(claseBien);
		return claseBienByPK;
	}

	
	
}
