package com.gja.gestionCasos.maestros.repository;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.sf.util.repository.AbstractRepository;

@Repository("maestroTipoCasoRepository")

public class MaestroTipoCasoRepositoryImpl extends AbstractRepository<Actividad> implements MaestroTipoCasoRepository{
	private static final Logger LOG = Logger
			.getLogger(MaestroTipoCasoRepositoryImpl.class);
	
	
	
}
