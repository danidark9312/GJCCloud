package com.gja.gestionCasos.reportes.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.gja.gestionCasos.reportes.service.ReportesService;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.Utilidades;

import net.minidev.json.JSONArray;

@Controller
@RequestMapping("/recursosPendientes")
public class RecursosPendientesController {

	private static final Logger LOG = Logger.getLogger(RecursosPendientesController.class);

	@Autowired
	private ReportesService reportesService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;
	
	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		String codigoResponsable = "";
		try {
			MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
			VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();
			Rol rol = menuVistaPermisosRolesWrapper.getRol();
			if (Integer.parseInt(Parametros.getRolAbogado()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolDependiente()) == rol.getCodigo()) {
				SocialUserDetails userDetails = Utilidades.getRealPrincipal(principal);
				codigoResponsable = userDetails.getId();
			}
			vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
					httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);
				
			model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
			model.addAttribute("permisos", vistaPermisosRolesWrapper);
			model.addAttribute("codigoResponsable", codigoResponsable);
		} catch (Exception e) {
			LOG.error("Exception: Ocurrio un error realizando el reporte de recursos pendientes. El error es: " + e.getMessage());
		}

		return "reportes/recursosPendientes"; // carpeta y el jsp
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarRecursosPendientes", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarRecursosPendientes(CasoFiltro casoFiltro,
			@RequestParam("iDisplayStart") int displayStart, @RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho) {
		List<Caso> casosFiltrados = null;
		SimpleDateFormat formatoFecha = new SimpleDateFormat(Parametros.getFormatoFechaDiaMesAnio());
		JSONObject res = new JSONObject();
		try {
			casoFiltro.setRecursosPendientesFiltro(Parametros.getActividadRecursosPendientes());
			casosFiltrados = reportesService.encontrarCasoPorFiltro(casoFiltro);
			Long cantidad = reportesService.getCountFilter(casoFiltro);
			JSONObject jsO = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (Caso caso : casosFiltrados) {
				jsO = new JSONObject();
				jsO.put("proceso", caso.getNombre());
				jsO.put("radicadoPrincipal", crearStringRadicadoprincipal(caso));
				jsO.put("demandadoPrincipal", crearStringDemandadoPrincipal(caso));
				jsO.put("despacho", caso.getNumeroDespacho() != null ? caso.getNumeroDespacho() : "");
				jsO.put("codigoCaso", caso.getCodigo());
				jsO.put("verDetalle",
						"<SPAN title='Ver detalle'><a name='verDetalle'><i  id='verDetalle' class='glyphicon glyphicon-chevron-down'></i></a></SPAN>");
				Set<TareaParticularCaso> tareasCaso = new HashSet<TareaParticularCaso>();
				for (CasoEquipoCaso equipoCaso : caso.getCasoEquipoCasoSet()) {
					for (ResponsableTarea responsable : equipoCaso.getResponsablesTareas()) {
						tareasCaso.add(responsable.getTareasParticularesCaso());
					}
				}
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("<table class='table table-bordered'>");
				if (tareasCaso != null && !tareasCaso.isEmpty()) {
					stringBuilder.append("<thead>");
					stringBuilder.append("<tr>");
					stringBuilder.append("<th name='thAudiencia1'>Tarea</th>");
					stringBuilder.append("<th name='thAudiencia2'>Fecha vencimiento</th>");
					stringBuilder.append("<th name='thAudiencia3'>Detalle</th>");
					stringBuilder.append("<th name='thAudiencia4'>Responsables</th>");
					stringBuilder.append("<th name='thAudiencia5'>Alertas</th>");
					stringBuilder.append("</tr>");
					stringBuilder.append("</thead>");
					stringBuilder.append("<tbody>");
					for (TareaParticularCaso tarea : tareasCaso) {
						if (tarea.getActividadParticular().getNombreActividad().toLowerCase()
								.contains(Parametros.getActividadRecursosPendientes().toLowerCase()) && Parametros.getCodigoActividadActivoSi().equals(tarea.getSnActiva())) {
							stringBuilder.append("<tr>");
							stringBuilder.append("<td>" + tarea.getTarea() + "</td>");
							stringBuilder.append("<td>" + formatoFecha.format(tarea.getFechaLimite()) + "</td>");
							stringBuilder.append("<td>" + tarea.getDetalle() + "</td>");
							StringBuilder builderResponsable = new StringBuilder();
							for (ResponsableTarea responsable : tarea.getResponsablesTareas()) {
								builderResponsable
										.append(responsable.getCasosEquiposCaso().getEquipoCaso().getNombre() + ", \n");
							}
							stringBuilder.append("<td>" + builderResponsable.toString().substring(0,
									builderResponsable.toString().length() - 3) + "</td>");
							if (tarea.getAlerta() != null)
								stringBuilder.append("<td>" + tarea.getAlerta().getTotalEnvios() + "</td>");
							else
								stringBuilder.append("<td>" + "0" + "</td>");
							stringBuilder.append("</tr>");
						}
					}
					stringBuilder.append("</tbody>");
				}
				stringBuilder.append("</table>");
				jsO.put("detalle", stringBuilder.toString());
				jsonArray.add(jsO);
			}
			res.put("sEcho", Integer.valueOf(sEcho));
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", jsonArray);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error realizando el reporte de audiencia. El error es: "
					+ e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error realizando el reporte de audiencia. El error es: "
					+ e.getMessage());
		} catch (Exception e) {
			LOG.error("Exception: Ocurrio un error realizando el reporte de audiencia. El error es: " + e.getMessage());
		}

		return res.toString();
	}

	/**
	 * Metodo encargado de recorrer los demandados de un caso y armar un string con todos ellos, en el momento se muestran todos, solo se deberia mostrar el principal.
	 * @param caso
	 * @return
	 */
	
	private String crearStringDemandadoPrincipal(Caso caso) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("");
		String demandados = "";
		for (CasoEquipoCaso casoEquipoCaso:caso.getCasoEquipoCasoSet()) {
			if (casoEquipoCaso.getTipoMiembro().getCodigo() == Integer.parseInt(Parametros.getDemandado())) {
				String nombreCompleto = casoEquipoCaso.getEquipoCaso().getNombre() + (casoEquipoCaso.getEquipoCaso().getApellido() != null ? casoEquipoCaso.getEquipoCaso().getApellido():"") + ", ";
				strBuilder.append(nombreCompleto);
			}
		}
		if (strBuilder.toString().length() > 0)
			demandados = strBuilder.toString().substring(0, strBuilder.toString().length() - 2);
		return demandados;
	}

	/**
	 * Metodo encargado de recorrer los radicados de un caso y armar un string con todos ellos, en el momento se muestran todos, solo se deberia mostrar el principal.
	 * @param caso
	 * @return
	 */
	private String crearStringRadicadoprincipal(Caso caso) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("");
		String radicados = "";
		for (Radicado radicado:caso.getRadicadoSet()) {			
			strBuilder.append(radicado.getRadicadoPK().getCodigoRadicado() + ", ");
		}
		if (strBuilder.toString().length() > 0)
			radicados = strBuilder.toString().substring(0, strBuilder.toString().length() - 2);
		return radicados;	
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportarRecursosPendientes", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String exportarRecursosPendientes(CasoFiltro casoFiltro, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject res = new JSONObject();
		List<Object[]> casosFiltrados = null;
		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.getFormatoFechaDiaMesAnio());
		try {
			casoFiltro.setRecursosPendientesFiltro(Parametros.getActividadRecursosPendientes());
			casosFiltrados = reportesService.obtenerAudienciasExcel(casoFiltro);
			JSONArray jsonArray = new JSONArray();
			for (Object object[] : casosFiltrados) {
				
				JSONObject jsO = new JSONObject();
				jsO.put("proceso", object[0]);
				jsO.put("responsable", object[1]);
				jsO.put("tarea", object[2]);
				jsO.put("detalle", object[3]);
				Date fechaLimite = (Date) object[4];
				jsO.put("fechaLimite", sdf.format(fechaLimite));
				jsO.put("radicado", object[5] != null ? object[5]:""  );
				jsO.put("alertas", object[6] );
				jsO.put("despacho", object[7] != null ? object[7] : "");
				jsO.put("demandado", object[9] != null ? object[9] : "");
				jsonArray.add(jsO);

			}
			res.put("recursos", jsonArray);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error realizando la consulta de recursos pendientes. El error es: "
					+ e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error realizando la consulta de recursos pendientes. El error es: "
					+ e.getMessage());
		} catch (Exception e) {
			LOG.error("Exception: Ocurrio un error realizando la consulta de recursos pendientes. El error es: "
					+ e.getMessage());
		}

		return res.toString();
	}
}
