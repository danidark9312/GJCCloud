package com.gja.gestionCasos.maestros.repository;

import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertasTareasRolDetalle;
import com.sf.util.DAOException;

public interface AlertaTareaRolDetalleRepository {

	public AlertasTareasRolDetalle merge(AlertasTareasRolDetalle alertasTareasRolDetalle) throws DAOException;
	public Integer eliminar(AlertaTareaRol alertaRol) throws DAOException;
}
