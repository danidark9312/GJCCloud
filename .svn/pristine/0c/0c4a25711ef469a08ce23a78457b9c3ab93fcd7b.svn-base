package com.gja.gestionCasos.actividades.repository;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.ResponsableTareaPK;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.social.signinmvc.common.controller.HomeController;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;


@Repository()
public class ResponsablesTareaRepositoryImpl extends AbstractRepository<ResponsableTarea> implements ResponsablesTareaRepository {
	private static final Logger LOG = Logger.getLogger(ResponsablesTareaRepositoryImpl.class);

	public void removerResponsableTarea(ResponsableTareaPK responsableTareaPK)
			throws DAOException, BusinessException {
		
		ResponsableTarea responsableTarea = entityManager.find(ResponsableTarea.class, responsableTareaPK);
		responsableTarea.setTareasParticularesCaso(null);
		responsableTarea.setCasosEquiposCaso(null);
		entityManager.remove(responsableTarea);
	}
	
	
	@Override
	public List<ResponsableTarea> consultarResponsablesPorCaso(CasoFiltro casoFiltro)
			throws DAOException, BusinessException {
			Query query = null;
		try {
			 query = entityManager.createQuery(crearQueryTareasPorEstado(casoFiltro));
			query.setParameter("pCodigoCaso", Integer.parseInt(casoFiltro.getCodigoCaso()));
			query.setParameter("pEstado", Parametros.getEstadoActivoTarea());
			if (casoFiltro != null && casoFiltro.getResponsablesFiltro()!= null && !casoFiltro.getResponsablesFiltro().isEmpty())
				query.setParameter("pEquipoCaso", Integer.parseInt(casoFiltro.getResponsablesFiltro().get(0)));			
		} catch (Exception e) {
			LOG.error("ERROR; Consultando las actividades de los responsable");
			e.printStackTrace();
		}
		return query.getResultList();
	}
	
	
	private String crearQueryTareasPorEstado(CasoFiltro casoFiltro) {
		StringBuilder strBuilder = new StringBuilder();
		try {
			strBuilder.append("SELECT DISTINCT a FROM ResponsableTarea a JOIN a.tareasParticularesCaso b ");
			strBuilder.append(" WHERE a.responsableTareaPK.codigoCaso = :pCodigoCaso ");
			strBuilder.append(" AND b.snActiva = :pEstado");
			if (casoFiltro != null && casoFiltro.getResponsablesFiltro()!= null && !casoFiltro.getResponsablesFiltro().isEmpty())
				strBuilder.append(" AND a.responsableTareaPK.codigoEquipoCaso = :pEquipoCaso ");			
			strBuilder.append(" ORDER BY a.responsableTareaPK.codigoEquipoCaso, b.fechaLimite asc ");
		} catch (Exception e) {
			LOG.error("Error; creando el sql de la consulta de las actividades de los responsables"); 
			e.printStackTrace();
		}
		return strBuilder.toString();
	}
	
	public void guardarResponsableTarea(ResponsableTarea responsableTarea) throws DAOException,BusinessException {
		entityManager.persist(responsableTarea);
		//return responsableTareaDB;
	}
	
}
