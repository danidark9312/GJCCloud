package com.gja.gestionCasos.casos.repository;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.Correo;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;


@Repository("correoRepository")
public class CorreoRepositoryImpl extends AbstractRepository<Correo> implements  CorreoRepository {

	public Correo guardarCorreo(Correo correo) throws DAOException,
			BusinessException {
		
		correo=this.merge(correo);
		return correo;
	}

}
