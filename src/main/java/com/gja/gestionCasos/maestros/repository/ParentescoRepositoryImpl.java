package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.Parentesco;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("parentescoRepository")
public class ParentescoRepositoryImpl extends AbstractRepository<Parentesco> implements ParentescoRepository{
	private static final Logger LOG = Logger
			.getLogger(TipoCasoRepositoryImpl.class);
	
	public List<Parentesco> obtenerParentesco() throws DAOException,BusinessException{
		
		List<Parentesco> parentescos=null;
		
		parentescos=entityManager.createQuery("SELECT p FROM Parentesco p  ORDER BY p.nombre").getResultList();
		return parentescos;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Parentesco>  obtenerCodigoParentesco(Parentesco parentesco) throws DAOException {
		List<Parentesco>  resultado;
		try {
			resultado =  entityManager.createQuery("SELECT pt FROM Parentesco pt WHERE UPPER(pt.nombre) = :pNombreTipoCaso")
					.setParameter("pNombreTipoCaso", parentesco.getNombre().toUpperCase())
					.getResultList();

		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error buscando codigo del caso. El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		return resultado;
	}

}
