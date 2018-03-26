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

import com.gja.gestionCasos.permisos.service.OpcionesService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Opcion;
import com.sf.roles.Rol;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

/**
 * @author Usuario
 *
 */
@Controller
@RequestMapping(value = { "/permisosOpciones" })
public class OpcionesController {
	private static final Logger LOG = Logger.getLogger(OpcionesController.class);
	private static final String estadoExito = "SUCCESS";
	private static final String estadoError = "ERROR";
	private static final String status = "status";

	@Autowired
	OpcionesService opcionesService;
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
			model.addAttribute("permisos", vistaPermisosRolesWrapper);

			model.addAttribute("opciones", opcionesService.getOpciones());
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las opciones. El error es: " + e.getMessage());
			e.printStackTrace();
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las opciones. El error es: " + e.getMessage());
			e.printStackTrace();
		}

		return "permisos/opciones";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asignarOpciones", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String asignOpcionesRol(@ModelAttribute("rol") Rol rol,
			@ModelAttribute("Opcion") List<Opcion> opciones) {
		JSONObject res = new JSONObject();

		try {
			if (opcionesService.asignOpcionesRol(rol, opciones))
				res.put(status, estadoExito);
			else
				res.put(status, estadoError);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error actualizando las opciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error actualizando las opciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		}

		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarOpciones", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getOpcionesRol() {
		List<Opcion> opciones = new ArrayList<Opcion>();
		JSONObject res = new JSONObject();
		JSONArray opcionesJson = new JSONArray();

		try {
			opciones = opcionesService.getOpciones();
			for (Opcion opcion : opciones) {
				JSONObject opcionObj = new JSONObject();
				opcionObj.put("nombre", opcion.getNombre());
				opcionObj.put("codigo", opcion.getCodigo());
				opcionObj.put("descripcion", opcion.getDescripcion());
				opcionesJson.add(opcionObj);
			}
			res.put("opciones", opcionesJson);
			res.put(status, estadoExito);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las opciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las opciones. El error es: " + e.getMessage());
			res.put(status, estadoError);
		}

		return res.toString();
	}

	@RequestMapping(value = "/consultarOpcionesRol", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getOpcionesRol(@ModelAttribute("rol") Rol rol) {

		return "";
	}

	@RequestMapping(value = "/desasignarOpciones", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String unasignOpcionesRol(@ModelAttribute("rol") Rol rol,
			@ModelAttribute("Opcion") List<Opcion> opciones) {

		return "";
	}

}
