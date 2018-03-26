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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.service.EquipoCasoService;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.gja.gestionCasos.reportes.service.ReportesService;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.common.controller.HomeController;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.Utilidades;

import net.minidev.json.JSONArray;

@Controller
@RequestMapping("/tareasProximoVencimiento")
public class TareasProximoVencimientoController {
	
	
	private static final Logger LOG = Logger.getLogger(CaducidadCasosController.class);

	
	@Autowired
	private EquipoCasoService equipoCasoService;
	
	@Autowired
	private ReportesService reportesService;
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

		return "reportes/tareasProximoVencimiento"; // carpeta y el jsp
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarTareasProximoVencimiento", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarTareasProximoVencimiento(CasoFiltro casoFiltro, Principal principal,
			@RequestParam("iDisplayStart") int displayStart,
			@RequestParam("iDisplayLength") int displayLength,
			@RequestParam(value = "nombreCaso", required = false) String nombreCaso,
			@RequestParam("iSortCol_0") int sortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho) {
		List<Object[]> tareasFiltradas = null;
		JSONObject res = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
    		Rol rol = menuVistaPermisosRolesWrapper.getRol();
    		
			if (Integer.parseInt(Parametros.getRolAbogado()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolDependiente()) == rol.getCodigo()) {
				SocialUserDetails userDetails = Utilidades.getRealPrincipal(principal);
				EquipoCaso usuarioEnEquipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(userDetails.getId());
				casoFiltro.setCodigoEquipoCaso(usuarioEnEquipoCaso.getCodigoEquipoCaso());
			}
			
			if (casoFiltro == null || casoFiltro.getFiltroDias() == null) {
				casoFiltro.setFiltroDias(Parametros.getDiasPorDefectoVencimientoTareas());
			}
			tareasFiltradas = reportesService.getTareasProximoVencimiento(casoFiltro, nombreCaso);
			Long cantidad = reportesService.getCountTareasProximoVencimiento(casoFiltro, nombreCaso);

			for (Object obj[] : tareasFiltradas) {
				jsonArray.add(crearJsonTareaProximoVencimiento(obj));
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
			e.printStackTrace();
			LOG.error("Exception: Ocurrio un error realizando el reporte de caducidad de casos. El error es: "
					+ e.getMessage());
		}

		return res.toString();
	}


	private JSONObject crearJsonTareaProximoVencimiento(Object[] obj) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat(Parametros.getFormatoFechaDiaMesAnio());
		JSONObject jsO = new JSONObject();
		jsO.put("tarea", obj[0]);
		jsO.put("responsables", obj[1]);
		Date fechaVencimiento = (Date) obj[2];
		jsO.put("fechaVencimiento", formatoFecha.format(fechaVencimiento));
		jsO.put("codigoCaso", obj[3]);
		jsO.put("nombreCaso", obj[4]);
		return jsO;
	}

}
