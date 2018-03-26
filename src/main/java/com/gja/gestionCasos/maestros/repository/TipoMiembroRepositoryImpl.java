package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.TipoMiembro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("tipoMiembroRepository")
public class TipoMiembroRepositoryImpl extends AbstractRepository<TipoMiembro> implements TipoMiembroRepository{
	
	
	public List<TipoMiembro> obtenerTipoMiembro() throws DAOException,BusinessException{
		
		List<TipoMiembro> tiposMiembros=null;
		
		tiposMiembros=entityManager.createQuery("SELECT t FROM TipoMiembro t  ORDER BY t.nombre").getResultList();
		return tiposMiembros;
	}

}
