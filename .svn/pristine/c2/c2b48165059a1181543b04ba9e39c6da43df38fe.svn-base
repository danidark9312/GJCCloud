package com.gja.gestionCasos.casos.repository;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.Telefono;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("telefonoRepository")
public class TelefonoRepositoryImpl extends AbstractRepository<Telefono> implements TelefonoRepository {

	public Telefono guardarTelefono(Telefono telefono) throws DAOException,
			BusinessException {
			
		telefono=this.merge(telefono);
		return telefono;
	}

}
