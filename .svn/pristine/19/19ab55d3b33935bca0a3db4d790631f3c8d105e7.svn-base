/**
 * 
 */
package com.gja.gestionCasos.actividades.repository;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.AlertaTarea;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author Usuario
 *
 */
public interface AlertaTareaRepository {
	
	public AlertaTarea getAlertaPorTarea(TareaActividad tareaActividad) throws BusinessException, DAOException;
	
	public List<AlertaTarea> getTareasPendientes() throws BusinessException, DAOException;
	
	public AlertaTarea guardarAlerta(AlertaTarea alertaTarea) throws BusinessException, DAOException;
}
