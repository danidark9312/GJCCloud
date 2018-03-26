package com.gja.gestionCasos.maestros.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.TareaParticularCasoRepository;
import com.gja.gestionCasos.maestros.controller.MaestroEscalamientoNotificacionController;
import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;
import com.gja.gestionCasos.maestros.entities.AlertasTareasRolDetalle;
import com.gja.gestionCasos.maestros.entities.AlertasTareasVencidaRolDetalle;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.gja.gestionCasos.maestros.repository.AlertaTareaRolDetalleRepository;
import com.gja.gestionCasos.maestros.repository.AlertaTareaRolDetalleVencidaRepository;
import com.gja.gestionCasos.maestros.repository.MaestroEscalamientoNotificacionRepository;
import com.gja.gestionCasos.maestros.repository.MaestroEscalamientoNotificacionVencidaRepository;
import com.gja.gestionCasos.maestros.repository.RolesUsuariosRepository;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

import net.minidev.json.JSONArray;

@Service("maestroEscalamientoNotificacionService")
public class MaestroEscalamientoNotificacionServiceImpl implements MaestroEscalamientoNotificacionService {
	
	private static final Logger LOG = Logger.getLogger(MaestroEscalamientoNotificacionController.class);
	private static final String ESTADOEXITO = "SUCCESS";

	@Autowired
	TareaParticularCasoRepository tareaParticularCasoRepository;
	
	@Autowired
	AlertaTareaRolDetalleRepository AlertaTareaRolDetalleRepository; 
	
	@Autowired
	MaestroEscalamientoNotificacionRepository maestroEscalamientoNotificacionRepository;
	
	@Autowired
	MaestroEscalamientoNotificacionVencidaRepository maestroEscalamientoNotificacionVencidaRepository;
	
	@Autowired
	AlertaTareaRolDetalleVencidaRepository alertaTareaRolDetalleVencidaRepository;
	
