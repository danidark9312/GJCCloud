package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.TipoMiembro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface TipoMiembroRepository {

	public List<TipoMiembro> obtenerTipoMiembro() throws DAOException,BusinessException;
}
