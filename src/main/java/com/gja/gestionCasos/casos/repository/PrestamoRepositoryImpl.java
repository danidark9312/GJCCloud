package com.gja.gestionCasos.casos.repository;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.Abono;
import com.gja.gestionCasos.casos.entities.ParametroReportePrestamo;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.PrestamoConsolidadoView;
import com.gja.gestionCasos.casos.entities.PrestamoReporte;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;
import static com.sf.util.Utilidades.*;

@Repository("prestamoRepository")
public class PrestamoRepositoryImpl extends AbstractRepository<Prestamo> implements PrestamoRepository{

	public Prestamo guardarPrestamo(Prestamo prestamo) throws DAOException,
			BusinessException {
		
		prestamo=this.merge(prestamo);
		return prestamo;
	}

	
	
	
	public Prestamo obtenerPrestamo(Prestamo prestamo)
			throws DAOException, BusinessException {
		prestamo = entityManager.find(Prestamo.class, prestamo.getCodigoPrestamo());
		Hibernate.initialize(prestamo.getEntidadFinaciera());
		Hibernate.initialize(prestamo.getCaso());
		return prestamo;
	}	
	
	public List<PrestamoConsolidadoView> loadPrestamoReporte(ParametroReportePrestamo parametroReportePrestamo)throws DAOException{
		List<PrestamoConsolidadoView> consolidadoViews = null;
		
		
		Query query = buildQuery(parametroReportePrestamo);
		// query.setParameter("prestamo", prestamo.getCodigoPrestamo());
		
		consolidadoViews = query.getResultList();
		
		return consolidadoViews;
	}




	private Query buildQuery(ParametroReportePrestamo parametroReportePrestamo) {
		String sql = "select a from PrestamoConsolidadoView a ";
		if(parametroReportePrestamo.hasFiltros()) {
			sql+="where ";
		}
		if(parametroReportePrestamo.getFechaInicioFiltro()!=null && parametroReportePrestamo.getFechaFinFiltro()!=null) {
			sql+="a.fechaprestamo > :pFechaInicial and a.fechaprestamo < :pFechaFinal and ";
		}
		if(!empty(parametroReportePrestamo.getnombreCasoFiltro())) {
			sql+="a.nombreCaso like :pCasoNombre and ";
		}
		if(!empty(parametroReportePrestamo.getIdentificadorDeudor())) {
			sql+="a.identificacionDeudor < :pIdentificadorDeudor and ";
		}
		if(!empty(parametroReportePrestamo.getNombreDeudor())) {
			sql+="a.nombreDeudor < :pNombreDeudor and ";
		}
		if(parametroReportePrestamo.hasFiltros()) {
			sql+="1 = 1";
		}
		
		Query query = this.entityManager.createQuery(sql,PrestamoConsolidadoView.class);
		
		
		if(parametroReportePrestamo.getFechaInicioFiltro()!=null && parametroReportePrestamo.getFechaFinFiltro()!=null) {
			query.setParameter("pFechaFinal", parametroReportePrestamo.getFechaFinFiltro());
			query.setParameter("pFechaInicial", parametroReportePrestamo.getFechaInicioFiltro());
		}
		if(!empty(parametroReportePrestamo.getnombreCasoFiltro())) {
			query.setParameter("pCasoNombre","%"+ parametroReportePrestamo.getnombreCasoFiltro()+"%");
		}
		if(!empty(parametroReportePrestamo.getIdentificadorDeudor())) {
			query.setParameter("pIdentificadorDeudor", parametroReportePrestamo.getIdentificadorDeudor());
		}
		if(!empty(parametroReportePrestamo.getNombreDeudor())) {
			query.setParameter("pNombreDeudor", parametroReportePrestamo.getNombreDeudor());
		}
		
		return query;
	}
}
