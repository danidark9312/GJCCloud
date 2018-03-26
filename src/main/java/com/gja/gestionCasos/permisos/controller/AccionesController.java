/**
 * 
 */
package com.gja.gestionCasos.permisos.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.permisos.service.AccionesService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.RolAccionOpcion;
import com.sf.roles.RolAccionOpcionPK;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author Usuario
 *
 */
@Controller
@RequestMapping(value = { "/permisosAcciones" })
public class AccionesController {
	private static final Logger LOG = Logger.getLogger(OpcionesController.class);
	private static final String estadoExito = "SUCCESS";
	private static final String estadoError = "ERROR";
	private static final String status = "status";

	@Autowired
	AccionesService accionesService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;

	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		try {
			MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
			VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();

			menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
			vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
					httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);

			model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
			model.addAttribute("acciones", accionesService.getAcciones());
			model.addAttribute("permisos", vistaPermisosRolesWrapper);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las acciones. El error es: " + e.getMessage());
			e.printStackTrace();
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las acciones. El error es: " + e.getMessage());
			e.printStackTrace();
		}

		return "permisos/acciones";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asignarAcciones", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String asignAccionesRol(@ModelAttribute("rolAccionOpcion") RolAccionOpcionPK rolAccionOpcionPk,
			@ModelAttribute("Opcion") List<RolAccionOpcion> accionesOpciones) {
		JSONObject res = new JSONObject();

		try {
			if (accionesService.asignAccionesRol(rolAccionOpcionPk, accionesOpciones))
				res.put(status, estadoExito);
			else
				res.put(status, estadoError);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error actualizando las acciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error actualizando las acciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		}

		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarAcciones", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getAccionesRol() {
		List<String> acciones = new ArrayList<String>();
		JSONArray accionesArray = new JSONArray();
		JSONObject res = new JSONObject();

		try {
			acciones = accionesService.getAcciones();

			for (String accion : acciones) {
				accionesArray.add(accion.toString());
			}

			res.put("acciones", accionesArray);
			res.put(status, estadoExito);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las acciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las acciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		}

		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarAccionesRol", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getAccionesRol(@ModelAttribute("rol") RolAccionOpcionPK rolAccionOpcionPk) {
		List<RolAccionOpcion> accionesOpciones = new ArrayList<RolAccionOpcion>();
		JSONObject res = new JSONObject();

		try {
			accionesOpciones = (List<RolAccionOpcion>) accionesService.getAcciones(rolAccionOpcionPk);
			res.put(status, estadoExito);
			res.put("acciones", accionesOpciones);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las acciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las acciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		}

		return res.toString();
	}

	@RequestMapping(value = "/desasignarAcciones", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String unasignAccionesRol(@ModelAttribute("rol") Rol rol,
			@ModelAttribute("Opcion") List<RolAccionOpcion> acciones) {

		return "";
	}

}
