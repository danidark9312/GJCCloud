package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.gja.gestionCasos.maestros.entities.Departamento;
import com.gja.gestionCasos.maestros.repository.CiudadRepositoryImpl;

import com.sf.util.BusinessException;
import com.sf.util.DAOException;


@Service("CiudadService")
@Transactional(readOnly = true)

public class CiudadServiceImpl implements CiudadService {
	private static final Logger LOG = Logger
			.getLogger(CiudadServiceImpl.class);

	@Autowired
	private CiudadRepositoryImpl ciudadRepository;


	public List<Ciudad> ciudadPorDepartamento(Departamento departamento)
			throws DAOException, BusinessException {
		List<Ciudad> ciudades = null;

		ciudades = ciudadRepository.ciudadPorDepartamento(departamento);

		return ciudades;
	}

	public List<Ciudad>  consultarCodigoCiudad(Ciudad ciudad) throws DAOException{
		List<Ciudad> ciudadByPK = null;
		ciudadByPK =  ciudadRepository.consultarCodigoCiudad(ciudad);
		return ciudadByPK;
	}


}
