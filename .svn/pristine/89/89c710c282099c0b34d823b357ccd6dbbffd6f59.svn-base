package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.gja.gestionCasos.maestros.entities.EntidadFinanciera;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("entidadFinancieraRepository")
public class EntidadFinancieraRepositoryImpl extends AbstractRepository<EntidadFinanciera> implements EntidadFinancieraRepository{

	private static final Logger LOG = Logger
			.getLogger(EntidadFinancieraRepositoryImpl.class);
	public List<EntidadFinanciera> obtenerEntidadesFinancieras()
			throws DAOException, BusinessException {
			
		 List<EntidadFinanciera> entidadesFinancieras =entityManager.createQuery("SELECT eF FROM EntidadFinanciera eF ORDER BY eF.nombreEntidadfinanciera").getResultList();
		
		return entidadesFinancieras;
	}

}
