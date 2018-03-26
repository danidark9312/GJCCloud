package com.gja.gestionCasos.maestros.repository;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;
import com.gja.gestionCasos.maestros.entities.AlertasTareasRolDetalle;
import com.gja.gestionCasos.maestros.entities.AlertasTareasVencidaRolDetalle;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;
@Repository("alertaTareaRolDetalleVencidaRepository")
public class AlertaTareaRolDetalleVencidaRepositoryImpl extends AbstractRepository<AlertasTareasVencidaRolDetalle> implements AlertaTareaRolDetalleVencidaRepository{
	private static final Logger LOG = Logger
			.getLogger(AlertaTareaRolDetalleVencidaRepositoryImpl.class);
	@Override
	public Integer eliminar(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException {
		Integer resultado;
		try {
			resultado = entityManager.createNativeQuery("DELETE FROM alertas_tareas_vencida_rol_detalle WHERE CDALERTA IN (SELECT CDALERTA FROM alertas_tareas_vencida_rol WHERE CDTIPOCASO=:pCodigoTipoCaso)")
					.setParameter("pCodigoTipoCaso", alertaTareaVencidaRol.getTipoCaso())
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
