package com.gja.gestionCasos.casos.repository;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;




import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;



@Repository("casoRepository")
public class CasoRepositoryImpl extends AbstractRepository<Caso> implements CasoRepository {
	private static final Logger LOG = Logger
			.getLogger(CasoRepository.class);
	
	public Caso findByPK(Caso caso) throws DAOException {
		Caso casoReturn;
		try {
			casoReturn = entityManager.find(Caso.class, caso.getCodigo());
			inicializarListas(casoReturn);
			} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error consultando el caso por PK " + caso.getCodigo() + ". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Exception: Error consultando el caso por PK " + caso.getCodigo() + ". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		return casoReturn;
	}

	public void inicializarListas(Caso casoReturn) {
		Hibernate.initialize(casoReturn.getBienAfectadoSet());
		Hibernate.initialize(casoReturn.getCasoEquipoCasoSet());
		Hibernate.initialize(casoReturn.getRadicadoSet());
		Hibernate.initialize(casoReturn.getPrestamosList());
		List<Prestamo> listPrestamo = (casoReturn.getPrestamosList());
		if (listPrestamo != null) {
			for (Prestamo objPrestamo: listPrestamo ) {
				Hibernate.initialize(objPrestamo.getEntidadFinaciera());
			}
		}
	
		if(casoReturn.getCiudadProceso()!=null){
			
			Hibernate.initialize(casoReturn.getCiudadProceso());
			Hibernate.initialize(casoReturn.getCiudadProceso().getDepartamento());
			Hibernate.initialize(casoReturn.getCiudadProceso().getDepartamento().getPais());
		}
		Hibernate.initialize(casoReturn.getCiudadHechos().getDepartamento().getPais());
		Hibernate.initialize(casoReturn.getCiudadHechos().getDepartamento());
		Hibernate.initialize(casoReturn.getCiudadHechos());
		Hibernate.initialize(casoReturn.getEstadoCaso());
		Hibernate.initialize(casoReturn.getTipoCaso());
		Hibernate.initialize(casoReturn.getRadicadoSet());
		List<BienAfectado> listBienAfectado = casoReturn.getBienAfectadoSet();
		for (BienAfectado objBienAfectado: listBienAfectado ) {
			Hibernate.initialize(objBienAfectado.getClase());
		}
		List<Radicado> listRadicado = casoReturn.getRadicadoSet();
		for (Radicado objRadicado: listRadicado ) {
			Hibernate.initialize(objRadicado.getInstancia());
		}
		List<CasoEquipoCaso> listCasoEquipoCaso = casoReturn.getCasoEquipoCasoSet();
		for (CasoEquipoCaso objCasoEquipoCaso: listCasoEquipoCaso ) {
			Hibernate.initialize(objCasoEquipoCaso.getTipoMiembro());
			Hibernate.initialize(objCasoEquipoCaso.getEquipoCaso());
			Hibernate.initialize(objCasoEquipoCaso.getParentesco());
			Hibernate.initialize(objCasoEquipoCaso.getEquipoCaso().getCorreoList());
			Hibernate.initialize(objCasoEquipoCaso.getEquipoCaso().getCelularList());
			Hibernate.initialize(objCasoEquipoCaso.getEquipoCaso().getTelefonoList());	
			Hibernate.initialize(objCasoEquipoCaso.getResponsablesTareas());
			Hibernate.initialize(objCasoEquipoCaso.getParentesco());
		}
	}
	
	public Long getCountFilter(CasoFiltro caso)  {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Caso> casoRoot = query.from(Caso.class);

		query.select(builder.count(casoRoot.<String> get("codigo")));

		List<Predicate> predicateList = getPredicateFiltro(caso, builder, casoRoot);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		return entityManager.createQuery(query).getSingleResult();
	}
	
	
	
	public List<Caso> encontrarCasoPorFiltro(CasoFiltro casoFiltro) {
		
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Caso> query = builder.createQuery(Caso.class);
		Root<Caso> casoRoot = query.from(Caso.class);
		
		query.select(casoRoot);
		//query.orderBy(casoRoot.<Integer> get("codigo"));
		List<Predicate> listaPredicados = getPredicateFiltro(casoFiltro, builder, casoRoot);
		Predicate[] predicados = new Predicate[listaPredicados.size()];
		listaPredicados.toArray(predicados);
		query.where(predicados);
		query.orderBy(builder.desc(casoRoot.<String> get("fechaUsuarioUltimaModificacion")));
				
		
		return entityManager.createQuery(query)
				.setMaxResults(casoFiltro.getiDisplayLength())
				.setFirstResult(casoFiltro.getiDisplayStart()).getResultList();
		
	}
	private List<Predicate> getPredicateFiltro(CasoFiltro casoFiltro, CriteriaBuilder builder, Root<Caso> casoRoot) {
			
		List<Predicate> listaPredicados = new ArrayList<Predicate>();
		Predicate codigoPredicado;
		Predicate estadoPredicate;
		Predicate estadoProcesalPredicate;
		Predicate estadoTareaPredicate;
		Predicate nombreCasoPredicate;
		Predicate palabraClavePredicate;
		Predicate fechaRangoPredicate;
		Predicate responsablesPredicate;
		Predicate audienciaPredicate;
		Predicate prejudicialPredicate;
		Predicate recursosPredicate;
		Predicate radicadoPredicate;
		
		if (casoFiltro != null && casoFiltro.getCodigoCaso() != null && !(casoFiltro.getCodigoCaso().isEmpty())) {
			codigoPredicado = builder.equal(builder.upper(casoRoot.<String> get("codigo")),	casoFiltro.getCodigoCaso());
			listaPredicados.add(codigoPredicado);
		}
		if (casoFiltro != null && casoFiltro.getEstadoCaso() != null  && !(casoFiltro.getEstadoCaso().isEmpty())) {
			estadoPredicate = builder.equal(casoRoot.get("estadoCaso").get("codigo"), casoFiltro.getEstadoCaso());
			listaPredicados.add(estadoPredicate);
		}
		if (casoFiltro != null && casoFiltro.getTipoCaso() != null  && !(casoFiltro.getTipoCaso().isEmpty())) {
			estadoPredicate = builder.equal(casoRoot.get("tipoCaso").get("codigo"), casoFiltro.getTipoCaso());
			listaPredicados.add(estadoPredicate);
		}
		
		if (casoFiltro != null && casoFiltro.getEstadoProcesal() != null && !(casoFiltro.getEstadoProcesal().isEmpty())) {
			estadoProcesalPredicate = builder.equal(casoRoot.get("estadoProcesal"), casoFiltro.getEstadoProcesal());
			listaPredicados.add(estadoProcesalPredicate);
		}
		if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().isEmpty()) {
			nombreCasoPredicate = builder.like(casoRoot.<String> get("nombre"), "%" + casoFiltro.getNombreCasoFiltro() + "%");
			listaPredicados.add(nombreCasoPredicate);
		}
		
