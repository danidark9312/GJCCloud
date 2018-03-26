package com.gja.gestionCasos.casos.repository;

import com.gja.gestionCasos.casos.entities.Correo;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface CorreoRepository {

	public Correo guardarCorreo(Correo correo) throws DAOException,BusinessException;
}
