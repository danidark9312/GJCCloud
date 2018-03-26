package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.TipoCuenta;

import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("tipoCuentaRepository")
public class TipoCuentaRepositoryImpl extends AbstractRepository<TipoCuenta> implements TipoCuentaRepository{

	public List<TipoCuenta> obtenerTipoCuenta() throws BusinessException,
			DAOException {
		
		List<TipoCuenta> tipoCuenta = null;
		tipoCuenta = entityManager.createQuery("SELECT t FROM TipoCuenta t  ORDER BY t.codigoTipoCuenta").getResultList();
		return tipoCuenta;
	}

}
