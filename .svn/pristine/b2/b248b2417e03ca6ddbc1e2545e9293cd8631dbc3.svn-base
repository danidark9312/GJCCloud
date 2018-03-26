package com.gja.gestionCasos.reportes.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.filters.JustificacionFiltro;
import com.gja.gestionCasos.maestros.entities.Parentesco;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.gja.gestionCasos.reportes.repository.JustificacionRepositoryImpl;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("justificacionService")
@Transactional(readOnly = true)
public class JustificacionServiceImpl implements JustificacionService {
	
	@Autowired
	JustificacionRepositoryImpl justificacionRepository; 
	
	@Transactional
	public void guardarJustificacion(Justificacion justificacion)throws DAOException, BusinessException {
		
		justificacion.setFechaModificacion(new Date());
		justificacion = justificacionRepository.merge(justificacion);		
	}

	
	public String construirInformacionEliminada(List<String> parametros, String parametroAuditoria)throws DAOException, BusinessException {
		for (int i = 0 ; i < parametros.size(); i++) {
			parametroAuditoria = parametroAuditoria.replace("{" + i + "}", parametros.get(i));
		}
		return parametroAuditoria;
	}


	@Override
	public List<Justificacion> obtenerJustificaciones(JustificacionFiltro justificacionFiltro, int displayStart, int displayLength) throws DAOException, BusinessException {
		List<Justificacion> todasLasjustificaciones = null;
		todasLasjustificaciones = justificacionRepository.obtenerJustificaciones(justificacionFiltro, displayStart, displayLength);
		return todasLasjustificaciones;
	}


	@Override
	public List<User> obtenerUsuariosJustificaciones() throws DAOException,	BusinessException {
		List<User> todasLosUsuariosjustificaciones = null;
		todasLosUsuariosjustificaciones = justificacionRepository.obtenerUsuariosJustificaciones();
		return todasLosUsuariosjustificaciones;
	}


	@Override
	public Long getCantidadJustificiones(JustificacionFiltro justificacionesFiltro) throws DAOException {
		return justificacionRepository.getCantidadJustificiones(justificacionesFiltro);
	}
	
}
