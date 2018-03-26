package com.gja.gestionCasos.casos.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.service.BienAfectadoService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;

@Controller
@RequestMapping(value = { "/bienAfectado" })

public class BienAfectadoController {

	private static final Logger LOG = Logger.getLogger(BienAfectadoController.class);

	@Autowired
	BienAfectadoService bienAfectadoService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;

	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();

		menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
		vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
				httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);

		model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
		model.addAttribute("permisos", vistaPermisosRolesWrapper);

		return "/"; // carpeta y el jsp
	}

	@RequestMapping(value = "/guardar", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String guardarBienAfectado(Caso caso) throws Exception {

		JSONObject res = new JSONObject();
		/*
		 * try {
		 * 
		 * bienesAfectados =
		 * bienAfectadoService.guardarBienAfectado(bienesAfectados);
		 * res.put("STATUS", "SUCCESS");
		 * 
		 * } catch (DAOException e) { LOG.error(
		 * "DAOException: Ocurrio un error guardando el caso . El error es: " +
		 * e.getMessage()); res.put("STATUS", "ERROR"); } catch
		 * (BusinessException e) { LOG.error(
		 * "BusinessException: Ocurrio un error guardando el caso . El error es: "
		 * + e.getMessage()); res.put("STATUS", "ERROR");
		 * 
		 * 
		 * }
		 */
		return res.toString();
	}
}
