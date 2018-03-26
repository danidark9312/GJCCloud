package com.gja.gestionCasos.reportes.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.gja.gestionCasos.casos.service.EquipoCasoService;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.filters.JustificacionFiltro;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.gja.gestionCasos.reportes.service.JustificacionService;
import com.gja.gestionCasos.reportes.service.ReportesService;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.Utilidades;

import net.minidev.json.JSONArray;

@Controller
@RequestMapping(value = { "/auditoria" })
public class AuditoriaController {

	private static final Logger LOG = Logger.getLogger(AuditoriaController.class);
	
	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";
	private final String status = "STATUS";

	@Autowired
	private ReportesService reportesService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;
	@Autowired
	EquipoCasoService equipoCasoService;
	@Autowired
	JustificacionService obtenerJustificaciones;
	@Autowired
	JustificacionService obtenerResponsablesJustificaciones;

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
			model.addAttribute("aniosCaducidad", Parametros.getAnosCaducidad());
			model.addAttribute("codigoResponsable", codigoResponsable);
		} catch (Exception e) {
			LOG.error("Exception: Ocurrio un error realizando el reporte de auditoria. El error es: " + e.getMessage());
		}
		
		
		return "reportes/auditoria"; // carpeta y el jsp
	}

	//Aca se muestran las auditorias
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarAuditorias", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarJustificaciones(JustificacionFiltro justificacionFiltro,
			@RequestParam("iDisplayStart") int displayStart, @RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho, Principal principal) {
		
		SimpleDateFormat fechaCreacionFormato = new SimpleDateFormat(Parametros.getFormatoFechaDiaMesAnio());
		
		Long cantidad = null;
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		JSONArray justificacionesArray = new JSONArray();
		
		try{
			List<Justificacion> listaJustificaciones = obtenerJustificaciones.obtenerJustificaciones(justificacionFiltro, displayStart, displayLength);
			cantidad = obtenerJustificaciones.getCantidadJustificiones(justificacionFiltro);
		
			for (Justificacion justificacion : listaJustificaciones) {
				jsO = new JSONObject();				
				jsO.put("caso", justificacion.getCodigoCaso().getNombre());
				jsO.put("moduloModificacion", justificacion.getCampoModificado());
				jsO.put("objetivoEliminado", justificacion.getInformacionEliminada());
				jsO.put("usuario", justificacion.getUsuarioModificacion().getNombre());
				jsO.put("fechaEliminacion", fechaCreacionFormato.format(justificacion.getFechaModificacion()));
				jsO.put("justificacion", justificacion.getJustificacion());	
				
				justificacionesArray.add(jsO);
			}
			res.put("sEcho", sEcho);
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", justificacionesArray);
			res.put("STATUS", estadoExito);
		}
		catch (DAOException e) {
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
	
	   // metodo para cargar los responsables de la justificaciones el select
		@RequestMapping(value="/obtenerResponsablesJustificaciones",method=RequestMethod.GET,produces="aplication/json; charset=utf-8")
		public @ResponseBody String obtenerResponsablesJustificaciones(){			
			JSONObject res = new JSONObject();
			JSONObject jsO = new JSONObject();
			try {
				List<User> usuario = obtenerResponsablesJustificaciones.obtenerUsuariosJustificaciones();
				JSONArray arrayUsuarios = new JSONArray();
				for (User usuarios : usuario) {					
						jsO = new JSONObject();						
						jsO.put("nombre", usuarios.getNombre() + " " + usuarios.getApellido());
						jsO.put("codigo", usuarios.getId());
						
						arrayUsuarios.add(jsO);					
				}
				res.put("abogados", arrayUsuarios);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return res.toString(); 
		}
		
		@RequestMapping(value="/obtenerCasosJustificaciones",method=RequestMethod.GET,produces="aplication/json; charset=utf-8")
		public @ResponseBody String obtenerCasosJustificaciones(){			
			JSONObject res = new JSONObject();
			JSONObject jsO = new JSONObject();
			try {
				List<User> usuario = obtenerResponsablesJustificaciones.obtenerUsuariosJustificaciones();
				JSONArray arrayUsuarios = new JSONArray();
				for (User usuarios : usuario) {					
						jsO = new JSONObject();						
						jsO.put("nombre", usuarios.getNombre()+" "+ usuarios.getApellido());
						
						arrayUsuarios.add(jsO);					
				}
				res.put("abogados", arrayUsuarios);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return res.toString(); 
		}
	
	/**
	 * Metodo encargado de recorrer los demandados de un caso y armar un string con todos ellos, en el momento se muestran todos, solo se deberia mostrar el principal.
	 * @param caso
	 * @return
	 */
/*	
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
/*
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
	}*/
/* Exportacion a ecxel se deja por si acaso
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportarAudiencias", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String exportarAudiencias(CasoFiltro casoFiltro, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject res = new JSONObject();
		List<Object[]> casosFiltrados = null;
		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.getFormatoFechaDiaMesAnio());
		try {
			casoFiltro.setAudienciaFiltro(Parametros.getActividadAudiencia());
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
				jsO.put("alertas",object[6] );
				jsonArray.add(jsO);
			}
			res.put("audiencias", jsonArray);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error realizando la consulta de audiencias. El error es: "
					+ e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error realizando la consulta de audiencias. El error es: "
					+ e.getMessage());
		} catch (Exception e) {
			LOG.error(
					"Exception: Ocurrio un error realizando la consulta de audiencias. El error es: " + e.getMessage());
		}

		return res.toString();
	}
	*/

}