		if (casoFiltro != null && casoFiltro.getAudienciaFiltro() != null && !casoFiltro.getAudienciaFiltro().isEmpty()) {
			audienciaPredicate = builder.like(casoRoot.join("casoEquipoCasoSet").join("responsablesTareas")
					.get("tareasParticularesCaso").get("actividadParticular").<String> get("nombreActividad"), "%" + casoFiltro.getAudienciaFiltro() + "%");
			listaPredicados.add(audienciaPredicate);
		}
		
		if (casoFiltro != null && casoFiltro.getPrejudicialFiltro() != null && !casoFiltro.getPrejudicialFiltro().isEmpty()) {
			prejudicialPredicate = builder.like(casoRoot.join("casoEquipoCasoSet").join("responsablesTareas")
					.get("tareasParticularesCaso").get("actividadParticular").<String> get("nombreActividad"), "%" + casoFiltro.getPrejudicialFiltro() + "%");
			listaPredicados.add(prejudicialPredicate);
		}
		
		if (casoFiltro != null && casoFiltro.getRecursosPendientesFiltro() != null && !casoFiltro.getRecursosPendientesFiltro().isEmpty()) {
			recursosPredicate = builder.like(casoRoot.join("casoEquipoCasoSet").join("responsablesTareas")
					.get("tareasParticularesCaso").get("actividadParticular").<String> get("nombreActividad"), "%" + casoFiltro.getRecursosPendientesFiltro() + "%");
			listaPredicados.add(recursosPredicate);
		}
		if (casoFiltro != null && casoFiltro.getRadicadoFiltro() != null && !casoFiltro.getRadicadoFiltro().isEmpty()) {
			radicadoPredicate = builder.like(casoRoot.join("radicadoSet").get("radicadoPK").<String> get("codigoRadicado"), "%" + casoFiltro.getRadicadoFiltro() + "%");
			listaPredicados.add(radicadoPredicate);
		}
		
		if (casoFiltro != null && casoFiltro.getVictimaFiltro() != null && !casoFiltro.getVictimaFiltro().isEmpty()) {
			palabraClavePredicate = 
					builder.and(
							builder.equal(casoRoot.join("casoEquipoCasoSet").get("casoEquipoCasoPK").get("miembro"), Parametros.getVictima()),
							builder.like(
									builder.concat(
											casoRoot.join("casoEquipoCasoSet").get("equipoCaso").<String> get("nombre"),
											casoRoot.join("casoEquipoCasoSet").get("equipoCaso").<String> get("apellido")
									),
									"%" + casoFiltro.getVictimaFiltro() + "%"
							)
					);
			listaPredicados.add(palabraClavePredicate);
		}
		
