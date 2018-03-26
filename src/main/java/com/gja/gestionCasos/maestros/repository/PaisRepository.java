package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.Pais;
import com.sf.util.DAOException;

public interface PaisRepository {
	List<Pais> obtenerPaises()throws DAOException;
	Pais consultarCodigoPais(Pais pais) throws DAOException;
}
