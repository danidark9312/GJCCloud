package com.gja.gestionCasos.actividades.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.actividades.entities.Archivo;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.ActividadCasoRepository;
import com.gja.gestionCasos.actividades.repository.ArchivoRepositoryImpl;
import com.gja.gestionCasos.actividades.repository.TareaParticularCasoRepository;
import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.repository.CasoRepositoryImpl;
import com.gja.gestionCasos.casos.repository.EquipoCasoRepositoryImpl;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.maestros.repository.ActividadTipoCasoRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Service("archivoService")
@Transactional(readOnly = true)
public class ArchivoServiceImpl implements ArchivoService {

	
	@Autowired
	ArchivoRepositoryImpl archivoRepository;
	
	public Archivo guardarArchivo(Archivo archivo) throws DAOException,
			BusinessException {			
		return archivoRepository.merge(archivo);
	
	}
	
	public List<Archivo> findByPK(Archivo archivo) throws DAOException{
		List<Archivo> casoFindByPK = null;
		casoFindByPK = archivoRepository.findByPK(archivo);
		return casoFindByPK;
	}

	
}
