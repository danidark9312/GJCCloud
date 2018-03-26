package com.gja.gestionCasos.reportes.repository;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Query;

import org.docx4j.docProps.variantTypes.Empty;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.filters.JustificacionFiltro;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;

@Repository("justificacionRepository")
public class JustificacionRepositoryImpl  extends AbstractRepository<Justificacion> implements JustificacionRepository{

	// Consultas de auditorias
	@Override
	public List<Justificacion> obtenerJustificaciones(JustificacionFiltro justificacionFiltro, int displayStart, int displayLength) throws DAOException, BusinessException {
		SimpleDateFormat auditoriaFechaFormato = new SimpleDateFormat(Parametros.getFormatoAnioMesDiaFechaString());
		List<Justificacion> obtenerTodasLasJustificaciones = null;
		StringBuilder strBuilder = new StringBuilder();	
		
		strBuilder.append("SELECT j FROM Justificacion j JOIN FETCH j.codigoCaso WHERE 1=1 ");
		
		// Filtro de fechas, en este caso, al agregarle asi el filtro, se le debe enviar por medio de una string, osea mira como se usan la ''
		if (justificacionFiltro != null && justificacionFiltro.getAuditoriaFechaInicioFiltro() != null ) {					
			strBuilder.append(" AND date_format(j.fechaModificacion , '%Y-%m-%d') >= '" + auditoriaFechaFormato.format(justificacionFiltro.getAuditoriaFechaInicioFiltro()) + "'");			
						
		} 
		
		if (justificacionFiltro != null  && justificacionFiltro.getAuditoriaFechaFinFiltro() != null ) {					
			strBuilder.append(" AND  date_format(j.fechaModificacion , '%Y-%m-%d') <= '" + auditoriaFechaFormato.format(justificacionFiltro.getAuditoriaFechaFinFiltro()) + "'");			
						
		} 
		//Filtro de responsables
		if(justificacionFiltro != null && justificacionFiltro.getAuditoriaResponsablesFiltro() != null && !justificacionFiltro.getAuditoriaResponsablesFiltro().isEmpty()){
			strBuilder.append(" AND j.usuarioModificacion.id  = " + justificacionFiltro.getAuditoriaResponsablesFiltro().get(0));
		}
		
		// Filtro de casos, en este ejemplo, al agregarle asi el filtro, se le debe enviar por medio de una string, osea mira como se usan la ''
		if (justificacionFiltro != null && justificacionFiltro.getAuditoriaNombreCasoFiltro() != null && !justificacionFiltro.getAuditoriaNombreCasoFiltro().isEmpty()) {
			strBuilder.append(" AND j.codigoCaso.nombre LIKE '%" + justificacionFiltro.getAuditoriaNombreCasoFiltro() + "%'");
		}
		
		strBuilder.append(" ORDER BY j.fechaModificacion DESC ");
				
		
		obtenerTodasLasJustificaciones = entityManager.createQuery(strBuilder.toString()).setFirstResult(displayStart).setMaxResults(displayLength).getResultList();				
		return obtenerTodasLasJustificaciones;
	}
	
	@Override
	public List<User> obtenerUsuariosJustificaciones() throws DAOException,	BusinessException {
		
		List<User> usuariosEnJustificaciones = entityManager.createQuery("SELECT DISTINCT a FROM User a JOIN a.justificacionesList ORDER BY a.nombre").getResultList();				
		return usuariosEnJustificaciones;
	}

	@Override
	public Long getCantidadJustificiones(JustificacionFiltro justificacionFiltro) throws DAOException {
		SimpleDateFormat auditoriaFechaFormato = new SimpleDateFormat(Parametros.getFormatoAnioMesDiaFechaString());
		Long cantidadArchivos = null;
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			stringBuilder.append ("SELECT count(j) FROM Justificacion j WHERE 1=1 ");
			
			if (justificacionFiltro != null && justificacionFiltro.getAuditoriaFechaInicioFiltro() != null && justificacionFiltro.getAuditoriaFechaFinFiltro() != null ) {					
				stringBuilder.append(" AND j.fechaModificacion >= '" + auditoriaFechaFormato.format(justificacionFiltro.getAuditoriaFechaInicioFiltro()) + "' AND j.fechaModificacion <= '" + auditoriaFechaFormato.format(justificacionFiltro.getAuditoriaFechaFinFiltro()) + "'");										
			}
			
			if(justificacionFiltro != null && justificacionFiltro.getAuditoriaResponsablesFiltro() != null && !justificacionFiltro.getAuditoriaResponsablesFiltro().isEmpty()){
				stringBuilder.append(" AND j.usuarioModificacion.id  = " + justificacionFiltro.getAuditoriaResponsablesFiltro().get(0));
			}
			
			if (justificacionFiltro != null && justificacionFiltro.getAuditoriaNombreCasoFiltro() != null && !justificacionFiltro.getAuditoriaNombreCasoFiltro().isEmpty()) {
				stringBuilder.append(" AND j.codigoCaso.nombre LIKE '%" + justificacionFiltro.getAuditoriaNombreCasoFiltro() + "%'");
			}
			
			Query query =  entityManager.createQuery(stringBuilder.toString());
													
			
			cantidadArchivos =  (Long)query.getSingleResult();
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		return cantidadArchivos;
	}
}
