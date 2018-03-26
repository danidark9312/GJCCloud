package com.gja.gestionCasos.maestros.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gja.gestionCasos.maestros.entities.TipoCuenta;
import com.gja.gestionCasos.maestros.repository.TipoCuentaRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("tipoCuentaService")
@Transactional(readOnly = true)
public class TipoCuentaServiceImpl implements TipoCuentaService{

	private static final Logger LOG = Logger
			.getLogger(TipoCuentaServiceImpl.class);
	
	@Autowired
	TipoCuentaRepository tipoCuentaRepository;
	@Transactional
	public List<TipoCuenta> obtenerTipoCuenta() throws DAOException,
			BusinessException {
		List<TipoCuenta> tiposCuenta = tipoCuentaRepository.obtenerTipoCuenta();
		return tiposCuenta;
	}
	
	

}
