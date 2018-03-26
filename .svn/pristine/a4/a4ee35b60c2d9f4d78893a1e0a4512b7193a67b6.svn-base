package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.EstadoCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("estadoCasoRepository")
public class EstadoCasoRepositoryImpl extends AbstractRepository<EstadoCaso>implements EstadoCasoRepository {
	private static final Logger LOG = Logger.getLogger(TipoCasoRepositoryImpl.class);

	@SuppressWarnings("unchecked")
	public List<EstadoCaso> obtenerEstadoCaso() throws DAOException, BusinessException {
		List<EstadoCaso> estadoCaso = null;
		estadoCaso = entityManager.createQuery("SELECT e FROM EstadoCaso e  ORDER BY e.nombre").getResultList();
		return estadoCaso;
	}

	public Integer obtenerCodigoEstadoCaso(EstadoCaso estadoCaso) throws DAOException {
		Integer resultado;
		try {
			resultado = entityManager
					.createQuery("SELECT tc.codigo FROM EstadoCaso tc WHERE UPPER(tc.nombre) = :pNombreEstadoCaso",
							Integer.class)
					.setParameter("pNombreEstadoCaso", estadoCaso.getNombre().toUpperCase()).getSingleResult();

		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error buscando codigo del caso. El error es: " + e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		return resultado;
	}

}
