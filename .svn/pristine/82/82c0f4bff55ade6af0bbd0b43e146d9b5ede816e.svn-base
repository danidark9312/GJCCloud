package com.gja.gestionCasos.actividades.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.maestros.repository.TipoCasoRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;

@Repository("areaParticularCasoRepository")
public class TareaParticularCasoRepositoryImpl extends AbstractRepository<TareaParticularCaso> implements TareaParticularCasoRepository {
	private static final Logger LOG = Logger
			.getLogger(TipoCasoRepositoryImpl.class);
	
	public TareaParticularCaso consultarTareaParticular(Integer codigoTarea)
			throws DAOException, BusinessException {
		TareaParticularCaso tareaParticularCaso;
		tareaParticularCaso = entityManager.find(TareaParticularCaso.class, codigoTarea);
		return tareaParticularCaso;
	}

	public TareaParticularCaso guardarTareaParticularCaso(TareaParticularCaso tareaParticularCaso) throws DAOException,
			BusinessException {
		tareaParticularCaso = entityManager.merge(tareaParticularCaso);
		return tareaParticularCaso;
	}

	@SuppressWarnings("unchecked")
	public List<TareaActividad>  obtenerCodigoTareaActividad(TareaActividad tareaActividades) throws DAOException {
		List<TareaActividad>  resultado;
		try {
			resultado =  entityManager.createQuery("SELECT ta FROM TareaActividad ta WHERE ta.dstarea LIKE :pNombreTarea")
					.setParameter("pNombreTarea", "%" + tareaActividades.getDstarea().toUpperCase() + "%")
					.getResultList();

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
	public List<Actividad>  obtenerCodigoActividad(Actividad Actividad) throws DAOException {
		List<Actividad>  resultado;
		try {
			resultado =  entityManager.createQuery("SELECT a FROM Actividad a WHERE UPPER(a.dsactividad) = :pNombreActividad")
					.setParameter("pNombreActividad", Actividad.getDsactividad().toUpperCase())
					.getResultList();

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
	
	public void borradoFisicoTarea(TareaParticularCaso tareaParticularCaso) throws DAOException, BusinessException {
		entityManager.remove(tareaParticularCaso);
	}
	
	@SuppressWarnings("unchecked")
	public List<TareaParticularCaso> obtenerTareasPendientes() throws DAOException, BusinessException {
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.set(Calendar.HOUR_OF_DAY, 0);
		fechaActual.set(Calendar.MINUTE, 0);
		fechaActual.set(Calendar.SECOND, 0);
		Date fecha = fechaActual.getTime();
		List<TareaParticularCaso> tareasPendientes = entityManager.createQuery("SELECT a FROM TareaParticularCaso a WHERE a.finalizada = :pPendiente AND "
				+ "a.fechaLimite > :pFechaActual AND a.numeroDiasAntesAlertas != null AND a.numeroAlertasPorDia != null")
				.setParameter("pPendiente", Parametros.getActividadPendiente())
				.setParameter("pFechaActual", fecha)
				.getResultList();
		return tareasPendientes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TareaParticularCaso> obtenerTareasPendientesFechaActual() throws DAOException, BusinessException {
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.set(Calendar.HOUR_OF_DAY, 0);
		fechaActual.set(Calendar.MINUTE, 0);
		fechaActual.set(Calendar.SECOND, 0);
		Date fecha = fechaActual.getTime();
		List<TareaParticularCaso> tareasPendientes = entityManager.createQuery("SELECT a FROM TareaParticularCaso a WHERE a.finalizada = :pPendiente AND "
				+ "a.fechaLimite > :pFechaActual")
				.setParameter("pPendiente", Parametros.getActividadPendiente())
				.setParameter("pFechaActual", fecha)
				.getResultList();
		return tareasPendientes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TareaParticularCaso> obtenerTareasVencimientoHoy() throws DAOException, BusinessException {
		Date fechaActual = new Date();
		List<TareaParticularCaso> tareasPendientes = entityManager.createQuery("SELECT a FROM TareaParticularCaso a WHERE a.finalizada = :pPendiente AND "
				+ "a.fechaLimite = :pFechaActual")
				.setParameter("pPendiente", Parametros.getActividadPendiente())
				.setParameter("pFechaActual", fechaActual)
				.getResultList();
		return tareasPendientes;
	}
	
	@Override
	public Long getCountTareasProximoVencimiento(CasoFiltro casoFiltro, String sSearch) throws DAOException, BusinessException {
		StringBuilder builderQuery = new StringBuilder();
		builderQuery.append("SELECT count(nombreTarea) FROM ( ");
		builderQuery.append(crearQueryTareasProximoVencimiento(casoFiltro, sSearch));
		builderQuery.append(" ) as tabla");
		
		Query queryTareasProximoVencimiento = entityManager.createNativeQuery(builderQuery.toString());
		
		setParametrosFechas(queryTareasProximoVencimiento, casoFiltro, sSearch);
		BigInteger cantidad = (BigInteger)queryTareasProximoVencimiento.getSingleResult();
		return cantidad.longValue();
	}
	
	@Override
	public List<Object[]> getTareasProximoVencimiento(CasoFiltro casoFiltro, String nombreCaso) throws DAOException, BusinessException {
		StringBuilder builderQuery = new StringBuilder();
		builderQuery.append(crearQueryTareasProximoVencimiento(casoFiltro, nombreCaso));
		
		Query queryTareasProximoVencimiento = entityManager.createNativeQuery(builderQuery.toString());
		
		setParametrosFechas(queryTareasProximoVencimiento, casoFiltro, nombreCaso);
		
		queryTareasProximoVencimiento.setFirstResult(casoFiltro.getiDisplayStart()).setMaxResults(casoFiltro.getiDisplayLength());
		
		return queryTareasProximoVencimiento.getResultList();
	}
	
	private void setParametrosFechas(Query query, CasoFiltro casoFiltro, String sSearch) {
		Calendar fechaInicial = Calendar.getInstance();
		fechaInicial.add(Calendar.DATE, -1);
		fechaInicial.set(Calendar.HOUR_OF_DAY, 23);
		fechaInicial.set(Calendar.MINUTE, 59);
		
		Calendar fechaFinal = Calendar.getInstance();
		fechaFinal.add(Calendar.DATE, casoFiltro.getFiltroDias());
		fechaFinal.set(Calendar.HOUR_OF_DAY, 23);
		fechaFinal.set(Calendar.MINUTE, 59);
		
		query.setParameter("pFechaInicial", fechaInicial.getTime());
		query.setParameter("pFechaFinal", fechaFinal.getTime());
		
		if (sSearch != null && !sSearch.isEmpty())
			query.setParameter("pNombreCaso", "%" + sSearch + "%");
		
		if (casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			query.setParameter("pResponsable", "%" + casoFiltro.getResponsablesFiltro().get(0) + "%");
	}

	private String crearQueryTareasProximoVencimiento(CasoFiltro casoFiltro, String nombreCaso) {
		StringBuilder builderQuery = new StringBuilder();
		builderQuery.append("SELECT * FROM ");
		builderQuery.append(" (SELECT a.dstarea as nombreTarea, group_concat(distinct(e.dsnombre)) as responsable, a.felimite as fechalimite, d.cdcaso as codigoCaso, d.dscaso as nombreCaso, group_concat(distinct(e.cdequipocaso)) as codigosequipocaso, a.snactiva  ");
		builderQuery.append(" FROM ");
		builderQuery.append(" tareas_particulares_caso a, ");
		builderQuery.append(" responsablestareas b, ");
		builderQuery.append(" casos_equipos_caso c, ");
		builderQuery.append(" casos d, ");
		builderQuery.append(" equipos_caso e ");
		builderQuery.append(" WHERE ");
		builderQuery.append(" a.felimite > :pFechaInicial AND a.felimite < :pFechaFinal ");				
		builderQuery.append(" AND a.cdtareaparticular = b.cdtareaparticular ");
		builderQuery.append(" AND b.cdcaso = c.cdcaso ");
		builderQuery.append(" AND b.cdmiembro = c.cdmiembro ");
		builderQuery.append(" AND b.cdequipocaso = c.cdequipocaso ");
		builderQuery.append(" AND c.cdcaso = d.cdcaso ");
		builderQuery.append(" AND b.cdequipocaso = e.cdequipocaso ");
		builderQuery.append(" AND a.snactiva != 'n' ");
		builderQuery.append(" group by a.cdtareaparticular) as tab ");
		builderQuery.append(" WHERE 1=1 ");				
		if (nombreCaso != null && !nombreCaso.isEmpty())
			builderQuery.append(" AND nombreCaso LIKE :pNombreCaso ");
		
		if (casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			builderQuery.append(" AND responsable LIKE :pResponsable ");
		
		if (casoFiltro.getCodigoEquipoCaso() != null) {			
			builderQuery.append(" AND (codigosequipocaso LIKE '" +  casoFiltro.getCodigoEquipoCaso()  + "' OR codigosequipocaso LIKE '" + casoFiltro.getCodigoEquipoCaso() + ",%' OR "
					+ " codigosequipocaso LIKE '%," +  casoFiltro.getCodigoEquipoCaso()  + "' OR codigosequipocaso LIKE '%," +  casoFiltro.getCodigoEquipoCaso()  + ",%') ");
		}
		builderQuery.append(" order by fechalimite ");
		return builderQuery.toString();
	}
	
	@Override
	public List<Object[]> consultarTareasPorEstado(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		List<Object[]> tareasPorEstado = null;
		if (equipoCaso == null) {			
			tareasPorEstado = entityManager.createNativeQuery("SELECT count(cdtareaparticular) AS cantidad, isfinalizada AS estado FROM tareas_particulares_caso GROUP BY isfinalizada").getResultList();
		} else {
			tareasPorEstado = entityManager.createNativeQuery("SELECT count(distinct a.cdtareaparticular) AS cantidad, isfinalizada AS estado FROM tareas_particulares_caso a "
					+ " JOIN responsablestareas b ON a.cdtareaparticular = b.cdtareaparticular WHERE b.cdequipocaso = :pCodigoEquipoCaso GROUP BY isfinalizada")
					.setParameter("pCodigoEquipoCaso", equipoCaso.getCodigoEquipoCaso())
					.getResultList();
		}
		return tareasPorEstado;
	}
	
	
	private String crearQueryPorcentajesTareasPorEstado(CasoFiltro casoFiltro) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("SELECT count(distinct(a.cdtareaparticular)) AS cantidad, isfinalizada AS estado FROM tareas_particulares_caso a join responsablestareas b on a.cdtareaparticular=b.cdtareaparticular ");
		strBuilder.append(" WHERE cdcaso = :pCodigoCaso ");
		
		if (casoFiltro != null && casoFiltro.getResponsablesFiltro()!= null && !casoFiltro.getResponsablesFiltro().isEmpty())
			strBuilder.append(" AND cdequipocaso = :pEquipoCaso ");
		
		strBuilder.append(" GROUP BY isfinalizada ");
		
		return strBuilder.toString();
	}
	
	@Override
	public List<Object[]> consultarPorcentajesTareasPorCasoYEstado(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		List<Object[]> tareasPorEstado = new ArrayList<Object[]>();
		Query query = entityManager.createNativeQuery(crearQueryPorcentajesTareasPorEstado(casoFiltro));
		
		query.setParameter("pCodigoCaso", casoFiltro.getCodigoCaso());
		
		
		if (casoFiltro != null && casoFiltro.getResponsablesFiltro()!= null && !casoFiltro.getResponsablesFiltro().isEmpty())
			query.setParameter("pEquipoCaso", casoFiltro.getResponsablesFiltro().get(0));
			
		return query.getResultList();
	}	
}