		if (casoFiltro != null && casoFiltro.getCodigoEquipoCaso() != null) {
			responsablesPredicate = builder.equal(casoRoot.join("casoEquipoCasoSet").get("casoEquipoCasoPK").get("codigoEquipoCaso"), casoFiltro.getCodigoEquipoCaso());
			listaPredicados.add(responsablesPredicate);
		}
		
		
		return listaPredicados;
	}
	
	@SuppressWarnings("unchecked")
	public Long getCountByTipoCasoCaso(Integer codigoTipoCaso) throws DAOException {
		Long resultado;
		try {
			resultado = entityManager.createQuery("SELECT COUNT(c.codigo) FROM Caso c INNER JOIN c.tipoCaso tc WHERE tc.codigo = :pCodigoTipoCaso", Long.class)
					.setParameter("pCodigoTipoCaso", codigoTipoCaso)
					.getSingleResult();
		} catch (IllegalStateException e) {
			LOG.error(
					"IllegalStateException: Error consultando la cantidad de tipos de casos por caso. El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		return resultado;
	}

	public List<User> consultarCorreoAdmind() {
			List<User> userLista = null;
		
			userLista = entityManager.createNativeQuery("SELECT u.* FROM usuarios u, roles_usuarios r WHERE r.CDROL=:pRolAdmind AND u.CDUSUARIO=r.CDUSUARIO ORDER BY DSNOMBRE", User.class).
					setParameter("pRolAdmind",Parametros.getRolAdmind()).getResultList();
			return userLista; 
	}


	public String getNombreCaso(Integer codigoCaso) {
		String nombreCaso = (String)entityManager.createQuery("SELECT a.nombre FROM Caso a WHERE a.codigo = :pCodigoCaso")
				.setParameter("pCodigoCaso", codigoCaso)
				.getSingleResult();
		return nombreCaso;
	}
	
	public Long getCountAudiencias(CasoFiltro caso) throws DAOException, BusinessException {
		Query query = getQueryAudiencias(caso, true);
		Long count = (Long)query.getSingleResult();
		return count.longValue();
	}
	
	public String getEstadoCaso(Caso caso) throws DAOException {
		String estado = null;
		try {
			estado = (String)entityManager.createQuery("SELECT a.nombre FROM EstadoCaso a, Caso b WHERE b.codigo = :pCodigoCaso AND a.codigo = b.estadoCaso.codigo ")
					.setParameter("pCodigoCaso", caso.getCodigo()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return estado;
	}
	
	
	public List<Object[]> obtenerAudienciasExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		List<Object[]> audiencias = new ArrayList<Object[]>();
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append(" SELECT A.DSCASO, group_concat(distinct(EQ.DSNOMBRE)), D.DSTAREA, D.DSDETALLE, D.FELIMITE,  R.CDRADICADO, ATE.NMENVIOSTOTALES, A.NMDESPACHO, A.CDCASO, DEMANDADOS.nombres ");
		strBuilder.append(" FROM CASOS_EQUIPOS_CASO B, RESPONSABLESTAREAS C, ACTIVIDADES_CASO E, EQUIPOS_CASO EQ, CASOS A ");
		strBuilder.append(" LEFT JOIN RADICADOS R ON A.CDCASO=R.CDCASO LEFT JOIN ");
		strBuilder.append(" (select cdcaso, group_concat(dsnombre) as nombres from casos_equipos_caso a join equipos_caso b on a.cdequipocaso=b.cdequipocaso where cdmiembro=");
		strBuilder.append(Parametros.getDemandado() + " group by cdcaso) as DEMANDADOS ON A.CDCASO = DEMANDADOS.CDCASO, ");
		strBuilder.append("  TAREAS_PARTICULARES_CASO D left JOIN   ALERTAS_TAREAS_PARTICULARES ATE ON   D.CDTAREAPARTICULAR = ATE.CDTAREA ");
		strBuilder.append(" WHERE  A.CDCASO = B.CDCASO AND B.CDCASO = C.CDCASO  AND  B.CDEQUIPOCASO = C.CDEQUIPOCASO ");
		strBuilder.append(" AND B.CDMIEMBRO = C.CDMIEMBRO AND C.CDTAREAPARTICULAR = D.CDTAREAPARTICULAR AND D.CDACTIVIDADPARTICULAR = E.CDACTIVIDADPARTICULAR ");
		strBuilder.append(" AND B.CDEQUIPOCASO = EQ.CDEQUIPOCASO ");
		
		if (casoFiltro != null && casoFiltro.getAudienciaFiltro() != null && !casoFiltro.getAudienciaFiltro().isEmpty())
			strBuilder.append(" AND E.DSACTIVIDAD LIKE '%" + Parametros.getActividadAudiencia() + "%' ");
		if (casoFiltro != null && casoFiltro.getRecursosPendientesFiltro() != null && !casoFiltro.getRecursosPendientesFiltro().isEmpty())
			strBuilder.append(" AND E.DSACTIVIDAD LIKE '%"+ Parametros.getActividadRecursosPendientes() + "%' ");		
		if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().isEmpty())
			strBuilder.append(" AND A.DSCASO LIKE '%"+ casoFiltro.getNombreCasoFiltro()  + "%' ");	
		
		strBuilder.append(" AND e.snActiva = '" + Parametros.getCodigoActividadActivoSi() + "' ");
		
		
		if (casoFiltro != null && casoFiltro.getEstadoTareaFiltro() != null && !casoFiltro.getEstadoTareaFiltro().isEmpty())
			strBuilder.append(" AND E.ISFINALIZADA LIKE '%"+ casoFiltro.getEstadoTareaFiltro() + "%' ");
		
		
		SimpleDateFormat sdfFechaInicio = new SimpleDateFormat("yyyy-MM-dd");
		String strFechaInicio = null;
		if(casoFiltro.getFechaInicioFiltro() != null){
			strFechaInicio = sdfFechaInicio.format(casoFiltro.getFechaInicioFiltro());
		}
		
		SimpleDateFormat sdfFechaFinal = new SimpleDateFormat("yyyy-MM-dd");
		String strFechaFinal = null;
		if (casoFiltro.getFechaFinFiltro() != null){
			strFechaFinal = sdfFechaFinal.format(casoFiltro.getFechaFinFiltro());
		}
		
//		if (casoFiltro != null && casoFiltro.getFechaInicioFiltro() != null && casoFiltro.getFechaFinFiltro() != null)
//			strBuilder.append(" AND (E.FELIMITE >= " + casoFiltro.getFechaInicioFiltro() + " AND E.FELIMITE <= " + casoFiltro.getFechaFinFiltro() + ")");
			
		if (casoFiltro != null && strFechaInicio != null && strFechaFinal != null)
			strBuilder.append(" AND (E.FELIMITE >= '" + strFechaInicio + "' AND E.FELIMITE <= '" + strFechaFinal + "')");
		
		if (casoFiltro != null && casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			strBuilder.append(" AND EQ.CDIDENTIFICACION IN (" + casoFiltro.getResponsablesFiltro().get(0) + ") ");
		
		if (casoFiltro != null && casoFiltro.getVictimaFiltro() != null && !casoFiltro.getVictimaFiltro().isEmpty()) {
			strBuilder.append(" AND (SELECT COUNT(X.CDCASO) FROM CASO X, CASOS_EQUIPOS_CASO Y, EQUIPOS_CASO Z WHERE X.CDCASO=Y.CDCASO AND Y.CDEQUIPOCASO=Z.CDEQUIPOCASO AND "); 
			strBuilder.append(" X.CDCASO = A.CDCASO AND CONCAT(CONCAT(Z.DSNOMBRE, ' '), Z.DSAPELLIDO) LIKE '% " + casoFiltro.getVictimaFiltro() + " %' AND Y.CDMIEMBRO=" + Integer.parseInt(Parametros.getVictima()) + " ) > 0 ");
		}
		if (casoFiltro != null && casoFiltro.getRadicadoFiltro() != null && !casoFiltro.getRadicadoFiltro().isEmpty()) {
			strBuilder.append("AND ( SELECT COUNT(X.CDCASO) FROM CASO X, RADICADOS Y WHERE X.CDCASO=Y.CDCASO AND X.CDCASO=A.CDCASO AND Y.CDRADICADO LIKE '% " + casoFiltro.getRadicadoFiltro() +" %' ) > 0 ");
		}
		strBuilder.append(" GROUP BY D.CDTAREAPARTICULAR ");
		
		if  (casoFiltro != null && Parametros.getOrdenarDescendente().equals(casoFiltro.getOrdenarPor())) {			
			strBuilder.append(" ORDER BY E.FELIMITE DESC");
		} else {
			strBuilder.append(" ORDER BY E.FELIMITE ASC");
		}
		audiencias = (List<Object[]>) entityManager.createNativeQuery(strBuilder.toString()).getResultList();		
		return audiencias;
	}
	
	/*
	 * Metodo que se encarga de armar el query para realizar la consulta de el reporte de Caducidad caso,
	 * 
	 * @Param casoFiltro  = objecto con los filtros por lo que se busca el reporte
	 * @Param isCountSelect  = Booleano que indica si esun select count, o si es el select mnormal que obtiene todos los resultados.
	 */
	
	public Query getQueryCaducidadCaso(CasoFiltro casoFiltro, boolean isCountSelect) throws DAOException, BusinessException {
		StringBuilder stringBuilder = new StringBuilder();
	
		if (isCountSelect) 			
			stringBuilder.append("SELECT count(distinct(e.cdactividadparticular)) ");
		else				
			stringBuilder.append("SELECT a.cdcaso, a.dscaso, group_concat(distinct(f.dsnombre)), a.fehecho, sum(d.NMENVIOSTOTALES), e.felimite ");
			
			stringBuilder.append("FROM casos a, casos_equipos_caso b, responsablestareas c, actividades_caso e, ");
			stringBuilder.append("(SELECT * FROM tareas_particulares_caso a LEFT JOIN alertas_tareas_particulares b ON a.cdtareaparticular = b.CDTAREA) AS d, equipos_caso f ");
			stringBuilder.append("WHERE e.dsactividad like :pPrejudicial AND a.cdcaso = b.cdcaso AND b.cdcaso = c.cdcaso AND b.cdequipocaso = c.cdequipocaso ");
			stringBuilder.append("AND b.cdmiembro = c.cdmiembro AND c.cdequipocaso = f.cdequipocaso AND c.cdtareaparticular = d.cdtareaparticular AND d.cdactividadparticular = e.cdactividadparticular ");
			
			
			if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().isEmpty())
				stringBuilder.append("AND a.dscaso LIKE :pNombreCaso ");
			
			if (casoFiltro != null && casoFiltro.getFechaInicioFiltro() != null && casoFiltro.getFechaFinFiltro() != null)
				stringBuilder.append("AND (e.felimite >= :pFechaInicioFiltro AND e.felimite <= :pFechaFinFiltro) ");
			
			if (casoFiltro != null && casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
				stringBuilder.append("AND f.cdidentificacion IN (:pResponsables) ");
			
			if (casoFiltro != null && casoFiltro.getVictimaFiltro() != null && !casoFiltro.getVictimaFiltro().isEmpty()) {
				stringBuilder.append("AND (SELECT COUNT(x.cdcaso) FROM casos x, casos_equipos_caso y, equipos_caso z WHERE x.cdcaso = y.cdcaso AND y.cdequipocaso = z.cdequipocaso AND "
						+ " x.cdcaso = a.cdcaso AND CONCAT(CONCAT(z.dsnombre, ' '), z.dsapellido) LIKE :pVictima AND y.cdmiembro = :pTipoVictima) > 0 ");
			}
			if (casoFiltro != null && casoFiltro.getRadicadoFiltro() != null && !casoFiltro.getRadicadoFiltro().isEmpty()) {
				stringBuilder.append("AND (SELECT COUNT(x.cdcaso) FROM casos x, radicados y WHERE x.cdcaso = y.cdcaso AND x.cdcaso = a.cdcaso AND y.cdradicado LIKE :pRadicado) > 0 ");
			}
			
			
			if (!isCountSelect) {
				if (casoFiltro != null && Parametros.getOrdenarDescendente().equals(casoFiltro.getOrdenarPor())) {				
					stringBuilder.append("GROUP BY e.cdactividadparticular ORDER BY e.felimite DESC");
				} else {
					if (casoFiltro != null && Parametros.getOrdenarAscendente().equals(casoFiltro.getOrdenarPor())) {				
						stringBuilder.append("GROUP BY e.cdactividadparticular ORDER BY e.felimite ASC");
					} else {
						if (casoFiltro != null && Parametros.getOrdenarAlbafeticamenteAZ().equals(casoFiltro.getOrdenarPor())) {				
							stringBuilder.append("GROUP BY e.cdactividadparticular ORDER BY a.dscaso ASC");
						} else {
							stringBuilder.append("GROUP BY e.cdactividadparticular ORDER BY a.dscaso DESC");
						}
					}
				} 
			}
			
			Query query = entityManager.createNativeQuery(stringBuilder.toString());
			
			if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().isEmpty())
				query.setParameter("pNombreCaso", "%" +  casoFiltro.getNombreCasoFiltro() + "%");
			
			if (casoFiltro != null && casoFiltro.getFechaInicioFiltro() != null && casoFiltro.getFechaFinFiltro() != null)
				query.setParameter("pFechaInicioFiltro", casoFiltro.getFechaInicioFiltro()).setParameter("pFechaFinFiltro", casoFiltro.getFechaFinFiltro());
			
			if (casoFiltro != null && casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
				query.setParameter("pResponsables", casoFiltro.getResponsablesFiltro());
			
			if (casoFiltro != null && casoFiltro.getVictimaFiltro() != null && !casoFiltro.getVictimaFiltro().isEmpty()) 
				query.setParameter("pVictima", "%" + casoFiltro.getVictimaFiltro() + "%").setParameter("pTipoVictima", Integer.parseInt(Parametros.getVictima()));
			
			if (casoFiltro != null && casoFiltro.getRadicadoFiltro() != null && !casoFiltro.getRadicadoFiltro().isEmpty())
				query.setParameter("pRadicado", "%" + casoFiltro.getRadicadoFiltro() + "%");
			
			query.setParameter("pPrejudicial", "%" + Parametros.getActividadPrejudicial() + "%");
			
			return query;
		}

	
	public Long getCountCaducidadCasos(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		Query query = getQueryCaducidadCaso(casoFiltro, true);
		BigInteger count = (BigInteger)query.getSingleResult();
		return count.longValue();
	}
	
	
	public List<Object[]> obtenerCaducidadCasos(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		Query query = getQueryCaducidadCaso(casoFiltro, false);
		query.setMaxResults(casoFiltro.getiDisplayLength())
			.setFirstResult(casoFiltro.getiDisplayStart());
		return query.getResultList();
	}
	
	
	public List<Object[]> obtenerCaducidadCasosExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		Query query = getQueryCaducidadCaso(casoFiltro, false);
		return query.getResultList();
	}
	
	
	
	public Query getQueryAudiencias(CasoFiltro casoFiltro, boolean isCountSelect) throws DAOException, BusinessException {
		StringBuilder stringBuilder = new StringBuilder();
		if (isCountSelect)
			stringBuilder.append("SELECT COUNT(DISTINCT a.codigo) ");
		else
			stringBuilder.append("SELECT DISTINCT(a) ");
		stringBuilder.append("FROM Caso a, CasoEquipoCaso  b, ResponsableTarea c, ActividadCaso e, TareaParticularCaso d ");
		stringBuilder.append("WHERE e.nombreActividad LIKE :pActividad AND e.snActiva = :pActiva AND a.codigo = b.casoEquipoCasoPK.codigo AND b.casoEquipoCasoPK.codigo = c.responsableTareaPK.codigoCaso AND ");
		stringBuilder.append("b.casoEquipoCasoPK.codigoEquipoCaso = c.responsableTareaPK.codigoEquipoCaso AND b.casoEquipoCasoPK.miembro = c.responsableTareaPK.codigoMiembro AND ");
		stringBuilder.append("c.tareasParticularesCaso.codigoTarea = d.codigoTarea AND d.actividadParticular.codigoActividadParticular = e.codigoActividadParticular ");
		
		
		if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().isEmpty())
			stringBuilder.append("AND a.nombre LIKE :pNombreCaso ");
		
		if (casoFiltro != null && casoFiltro.getEstadoTareaFiltro() != null && !casoFiltro.getEstadoTareaFiltro().isEmpty())
			stringBuilder.append("AND e.finalizada = :pEstadoActividad ");
		
		if (casoFiltro != null && casoFiltro.getFechaInicioFiltro() != null && casoFiltro.getFechaFinFiltro() != null)
			stringBuilder.append("AND (e.fechaLimite >= :pFechaInicioFiltro AND e.fechaLimite <= :pFechaFinFiltro) ");
		
		if (casoFiltro != null && casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			stringBuilder.append("AND b.equipoCaso.identificacion IN (:pResponsables) ");
		
		if (casoFiltro != null && casoFiltro.getVictimaFiltro() != null && !casoFiltro.getVictimaFiltro().isEmpty()) {
			stringBuilder.append("AND (SELECT COUNT(x.codigo) FROM Caso x, CasoEquipoCaso y, EquipoCaso z WHERE x.codigo = y.casoEquipoCasoPK.codigo AND y.casoEquipoCasoPK.codigoEquipoCaso = z.codigoEquipoCaso AND "
					+ " x.codigo=a.codigo AND CONCAT(CONCAT(z.nombre, ' '), z.apellido) LIKE :pVictima AND y.casoEquipoCasoPK.miembro = :pTipoVictima) > 0 ");
		}
		if (casoFiltro != null && casoFiltro.getRadicadoFiltro() != null && !casoFiltro.getRadicadoFiltro().isEmpty()) {
			stringBuilder.append("AND (SELECT COUNT(x.codigo) FROM Caso x, Radicado y WHERE x.codigo = y.radicadoPK.codigoCaso AND x.codigo=a.codigo AND y.radicadoPK.codigoRadicado AND y.activo != 'N' LIKE :pRadicado) > 0 ");
		}
		
		if  (casoFiltro != null && Parametros.getOrdenarDescendente().equals(casoFiltro.getOrdenarPor())) {			
			stringBuilder.append("ORDER BY e.fechaLimite DESC");
		} else {
			stringBuilder.append("ORDER BY e.fechaLimite ASC");
		}
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		
		query.setParameter("pActiva", Parametros.getCodigoActividadActivoSi());
		
		if (casoFiltro != null && casoFiltro.getAudienciaFiltro() != null && !casoFiltro.getAudienciaFiltro().isEmpty())
			query.setParameter("pActividad", "%" + Parametros.getActividadAudiencia() + "%");
		
		if (casoFiltro != null && casoFiltro.getRecursosPendientesFiltro() != null && !casoFiltro.getRecursosPendientesFiltro().isEmpty())
			query.setParameter("pActividad", "%" + Parametros.getActividadRecursosPendientes() + "%");
		
		if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().isEmpty())
			query.setParameter("pNombreCaso", "%" +  casoFiltro.getNombreCasoFiltro() + "%");
		
		if (casoFiltro != null && casoFiltro.getEstadoTareaFiltro() != null && !casoFiltro.getEstadoTareaFiltro().isEmpty())
			query.setParameter("pEstadoActividad", casoFiltro.getEstadoTareaFiltro());
		
		if (casoFiltro != null && casoFiltro.getFechaInicioFiltro() != null && casoFiltro.getFechaFinFiltro() != null)
			query.setParameter("pFechaInicioFiltro", casoFiltro.getFechaInicioFiltro()).setParameter("pFechaFinFiltro", casoFiltro.getFechaFinFiltro());
		
		if (casoFiltro != null && casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			query.setParameter("pResponsables", casoFiltro.getResponsablesFiltro());
		
		if (casoFiltro != null && casoFiltro.getVictimaFiltro() != null && !casoFiltro.getVictimaFiltro().isEmpty()) 
			query.setParameter("pVictima", "%" + casoFiltro.getVictimaFiltro() + "%").setParameter("pTipoVictima", Integer.parseInt(Parametros.getVictima()));
		
		if (casoFiltro != null && casoFiltro.getRadicadoFiltro() != null && !casoFiltro.getRadicadoFiltro().isEmpty())
			query.setParameter("pRadicado", "%" + casoFiltro.getRadicadoFiltro() + "%");
		return query;
	}
	

	public List<Caso> obtenerCasosAudiencias(CasoFiltro casoFiltro) throws DAOException {
		Query query;
		List<Caso> casos = null;
		try {
			query = getQueryAudiencias(casoFiltro, false);
			casos = query.setMaxResults(casoFiltro.getiDisplayLength())
					.setFirstResult(casoFiltro.getiDisplayStart()).getResultList();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return casos;
	}
	
	public Long getCountDocumentosPendientes(CasoFiltro caso) throws DAOException, BusinessException {
		Query query = getQueryDocumentosPendientes(caso, true);
		BigInteger count = (BigInteger)query.getSingleResult();
		return count.longValue();
	}
	
	public List<Object[]> obtenerDocumentosPendientes(CasoFiltro casoFiltro) throws DAOException, BusinessException {
		Query query = getQueryDocumentosPendientes(casoFiltro, false);
		query.setMaxResults(casoFiltro.getiDisplayLength())
			.setFirstResult(casoFiltro.getiDisplayStart());
		return query.getResultList();
	}
	
	/*
	 * Metodo que se encarga de armar el query para realizar la consulta de el reporte de documentos pendientes,
	 * 
	 * @Param casoFiltro  = objecto con los filtros por lo que se busca el reporte
	 * @Param isCountSelect  = Booleano que indica si es un select count, o si es el select normal que obtiene todos los resultados.
	 */
	
	public Query getQueryDocumentosPendientes(CasoFiltro casoFiltro, boolean isCountSelect) throws DAOException, BusinessException {
		StringBuilder stringBuilder = new StringBuilder();
	
		if (isCountSelect) 			
			stringBuilder.append("SELECT count(codigoCaso) ");
		else
			stringBuilder.append("SELECT codigoCaso, descripcion, nombre, parentesco, tarea, detalleTarea, vencimiento, responsables, alertas, vencimientoactividad ");
		
		stringBuilder.append("FROM ");
		stringBuilder.append("(SELECT x.cdcaso, concat(concat(z.dsnombre, ' '), z.dsapellido) as nombre, p.dsnombre as parentesco FROM ");
		stringBuilder.append("casos x, casos_equipos_caso y, equipos_caso z, parentescos p ");
	    stringBuilder.append("WHERE x.cdcaso = y.cdcaso AND Y.cdequipocaso = z.cdequipocaso AND y.cdparentesco = p.cdparentesco) as a, ");
	    
	    stringBuilder.append("(SELECT a.cdcaso as codigoCaso, a.dscaso as descripcion, d.dstarea as tarea, d.dsdetalle as detalleTarea, e.felimite as vencimientoactividad, ");
	    stringBuilder.append("group_concat(distinct (f.dsnombre)) as responsables, d.felimite as vencimiento, sum(d.NMENVIOSTOTALES) as alertas ");  
	    stringBuilder.append("from casos a, casos_equipos_caso b, responsablestareas c, actividades_caso e, (SELECT * from tareas_particulares_caso a ");
	    stringBuilder.append("LEFT JOIN alertas_tareas_particulares b ON a.cdtareaparticular = b.CDTAREA) as d, equipos_caso f ");
	    stringBuilder.append("WHERE e.dsactividad like :pDocumentosRequeridos AND d.isfinalizada = :pPendiente AND a.cdcaso = b.cdcaso and b.cdcaso = c.cdcaso AND b.cdequipocaso = c.cdequipocaso ");
	    stringBuilder.append("and b.cdmiembro = c.cdmiembro AND b.cdequipocaso = f.cdequipocaso AND c.cdtareaparticular = d.cdtareaparticular AND d.cdactividadparticular = e.cdactividadparticular ");
		
		if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().isEmpty())
			stringBuilder.append("AND a.dscaso LIKE :pNombreCaso ");
		
		if (casoFiltro != null && casoFiltro.getFechaInicioFiltro() != null && casoFiltro.getFechaFinFiltro() != null)
			stringBuilder.append("AND (e.felimite >= :pFechaInicioFiltro AND e.felimite <= :pFechaFinFiltro) ");
		
		if (casoFiltro != null && casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			stringBuilder.append("AND f.cdidentificacion IN (:pResponsables) ");
		
		if (casoFiltro != null && casoFiltro.getVictimaFiltro() != null && !casoFiltro.getVictimaFiltro().isEmpty()) {
			stringBuilder.append("AND (SELECT COUNT(x.cdcaso) FROM casos x, casos_equipos_caso y, equipos_caso z WHERE x.cdcaso = y.cdcaso AND y.cdequipocaso = z.cdequipocaso AND "
					+ " x.cdcaso = a.cdcaso AND CONCAT(CONCAT(z.dsnombre, ' '), z.dsapellido) LIKE :pVictima AND y.cdmiembro = :pTipoVictima) > 0 ");
		}
		if (casoFiltro != null && casoFiltro.getRadicadoFiltro() != null && !casoFiltro.getRadicadoFiltro().isEmpty()) {
			stringBuilder.append("AND (SELECT COUNT(x.cdcaso) FROM casos x, radicados y WHERE x.cdcaso = y.cdcaso AND x.cdcaso = a.cdcaso AND y.cdradicado LIKE :pRadicado) > 0 ");
		}
		stringBuilder.append("GROUP BY d.cdtareaparticular ");
		
//		stringBuilder.append(") as b WHERE a.cdcaso = b.codigoCaso AND b.tarea LIKE concat('%', a.nombre) ");
		stringBuilder.append(") as b ");
		if (!isCountSelect) {
			if (casoFiltro != null && Parametros.getOrdenarDescendente().equals(casoFiltro.getOrdenarPor())) {				
				stringBuilder.append("ORDER BY vencimientoactividad DESC");
			} else {				
				stringBuilder.append("ORDER BY vencimientoactividad ASC");
			} 
		}
		
		Query query = entityManager.createNativeQuery(stringBuilder.toString());
		
		if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().isEmpty())
			query.setParameter("pNombreCaso", "%" +  casoFiltro.getNombreCasoFiltro() + "%");
		
		if (casoFiltro != null && casoFiltro.getFechaInicioFiltro() != null && casoFiltro.getFechaFinFiltro() != null)
			query.setParameter("pFechaInicioFiltro", casoFiltro.getFechaInicioFiltro()).setParameter("pFechaFinFiltro", casoFiltro.getFechaFinFiltro());
		
		if (casoFiltro != null && casoFiltro.getResponsablesFiltro() != null && !casoFiltro.getResponsablesFiltro().isEmpty())
			query.setParameter("pResponsables", casoFiltro.getResponsablesFiltro());
		
		if (casoFiltro != null && casoFiltro.getVictimaFiltro() != null && !casoFiltro.getVictimaFiltro().isEmpty()) 
			query.setParameter("pVictima", "%" + casoFiltro.getVictimaFiltro() + "%").setParameter("pTipoVictima", Integer.parseInt(Parametros.getVictima()));
		
		if (casoFiltro != null && casoFiltro.getRadicadoFiltro() != null && !casoFiltro.getRadicadoFiltro().isEmpty())
			query.setParameter("pRadicado", "%" + casoFiltro.getRadicadoFiltro() + "%");
		
		query.setParameter("pDocumentosRequeridos", "%" + Parametros.getActividadDocumentosRequeridos() + "%");
		query.setParameter("pPendiente", Parametros.getActividadPendiente());
		
		return query;
	}
	
	public List<Object[]> obtenerDocumentosPendientesExcel(CasoFiltro casoFiltro)
			throws DAOException, BusinessException {
		Query query = getQueryDocumentosPendientes(casoFiltro, false);
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> consultarCasosPorTipoCaso(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		List<Object[]> cantidadCasosPorTipoCaso = null;
		if (equipoCaso == null) {
			cantidadCasosPorTipoCaso = entityManager.createQuery("SELECT count(a), b.nombre FROM Caso a, TipoCaso b WHERE b.activo = :pActivo AND "
					+ " a.tipoCaso.codigo = b.codigo GROUP BY a.tipoCaso.codigo").setParameter("pActivo", Parametros.getCodigoActividadActivoSi()).getResultList();
		} else {
			cantidadCasosPorTipoCaso = entityManager.createQuery("SELECT count(distinct a), b.nombre FROM Caso a JOIN a.casoEquipoCasoSet c, TipoCaso b WHERE b.activo = :pActivo AND "
					+ " a.tipoCaso.codigo = b.codigo AND c.casoEquipoCasoPK.codigoEquipoCaso = :pCodigoEquipoCaso GROUP BY a.tipoCaso.codigo")
					.setParameter("pActivo", Parametros.getCodigoActividadActivoSi())
					.setParameter("pCodigoEquipoCaso", equipoCaso.getCodigoEquipoCaso())
					.getResultList();
		}
		
		return cantidadCasosPorTipoCaso;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> consultarCasosActividadesPrincipales(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		List<Object[]> casosActividadesPrincipales = null;
		try {
			if (equipoCaso == null) {
				casosActividadesPrincipales = entityManager.createNativeQuery("select   count(c.dsestadoprocesal), a.dsactividad from casos c join tipos_de_caso t on c.cdtipocaso=t.cdtipocaso"
						+ "  join actividades_tipo_caso tc on t.cdtipocaso=tc.cdtipocaso         join actividades a on tc.cdactividad=a.cdactividad "
						+ "	WHERE a.dsactividad IN (:pCodigoDocumentosRequeridos, :pCodigoSolicitudPrejudicial, :pCodigoAudiencia, :pRecursosPendientes) "
						+ " GROUP BY a.dsactividad")
						.setParameter("pCodigoDocumentosRequeridos", Parametros.getActividadPrincipalDocumento())
						.setParameter("pCodigoSolicitudPrejudicial", Parametros.getActividadPrincipalSolicitud())
						.setParameter("pCodigoAudiencia", Parametros.getActividadPrincipalAudiencia())
						.setParameter("pRecursosPendientes", Parametros.getActividadPrincipalRecursos()).getResultList();
			} else {
				casosActividadesPrincipales = entityManager.createNativeQuery("select sum(cantidad), dsactividad from "
						+ "	(select 0 as cantidad, dsactividad FROM actividades a WHERE dsactividad IN (:pCodigoDocumentosRequeridos, :pCodigoSolicitudPrejudicial, :pCodigoAudiencia, :pRecursosPendientes) "
						+ " UNION  "
						+ " Select count(c.dsestadoprocesal) as cantidad, dsactividad FROM casos c join tipos_de_caso t on c.cdtipocaso=t.cdtipocaso  join actividades_tipo_caso tc on t.cdtipocaso=tc.cdtipocaso "
						+ "  join actividades a on tc.cdactividad=a.cdactividad LEFT JOIN casos_equipos_caso cq ON c.cdcaso = cq.cdcaso WHERE dsactividad IN  (:pCodigoDocumentosRequeridos, :pCodigoSolicitudPrejudicial, "
						+ " :pCodigoAudiencia, :pRecursosPendientes) AND (cdequipocaso = :pCodigoEquipoCaso) GROUP BY dsactividad) as a "
						+ " GROUP BY dsactividad")
						.setParameter("pCodigoDocumentosRequeridos",  Parametros.getActividadPrincipalDocumento())
						.setParameter("pCodigoSolicitudPrejudicial", Parametros.getActividadPrincipalSolicitud())
						.setParameter("pCodigoAudiencia",Parametros.getActividadPrincipalAudiencia())
						.setParameter("pRecursosPendientes", Parametros.getCodigoRecursosPendientes())
						.setParameter("pCodigoEquipoCaso", Parametros.getActividadPrincipalRecursos())
						.getResultList();
			}			
		} catch (Exception e) {
			LOG.error("Error; consultando las actividades principales");
			e.printStackTrace();
		}
		return casosActividadesPrincipales;
	}
	
	@Override
	public List<Object[]> consultarCasosPorEstadoCaso(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		List<Object[]> consultarCasosPorEstadoCaso = null;
		if (equipoCaso == null) {
			consultarCasosPorEstadoCaso = entityManager.createQuery("SELECT count(a), b.nombre FROM Caso a, EstadoCaso b WHERE "
					+ " a.estadoCaso.codigo = b.codigo GROUP BY a.estadoCaso")
					.getResultList();
		} else {
			consultarCasosPorEstadoCaso = entityManager.createQuery("SELECT count(a), b.nombre FROM Caso a JOIN a.casoEquipoCasoSet c, EstadoCaso b WHERE "
					+ " a.estadoCaso.codigo = b.codigo AND c.casoEquipoCasoPK.codigoEquipoCaso = :pCodigoEquipoCaso GROUP BY a.estadoCaso")
					.setParameter("pCodigoEquipoCaso", equipoCaso.getCodigoEquipoCaso())
					.getResultList();
		}
		return consultarCasosPorEstadoCaso;
	}
	
	
	@Override
	public List<Object[]> getDemandadosCasoString(String codigoCaso)throws DAOException, BusinessException {
		List<Object[]> getDemandadosCasoString = new ArrayList<Object[]>();
		getDemandadosCasoString = entityManager.createNativeQuery("SELECT dsnombre, dsapellido AS nombre FROM equipos_caso WHERE cdequipocaso IN "
				+ "(SELECT cdequipocaso FROM casos_equipos_caso WHERE cdmiembro = :pDemandado AND cdcaso = :pCdCaso)")
						.setParameter("pDemandado", Parametros.getDemandado())
						.setParameter("pCdCaso", codigoCaso)
						.getResultList();
		return getDemandadosCasoString;
	}
	
	@Override
	public List<Object[]> consultarNombresCasos(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		List<Object[]> casos = new ArrayList<Object[]>();
		if (equipoCaso == null) {
			casos = entityManager.createNativeQuery("SELECT cdcaso, dscaso FROM casos ORDER BY dscaso asc").getResultList();			
		} else {
			casos = entityManager.createNativeQuery("SELECT a.cdcaso, a.dscaso FROM casos a JOIN casos_equipos_caso b on a.cdcaso = b.cdcaso "
					+ " WHERE b.cdequipocaso = :pCodigoEquipoCaso GROUP BY a.cdcaso ORDER BY dscaso asc")
					.setParameter("pCodigoEquipoCaso", equipoCaso.getCodigoEquipoCaso())
					.getResultList();
		}
		return casos;
	}
}