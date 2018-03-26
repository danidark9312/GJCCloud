package com.gja.gestionCasos.maestros.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.casos.repository.CasoRepository;
import com.gja.gestionCasos.filters.ActividadFiltro;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;


@Repository("maestroActividadRepository")

public class MaestroActividadRepositoryImpl extends AbstractRepository<Actividad> implements MaestroActividadesRepository{
	private static final Logger LOG = Logger
			.getLogger(MaestroActividadRepositoryImpl.class);
	
	
	public Long getCountFilter(ActividadFiltro actividad) throws DAOException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Actividad> clienteRoot = query.from(Actividad.class);
		query.select(builder.count(clienteRoot.<String> get("cdactividad")));
		List<Predicate> predicateList = getPredicateFiltro(actividad, builder, clienteRoot);
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		return entityManager.createQuery(query).getSingleResult();
	}
	
	public List<Actividad> getActividadByFilter(ActividadFiltro actividad)
			throws DAOException {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Actividad> query = builder.createQuery(Actividad.class);
		Root<Actividad> actividadRoot = query.from(Actividad.class);

		query.select(actividadRoot);
		
		List<Predicate> predicateList = getPredicateFiltro(actividad, builder, actividadRoot);
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		query.orderBy(builder.asc(actividadRoot.<String> get("dsactividad")));

		return entityManager.createQuery(query)
				.setMaxResults(actividad.getiDisplayLength())
				.setFirstResult(actividad.getiDisplayStart()).getResultList();
	}
	
	public List<Actividad> obtenerActividades() throws DAOException{
		List<Actividad> actividades = null; 
		try
		{
			actividades =entityManager.createQuery("SELECT a FROM Actividad a WHERE isactivo='S' ORDER BY a.dsactividad").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return actividades;
	}
	
	private List<Predicate> getPredicateFiltro(ActividadFiltro actividad,CriteriaBuilder builder,Root<Actividad> clienteRoot) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		Predicate nombrePredicate;
		
		if (actividad != null && actividad.getsSearch() != null
				&& !(actividad.getsSearch().isEmpty())) {
			nombrePredicate = builder.like(
					builder.upper(clienteRoot.<String> get("dsactividad")),
					"%" + actividad.getsSearch() + "%");
			predicateList.add(nombrePredicate);
		}
		
		if (actividad != null && actividad.getActivo() != null
				&& !(actividad.getActivo().isEmpty())) {
			nombrePredicate = builder.equal(
					builder.upper(clienteRoot.<String> get("isactivo")),
					actividad.getActivo());
			predicateList.add(nombrePredicate);
		}
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		return predicateList;
	}

	public Actividad findByPK(Actividad actividad) throws DAOException {
		Actividad actividadReturn;
		try {
			actividadReturn = entityManager.find(Actividad.class, actividad.getCdactividad());
			inicializarListas(actividadReturn);
			} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error consultando el caso por PK " + actividad.getCdactividad() + ". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Exception: Error consultando el caso por PK " + actividad.getCdactividad() + ". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		return actividadReturn;
	}
	
	@SuppressWarnings("unchecked")
	public boolean existNameActividad(String nombre) throws DAOException {
		boolean existe = false;
		Long resultado;
		try {
			resultado = entityManager.createQuery("SELECT COUNT(a.cdactividad) FROM Actividad a WHERE UPPER(a.dsactividad) = :pNombreActividad",Long.class)
					.setParameter("pNombreActividad", nombre.toUpperCase())
					.getSingleResult();
			
			if (resultado>0){
				existe=true;
			}
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error validando si existe el nombre de la actividad. El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		return existe;
	}
	
	
	public void inicializarListas(Actividad actividadReturn){
		Hibernate.initialize(actividadReturn.getActividadTipoCasoList());
		Hibernate.initialize(actividadReturn.getTareaActividadList());
		
		List<TareaActividad> listTarea = actividadReturn.getTareaActividadList();
		for(TareaActividad objTareaActividad: listTarea ){
//			Hibernate.initialize(objTareaActividad.getCdtarea());
//			Hibernate.initialize(objTareaActividad.getCdtarea().getDstarea());
			
		}
		
		
	}
}
