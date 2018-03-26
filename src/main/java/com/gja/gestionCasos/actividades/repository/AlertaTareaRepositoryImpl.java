/**
 * 
 */
package com.gja.gestionCasos.actividades.repository;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.AlertaTarea;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;

/**
 * @author Usuario
 *
 */
@Repository("AlertaTareaRepository")
public class AlertaTareaRepositoryImpl extends AbstractRepository<AlertaTarea> implements AlertaTareaRepository {

	public AlertaTarea guardarAlerta(AlertaTarea alertaTarea) throws BusinessException, DAOException {
		alertaTarea = merge(alertaTarea);
		
		return alertaTarea;
	}
	
	public AlertaTarea getAlertaPorTarea(TareaActividad tareaActividad)
			throws BusinessException, DAOException {
		AlertaTarea alertaTarea = null;

//		Object object = entityManager.createQuery("SELECT a FROM AlertaTarea a WHERE a.tareaParticularCaso.codigoTarea = :pCodigoTarea")
		Object object = entityManager.createQuery("SELECT a FROM AlertaTarea a WHERE a.alertaTareaCaso.codigoTarea = :pCodigoTarea")
				.setParameter("pCodigoTarea", tareaActividad.getCdactividad()).getSingleResult();
//				.setParameter("pCodigoTarea", tarea.getCodigoTarea()).getSingleResult();
		alertaTarea = (AlertaTarea) object;

		return alertaTarea;
	}

	@SuppressWarnings("unchecked")
	public List<AlertaTarea> getTareasPendientes() throws BusinessException, DAOException {
		Calendar fechaActual = Calendar.getInstance();
		Date fecha = new Date();
		
		fechaActual.set(Calendar.HOUR_OF_DAY, 0);
		fechaActual.set(Calendar.MINUTE, 0);
		fechaActual.set(Calendar.SECOND, 0);
		fecha = fechaActual.getTime();
		List<AlertaTarea> tareasPendientes = entityManager.createQuery("SELECT a FROM AlertaTarea a WHERE a.finalizada = :pPendiente AND "
				+ "a.fechaLimite > :pFechaActual")
				.setParameter("pPendiente", Parametros.getActividadPendiente())
				.setParameter("pFechaActual", fecha)
				.getResultList();
		
		return tareasPendientes;
	}

}