	@Autowired
	RolesUsuariosRepository rolesUsuariosRepository; 
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JSONObject consultarConfigiracionAlertaProximaAVencer(AlertaTareaRol alertaTareaRol) throws BusinessException, DAOException {
		JSONObject root = new JSONObject();
	
		try {
			alertaTareaRol =maestroEscalamientoNotificacionRepository.consultarConfiguracion(alertaTareaRol);
			if(null != alertaTareaRol){
				root =	construirJsonRespuestaProximasVencer(alertaTareaRol);
			}
			root.put("STATUS", ESTADOEXITO);
		} catch (Exception e) {
			LOG.error("Error; guardando el detalle de alerta de tarea por roles");
		}
		return root;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject construirJsonRespuestaProximasVencer(AlertaTareaRol alertaTareaRol) throws BusinessException{
		JSONObject response = new JSONObject();
		JSONArray arrayRoles = new JSONArray();
		JSONObject roles = new JSONObject();
		try {
			response.put("numeroAlertaDiaria", alertaTareaRol.getNumeroAlertaDiaria());
			response.put("numeroDiasAntes", alertaTareaRol.getNumeroDiasAntes());
			response.put("estado", alertaTareaRol.getEstado());
			for(AlertasTareasRolDetalle objDetalle : alertaTareaRol.getAlertasTareasRolDetalleCollection()){
				roles = new JSONObject();
				roles.put("rolCodigo", objDetalle.getRol().getCodigo());
				arrayRoles.add(roles);
			}
			response.put("roles", arrayRoles);
		} catch (Exception e) {
			LOG.error("Error; creando el json de respuesta de las configuracion actual de las tareas proximas a vencer");
		}
		return response;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public AlertaTareaRol guardarEscalamientoNotificacion(AlertaTareaRol alertaTareaRol,TareaParticularCaso objTareaParticular) throws BusinessException, DAOException {
		AlertaTareaRol alertaTareaRolTemp = new AlertaTareaRol();
		try {
					alertaTareaRol.setTareaparticular(objTareaParticular);
					alertaTareaRol =calcularFechaAlert(alertaTareaRol);
					alertaTareaRol.setAlertasTareasRolDetalleCollection(null);
					alertaTareaRolTemp=	maestroEscalamientoNotificacionRepository.merge(alertaTareaRol);
		} catch (Exception e) {
			LOG.error("Error; guardando las alerta de tarea por roles");
		}
		return alertaTareaRolTemp;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JSONObject consultarConfigiracionVencida(AlertaTareaVencidaRol alertaTareaVencidaRol) throws BusinessException, DAOException {
		JSONObject root = new JSONObject();
		try {
			alertaTareaVencidaRol =maestroEscalamientoNotificacionVencidaRepository.consultarConfiguracionVencida(alertaTareaVencidaRol);
			if(null != alertaTareaVencidaRol){
				root =	construirJsonRespuestaVencida(alertaTareaVencidaRol);
			}
			root.put("STATUS", ESTADOEXITO);
		} catch (Exception e) {
			LOG.error("Error; guardando el detalle de alerta de tarea por roles");
		}
		return root;
	}

	@SuppressWarnings("unchecked")
	private JSONObject construirJsonRespuestaVencida(AlertaTareaVencidaRol alertaTareaVencidaRol) throws BusinessException{
		JSONObject response = new JSONObject();
		JSONArray arrayRoles = new JSONArray();
		JSONObject roles = new JSONObject();
		try {
			response.put("numeroAlertaDiaria", alertaTareaVencidaRol.getNumeroAlertasDiarias());
			response.put("numeroDiasDespues", alertaTareaVencidaRol.getNumeroDiaDespues());
			response.put("estado", alertaTareaVencidaRol.getEstado());
			for(AlertasTareasVencidaRolDetalle objDetalle : alertaTareaVencidaRol.getAlertasTareasDespuesRolDetalleList()){
				roles = new JSONObject();
				roles.put("rolCodigo", objDetalle.getRol().getCodigo());
				arrayRoles.add(roles);
			}
			response.put("roles", arrayRoles);
		} catch (Exception e) {
			LOG.error("Error; creando el json de respuesta de las configuracion actual de las tareas proximas a vencer");
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public AlertaTareaVencidaRol guardarEscalamientoNotificacionVencida(AlertaTareaVencidaRol alertaTareaRol,TareaParticularCaso objTareaParticular) throws BusinessException, DAOException {
		AlertaTareaVencidaRol alertaTareaRolTemp = new AlertaTareaVencidaRol();		try {
					alertaTareaRol.setTareaParticular(objTareaParticular);
					alertaTareaRol =calcularFechaAlertVencida(alertaTareaRol);
					alertaTareaRol.setAlertasTareasDespuesRolDetalleList(null);
					alertaTareaRolTemp =maestroEscalamientoNotificacionVencidaRepository.merge(alertaTareaRol);
		} catch (Exception e) {
			LOG.error("Error; guardando las alerta de tarea por roles");
		}
		return alertaTareaRolTemp;
	}
	
	
	@Transactional
	public  int eliminarNotificacionesRolesDetalle(AlertaTareaRol alertaRol) throws DAOException, BusinessException{
		int result = 0;
		try {
			AlertaTareaRolDetalleRepository.eliminar(alertaRol);
		} catch (Exception e) {
			LOG.error("Error; eliminando las alerta de tarea por roles");
		}
		return result;
	}
	
	
	@Transactional
	public  Integer eliminarNotificacionesRoles(Integer resultadoDetalle, AlertaTareaRol alertaRol) throws DAOException, BusinessException{
		Integer result = 0;
		try {
			if(null != resultadoDetalle){
				result =maestroEscalamientoNotificacionRepository.eliminar(alertaRol);				
			}
		} catch (Exception e) {
			LOG.error("Error; eliminando las alerta de tarea por roles");
		}
		return result;
	}
	
	private AlertaTareaRol calcularFechaAlert(AlertaTareaRol alertaTareaRol) throws BusinessException{
		try {
			if(null != alertaTareaRol.getNumeroDiasAntes() && null != alertaTareaRol.getTareaparticular().getFechaLimite()){
				alertaTareaRol.setFePrimeraAlerta(restarDiasFecha (alertaTareaRol.getTareaparticular().getFechaLimite(), alertaTareaRol.getNumeroDiasAntes()));
			}
		} catch (Exception e) {
			LOG.error("Error; calculando los dias de alerta");
		}
		return alertaTareaRol;
	}
	
@SuppressWarnings("unchecked")
 private  Date restarDiasFecha(Date fecha, int dias)throws BusinessException{
	  Date nuevaFecha = null;
	  try {
		  Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); 
	      calendar.add(Calendar.DAY_OF_YEAR, -dias); 
	      nuevaFecha = calendar.getTime(); 
	} catch (Exception e) {
		LOG.error("Error; sumando dias a la fecha de perimera alerta");
	}
      return nuevaFecha;
 }
	
@SuppressWarnings("unchecked")
private  Date sumarDiasFecha(Date fecha, int dias)throws BusinessException{
	  Date nuevaFecha = null;
	  try {
		  Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); 
	      calendar.add(Calendar.DAY_OF_YEAR, dias); 
	      nuevaFecha = calendar.getTime(); 
	} catch (Exception e) {
		LOG.error("Error; restando dias a la fecha de perimera alerta");
	}
     return nuevaFecha;
}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JSONObject guardarEscalamientoNotificacionDetelle(List<AlertaTareaRol> listRolTareas) throws BusinessException, DAOException {
		JSONObject root = new JSONObject();
		try {
			if(null != listRolTareas && !listRolTareas.isEmpty()){
				for(AlertaTareaRol objPadre :listRolTareas){
					for(AlertasTareasRolDetalle objDetalle :objPadre.getAlertasTareasRolDetalleCollection()){
						objDetalle.setAlertaTareaRol(objPadre);
						AlertaTareaRolDetalleRepository.merge(objDetalle);
					}
				}
			}
			root.put("STATUS", ESTADOEXITO);
		} catch (Exception e) {
			LOG.error("Error; guardando el detalle de alerta de tarea por roles");
		}
		return root;
	}
	
	

	

	private AlertaTareaVencidaRol calcularFechaAlertVencida(AlertaTareaVencidaRol alertaTareaRol) throws BusinessException{
		try {
			if(null != alertaTareaRol.getNumeroDiaDespues() && null != alertaTareaRol.getTareaParticular().getFechaLimite()){
				alertaTareaRol.setFeUltimaAlerta(sumarDiasFecha (alertaTareaRol.getTareaParticular().getFechaLimite(), alertaTareaRol.getNumeroDiaDespues()));
			}
		} catch (Exception e) {
			LOG.error("Error; calculando los dias de alerta");
		}
		return alertaTareaRol;
	}
	@Transactional
	public  int eliminarNotificacionesRolesDetalleVencido(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException, BusinessException{
		int result = 0;
		try {
			alertaTareaRolDetalleVencidaRepository.eliminar(alertaTareaVencidaRol);
		} catch (Exception e) {
			LOG.error("Error; eliminando las alerta de tarea por roles");
		}
		return result;
	}
	
	
	@Transactional
	public  Integer eliminarNotificacionesRolesVencido(Integer resultadoDetalle,AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException, BusinessException{
		Integer result = 0;
		try {
			if(null != resultadoDetalle && resultadoDetalle > 0){
				result =maestroEscalamientoNotificacionVencidaRepository.eliminar(alertaTareaVencidaRol);				
			}
		} catch (Exception e) {
			LOG.error("Error; eliminando las alerta de tarea por roles");
		}
		return result;
	}
	
	
	
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JSONObject guardarEscalamientoNotificacionDetelleVencido(List<AlertaTareaVencidaRol> listRolTareas) throws BusinessException, DAOException {
		JSONObject root = new JSONObject();
		try {
			if(null != listRolTareas && !listRolTareas.isEmpty()){
				for(AlertaTareaVencidaRol objPadre :listRolTareas){
					for(AlertasTareasVencidaRolDetalle objDetalle :objPadre.getAlertasTareasDespuesRolDetalleList()){
						objDetalle.setTareaParticularRol(objPadre);
						alertaTareaRolDetalleVencidaRepository.merge(objDetalle);
					}
				}
			}
			root.put("STATUS", ESTADOEXITO);
		} catch (Exception e) {
			LOG.error("Error; guardando el detalle de alerta de tarea por roles");
		}
		return root;
	}

	@Transactional
	public List<AlertaTareaRol> consultarNotificacionesAbiertas() throws BusinessException{
		Date fechaActual = new Date();
		SimpleDateFormat sfdA = new SimpleDateFormat("yyyy");
		SimpleDateFormat sfdM = new SimpleDateFormat("MM");
		SimpleDateFormat sfdD = new SimpleDateFormat("dd");
		 List<AlertaTareaRol> list = new  ArrayList<AlertaTareaRol>();
		try {
			list =maestroEscalamientoNotificacionRepository.consultarNotificacionEnviar(Integer.parseInt(sfdA.format(fechaActual)), Integer.parseInt(sfdM.format(fechaActual)),  Integer.parseInt(sfdD.format(fechaActual)));
			if(null != list){
				for(AlertaTareaRol objRol :list){
					Hibernate.initialize(objRol.getAlertasTareasRolDetalleCollection());
					Hibernate.initialize(objRol.getTareaparticular().getResponsablesTareas());
				}
			}
		} catch (NumberFormatException e) {
			LOG.error("Error; consultando las notificaciones abiertas");
			e.printStackTrace();
		} catch (DAOException e) {
			LOG.error("Error; consultando las notificaciones abiertas");
			e.printStackTrace();
		}
		return list;
	}
	@Transactional
	public List<AlertaTareaVencidaRol> consultarNotificacionesVencidas() throws BusinessException, DAOException{
		Date fechaActual = new Date();
		SimpleDateFormat sfdA = new SimpleDateFormat("yyyy");
		SimpleDateFormat sfdM = new SimpleDateFormat("MM");
		SimpleDateFormat sfdD = new SimpleDateFormat("dd");
		 List<AlertaTareaVencidaRol> list = new  ArrayList<AlertaTareaVencidaRol>();
		try {
			list =maestroEscalamientoNotificacionVencidaRepository.consultarNotificacionEnviarVencida(Integer.parseInt(sfdA.format(fechaActual)), Integer.parseInt(sfdM.format(fechaActual)),  Integer.parseInt(sfdD.format(fechaActual)));
			if(null != list){
				for(AlertaTareaVencidaRol objRol :list){
					Hibernate.initialize(objRol.getAlertasTareasDespuesRolDetalleList());
					Hibernate.initialize(objRol.getTareaParticular().getResponsablesTareas());
				}
			}
		} catch (NumberFormatException e) {
			LOG.error("Error; consultando las notificaciones abiertas");
			e.printStackTrace();
		} catch (DAOException e) {
			LOG.error("Error; consultando las notificaciones abiertas");
			e.printStackTrace();
		}
		return list;
	}
	
	public 	List<Object>  getUsuarioByRol (Rol rol, TipoCaso tipoCaso)  throws BusinessException, DAOException{
		List<Object>   list = new  ArrayList<Object>();
		try {
			if (null != rol)
				list =rolesUsuariosRepository.findByNotificacion(rol,tipoCaso);
		} catch (Exception e) {
			LOG.error("Error; consultando los usuarios por roles");
		}
		return list;
	}
	
	@Transactional
	public Integer updateNotificacion(AlertaTareaRol alertaTareaRol)throws BusinessException, DAOException{
	if(null ==alertaTareaRol.getTotalNotificacion()){
		alertaTareaRol.setTotalNotificacion(1);
	}else{
		alertaTareaRol.setTotalNotificacion(alertaTareaRol.getTotalNotificacion()+1);
	}
	 return maestroEscalamientoNotificacionRepository.update(alertaTareaRol);
	}
	
	
	public Integer updateNotificacionVencida(AlertaTareaVencidaRol alertaTareaRol)throws BusinessException, DAOException{
		if(null == alertaTareaRol.getTotalNotificacion()){
			alertaTareaRol.setTotalNotificacion(1);
		}else{
			alertaTareaRol.setTotalNotificacion(alertaTareaRol.getTotalNotificacion()+1);
		}
		 return maestroEscalamientoNotificacionVencidaRepository.update(alertaTareaRol);
		}
		

}
