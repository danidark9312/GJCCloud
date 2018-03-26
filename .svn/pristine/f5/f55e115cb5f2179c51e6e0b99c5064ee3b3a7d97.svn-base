package com.gja.gestionCasos.reportes.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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

import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.service.CasoService;
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
@RequestMapping("/caducidadCasos")
public class CaducidadCasosController {

	private static final Logger LOG = Logger.getLogger(CaducidadCasosController.class);

	@Autowired
	private ReportesService reportesService;
	@Autowired
	private CasoService casoService;
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
			LOG.error("Exception: Ocurrio un error realizando el reporte de caducidad de casos. El error es: " + e.getMessage());
		}

		return "reportes/caducidadCasos"; // carpeta y el jsp
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarCaducidadCasos", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarCaducidadCasos(CasoFiltro casoFiltro,
			@RequestParam("iDisplayStart") int displayStart, @RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho) {
		List<Object[]> casosFiltrados = null;
		SimpleDateFormat formatoFecha = new SimpleDateFormat(Parametros.getFormatoFechaDiaMesAnio());
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			casoFiltro.setPrejudicialFiltro(Parametros.getActividadPrejudicial());
			casosFiltrados = reportesService.obtenerCaducidadCasos(casoFiltro);
			Long cantidad = reportesService.getCountCaducidadCasos(casoFiltro);

			for (Object obj[] : casosFiltrados) {
				jsO = new JSONObject();
				jsO.put("reponsables", obj[2]);
				jsO.put("codigoCaso", obj[0]);
				jsO.put("proceso", obj[1]);
				Date fechaHechos = (Date) obj[3];
				jsO.put("fechaHechos", formatoFecha.format(fechaHechos));
				jsO.put("alertas", obj[4]);
				Date caducidadPrejudicial = (Date) obj[5];
				jsO.put("caducidadPrejudicial", formatoFecha.format(caducidadPrejudicial));
				jsO.put("demandadoPrincipal", crearStringDemandadoPrincipal(obj[0].toString()));
				jsO.put("caducidadDemanda", "");
				jsonArray.add(jsO);
			}
			res.put("sEcho", Integer.valueOf(sEcho));
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", jsonArray);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error realizando el reporte de caducidad de casos. El error es: "
					+ e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error realizando el reporte de caducidad de casos. El error es: "
					+ e.getMessage());
		} catch (Exception e) {
			LOG.error("Exception: Ocurrio un error realizando el reporte de caducidad de casos. El error es: "
					+ e.getMessage());
		}

		return res.toString();
	}

	private String crearStringDemandadoPrincipal(String codigoCaso) throws DAOException, BusinessException {		
		return casoService.getDemandadosCasoString(codigoCaso);
	}

	/**
	 * Metodo encargado de recorrer los demandados de un caso y armar un string con todos ellos, en el momento se muestran todos, solo se deberia mostrar el principal.
	 * @param caso
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportarCaducidadCasos", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String exportarCaducidadCasos(CasoFiltro casoFiltro) {
		List<Object[]> casosFiltrados = null;
		SimpleDateFormat formatoFecha = new SimpleDateFormat(Parametros.getFormatoFechaDiaMesAnio());
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			casoFiltro.setPrejudicialFiltro(Parametros.getActividadPrejudicial());
			casosFiltrados = reportesService.obtenerCaducidadCasosExcel(casoFiltro);
			for (Object obj[] : casosFiltrados) {
				jsO = new JSONObject();
				jsO.put("codigoCaso", obj[0]);
				jsO.put("proceso", obj[1]);
				jsO.put("reponsables", obj[2]);
				Date fechaHechos = (Date) obj[3];
				jsO.put("fechaHechos", formatoFecha.format(fechaHechos));
				jsO.put("alertas", obj[4]);
				Date caducidadPrejudicial = (Date) obj[5];
				jsO.put("caducidadPrejudicial", formatoFecha.format(caducidadPrejudicial));
				jsO.put("caducidadDemanda", "");
				jsO.put("demandadoPrincipal", "");
				jsonArray.add(jsO);
			}
			res.put("caducidadCasos", jsonArray);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error realizando el reporte de caducidad de casos. El error es: "
					+ e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error realizando el reporte de caducidad de casos. El error es: "
					+ e.getMessage());
		} catch (Exception e) {
			LOG.error("Exception: Ocurrio un error realizando el reporte de caducidad de casos. El error es: "
					+ e.getMessage());
		}

		return res.toString();
	}
}
