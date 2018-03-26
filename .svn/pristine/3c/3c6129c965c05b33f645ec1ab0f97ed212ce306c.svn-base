package com.gja.gestionCasos.casos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.ResponsableTareaPK;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.ResponsablesTareaRepository;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service()
public class ResponsablesTareaServiceImpl implements ResponsablesTareaService {

	@Autowired
	ResponsablesTareaRepository responsablesTareaRepository;
	
	
	@Transactional
	public void removerResponsableTarea(ResponsableTareaPK responsableTareaPK)
			throws DAOException, BusinessException {
		responsablesTareaRepository.removerResponsableTarea(responsableTareaPK);
	}
	
	
	@Override
	public List<ResponsableTarea> consultarResponsablesPorCaso(CasoFiltro casoFiltro)
			throws DAOException, BusinessException {
		return responsablesTareaRepository.consultarResponsablesPorCaso(casoFiltro);
	}
	
	@Transactional
	public void guardarResponsableTarea(ResponsableTarea responsableTarea) throws DAOException, BusinessException {
		/*ResponsableTarea guardarResponsableTarea = */responsablesTareaRepository.guardarResponsableTarea(responsableTarea);
		// return guardarResponsableTarea;
	}
}
