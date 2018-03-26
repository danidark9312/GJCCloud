package com.gja.gestionCasos.actividades.repository;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.AlertaTareaParticular;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("alertaTareaParticularRepository")
public class AlertaTareaParticularRepositoryImpl extends AbstractRepository<AlertaTareaParticular> 
	implements AlertaTareaParticularRepository {

	
	public AlertaTareaParticular guardarAlerta(AlertaTareaParticular alertaTarea)
			throws DAOException, BusinessException {
		alertaTarea = merge(alertaTarea);
		return alertaTarea;
	}
	
	public AlertaTareaParticular obtenerAlertaPorTarea(TareaParticularCaso tarea)
			throws DAOException, BusinessException {
		AlertaTareaParticular alertaTarea = null;
		try {
			Object object = entityManager.createQuery("SELECT a FROM AlertaTareaParticular a WHERE a.tareaParticularCaso.codigoTarea = :pCodigoTarea")
					.setParameter("pCodigoTarea", tarea.getCodigoTarea()).getSingleResult();
			alertaTarea = (AlertaTareaParticular)object;
		} catch (NoResultException e) {
			return null;
		}
		return alertaTarea;
	}
}
