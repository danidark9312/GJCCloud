package com.gja.gestionCasos.maestros.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.filters.ActividadFiltro;
import com.gja.gestionCasos.maestros.service.MaestroActividadesService;
import com.gja.gestionCasos.permisos.service.AccionesService;
import com.gja.gestionCasos.permisos.service.RolesService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Controller
@RequestMapping(value = { "/maestroActividad" })
public class MaestroActividadController {

	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";
	private final String status = "STATUS";

	@Autowired
	AccionesService accionesService;
	@Autowired
	MaestroActividadesService maestroActividadService;
	@Autowired
	RolesService rolesService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;

	private static final Logger LOG = Logger.getLogger(MaestroActividadController.class);

	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		List<Rol> roles = new ArrayList<Rol>();
		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();
		
		menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
		vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
				httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);

		try {
			roles = rolesService.getRoles();
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error obteniendo los Roles. El error es: " + e.getMessage());
			e.printStackTrace();
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error obteniendo los Roles. El error es: " + e.getMessage());
			e.printStackTrace();
		}

		model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
		model.addAttribute("permisos", vistaPermisosRolesWrapper);
		model.addAttribute("listaRol", roles);

		return "maestros/maestroActividad";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarActividad", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody Actividad consultarActividad(@ModelAttribute("actividad") Actividad actividad) {
		JSONObject res = new JSONObject();
		try {
			actividad = maestroActividadService.findByPK(actividad);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando la Actividad . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando la  Actividad . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return actividad;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminarActividad", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String eliminarActividad(@ModelAttribute("actividad") Actividad actividad) {
		JSONObject res = new JSONObject();
		int retunValor = 0;
		try {
			actividad = maestroActividadService.findByPK(actividad);
			retunValor = maestroActividadService.eliminarActividades(actividad);
			if (retunValor == 1) {
				res.put("STATUS", estadoExito);
			} else {
				res.put("STATUS", estadoError);
			}
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error eliminando la Actividad . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error eliminando la  Actividad . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarActividades", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getAllTable(ActividadFiltro filtro, @RequestParam("activo") String activo,
			@RequestParam("iDisplayStart") int displayStart, @RequestParam("iDisplayLength") int displayLength,
			@RequestParam("sSearch") String sSearch, @RequestParam("iSortCol_0") int sortColumn0,
			@RequestParam("sSortDir_0") String sortDirection0, @RequestParam("sEcho") int sEcho) {
		int cantidad = 0;
		List<Actividad> actividades = null;
		JSONObject res = new JSONObject();
		try {
			JSONObject jsO = new JSONObject();
			cantidad = maestroActividadService.getCountFilter(filtro).intValue();
			actividades = maestroActividadService.getActividadByFilter(filtro);
			JSONArray arrayActividades = new JSONArray();
			for (Actividad actividad : actividades) {
				/**TODO 
				 * En cuanto pueda refactorizar y armar el html en jsp en vez de aca
				 * Revisar si esta haciendo el seteo de permiso segun rol en vista sino aca
				 */
				jsO = new JSONObject();
				jsO.put("codigoActividad", actividad.getCdactividad());
				jsO.put("nombreActividad", actividad.getDsactividad());
				jsO.put("papelera",
						"<a data-toggle='modal' class='btn btn-success btn-circle .btn-xs' style='background-color: red; border:0;' onclick='eliminarActividad("
								+ actividad.getCdactividad()
								+ ")' title='Eliminar'><i class='glyphicon glyphicon-trash' style='color: white;'></i></a>");
				// }else{
				// jsO.put("papelera", "<a data-toggle='modal' class='btn
				// btn-success btn-circle .btn-xs' style='background-color:
				// lightRed; border:0;' title='Eliminar'><i class='glyphicon
				// glyphicon-trash' style='color: white;'></i></a>");
				// }

				arrayActividades.add(jsO);
			}
			res.put("sEcho", sEcho);
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", arrayActividades);
			res.put("STATUS", estadoExito);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando las Actividades. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error(
					"BusinessException: Ocurrio un error consultando las Actividades. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/guardarActividad" )
	public @ResponseBody String guardar(@ModelAttribute("actividad") Actividad actividad) throws Exception {
		JSONObject res = new JSONObject();
		try {
			actividad = maestroActividadService.guardarActividades(actividad);

			res.put(status, estadoExito);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando las Actividades . El error es: " + e.getMessage());
			res.put(status, estadoError);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando las Actividades . El error es: " + e.getMessage());
			res.put(status, estadoError);
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/existNameActividad")
	public @ResponseBody String existNameActividad(@ModelAttribute("actividad") Actividad actividad) throws Exception {
		JSONObject res = new JSONObject();
		try {
			boolean existe = maestroActividadService.existNameActividad(actividad);
			if (existe) {
				res.put("STATUS", estadoExito);
			} else {
				res.put("STATUS", estadoError);
			}

		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las actividades por nombre . El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las actividades por nombre . El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toString();
	}

}
