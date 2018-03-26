package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.ClaseBien;
import com.gja.gestionCasos.maestros.entities.Instancia;
import com.gja.gestionCasos.maestros.repository.InstanciaRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;


@Service("instanciaService")
@Transactional(readOnly = true)
public class InstanciaServiceImpl implements InstanciaService {

	private static final Logger LOG = Logger
			.getLogger(InstanciaServiceImpl.class);
	
	@Autowired
	InstanciaRepository instanciaRepository;
	
	public List<Instancia> obtenerInstancia() throws DAOException,BusinessException
	{
		List<Instancia> instancias=null;
		instancias=instanciaRepository.obtenerInstancia();		
		return instancias;
	}
	
	public Instancia  consultarCodigoInstancia(Instancia instancia) throws DAOException, BusinessException{
		Instancia instanciaByPK = null;
		instanciaByPK =  instanciaRepository.consultarCodigoInstancia(instancia);
		return instanciaByPK;
	}

	
}
