package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.Parentesco;
import com.gja.gestionCasos.maestros.repository.ParentescoRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("parentescoService")
@Transactional(readOnly = true)
public class ParentescoServiceImpl implements ParentescoService {

	private static final Logger LOG = Logger
			.getLogger(ParentescoServiceImpl.class);
	
	@Autowired
	ParentescoRepository parentescoRepository; 
	@Transactional(readOnly = true)
	public List<Parentesco> obtenerParentesco() throws DAOException,BusinessException{
		
		List<Parentesco> parentescos=null;
		parentescos=parentescoRepository.obtenerParentesco();
		return parentescos;
	}
	
	public  List<Parentesco> obtenerCodigoParentesco(Parentesco parentesco) throws DAOException,
	BusinessException {
		 List<Parentesco> tipoCasoByPK = parentescoRepository.obtenerCodigoParentesco(parentesco);
		return tipoCasoByPK;
	}
}
