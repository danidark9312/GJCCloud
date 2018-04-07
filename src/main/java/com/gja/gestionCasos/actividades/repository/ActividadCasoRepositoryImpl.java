package com.gja.gestionCasos.actividades.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.maestros.repository.TipoCasoRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;

@Repository("actividadCasoRepository")
public class ActividadCasoRepositoryImpl extends AbstractRepository<ActividadCaso> implements ActividadCasoRepository {

	private static final Logger LOG = Logger
			.getLogger(TipoCasoRepositoryImpl.class);
	
	public ActividadCaso guardarActividadCaso(ActividadCaso actividadCaso) throws DAOException,BusinessException {
		actividadCaso = entityManager.merge(actividadCaso);
		return actividadCaso;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ActividadCaso> getActividadByCaso(Integer codigoCaso) throws DAOException {
		List<ActividadCaso> actividades=null;
		try {
			actividades = entityManager.createQuery("SELECT distinct(ac) FROM ActividadCaso ac, TareaParticularCaso tpc, ResponsableTarea rt WHERE"
					+ " ac.codigoActividadParticular = tpc.actividadParticular.codigoActividadParticular AND tpc.codigoTarea = rt.responsableTareaPK.codigoTareaparticular"
					+ " AND rt.responsableTareaPK.codigoCaso = :pCodigoCaso")
					.setParameter("pCodigoCaso", codigoCaso)
					.getResultList();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error consultando la cantidad de actividades de los tipos de casos activos. El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		return actividades;
	}
	
	@SuppressWarnings("unchecked")
	public List<ActividadCaso> getActividadPendienteByCaso(Integer codigoCaso) throws DAOException {
		List<ActividadCaso> actividades=null;
		try {
			actividades = entityManager.createQuery("SELECT distinct(ac) FROM ActividadCaso ac, TareaParticularCaso tpc, ResponsableTarea rt WHERE"
					+ " ac.codigoActividadParticular = tpc.actividadParticular.codigoActividadParticular AND tpc.codigoTarea = rt.responsableTareaPK.codigoTareaparticular"
					+ " AND rt.responsableTareaPK.codigoCaso = :pCodigoCaso and ac.finalizada = :pFinalizada order by ac.orden asc")
					.setParameter("pCodigoCaso", codigoCaso)
					.setParameter("pFinalizada", "N")
					.getResultList();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error consultando la cantidad de actividades de los tipos de casos activos. El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		return actividades;
	}
	
	@SuppressWarnings("unchecked")
	public List<ActividadCaso> getActividadCompladaByCaso(Integer codigoCaso) throws DAOException {
		List<ActividadCaso> actividades=null;
		try {
			actividades = entityManager.createQuery("SELECT distinct(ac) FROM ActividadCaso ac, TareaParticularCaso tpc, ResponsableTarea rt WHERE"
					+ " ac.codigoActividadParticular = tpc.actividadParticular.codigoActividadParticular AND tpc.codigoTarea = rt.responsableTareaPK.codigoTareaparticular"
					+ " AND rt.responsableTareaPK.codigoCaso = :pCodigoCaso and ac.finalizada = :pFinalizada order by ac.orden asc")
					.setParameter("pCodigoCaso", codigoCaso)
					.setParameter("pFinalizada", "S")
					.getResultList();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error consultando la cantidad de actividades de los tipos de casos activos. El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		return actividades;
	}
	
	@SuppressWarnings("unchecked")
	public int updateActividadCasoOrden(ActividadCaso actividadCaso) throws DAOException {
		int result = 0;
		try {
			result = entityManager.createQuery("update ActividadCaso a set a.orden = :pOrden where codigoActividadParticular = :pCodigo")
					.setParameter("pOrden", actividadCaso.getOrden())
					.setParameter("pCodigo", actividadCaso.getCodigoActividadParticular())
					.executeUpdate();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error consultando la cantidad de actividades de los tipos de casos activos. El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		return result;
	}


	public ActividadCaso consultarActividad(Integer codigoCaso)
			throws DAOException, BusinessException {
		ActividadCaso actividad = null;
		actividad = entityManager.find(ActividadCaso.class, codigoCaso);
		return actividad;
	}

	public void borradoFisicoActividad(ActividadCaso actividadCaso)	throws DAOException, BusinessException {
		entityManager.remove(actividadCaso);		
	}
	
	public List<Actividad> obtenerEstadosProcesales()
			throws DAOException, BusinessException {
		List<Actividad> estadosProcesales = entityManager.createQuery("SELECT distinct(a) FROM Actividad a, ActividadTipoCaso b WHERE a.cdactividad = b.cdactividad.cdactividad").getResultList();
		return estadosProcesales;
	}
	
	public List<ActividadCaso> obtenerActividadesCaso(CasoFiltro casoFiltro) throws DAOException {
		List<ActividadCaso> actividades = null;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT distinct(ac) FROM ActividadCaso ac, TareaParticularCaso tpc, ResponsableTarea rt WHERE ");
		stringBuilder.append("ac.codigoActividadParticular = tpc.actividadParticular.codigoActividadParticular AND tpc.codigoTarea = rt.responsableTareaPK.codigoTareaparticular ");
		stringBuilder.append("AND rt.responsableTareaPK.codigoCaso = :pCodigoCaso ");
		Query query = entityManager.createQuery(stringBuilder.toString());
		return actividades;
	}
	
	
	@Override
	public List<ActividadCaso> obtenerActividadesVencidas() throws DAOException, BusinessException {
		List<ActividadCaso> actividadesVencidas = entityManager.createQuery("SELECT a FROM ActividadCaso a WHERE a.fechaLimite < :pFechaActual AND a.finalizada = :pPendiente")
													.setParameter("pFechaActual", new Date())
													.setParameter("pPendiente", Parametros.getActividadPendiente()).getResultList();
		return actividadesVencidas;
	}
	
	@Override
	public List<ActividadCaso> obtenerActividadesPendientes() throws DAOException, BusinessException {
		Calendar diaAnterior = Calendar.getInstance();
		diaAnterior.add(Calendar.DATE, -1);
		diaAnterior.set(Calendar.HOUR_OF_DAY, 23);
		diaAnterior.set(Calendar.MINUTE, 59);
		List<ActividadCaso> actividadesPendientes = entityManager.createQuery("SELECT a FROM ActividadCaso a WHERE a.fechaLimite > :pFechaActual AND a.finalizada = :pPendiente")
				.setParameter("pFechaActual", diaAnterior.getTime())
				.setParameter("pPendiente", Parametros.getActividadPendiente()).getResultList();
		return actividadesPendientes;
	}
	
	
	
	private String crearQueryActividadPapelera(CasoFiltro casoFiltro) {
		StringBuilder builderQuery = new StringBuilder();
		builderQuery.append(" SELECT distinct(a) FROM ActividadCaso a, TareaParticularCaso b, ResponsableTarea c, CasoEquipoCaso d, Caso e ");
		builderQuery.append(" WHERE (a.snActiva = :pActNoActiva or  b.snActiva = :pTareaNoActiva) ");
		builderQuery.append(crearCondicionesQueryActividadesPapelera(casoFiltro));
		
		builderQuery.append(" AND a.codigoActividadParticular = b.actividadParticular.codigoActividadParticular and b.codigoTarea = c.responsableTareaPK.codigoTareaparticular ");
		builderQuery.append("AND c.responsableTareaPK.codigoEquipoCaso = d.casoEquipoCasoPK.codigoEquipoCaso AND c.responsableTareaPK.codigoMiembro = d.casoEquipoCasoPK.miembro ");
		builderQuery.append("AND c.responsableTareaPK.codigoCaso = d.casoEquipoCasoPK.codigo AND d.casoEquipoCasoPK.codigo = e.codigo ORDER BY e.codigo desc");
		return builderQuery.toString();
	}
	
	
	private String crearCondicionesQueryActividadesPapelera(CasoFiltro casoFiltro) {
		StringBuilder builderQuery = new StringBuilder();
		
		builderQuery.append(" ");  // Liena de codigo que garantiza que el stringbuilder no va a estar nulo
		if (casoFiltro.getCodigoCaso() != null && !casoFiltro.getCodigoCaso().isEmpty())
			builderQuery.append(" AND e.nombre LIKE :pCodigocaso ");
		
		if (casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			builderQuery.append(" AND d.equipoCaso.identificacion IN (:pResponsables) ");
		return builderQuery.toString();
	}


	public List<ActividadCaso> obtenerFiltroActividadesInactivas(CasoFiltro casoFiltro)
			throws DAOException, BusinessException {
		List<ActividadCaso> actividadesInactivas = null;
		
		
		Query queryActividadesInactivas = entityManager.createQuery(crearQueryActividadPapelera(casoFiltro))
											.setParameter("pActNoActiva", Parametros.getCodigoActividadActivoNo())
											.setParameter("pTareaNoActiva", Parametros.getCodigoTareaActivoNo());
		
		
		if (casoFiltro.getCodigoCaso() != null && !casoFiltro.getCodigoCaso().isEmpty())
			queryActividadesInactivas.setParameter("pCodigocaso", '%' + casoFiltro.getCodigoCaso() + '%');
		
		if (casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			queryActividadesInactivas.setParameter("pResponsables", casoFiltro.getResponsablesFiltro());
		
		
		actividadesInactivas = queryActividadesInactivas.setFirstResult(casoFiltro.getiDisplayStart())
														.setMaxResults(casoFiltro.getiDisplayLength()).getResultList();
											
//		if (casoFiltro.getCodigoCaso() != null && !casoFiltro.getCodigoCaso().isEmpty()) {			
//			actividadesInactivas = entityManager.createQuery("SELECT distinct(a) FROM ActividadCaso a, TareaParticularCaso b, ResponsableTarea c, CasoEquipoCaso d, Caso e "
//					+ "WHERE (a.snActiva = :pActNoActiva or  b.snActiva = :pTareaNoActiva) AND e.nombre LIKE :pCodigocaso "
//					+ "AND a.codigoActividadParticular = b.actividadParticular.codigoActividadParticular and b.codigoTarea = c.responsableTareaPK.codigoTareaparticular "
//					+ "AND c.responsableTareaPK.codigoEquipoCaso = d.casoEquipoCasoPK.codigoEquipoCaso AND c.responsableTareaPK.codigoMiembro = d.casoEquipoCasoPK.miembro "
//					+ "AND c.responsableTareaPK.codigoCaso = d.casoEquipoCasoPK.codigo AND d.casoEquipoCasoPK.codigo = e.codigo ORDER BY e.codigo desc")
//					.setParameter("pActNoActiva", Parametros.getCodigoActividadActivoNo())
//					.setParameter("pTareaNoActiva", Parametros.getCodigoTareaActivoNo())
//					.setParameter("pCodigocaso", '%' + casoFiltro.getCodigoCaso() + '%')
//					.setFirstResult(casoFiltro.getiDisplayStart())
//					.setMaxResults(casoFiltro.getiDisplayLength()).getResultList();
//		} else {
//			actividadesInactivas = entityManager.createQuery("SELECT distinct(a) FROM ActividadCaso a, TareaParticularCaso b, ResponsableTarea c, CasoEquipoCaso d, Caso e "
//					+ "WHERE (a.snActiva = :pActNoActiva or  b.snActiva = :pTareaNoActiva) "
//					+ "AND a.codigoActividadParticular = b.actividadParticular.codigoActividadParticular and b.codigoTarea = c.responsableTareaPK.codigoTareaparticular "
//					+ "AND c.responsableTareaPK.codigoEquipoCaso = d.casoEquipoCasoPK.codigoEquipoCaso AND c.responsableTareaPK.codigoMiembro = d.casoEquipoCasoPK.miembro "
//					+ "AND c.responsableTareaPK.codigoCaso = d.casoEquipoCasoPK.codigo AND d.casoEquipoCasoPK.codigo = e.codigo ORDER BY e.codigo desc")
//					.setParameter("pActNoActiva", Parametros.getCodigoActividadActivoNo())
//					.setParameter("pTareaNoActiva", Parametros.getCodigoTareaActivoNo())
//					.setFirstResult(casoFiltro.getiDisplayStart())
//					.setMaxResults(casoFiltro.getiDisplayLength()).getResultList();
//		}
		return actividadesInactivas;
	}
	
	public List<ActividadCaso> obtenerTotalActividadesInactivas(CasoFiltro casoFiltro)
			throws DAOException, BusinessException {
		List<ActividadCaso> actividadesInactivas = null;
	
		Query queryActividadesInactivas = entityManager.createQuery(crearQueryActividadPapelera(casoFiltro))
				.setParameter("pActNoActiva", Parametros.getCodigoActividadActivoNo())
				.setParameter("pTareaNoActiva", Parametros.getCodigoTareaActivoNo());

		if (casoFiltro.getCodigoCaso() != null && !casoFiltro.getCodigoCaso().isEmpty())
			queryActividadesInactivas.setParameter("pCodigocaso", '%' + casoFiltro.getCodigoCaso() + '%');
		
		if (casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			queryActividadesInactivas.setParameter("pResponsables", casoFiltro.getResponsablesFiltro());
		
		actividadesInactivas = queryActividadesInactivas.getResultList();
		return actividadesInactivas;
	}
	
	
}
