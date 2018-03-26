package com.gja.gestionCasos.maestros.repository;

import com.sf.roles.RolAccionOpcion;
import com.sf.util.DAOException;

public interface RolAccionOpcionRepository {
	RolAccionOpcion merge(RolAccionOpcion rolAccionOpcion) throws DAOException;
}
