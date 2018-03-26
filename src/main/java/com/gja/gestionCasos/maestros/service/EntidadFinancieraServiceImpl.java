package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.EntidadFinanciera;
import com.gja.gestionCasos.maestros.repository.EntidadFinancieraRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Service("EntidadFinancieraService")
@Transactional(readOnly = true)
public class EntidadFinancieraServiceImpl extends AbstractRepository<EntidadFinanciera> implements EntidadFinancieraService{

	@Autowired
	EntidadFinancieraRepository entidadFinancieraRepository;
	public List<EntidadFinanciera> obtenerEntidadesFinancieras()
			throws DAOException, BusinessException {
		
		List<EntidadFinanciera>  entidadesFinancieras=entidadFinancieraRepository.obtenerEntidadesFinancieras();
		return entidadesFinancieras;
	}

}
