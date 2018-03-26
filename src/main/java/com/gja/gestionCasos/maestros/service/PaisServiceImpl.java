package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.gja.gestionCasos.maestros.entities.Pais;
import com.gja.gestionCasos.maestros.repository.PaisRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;


@Service("paisService")
@Transactional(readOnly = true)

public class PaisServiceImpl implements PaisService {

	private static final Logger LOG = Logger
			.getLogger(PaisServiceImpl.class);
	@Autowired
	PaisRepositoryImpl paisRepository;

	public List<Pais> obtenerPaises() throws DAOException, BusinessException {
		List<Pais> paises = null;

		try{
			paises = paisRepository.obtenerPaises();
		}
		catch(Exception e)
		{
			LOG.error(
					"Exception in Service: Error consultando todos los paises. El error es: "
							+ e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
	
		}
		return paises;

	}
	
	
	
	public Pais  consultarCodigoPais(Pais pais) throws DAOException{
		Pais paisByPK = null;
		paisByPK =  paisRepository.consultarCodigoPais(pais);
		return paisByPK;
	}
}
