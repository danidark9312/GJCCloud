package com.gja.gestionCasos.maestros.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("maestroEscalamientoNotificacionVencidaRepository")
public class MaestroEscalamientoNotificacionVencidaRepositoryImpl extends AbstractRepository<AlertaTareaVencidaRol> implements MaestroEscalamientoNotificacionVencidaRepository{
	private static final Logger LOG = Logger
			.getLogger(MaestroEscalamientoNotificacionVencidaRepositoryImpl.class);
	@Override
	public int eliminar(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException {
		int resultado;
		try {
			resultado = entityManager.createNativeQuery("DELETE FROM alertas_tareas_vencida_rol WHERE CDTIPOCASO=:pCodigoTipoCaso ")
					.setParameter("pCodigoTipoCaso", alertaTareaVencidaRol.getTipoCaso())
					.executeUpdate();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error eliminando las notificaciones por rol "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
		return resultado;
	}
	
	@Override
	public AlertaTareaVencidaRol consultarConfiguracionVencida(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException {
		AlertaTareaVencidaRol alertaTareaRol = new AlertaTareaVencidaRol ();
		try {
			alertaTareaRol = (AlertaTareaVencidaRol) entityManager.createQuery("SELECT A FROM AlertaTareaVencidaRol A WHERE A.tipoCaso=:pCodigoTipoCaso")
					.setParameter("pCodigoTipoCaso", alertaTareaVencidaRol.getTipoCaso())
					.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			Log.error("Error; consultando la configuracion de las alertas de las tareas proximas a vencer");
		}
		return alertaTareaRol;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<AlertaTareaVencidaRol> consultarNotificacionEnviarVencida(int anio, int mes, int dia ) throws DAOException {
		List<AlertaTareaVencidaRol>  alertaTareaRolList = new ArrayList<AlertaTareaVencidaRol>();
			try {
			alertaTareaRolList = (List<AlertaTareaVencidaRol>) entityManager.createQuery("SELECT A FROM AlertaTareaVencidaRol A WHERE  EXTRACT( year  FROM A.feUltimaAlerta )=:pAnio AND "
					+ " EXTRACT( month  FROM A.feUltimaAlerta )=:pMes AND EXTRACT( day  FROM A.feUltimaAlerta )=:pDia AND A.estado='ACTIVO'  ")
					.setParameter("pAnio", anio)
					.setParameter("pMes", mes)
					.setParameter("pDia", dia)
					.getResultList();
		} catch (Exception e) {
			Log.error("Error; consultando la configuracion de las alertas de las tareas proximas a vencer");
		}
		return alertaTareaRolList;
	}
	
	
	public Integer update(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException {
		int resultado;
		try {
			resultado = entityManager.createNativeQuery("update  alertas_tareas_vencida_rol set NMTOTALOTIFICACION =:pTotal where cdalerta =:pcodigoAlerta")
					.setParameter("pTotal", alertaTareaVencidaRol.getTotalNotificacion())
					.setParameter("pcodigoAlerta", alertaTareaVencidaRol.getCodigo())
					.executeUpdate();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error eliminando las notificaciones por rol "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
		return resultado;
	}
}
