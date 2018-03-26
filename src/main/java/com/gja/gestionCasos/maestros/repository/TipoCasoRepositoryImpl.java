package com.gja.gestionCasos.maestros.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.casos.repository.CasoRepository;
import com.gja.gestionCasos.filters.TipoCasoFiltro;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("tipoCasoRepository")

public class TipoCasoRepositoryImpl extends AbstractRepository<TipoCaso> implements TipoCasoRepository{
	
	private static final Logger LOG = Logger
			.getLogger(TipoCasoRepositoryImpl.class);

public List<TipoCaso> obtenerTipoCaso () throws DAOException, BusinessException{
		
	List<TipoCaso> tipoCaso=null;
		
		tipoCaso = entityManager.createQuery("SELECT t FROM TipoCaso t  ORDER BY t.nombre").getResultList();
		
		
		
		return tipoCaso; 
	}

public TipoCaso findByPK(TipoCaso tipoCaso) throws DAOException {
	TipoCaso tipoCasoReturn;
	try {
		tipoCasoReturn = entityManager.find(TipoCaso.class, tipoCaso.getCodigo());
		//inicializarListas(actividadReturn);
		} catch (IllegalStateException e) {
		LOG.error("IllegalStateException: Error consultando el tipo caso por PK " + tipoCaso.getCodigo() + ". El error es: "
						+ e.getMessage(), e);
		throw new DAOException(e.getMessage(), e);
	} catch (Exception e) {
		LOG.error("Exception: Error consultando el tipo caso por PK " + tipoCaso.getCodigo() + ". El error es: "
						+ e.getMessage(), e);
		throw new DAOException(e.getMessage(), e);
	}
	return tipoCasoReturn;
}


public Long getCountFilter(TipoCasoFiltro tipoCaso) throws DAOException {
	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	CriteriaQuery<Long> query = builder.createQuery(Long.class);
	Root<TipoCaso> clienteRoot = query.from(TipoCaso.class);
	query.select(builder.count(clienteRoot.<String> get("codigo")));
	List<Predicate> predicateList = getPredicateFiltro(tipoCaso, builder, clienteRoot);
	Predicate[] predicates = new Predicate[predicateList.size()];
	predicateList.toArray(predicates);
	query.where(predicates);

	return entityManager.createQuery(query).getSingleResult();
}

public List<TipoCaso> getTipoCasoByFilter(TipoCasoFiltro tipoCaso)
		throws DAOException {

	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	CriteriaQuery<TipoCaso> query = builder.createQuery(TipoCaso.class);
	Root<TipoCaso> tipoCasoRoot = query.from(TipoCaso.class);

	query.select(tipoCasoRoot);
	
	List<Predicate> predicateList = getPredicateFiltro(tipoCaso, builder, tipoCasoRoot);
	Predicate[] predicates = new Predicate[predicateList.size()];
	predicateList.toArray(predicates);
	query.where(predicates);

	query.orderBy(builder.asc(tipoCasoRoot.<String> get("nombre")));

	return entityManager.createQuery(query)
			.setMaxResults(tipoCaso.getiDisplayLength())
			.setFirstResult(tipoCaso.getiDisplayStart()).getResultList();
}
	

private List<Predicate> getPredicateFiltro(TipoCasoFiltro tipoCaso,CriteriaBuilder builder,Root<TipoCaso> clienteRoot) {
	List<Predicate> predicateList = new ArrayList<Predicate>();
	
	Predicate nombrePredicate;
	
	if (tipoCaso != null && tipoCaso.getsSearch() != null
			&& !(tipoCaso.getsSearch().isEmpty())) {
		nombrePredicate = builder.like(
				builder.upper(clienteRoot.<String> get("nombre")),
				"%" + tipoCaso.getsSearch() + "%");
		predicateList.add(nombrePredicate);
	}
	
	if (tipoCaso != null && tipoCaso.getActivo() != null
			&& !(tipoCaso.getActivo().isEmpty())) {
		nombrePredicate = builder.equal(
				builder.upper(clienteRoot.<String> get("activo")),
				tipoCaso.getActivo());
		predicateList.add(nombrePredicate);
	}
	
	Predicate[] predicates = new Predicate[predicateList.size()];
	predicateList.toArray(predicates);
	return predicateList;
}

@SuppressWarnings("unchecked")
public Long getCountByActividadTipoCasoActivo(Integer codigoActividad) throws DAOException {
	Long resultado;
	try {
		resultado = entityManager.createQuery("SELECT COUNT(t.codigo) FROM TipoCaso t INNER JOIN t.actividadTipoCasoList atc WHERE t.activo='S' and atc.cdactividad.cdactividad = :pCodigoActividad",Long.class)
				.setParameter("pCodigoActividad", codigoActividad)
				.getSingleResult();
	} catch (IllegalStateException e) {
		LOG.error(
				"IllegalStateException: Error consultando la cantidad de actividades de los tipos de casos activos. El error es: "
						+ e.getMessage(), e);
		throw new DAOException(e.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
	return resultado;
}
	
@SuppressWarnings("unchecked")
public boolean existNameTipoCaso(String nombre) throws DAOException {
	boolean existe=false;
	Long resultado;
	try {
		resultado = entityManager.createQuery("SELECT COUNT(tc.codigo) FROM TipoCaso tc WHERE UPPER(tc.nombre) = :pNombreTipoCaso",Long.class)
				.setParameter("pNombreTipoCaso", nombre.toUpperCase())
				.getSingleResult();
		
		if (resultado>0){
			existe=true;
		}
	} catch (IllegalStateException e) {
		LOG.error(
				"IllegalStateException: Error validando si existe el nombre del tipo de caso. El error es: "
						+ e.getMessage(), e);
		throw new DAOException(e.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
	return existe;
}
	

@SuppressWarnings("unchecked")
public Integer obtenerCodigoTipoCaso(TipoCaso nombreTipoCaso) throws DAOException {
	Integer resultado;
	try {
		resultado =  entityManager.createQuery("SELECT tc.codigo FROM TipoCaso tc WHERE UPPER(tc.nombre) = :pNombreTipoCaso",Integer.class)
				.setParameter("pNombreTipoCaso", nombreTipoCaso.getNombre().toUpperCase())
				.getSingleResult();

	} catch (IllegalStateException e) {
		LOG.error(
				"IllegalStateException: Error buscando codigo del caso. El error es: "
						+ e.getMessage(), e);
		throw new DAOException(e.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
	return resultado;
}




@SuppressWarnings("unchecked")
public TipoCaso obtenerActividades(TipoCaso tipoCaso) throws DAOException {
	TipoCaso resultado = null;
	try {
	
		resultado =  entityManager.createQuery("SELECT tc FROM TipoCaso tc  where tc.codigo = :pcodigoTipoCaso", TipoCaso.class)
				.setParameter("pcodigoTipoCaso", tipoCaso.getCodigo())
				.getSingleResult();

	} catch (IllegalStateException e) {
		LOG.error(
				"IllegalStateException: Error buscando las actividades del tipo caso. El error es: "
						+ e.getMessage(), e);
		
	} catch (Exception e) {
		resultado = null;
				
	}
	return resultado;
}


}
