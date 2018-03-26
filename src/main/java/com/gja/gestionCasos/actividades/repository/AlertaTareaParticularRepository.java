package com.gja.gestionCasos.actividades.repository;

import com.gja.gestionCasos.actividades.entities.AlertaTareaParticular;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface AlertaTareaParticularRepository {
	public AlertaTareaParticular obtenerAlertaPorTarea(TareaParticularCaso tarea) throws DAOException,BusinessException;
	public AlertaTareaParticular guardarAlerta(AlertaTareaParticular alertaTarea) throws DAOException,BusinessException;
}
