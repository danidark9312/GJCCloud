package com.gja.gestionCasos.maestros.repository;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("maestroEscalamientoNotificacionRepository")
public class MaestroEscalamientoNotificacionRepositoryImpl extends AbstractRepository<AlertaTareaRol> implements MaestroEscalamientoNotificacionRepository{
	private static final Logger LOG = Logger
			.getLogger(MaestroEscalamientoNotificacionRepositoryImpl.class);
	@Override
	public int eliminar( AlertaTareaRol alertaRol) throws DAOException {
		int resultado;
		try {
			resultado = entityManager.createNativeQuery("DELETE FROM alertas_tareas_rol WHERE CDTIPOCASO=:pTipocaso")
					.setParameter("pTipocaso", alertaRol.getTipoCaso()).executeUpdate();
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
	public AlertaTareaRol consultarConfiguracion(AlertaTareaRol alertaTareaRol) throws DAOException {
		
		try {
			alertaTareaRol = (AlertaTareaRol) entityManager.createQuery("SELECT A FROM AlertaTareaRol A WHERE A.tipoCaso = :pTipoCaso ")
					.setParameter("pTipoCaso", alertaTareaRol.getTipoCaso())
					.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			Log.error("Error; consultando la configuracion de las alertas de las tareas proximas a vencer");
		}
		return alertaTareaRol;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AlertaTareaRol> consultarNotificacionEnviar(int anio, int mes, int dia ) throws DAOException {
		List<AlertaTareaRol>  alertaTareaRolList = new ArrayList<AlertaTareaRol>();
			try {
			alertaTareaRolList = (List<AlertaTareaRol>) entityManager.createQuery("SELECT A FROM AlertaTareaRol A WHERE  EXTRACT( year  FROM A.fePrimeraAlerta )=:pAnio AND "
					+ " EXTRACT( month  FROM A.fePrimeraAlerta )=:pMes AND EXTRACT( day  FROM A.fePrimeraAlerta )=:pDia and A.estado='ACTIVO'  ")
					.setParameter("pAnio", anio)
					.setParameter("pMes", mes)
					.setParameter("pDia", dia)
					.getResultList();
		} catch (Exception e) {
			Log.error("Error; consultando la configuracion de las alertas de las tareas proximas a vencer");
		}
		return alertaTareaRolList;
	}
	
	
	@Transactional
	public Integer update(AlertaTareaRol alertaTareaRol) throws DAOException {
		int resultado;
		try {
			resultado = entityManager.createNativeQuery("update  alertas_tareas_rol set NMNOTIFICACIONESENVIADAS = :pTotal where cdalerta =:pcodigoAlerta")
					.setParameter("pTotal", alertaTareaRol.getTotalNotificacion())
					.setParameter("pcodigoAlerta", alertaTareaRol.getCodigo())
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
