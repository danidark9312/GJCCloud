package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.casos.repository.CasoRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("actividadTipoCasoRepository")
public class ActividadTipoCasoRepositoryImpl extends AbstractRepository<ActividadTipoCaso> implements
		ActividadTipoCasoRepository {
	
	private static final Logger LOG = Logger
			.getLogger(ActividadTipoCasoRepositoryImpl.class);
	
	@SuppressWarnings("unchecked")
	public int deleteByTipoCaso(Integer codigoTipoCaso) throws DAOException {
		int resultado;
		try {
			resultado = entityManager.createQuery("DELETE FROM ActividadTipoCaso atc WHERE atc.cdtipocaso.codigo = :pCodigo").setParameter("pCodigo", codigoTipoCaso).executeUpdate();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error eliminando las actividades de tipos de casos por codigo tipo de caso "+codigoTipoCaso+". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public Long getCountByActividad(Integer codigoActividad) throws DAOException {
		Long resultado;
		try {
			resultado = entityManager.createQuery("SELECT COUNT(atc) FROM ActividadTipoCaso atc WHERE atc.cdactividad.cdactividad = :pCodigoActividad",Long.class)
					.setParameter("pCodigoActividad", codigoActividad)
					.getSingleResult();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error consultando la cantidad de actividades tipos de casos filtradas por codigo actividad. El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
		return resultado;
	}

	public List<ActividadTipoCaso> obtenerActividadesTipoCaso(Integer codigoTipoCaso) throws DAOException, BusinessException {
		
		List<ActividadTipoCaso> actividadesTipoCaso=null;
		
		actividadesTipoCaso = entityManager.createQuery("SELECT atc FROM ActividadTipoCaso atc WHERE atc.cdtipocaso.codigo = :pCodigoTipoCaso").setParameter("pCodigoTipoCaso", codigoTipoCaso)
				.getResultList();
		
		
		return actividadesTipoCaso;
	}

}
