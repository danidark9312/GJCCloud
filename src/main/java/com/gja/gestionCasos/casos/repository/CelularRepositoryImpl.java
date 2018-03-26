package com.gja.gestionCasos.casos.repository;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.Celular;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("celularRepository")
public class CelularRepositoryImpl extends AbstractRepository<Celular> implements CelularRepository{

	public Celular guardarCelular(Celular celular) throws DAOException,
			BusinessException {
		celular=this.merge(celular);
	
		return celular;
	}

}
