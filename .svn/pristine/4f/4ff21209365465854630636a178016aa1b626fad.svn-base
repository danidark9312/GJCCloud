package com.gja.gestionCasos.reportes.service;

import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.repository.TareaParticularCasoRepository;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.repository.CasoRepository;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("reportesService")
@Transactional(readOnly = true)
public class ReportesServiceImpl implements ReportesService {

	@Autowired
	private CasoRepository casoRepository;
	
	@Autowired
	private TareaParticularCasoRepository tareaParticularCasoRepository;
	
	
	public List<Caso> encontrarCasoPorFiltro(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		List<Caso> casos = null;
		casos = casoRepository.obtenerCasosAudiencias(casoFiltro);
		for (Caso caso: casos) {
			Hibernate.initialize(caso.getRadicadoSet());
			Hibernate.initialize(caso.getCasoEquipoCasoSet());
			for (CasoEquipoCaso casoEquipoCaso:caso.getCasoEquipoCasoSet() ) {
				Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getTelefonoList());
				Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getCelularList());
				Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getCorreoList());
				Hibernate.initialize(casoEquipoCaso.getResponsablesTareas());
				for (ResponsableTarea reponsable:casoEquipoCaso.getResponsablesTareas()) {
					Hibernate.initialize(reponsable.getTareasParticularesCaso().getResponsablesTareas());
					Hibernate.initialize(reponsable.getTareasParticularesCaso().getActividadParticular());
				}
			}
			
		}
		return casos;
	}
	
	public Long getCountFilter(CasoFiltro caso) throws DAOException, BusinessException {
		Long  cantidad = null;
		cantidad = casoRepository.getCountAudiencias(caso);
		return cantidad;
	}
	
	public List<Object[]> obtenerAudienciasExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		List<Object[]> audiencias = casoRepository.obtenerAudienciasExcel(casoFiltro);
		return audiencias;
	}
	
	public List<Object[]> obtenerCaducidadCasos(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		List<Object[]> casos = null;
		casos = casoRepository.obtenerCaducidadCasos(casoFiltro);		
		return casos;
	}
	
	public List<Object[]> obtenerCaducidadCasosExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		List<Object[]> casos = null;
		casos = casoRepository.obtenerCaducidadCasosExcel(casoFiltro);
		return casos;
	}
	
	public Long getCountCaducidadCasos(CasoFiltro caso) throws DAOException, BusinessException {
		Long  cantidad = null;
		cantidad =  casoRepository.getCountCaducidadCasos(caso);
		return cantidad;
	}
	
	public Long getCountDocumentosPendientes(CasoFiltro caso) throws DAOException, BusinessException {
		Long  cantidad = null;
		cantidad =  casoRepository.getCountDocumentosPendientes(caso);
		return cantidad;
	}
	
	public List<Object[]> obtenerDocumentosPendientes(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		List<Object[]> casos = null;
		casos = casoRepository.obtenerDocumentosPendientes(casoFiltro);
		return casos;
	}
	
	public List<Object[]> obtenerDocumentosPendientesExcel(CasoFiltro casoFiltro)
			throws DAOException, BusinessException {
		List<Object[]> casos = null;
		casos = casoRepository.obtenerDocumentosPendientesExcel(casoFiltro);
		return casos;
	}
	
	@Override
	public Long getCountTareasProximoVencimiento(CasoFiltro casoFiltro, String nombreCaso) throws DAOException, BusinessException {
		return tareaParticularCasoRepository.getCountTareasProximoVencimiento(casoFiltro, nombreCaso);
	}
	
	@Override
	public List<Object[]> getTareasProximoVencimiento(CasoFiltro casoFiltro, String nombreCaso) throws DAOException, BusinessException {
		return tareaParticularCasoRepository.getTareasProximoVencimiento(casoFiltro, nombreCaso);
	}
}