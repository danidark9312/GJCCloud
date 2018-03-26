package com.gja.gestionCasos.casos.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
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

import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.service.ActividadCasoService;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.service.TareaParticularCasoService;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Controller
@RequestMapping(value = { "/papelera" })
public class PapeleraController {

	private static final Logger LOG = Logger.getLogger(BienAfectadoController.class);

	@Autowired
	ActividadCasoService actividadCasoService;

	@Autowired
	TareaParticularCasoService tareaParticularCasoService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;

	@RequestMapping
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();

		menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
		vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
				httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);

		model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
		model.addAttribute("permisos", vistaPermisosRolesWrapper);
		model.addAttribute("aniosCaducidad", Parametros.getAnosCaducidad());

		return "casos/papelera"; // carpeta y el jsp
	}

	@RequestMapping(value = "/getActividadesPapelera", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getActividadesPapelera(CasoFiltro casoFiltro,
			@RequestParam("iDisplayStart") int displayStart, @RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho, HttpServletRequest request) {
		int cantidad = 0;
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		JSONArray actividadesArray = new JSONArray();
		try {

			List<ActividadCaso> listActividades = actividadCasoService.obtenerActividadesInactivas(casoFiltro);
			List<ActividadCaso> totalActividades = actividadCasoService.obtenerTotalActividadesInactivas(casoFiltro);
			cantidad = totalActividades.size();
			for (ActividadCaso actividad : listActividades) {
				String accion = "";
				jsO = new JSONObject();
				List<TareaParticularCaso> tareas = new ArrayList<TareaParticularCaso>();
				tareas.addAll(actividad.getTareaParticularCasoSet());
				String nombreCaso = tareas.get(0).getResponsablesTareas().get(0).getCasosEquiposCaso().getCaso()
						.getNombre();
				jsO.put("nombreCaso", nombreCaso.isEmpty() ? "" : nombreCaso);
				jsO.put("nombreActividad",
						actividad.getNombreActividad() == null ? "" : actividad.getNombreActividad());
				StringBuilder strBuilder = new StringBuilder();
				strBuilder.append("<div class='col-md-11'>");
				strBuilder.append("<table class='table table-bordered'>");
				if (actividad.getTareaParticularCasoSet() != null && !actividad.getTareaParticularCasoSet().isEmpty()) {
					strBuilder.append("<thead>");
					strBuilder.append("<tr>");
					strBuilder.append("<th>Tarea</th>");
					strBuilder.append("<th>Detalle</th>");
					strBuilder.append("<th>Acci\u00F3n</th>");
					strBuilder.append("</tr>");
					strBuilder.append("</thead>");
					strBuilder.append("<tbody>");
					for (TareaParticularCaso tarea : actividad.getTareaParticularCasoSet()) {
						if (tarea.getSnActiva() != null
								&& tarea.getSnActiva().equals(Parametros.getCodigoTareaActivoNo())) {
							strBuilder.append("<tr>");
							strBuilder.append("<td>" + tarea.getTarea() + "</td>");
							strBuilder.append("<td>" + tarea.getDetalle() + "</td>");
							strBuilder.append("<td class='text-right'>");
							if (request.isUserInRole("ROLE_SUPER_USER") || request.isUserInRole("ROLE_ADMIN")
									|| request.isUserInRole("ROLE_SOCIO") || request.isUserInRole("ROLE_ASOCIADO")
									|| request.isUserInRole("ROLE_JUNIOR")
									|| request.isUserInRole("ROLE_DEPENDIENTE")) {
								strBuilder.append("<a onclick=\'activarTarea(\"" + tarea.getCodigoTarea()
										+ "\");\' class='btn btn-circle btn-info' title='Activar'><i class='glyphicon glyphicon-ok'></i></a>");
							}
							if (request.isUserInRole("ROLE_SUPER_USER") || request.isUserInRole("ROLE_ADMIN")) {
								strBuilder.append("<a onclick=\'mostrarConfirmacionTarea(\"" + tarea.getCodigoTarea()
										+ "\");\' class='btn btn-circle btn-danger' title='Borrar'><i class='glyphicon glyphicon-trash'></i></a>");
							}
							strBuilder.append("</td>");
							strBuilder.append("</tr>");
						}
					}
					strBuilder.append("</tbody>");
				}
				strBuilder.append("</table>");
				strBuilder.append("</div>");
				strBuilder.append("<div class='col-md-1'>");
				strBuilder.append("</div>");
				jsO.put("detalle", strBuilder.toString());
				jsO.put("btnVerDetalle",
						"<SPAN title='Ver tareas'><a name='verTareas'><i  id='verDetalle' class='glyphicon glyphicon-chevron-down'></i></a></SPAN>");
				if (actividad.getSnActiva() != null
						&& actividad.getSnActiva().equals(Parametros.getCodigoActividadActivoNo())) {
					if (request.isUserInRole("ROLE_SUPER_USER") || request.isUserInRole("ROLE_ADMIN")
							|| request.isUserInRole("ROLE_SOCIO") || request.isUserInRole("ROLE_ASOCIADO")
							|| request.isUserInRole("ROLE_JUNIOR") || request.isUserInRole("ROLE_DEPENDIENTE")) {
						accion = accion + "<a onclick=\'activarActividad(\"" + actividad.getCodigoActividadParticular()
								+ "\");\' class='btn btn-circle btn-info' title='Activar'><i class='glyphicon glyphicon-ok'></i></a>";
					}
					if (request.isUserInRole("ROLE_SUPER_USER") || request.isUserInRole("ROLE_ADMIN")) {
						accion = accion + "<a onclick=\'mostrarConfirmacionActividad(\""
								+ actividad.getCodigoActividadParticular()
								+ "\");\' class='btn btn-circle btn-danger' title='Borrar'><i class='glyphicon glyphicon-trash'></i></a>";
					}
				}
				jsO.put("acciones", accion);
				actividadesArray.add(jsO);
			}
			res.put("sEcho", Integer.valueOf(sEcho));
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", actividadesArray);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las actividades de la papelera. El error es: "
					+ e.getMessage());
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las actividades de la papelera. El error es: "
					+ e.getMessage());
		}
		return res.toJSONString();
	}

	@RequestMapping(value = "/activarActividad", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String activarActividad(@ModelAttribute("ActividadCaso") ActividadCaso actividad) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		List<String> parametros = new ArrayList<String>();
		JSONObject res = new JSONObject();
		try {
			actividad = actividadCasoService.activarActividadCaso(actividad);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	@RequestMapping(value = "/activarTarea", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String activarTarea(@ModelAttribute("TareaParticularCaso") TareaParticularCaso tarea,
			@RequestParam(value = "activarActividad", required = false) boolean isActivarActividad) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		List<String> parametros = new ArrayList<String>();
		JSONObject res = new JSONObject();
		try {
			tarea = tareaParticularCasoService.activarTareaParticularCaso(tarea, isActivarActividad);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR_ACTIVIDAD");
		}
		return res.toString();
	}

	@RequestMapping(value = "/borradoFisicoTarea", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String borradoFisicoTarea(@ModelAttribute("TareaParticularCaso") TareaParticularCaso tarea) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		List<String> parametros = new ArrayList<String>();
		JSONObject res = new JSONObject();
		try {
			tareaParticularCasoService.borradoFisicoTarea(tarea);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	@RequestMapping(value = "/borradoFisicoActividad", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String borradoFisicoActividad(@ModelAttribute("ActividadCaso") ActividadCaso actividad) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		List<String> parametros = new ArrayList<String>();
		JSONObject res = new JSONObject();
		try {
			actividadCasoService.borradoFisicoActividad(actividad);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}
}
