package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.EstadoCaso;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.gja.gestionCasos.maestros.repository.EstadoCasoRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;


@Service("estadoCasoService")
@Transactional(readOnly = true)
public class EstadoCasoServiceImpl implements EstadoCasoService{

	private static final Logger LOG = Logger
			.getLogger(PaisServiceImpl.class);
	
	@Autowired
	EstadoCasoRepositoryImpl estadoCasoRepository;
	
	public List<EstadoCaso> obtenerEstadoCaso() throws DAOException,BusinessException
	{
		List<EstadoCaso> estadoCaso=null;
		estadoCaso=estadoCasoRepository.obtenerEstadoCaso();
		return estadoCaso;
		
	}
	
	
	public Integer consultarCodigoEstadoCaso(EstadoCaso estadoCaso) throws DAOException,
	BusinessException {
		Integer tipoCasoByPK = estadoCasoRepository.obtenerCodigoEstadoCaso(estadoCaso);
		return tipoCasoByPK;
	}
	
}
