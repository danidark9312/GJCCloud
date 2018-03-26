package com.gja.gestionCasos.casos.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.util.Parametros;
import com.sf.util.Utilidades;

@Controller
@RequestMapping(value = { "/visorCaso" })

public class VisorCasoController {

	private static final Logger LOG = Logger.getLogger(VisorCasoController.class);
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;

	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal, String tipoCaso, String actividadPrincipal, String estado) {
		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();
		String tipoCasoDecodificado = "";
		String actividadPrincipalDecodificado = "";
		String estadoDecodificado = "";
		try {
			menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
			vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
					httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);
			model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
			model.addAttribute("permisos", vistaPermisosRolesWrapper);
			model.addAttribute("aniosCaducidad", Parametros.getAnosCaducidad());
			// Decodificar dato que viene por URL
			if (tipoCaso != null)
				tipoCasoDecodificado = Utilidades.decodificarCaracteres(tipoCaso);
			if (actividadPrincipal != null)
				actividadPrincipalDecodificado = Utilidades.decodificarCaracteres(actividadPrincipal);
			if (estado != null)
				estadoDecodificado = Utilidades.decodificarCaracteres(estado);
			model.addAttribute("tipoCaso", tipoCasoDecodificado);
			model.addAttribute("actividadPrincipal", actividadPrincipalDecodificado);
			model.addAttribute("estado", estadoDecodificado);
		} catch (Exception e) {
			LOG.error("Error; cargado pagina visor de caso");
		}
		return "casos/visorCaso"; // carpeta y el jsp
	}

}
