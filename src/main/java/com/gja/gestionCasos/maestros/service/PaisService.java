package com.gja.gestionCasos.maestros.service;

import java.util.List;
import com.gja.gestionCasos.maestros.entities.Pais;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;



public interface PaisService {
	List<Pais> obtenerPaises()throws DAOException, BusinessException;
	Pais consultarCodigoPais(Pais pais) throws DAOException, BusinessException;
}
