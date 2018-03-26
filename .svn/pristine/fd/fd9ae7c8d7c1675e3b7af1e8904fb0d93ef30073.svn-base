package com.gja.gestionCasos.reportes.service;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.filters.JustificacionFiltro;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface JustificacionService {

	void guardarJustificacion(Justificacion justificacion) throws DAOException,BusinessException; 
	String construirInformacionEliminada(List<String> parametros, String parametroAuditoria) throws DAOException,BusinessException;
	
	public List<Justificacion> obtenerJustificaciones(JustificacionFiltro justificacionFiltro, int displayStart, int displayLength) throws DAOException, BusinessException;	
	public List<User> obtenerUsuariosJustificaciones() throws DAOException, BusinessException;
	Long getCantidadJustificiones(JustificacionFiltro justificacionFiltro) throws DAOException;
	
}
