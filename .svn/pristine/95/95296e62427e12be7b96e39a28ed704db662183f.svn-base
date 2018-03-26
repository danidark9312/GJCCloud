package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.gja.gestionCasos.maestros.repository.TipoDocumentoRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("tipoDocumentoService")
@Transactional(readOnly = true)
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository; 
	
	
	@Transactional(readOnly = true)
	public List<TipoDocumento> obtenerTiposDeDocumentos() throws DAOException,BusinessException{
		return tipoDocumentoRepository.obtenerTiposDeDocumentos();
	}
	
	public  TipoDocumento obtenerCodigoTipoDocumento(TipoDocumento tipoDocumento) throws DAOException,BusinessException {
		TipoDocumento tipoCasoByPK = tipoDocumentoRepository.obtenerCodigoTipoDocumento(tipoDocumento);
		return tipoCasoByPK;
	}

}
