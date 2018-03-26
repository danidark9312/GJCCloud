package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.TipoMiembro;
import com.gja.gestionCasos.maestros.repository.TipoMiembroRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("tipoMiembroService")
@Transactional(readOnly = true)
public class TipoMiembroServiceImpl implements TipoMiembroService {

	private static final Logger LOG = Logger
			.getLogger(TipoMiembroServiceImpl.class);
	@Autowired
	TipoMiembroRepository tipoMiembroRepository;
	@Transactional(readOnly = true)
	public List<TipoMiembro> obtenerTipoMiembro() throws DAOException,BusinessException{
		List<TipoMiembro> tiposMiembros=null;
		tiposMiembros=tipoMiembroRepository.obtenerTipoMiembro();
		return tiposMiembros;
	}
	
}
