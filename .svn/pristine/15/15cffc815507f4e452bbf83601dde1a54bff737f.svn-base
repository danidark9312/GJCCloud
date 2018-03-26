package com.gja.gestionCasos.casos.service;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.ResponsableTareaPK;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface ResponsablesTareaService {
	void removerResponsableTarea(ResponsableTareaPK responsableTareaPK) throws DAOException,BusinessException;
	public List<ResponsableTarea> consultarResponsablesPorCaso(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	void guardarResponsableTarea(ResponsableTarea responsableTarea) throws DAOException, BusinessException;
}
