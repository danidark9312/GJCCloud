package com.gja.gestionCasos.maestros.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.AlertaTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.TareaParticularCasoRepository;
import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;
import com.gja.gestionCasos.maestros.entities.AlertasTareasRolDetalle;
import com.gja.gestionCasos.maestros.entities.AlertasTareasVencidaRolDetalle;
import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.gja.gestionCasos.maestros.service.MaestroEscalamientoNotificacionService;
import com.gja.gestionCasos.permisos.service.RolesService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

@Controller
@RequestMapping("/maestroEscalamientoNotificacion")
public class MaestroEscalamientoNotificacionController {


	private static final String ESTADOERROR = "ERROR";

	private static final Logger LOG = Logger.getLogger(MaestroEscalamientoNotificacionController.class);
	
	@Autowired
	RolesService rolesService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;
	@Autowired
	MaestroEscalamientoNotificacionService maestroEscalamientoNotificacionService;
	@Autowired
	TareaParticularCasoRepository tareaParticularCasoRepository;
	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		try {
			List<Rol> roles = new ArrayList<Rol>();
			MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
			VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();
			menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
			roles = rolesService.getRoles();
			vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
					httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);
			model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
			model.addAttribute("permisos", vistaPermisosRolesWrapper);
			model.addAttribute("roles", roles);
		} catch (DAOException e) {
			LOG.error("Error; creando la vista del maestro de escalamiento de  notificaciones "); 
			e.printStackTrace();
		} catch (BusinessException e) {
			LOG.error("Error; creando la vista del maestro de escalamiento de  notificaciones "); 
			e.printStackTrace();
		}
		return "maestros/maestroEscalamientoNotificacion";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/guardarConfiguracionAntes", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String guardarConfiguracionAntes(@ModelAttribute("alertaRol") AlertaTareaRol alertaRol) {
		JSONObject res = new JSONObject();
		Integer result = 0;
		List<AlertaTareaRol> listAlertaRoles = new ArrayList<AlertaTareaRol>();
		List<AlertasTareasRolDetalle> alertasTareasRolDetalle = new ArrayList<AlertasTareasRolDetalle>();
		AlertaTareaRol alertaTareaRolTemp = new AlertaTareaRol();
		try {
			result = maestroEscalamientoNotificacionService.eliminarNotificacionesRolesDetalle(alertaRol);
			result =maestroEscalamientoNotificacionService.eliminarNotificacionesRoles(result,alertaRol);
			if(null != result && result >= 0){
				List<TareaParticularCaso> tareasPendientes = tareaParticularCasoRepository.obtenerTareasPendientesFechaActual();
				if(null != tareasPendientes && !tareasPendientes.isEmpty()){
					alertasTareasRolDetalle=alertaRol.getAlertasTareasRolDetalleCollection();
					for(TareaParticularCaso objTareaParticular: tareasPendientes){
						alertaTareaRolTemp =maestroEscalamientoNotificacionService.guardarEscalamientoNotificacion(alertaRol,objTareaParticular);
						alertaTareaRolTemp.setAlertasTareasRolDetalleCollection(alertasTareasRolDetalle);
						listAlertaRoles.add(alertaTareaRolTemp );
					}
				}
				res =maestroEscalamientoNotificacionService.guardarEscalamientoNotificacionDetelle(listAlertaRoles);
			}
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error guardando la alerta por rol . El error es: " + e.getMessage());
			res.put("STATUS", ESTADOERROR);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error guardando la alerta por rol . El error es: " + e.getMessage());
			res.put("STATUS", ESTADOERROR);
		}
		return res.toString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarConfigProximaVencer", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String consultarConfigProximaVencer( @ModelAttribute("alertaRol") AlertaTareaRol alertaRol) {
		JSONObject res = new JSONObject();
		try {
			res =	maestroEscalamientoNotificacionService.consultarConfigiracionAlertaProximaAVencer(alertaRol);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando la alerta por rol proximas a vencer . El error es: " + e.getMessage());
			res.put("STATUS", ESTADOERROR);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando la alerta por rol proximas a vencer . El error es: " + e.getMessage());
			res.put("STATUS", ESTADOERROR);
		}
		return res.toString();
	}

	
	@RequestMapping(value = "/guardarConfiguracionDespues", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String guardarConfiguracionDespues(@ModelAttribute("alertaTareaVencidaRol") AlertaTareaVencidaRol alertaTareaVencidaRol) {
		JSONObject res = new JSONObject();
		List<AlertaTareaVencidaRol> listaRoles = new ArrayList<AlertaTareaVencidaRol>();
		List<AlertasTareasVencidaRolDetalle> alertasTareasRolDetalle = new ArrayList<AlertasTareasVencidaRolDetalle>();
		AlertaTareaVencidaRol alertaTareaRolTemp = new AlertaTareaVencidaRol();
		Integer result = 0;
		try {
			result = maestroEscalamientoNotificacionService.eliminarNotificacionesRolesDetalleVencido(alertaTareaVencidaRol);
			result =maestroEscalamientoNotificacionService.eliminarNotificacionesRolesVencido(result,alertaTareaVencidaRol);
			if(null != result && result >= 0){
				List<TareaParticularCaso> tareasPendientes = tareaParticularCasoRepository.obtenerTareasPendientesFechaActual();
				if(null != tareasPendientes && !tareasPendientes.isEmpty()){
					alertasTareasRolDetalle=alertaTareaVencidaRol.getAlertasTareasDespuesRolDetalleList();
					for(TareaParticularCaso objTareaParticular: tareasPendientes){
						alertaTareaRolTemp =	 maestroEscalamientoNotificacionService.guardarEscalamientoNotificacionVencida(alertaTareaVencidaRol,objTareaParticular);
						alertaTareaRolTemp.setAlertasTareasDespuesRolDetalleList(alertasTareasRolDetalle);
						listaRoles.add(alertaTareaRolTemp );
					}
				}
				res =maestroEscalamientoNotificacionService.guardarEscalamientoNotificacionDetelleVencido(listaRoles);
			}
			} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error guardando la alerta por rol . El error es: " + e.getMessage());
			res.put("STATUS", ESTADOERROR);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error guardando la alerta por rol . El error es: " + e.getMessage());
			res.put("STATUS", ESTADOERROR);
		}
		return res.toString();
	}

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarConfingVencida", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String consultarConfingVencida(@ModelAttribute("alertaTareaVencidaRol") AlertaTareaVencidaRol alertaTareaVencidaRol) {
		JSONObject res = new JSONObject();
		try {
			res =	maestroEscalamientoNotificacionService.consultarConfigiracionVencida(alertaTareaVencidaRol);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando la alerta por rol proximas a vencer . El error es: " + e.getMessage());
			res.put("STATUS", ESTADOERROR);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando la alerta por rol proximas a vencer . El error es: " + e.getMessage());
			res.put("STATUS", ESTADOERROR);
		}
		return res.toString();
	}
}
