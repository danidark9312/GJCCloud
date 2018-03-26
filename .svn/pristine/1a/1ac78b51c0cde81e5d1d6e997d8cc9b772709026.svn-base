package com.gja.gestionCasos.maestros.repository;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertasTareasRolDetalle;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;
@Repository("alertaTareaRolDetalleRepository")
public class AlertaTareaRolDetalleRepositoryImpl extends AbstractRepository<AlertasTareasRolDetalle> implements AlertaTareaRolDetalleRepository{
	private static final Logger LOG = Logger
			.getLogger(AlertaTareaRolDetalleRepositoryImpl.class);
	@Override
	public Integer eliminar(AlertaTareaRol alertaRol) throws DAOException {
		Integer resultado;
		try {
			resultado = entityManager.createNativeQuery("DELETE FROM alertas_tareas_rol_detalle WHERE CDALERTA IN(SELECT CDALERTA FROM  alertas_tareas_rol WHERE CDTIPOCASO=:pTipoCaso)")
					.setParameter("pTipoCaso", alertaRol.getTipoCaso())
					.executeUpdate();
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error eliminando el detalle de las alertas por roles "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
		return resultado;
	
	}

}
