package com.gja.gestionCasos.maestros.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.service.EquipoCasoService;
import com.gja.gestionCasos.maestros.service.MaestroActividadesService;
import com.gja.gestionCasos.maestros.service.TipoCasoService;
import com.gja.gestionCasos.permisos.service.AccionesService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Controller
@RequestMapping(value = { "/maestroReasignacionCasos" })
public class MaestroReasignacionCasoController {

	private static final Logger LOG = Logger.getLogger(MaestroReasignacionCasoController.class);
	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";
	@Autowired
	AccionesService accionesService;
	@Autowired
	MaestroActividadesService maestroActividadService;
	@Autowired
	TipoCasoService tipoCasoService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;
	
	@Autowired
	EquipoCasoService equipoCasoService; 
	
	

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
			model.addAttribute("abogados", equipoCasoService.obtenerAbogados());
			

		} catch (Exception e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		return "maestros/maestroReasignacionCasos";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarCasosAbogado", produces = "application/json; charset=utf-8")
	public @ResponseBody String consultarActividad(User abogado) {
		JSONObject res = new JSONObject();
		try {
			List<Caso> casosAbogado = equipoCasoService.obtenerCasosAbogado(abogado);
			JSONObject jsO = new JSONObject();

			JSONArray arrayCasos = new JSONArray();
			JSONObject cecJson = null;
			for (Caso caso: casosAbogado) {
				jsO = new JSONObject();
				jsO.put("codigo", caso.getCodigo());
				jsO.put("nombre", caso.getNombre());
				jsO.put("estado", caso.getEstadoCaso().getNombre());
				for(CasoEquipoCaso cec : caso.getCasoEquipoCasoSet()) {
					if(cec.getEquipoCaso().getIdentificacion().equals(abogado.getId())) {
						cecJson = new JSONObject();
						cecJson.put("codigoEquipoCaso", cec.getCasoEquipoCasoPK().getCodigoEquipoCaso());
						cecJson.put("codigo", cec.getCasoEquipoCasoPK().getCodigo());
						cecJson.put("miembro", cec.getCasoEquipoCasoPK().getMiembro());
						jsO.put("casoEquipoCasoPK", cecJson);
					}
				}
				arrayCasos.add(jsO);
			}

			res.put("aaData", arrayCasos);

		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando la Actividad . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando la  Actividad . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toString();
	}
	
}
