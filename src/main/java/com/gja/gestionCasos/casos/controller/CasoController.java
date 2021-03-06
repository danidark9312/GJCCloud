package com.gja.gestionCasos.casos.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONArray;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.ListadoArchivos;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.ResponsableTareaPK;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.ListaActividades;
import com.gja.gestionCasos.actividades.service.ActividadCasoService;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCasoPK;
import com.gja.gestionCasos.casos.entities.Celular;
import com.gja.gestionCasos.casos.entities.Correo;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.ListaPrestamos;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.entities.Telefono;
import com.gja.gestionCasos.casos.entities.HelperWrapperCasoEquipoCasoPK;
import com.gja.gestionCasos.casos.service.CasoEquipoCasoService;
import com.gja.gestionCasos.casos.service.CasoService;
import com.gja.gestionCasos.casos.service.EquipoCasoService;
import com.gja.gestionCasos.casos.service.ResponsablesTareaService;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.gja.gestionCasos.reportes.entities.ListaJustificaciones;
import com.gja.gestionCasos.reportes.service.JustificacionService;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.Utilidades;

@Controller
@RequestMapping(value = { "/caso" })
public class CasoController {

	private static final Logger LOG = Logger.getLogger(CasoController.class);
	private final String errorMessage = "ERROR";

	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";

	@Autowired
	private CasoService casoService;
	@Autowired
	private ActividadCasoService actividadCasoService;
	@Autowired
	private JustificacionService justificacionService;
	@Autowired
	private CasoEquipoCasoService casoEquipoCasoService;
	@Autowired
	private EquipoCasoService equipoCasoService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VistaRolesAccionOpciones vistaRolesAccionOpciones;
	
	@Autowired
	private ResponsablesTareaService responsablesTareaService;
	private boolean enviarCorreoCambioFecha = false;

	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();

		menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
		vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
				httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);

		model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
		model.addAttribute("permisos", vistaPermisosRolesWrapper);

		model.addAttribute("aniosCaducidad", Parametros.getAnosCaducidad());
		return "casos/caso"; // carpeta y el jsp
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String guardar(@ModelAttribute("caso") Caso caso, ListaPrestamos prestamos,
			ListaActividades listaActividadDelCaso) throws Exception {
		JSONObject res = new JSONObject();
		try {
			caso = casoService.guardarCaso(caso, prestamos, listaActividadDelCaso);
			JSONArray codigosResponsables = casoService.obtenerCodigoEquipo(caso);

			res.put("codigoCaso", caso.getCodigo());
			res.put("equipoCaso", codigosResponsables);

		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}
		return res.toString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateActividadesCasoOrden", produces = "application/json; charset=utf-8")
	public @ResponseBody String guardar(ListaActividades listaActividadDelCaso) throws Exception {
		JSONObject res = new JSONObject();
		try {
			int result = actividadCasoService.updateActividadesCasoOrden(listaActividadDelCaso);
			res.put("STATUS", ((result == listaActividadDelCaso.getListaActividadesCaso().size())?"SUCCESS":"ERROR"));
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/guardarActividades")
	public @ResponseBody String guardarActividades(
			@ModelAttribute("ListaActividades") ListaActividades listaActividadDelCaso,
			ListaJustificaciones justificaciones, HttpServletRequest request) throws Exception {
		JSONObject res = new JSONObject();
		try {
 			for (ActividadCaso actividadDelCaso : listaActividadDelCaso.getListaActividadesCaso()) {
				if (actividadDelCaso.getCodigoActividadParticular() != null) {
					
						actividadDelCaso = actividadCasoService.modificarActividadCaso(actividadDelCaso);
					
				} else{									
					if(listaActividadDelCaso.getListaActividadesCaso().get(0).getTareaParticularCasoSet().get(0).getResponsablesTareas().get(0).getResponsableTareaPK().getCodigoMiembro() == 4){
						actividadDelCaso = guardarActividadCaso(actividadDelCaso);
					}
					else{
						actividadDelCaso = actividadCasoService.guardarActividadCaso(actividadDelCaso, null);
					
					}
				}
			}
			if (justificaciones.getListaJustificaciones() != null
					&& !justificaciones.getListaJustificaciones().isEmpty()) {
				List<User> listaUsuarios = new ArrayList<User>();
				listaUsuarios = casoService.consultarCorreoAdmind();
				Caso caso = casoService.findByPK(new Caso(justificaciones.getListaJustificaciones().get(0).getCodigoCaso().getCodigo()));
//				Caso caso = casoService.findByPK(new Caso(justificaciones.getListaJustificaciones().get(0).getCodigoDelCaso()));
				User usuario = equipoCasoService
						.findUserByPK(justificaciones.getListaJustificaciones().get(0).getUsuarioModificacion().getId());
				for (Justificacion justificacion : justificaciones.getListaJustificaciones()) {
					if (request.isUserInRole("ROLE_SOCIO") || request.isUserInRole("ROLE_ASOCIADO")
							|| request.isUserInRole("ROLE_JUNIOR") || request.isUserInRole("ROLE_DEPENDIENTE")) {

						StringBuilder cuerpoMensaje = new StringBuilder();
						MimeMessage message = null;
						MimeMessageHelper helper = null;
						message = this.mailSender.createMimeMessage();
						helper = new MimeMessageHelper(message, true);
						List to = new ArrayList();
						for (User objUser : listaUsuarios) {
							to.add(objUser.getEmail());
						}
						cuerpoMensaje.append("Caso: " + caso.getNombre() + "\n");
						cuerpoMensaje.append("Elemento modificado: " + justificacion.getMensajeCorreo() + "\n");
						cuerpoMensaje.append("Fecha actualizada: " + justificacion.getFechaActualizada() + "\n");
						cuerpoMensaje.append("Fecha anterior: " + justificacion.getFechaAnterior() + "\n");
						cuerpoMensaje.append("Usuario: " + usuario.getNombre() + " "
								+ (usuario.getApellido() == null ? "" : usuario.getApellido()) + "\n");
						cuerpoMensaje.append("Justificaci\u00F3n: " + justificacion.getJustificacion() + "\n");
						helper.setTo((String[]) to.toArray(new String[0]));
						helper.setSubject(
								Parametros.getAsuntoModificacionFechas() + " " + justificacion.getMensajeCorreo());
						helper.setText(cuerpoMensaje.toString());
						this.mailSender.send(message);
					}
					justificacionService.guardarJustificacion(justificacion);
				}
			}

			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarCasos", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarCasos(CasoFiltro casoFiltro, @RequestParam("iDisplayStart") int displayStart,
			@RequestParam("iDisplayLength") int displayLength, @RequestParam("iSortCol_0") int sortColumn0,
			@RequestParam("sSortDir_0") String sortDirection0, @RequestParam("sEcho") int sEcho, Principal principal) {
		List<Caso> casosFiltrados = null;
		String accion = null;
		JSONObject res = new JSONObject();
		
		
		
		try {
			MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
			Rol rol = menuVistaPermisosRolesWrapper.getRol();
			if (Integer.parseInt(Parametros.getRolAbogado()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolDependiente()) == rol.getCodigo()) {
				SocialUserDetails userDetails = Utilidades.getRealPrincipal(principal);
				EquipoCaso usuarioEnEquipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(userDetails.getId());
				casoFiltro.setCodigoEquipoCaso(usuarioEnEquipoCaso.getCodigoEquipoCaso());
			}
			
			casosFiltrados = casoService.encontrarCasoPorFiltro(casoFiltro);
			Long cantidad = casoService.getCountFilter(casoFiltro);
			JSONObject jsO = new JSONObject();
			JSONArray arrayCasosFiltrados = new JSONArray();
			for (Caso caso : casosFiltrados) {
				accion = "";
				jsO = new JSONObject();
				jsO.put("codigo", caso.getCodigo());
				jsO.put("nombre", caso.getNombre());
				jsO.put("estadoCaso", caso.getEstadoCaso().getNombre());
				jsO.put("tipoCaso", caso.getTipoCaso().getNombre());
				jsO.put("estadoProcesal", caso.getEstadoProcesal());

				// inicio tabla radicados
				List<Radicado> radicados = caso.getRadicadoSet();
				String tabla = "<table class='table table-bordered'>";				
				if (radicados != null && !radicados.isEmpty()) {
					tabla = tabla + "<thead>";
					tabla = tabla + "<tr>";
					tabla = tabla + "<th style='width: 50%;'>N&uacute;mero de Radicado &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "<th style='width: 50%;'>Instancia &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "</tr>";
					tabla = tabla + "</thead>";
					tabla = tabla + "<tbody>";
					for (Radicado radicado : radicados) {
						if(!radicado.getActivo().equals("N")){
							tabla = tabla + "<tr>";
							tabla = tabla + "<td>" + radicado.getRadicadoPK().getCodigoRadicado() + "&nbsp;&nbsp;&nbsp;&nbsp;"
									+ "</td>";
							tabla = tabla + "<td>" + radicado.getInstancia().getNombre() + "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
							tabla = tabla + "</tr>";
						}
					}
					tabla = tabla + "</tbody>";
				} else {
					tabla = tabla + "<tr>";
					tabla = tabla + "<th>El caso no tiene radicados &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "</tr>";
				}
				tabla = tabla + "</table>";
				jsO.put("detalle", tabla);
				accion = accion
						+ "<SPAN title='Ver detalles'><a name='verDetalle'><i  id='verDetalle' class='glyphicon glyphicon-chevron-down'></i></a></SPAN>";
				jsO.put("accion", accion);
				
				// fin tabla radicados e inicio tabla equipo del caso
								
				List<CasoEquipoCaso> casosEquipoCaso = caso.getCasoEquipoCasoSet();
				tabla = "<table class='table table-bordered'>";
				Calendar calendario = Calendar.getInstance();
				Long fechaLimite = calendario.getTimeInMillis() - (24 * 60 * 60 * 1000);
				List<ResponsableTarea> fechasTareasProximasAVencer = new ArrayList<ResponsableTarea>();

				if (casosEquipoCaso != null && !casosEquipoCaso.isEmpty()) {
					tabla = tabla + "<thead>";
					tabla = tabla + "<tr>";
					tabla = tabla + "<th style='width:20%'>Nombre &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "<th style='width:20%'>Tipo de Miembro &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "<th style='width:20%'>Tel&eacute;fonos &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "<th style='width:20%'>Celulares &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "<th style='width:20%'>Correo &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "</tr>";
					tabla = tabla + "</thead>";
					tabla = tabla + "<tbody>";
					for (CasoEquipoCaso casoEquipoCaso : casosEquipoCaso) {
						if (Parametros.getMiembroActivoSi().equals(casoEquipoCaso.getActivo())) {
							tabla = tabla + "<tr>";
							if (casoEquipoCaso.getEquipoCaso().getApellido() != null) {
								
								tabla = tabla + "<td>" + casoEquipoCaso.getEquipoCaso().getNombre() + "&nbsp;&nbsp;"
										+ casoEquipoCaso.getEquipoCaso().getApellido() + "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
							} else {
								tabla = tabla + "<td>" + casoEquipoCaso.getEquipoCaso().getNombre() + "&nbsp;&nbsp;&nbsp;&nbsp;"
										+ "</td>";
							}
							// tabla = tabla + "<td>" +
							// casoEquipoCaso.getEquipoCaso().getApellido() +
							// "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
							if(casoEquipoCaso.getTipoMiembro().getNombre().equals("Victima")){
								tabla = tabla + "<td>" + casoEquipoCaso.getTipoMiembro().getNombre() + "&nbsp;&nbsp;&nbsp;&nbsp;"
									+ "</td>";
							}else if(casoEquipoCaso.getTipoMiembro().getNombre().equals("Demandante")){
								tabla = tabla + "<td>" + casoEquipoCaso.getTipoMiembro().getNombre() + "&nbsp;&nbsp;&nbsp;&nbsp;"
										+ "</td>";
							}else if(casoEquipoCaso.getTipoMiembro().getNombre().equals("Testigo")){
								tabla = tabla + "<td>" + casoEquipoCaso.getTipoMiembro().getNombre() + "&nbsp;&nbsp;&nbsp;&nbsp;"
										+ "</td>";
							}else if(casoEquipoCaso.getTipoMiembro().getNombre().equals("Demandado")){
								tabla = tabla + "<td>" + casoEquipoCaso.getTipoMiembro().getNombre() + "&nbsp;&nbsp;&nbsp;&nbsp;"
										+ "</td>";
							}else if(casoEquipoCaso.getTipoMiembro().getNombre().equals("Abogado")){
								tabla = tabla + "<td>" + casoEquipoCaso.getTipoMiembro().getNombre() + "&nbsp;&nbsp;&nbsp;&nbsp;"
										+ "</td>";
							}else if(casoEquipoCaso.getTipoMiembro().getNombre().equals("Otro")){
								tabla = tabla + "<td>" + casoEquipoCaso.getTipoMiembro().getNombre() + "&nbsp;&nbsp;&nbsp;&nbsp;"
										+ "</td>";
							}
							// List<Telefono> telefonos =
							// casoEquipoCaso.getEquipoCaso().getTelefonoList();
							
							// se muestran los telefonos del miembro del equipo
							List<Telefono> telefonos = casoEquipoCaso.getEquipoCaso().getTelefonoList();
							if (telefonos != null && telefonos.size() > 0) {
								
								String numerosTelefonos = "";
								for (Telefono telefono : telefonos) {
									numerosTelefonos += telefono.getNumero() + ", ";
								}
								tabla = tabla + "<td>" + numerosTelefonos + "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
								
							} else {
								tabla = tabla + "<td>" + "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
								
							}
							
							// se muestran los Celulares del miembro del equipo
							List<Celular> celulares = casoEquipoCaso.getEquipoCaso().getCelularList();
							if (celulares != null && celulares.size() > 0) {
								
								String numerosCelular = "";
								for (Celular celular : celulares) {
									numerosCelular += celular.getNumero() + ", ";
								}
								tabla = tabla + "<td>" + numerosCelular + "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
								
							} else {
								
								tabla = tabla + "<td>" + "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
							}
							
							// se muestran los Correos del miembro del equipo
							
							List<Correo> correos = casoEquipoCaso.getEquipoCaso().getCorreoList();
							if (correos != null && correos.size() > 0) {
								
								String correosElectronicos = "";
								for (Correo correo : correos) {
									correosElectronicos += correo.getCorreo() + ", ";
								}
								tabla = tabla + "<td>" + correosElectronicos + "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
								
							} else {
								
								tabla = tabla + "<td>" + "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
							}
							
							tabla = tabla + "</tr>";
							
							for (ResponsableTarea responsables : casoEquipoCaso.getResponsablesTareas()) {
								TareaParticularCaso tarea = responsables.getTareasParticularesCaso();
								if (tarea.getFechaLimite() != null && tarea.getFechaLimite().getTime() > fechaLimite && Parametros.getParametroSiCorto().equals(tarea.getSnActiva())) {
									fechasTareasProximasAVencer.add(responsables);
								}
							}
						}
					}															
					tabla = tabla + "</tbody>";
				} else {
					tabla = tabla + "<tr>";
					tabla = tabla + "<th>El caso no tiene miembros &nbsp;&nbsp;&nbsp;&nbsp;</th>";
					tabla = tabla + "</tr>";

				}
				tabla = tabla + "</table>";
				jsO.put("detalleEquipoCaso", tabla);
				accion = "";
				accion = accion
						+ "<SPAN title='Ver detalles'><a name='verDetalleEquipoCaso'><i  id='verDetalle' class='glyphicon glyphicon-chevron-down'></i></a></SPAN>";
				jsO.put("accionEquipo", accion);

				List<ActividadCaso> actividades = actividadCasoService.getActividadByCaso(caso.getCodigo());
				
				jsO.put("detalleTarea", crearTablaActividades(actividades));

				accion = "";
				accion = accion
						+ "<SPAN title='Ver detalles'><a name='verDetalleTarea'><i  id='verDetalle' class='glyphicon glyphicon-chevron-down'></i></a></SPAN>";
				jsO.put("accionDetalleTarea", accion);

				// fin tabla

				arrayCasosFiltrados.add(jsO);
			}

			res.put("sEcho", Integer.valueOf(sEcho));
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", arrayCasosFiltrados);
			
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error en el listado de casos. El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error en el listado de casos. El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (Exception e) {
			LOG.error("Exception: Ocurrio un error en el listado de casos. El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}
		

		return res.toString();

	}

	private String crearTablaActividades(List<ActividadCaso> actividades) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder tabla = new StringBuilder();
		tabla.append("<table class='table table-bordered'>");
		tabla.append("<thead>");
		tabla.append("<tr>");
		tabla.append("<th>Actividad</th>");
		tabla.append("<th>Fecha de Vencimiento</th>");
		tabla.append("<th>Estado</th>");
		tabla.append("</tr>");
		tabla.append("</thead>");
		tabla.append("<tbody>");

		for (ActividadCaso actividad : actividades) {
			if (Parametros.getCodigoActividadActivoSi().equals(actividad.getSnActiva())) {				
				String estado = "";
				Date fechaLimite = new Date(actividad.getFechaLimite().getTime());
				tabla.append("<tr>");
				tabla.append("<td>" + actividad.getNombreActividad() + "</td>");
				tabla.append("<td>" + formatoFecha.format(fechaLimite) + "</td>");
				if (Parametros.getActividadFinalizada().equals(actividad.getFinalizada())) 
					estado = "Completada";
				else {
					Calendar fechaActual = Calendar.getInstance();
					fechaActual.add(Calendar.DATE, -1);
					fechaActual.set(Calendar.HOUR_OF_DAY, 23);
					fechaActual.set(Calendar.MINUTE, 59);
					if (fechaActual.getTime().after(fechaLimite)) 
						estado = "Vencida";
					else
						estado = "Pendiente";
				}
				tabla.append("<td>" + estado + "</td>");
				tabla.append("</tr>");
				tabla.append("</tbody>");
			}
		}
		
		tabla.append("</table>");
		return tabla.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/canIsDeleteTipoCaso")
	public @ResponseBody String guardar(@ModelAttribute("tipoCaso") TipoCaso tipoCaso) throws Exception {
		JSONObject res = new JSONObject();
		try {
			Long cantidad = casoService.getCountByTipoCasoCaso(tipoCaso);
			if (cantidad.intValue() == 0) {
				res.put("STATUS", estadoExito);
			} else {
				res.put("STATUS", estadoError);
			}

		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error validando si se puede eliminar el tipo de caso . El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			LOG.error(
					"BusinessException: Ocurrio un error validando si se puede eliminar el tipo de caso . El error es: "
							+ e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toString();
	}

	// modificar estado del caso
	@SuppressWarnings("unchecked")
	@RequestMapping("/guardarEstadoCaso")
	public @ResponseBody String guardarEstadoCaso(@ModelAttribute("caso") Caso caso, Justificacion justificacion,
			HttpServletRequest request) throws Exception {
		JSONObject res = new JSONObject();

		try {
			caso = casoService.modificarEstadoCaso(caso, justificacion, request);

			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}

		return res.toString();
	}

	// Modicar detalle del caso
	@SuppressWarnings("unchecked")
	@RequestMapping("/guardarDetalleCaso")
	public @ResponseBody String guardarDetalleCaso(@ModelAttribute("caso") Caso caso,boolean fechaCaducidadCambio,Principal principal) throws Exception {
		JSONObject res = new JSONObject();
		try {
			SocialUserDetails datosUsuario = Utilidades.getRealPrincipal(principal);
			caso = casoService.modificarDetalleCaso(caso);
			
			if(fechaCaducidadCambio && enviarCorreoCambioFecha)
				casoService.enviarCorreoCambioFechaCaducidad(caso, datosUsuario);

			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}

		return res.toString();
	}

	// consultar detalle miembro del caso
	@SuppressWarnings("unchecked")
	@RequestMapping("/consultarEquipoCaso")
	public @ResponseBody JSONObject consultarEquipoCaso(
			@ModelAttribute("CasoEquipoCasoPK") CasoEquipoCasoPK casoEquipoCasoPK) throws Exception {
		JSONObject res = new JSONObject();
		CasoEquipoCaso casoEquipoCaso = null;
		try {
			casoEquipoCaso = casoEquipoCasoService.consultarCasoEquipoCaso(casoEquipoCasoPK);
			for (ResponsableTarea responsables : casoEquipoCaso.getResponsablesTareas()) {
				TareaParticularCaso tarea = responsables.getTareasParticularesCaso();
				if (tarea.getActividadParticular().getFinalizada() == null || tarea.getActividadParticular()
						.getFinalizada().equals(Parametros.getEstadoActividadPendiente())) {
					if (tarea.getFinalizada() == null
							|| tarea.getFinalizada().equals(Parametros.getEstadoActividadPendiente())) {
						casoEquipoCaso.setHasActividadesPendientes(true);
						break;
					}
				}
			}
			res.put("STATUS", "SUCCESS");
			res.put("casoEquipoCaso", casoEquipoCaso);
			if (casoEquipoCaso.getEquipoCaso().getUsuario() != null) {				
				res.put("tarjetaProfesional", casoEquipoCaso.getEquipoCaso().getUsuario().getNumeroTarjetaProfesional());
			}
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/modificarMiembroEquipoCaso" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = {
					"application/json; charset=utf-8" }, headers = { "Accept=*/*" })
	public @ResponseBody String modificarMiembroEquipoCaso(
			@ModelAttribute(value = "listadoArchivos") ListadoArchivos listadoArchivos, HttpServletRequest context,
			@ModelAttribute("CasoEquipoCaso") CasoEquipoCaso casoEquipoCaso, CasoEquipoCasoPK casoEquipoCasoPK)
					throws Exception {
		JSONObject res = new JSONObject();
		MultipartFile file = null;
		try {
			if (listadoArchivos != null) {

				String[] nombres = new String[listadoArchivos.size()];
				for (int i = 0; i < listadoArchivos.size(); i++) {
					file = (MultipartFile) listadoArchivos.getArchivos().get(i);
					if (!file.isEmpty()) {
						nombres[i] = file.getOriginalFilename();
						InputStream inputStream = file.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
						File archivo = new File(Parametros.getRutaCargaArchivos());
						if (!archivo.exists()) {
							archivo.mkdir();
						}
						archivo = new File(Parametros.getRutaCargaArchivos() + file.getOriginalFilename());
						file.transferTo(archivo);
					}

				}
			}
			if (listadoArchivos != null) {
				casoEquipoCaso = casoEquipoCasoService.modificarMiembroEquipoCaso(casoEquipoCaso, casoEquipoCasoPK,
						file.getOriginalFilename());

			} else {
				casoEquipoCaso = casoEquipoCasoService.modificarMiembroEquipoCaso(casoEquipoCaso, casoEquipoCasoPK,
						null);

			}

			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}  catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/modificarMiembroEquipoCasoSinFoto")
	public @ResponseBody String modificarMiembroEquipoCasoSinFoto(
			@ModelAttribute("CasoEquipoCaso") CasoEquipoCaso casoEquipoCaso, CasoEquipoCasoPK casoEquipoCasoPK)
					throws Exception {
		JSONObject res = new JSONObject();

		try {
			casoEquipoCaso = casoEquipoCasoService.modificarMiembroEquipoCaso(casoEquipoCaso, casoEquipoCasoPK, null);

			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}
		return res.toString();
	}

	// consultar detalle miembro del caso
	@SuppressWarnings("unchecked")
	@RequestMapping("/inactivarEquipoCaso")
	public @ResponseBody String inactivarEquipoCaso(
			@ModelAttribute("CasoEquipoCasoPK") CasoEquipoCasoPK casoEquipoCasoPK,
			@ModelAttribute("justificacion") Justificacion justificacion) throws Exception {
		JSONObject res = new JSONObject();
		List<String> parametros = new ArrayList<String>();
		CasoEquipoCaso casoEquipoCaso = null;
		Caso codigoCaso = new Caso(casoEquipoCasoPK.getCodigo());
		
		try {
			casoEquipoCaso = casoEquipoCasoService.inactivarEquipoCaso(casoEquipoCasoPK);
			parametros.add(casoEquipoCaso.getTipoMiembro().getNombre());
			parametros.add(casoEquipoCaso.getEquipoCaso().getNombre() + " "
					+ (casoEquipoCaso.getEquipoCaso().getApellido() == null ? ""
							: casoEquipoCaso.getEquipoCaso().getApellido()));
			parametros.add(casoEquipoCaso.getEquipoCaso().getIdentificacion() == null ? ""
					: casoEquipoCaso.getEquipoCaso().getIdentificacion());
			String informacion = Parametros.getAuditoriaMiembro();
			informacion = justificacionService.construirInformacionEliminada(parametros, informacion);
			justificacion.setInformacionEliminada(informacion);
			justificacion.setTipoAccion(Parametros.getTipoAccionEliminacion());
			justificacion.setCampoModificado(Parametros.getCampoModificadoMiembro());
			justificacion.setCodigoCaso(codigoCaso);
			justificacionService.guardarJustificacion(justificacion);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/validarCantidadMiembros")
	public @ResponseBody String validarCantidadMiembros(@RequestParam("codigo") Integer codigo, @RequestParam("tipoMiembro") Integer tipoMiembro) throws Exception {
		JSONObject res = new JSONObject();
		Caso caso = new Caso(codigo);
		int contadorMiembros = 0;
		try {
			caso = casoService.findByPK(caso);
			for (CasoEquipoCaso miembro : caso.getCasoEquipoCasoSet()) {
				if (miembro.getActivo() != null && miembro.getActivo().equals(Parametros.getEstadoActivo())
						&& miembro.getCasoEquipoCasoPK().getMiembro() == tipoMiembro)
					contadorMiembros++;
			}
			if (contadorMiembros > 1)
				res.put("STATUS", "SUCCESS");
			else
				res.put("STATUS", "ERROR");
		} catch (DAOException e) {
			LOG.error(
					"DAOException: Ocurrio un error consultando los miembros del caso. El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException:  Ocurrio un error consultando los miembros del caso. El error es: "
					+ e.getMessage());
			res.put("STATUS", errorMessage);
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/asignarNuevoResponsable")
	public @ResponseBody String asignarNuevoResponsable(
			@ModelAttribute("CasoEquipoCasoPK") CasoEquipoCasoPK casoEquipoCasoPK,
			@RequestParam(value = "idUsuario") String idUsuario) throws Exception {
		JSONObject res = new JSONObject();
		CasoEquipoCaso casoEquipoCaso = null;
		List<User> usuario = null;
		boolean isMiembroExistente = false;
		CasoEquipoCasoPK equipoCasoExistentePK = null;
		try {
			Caso caso = casoService.findByPK(new Caso(casoEquipoCasoPK.getCodigo()));
			usuario = equipoCasoService.obtenerAbogadosSeleccionados(idUsuario);
			casoEquipoCaso = casoEquipoCasoService.consultarCasoEquipoCaso(casoEquipoCasoPK);
			if (usuario != null && !usuario.isEmpty()) {
				User user = usuario.get(0);
				for (CasoEquipoCaso iterador : caso.getCasoEquipoCasoSet()) {
					if (user.getId().equals(iterador.getEquipoCaso().getIdentificacion())) {
						isMiembroExistente = true;
						equipoCasoExistentePK = iterador.getCasoEquipoCasoPK();
						break;
					}
				}
				if (isMiembroExistente) {
					asignarTareasMiembroExistente(casoEquipoCaso, equipoCasoExistentePK);
				} else {
					asignarTareasMiembroNuevo(casoEquipoCaso, user);
				}
				
				
				res.put("STATUS", "SUCCESS");
			} else {
				res.put("STATUS", "ERROR");
			}
		} catch (DAOException e) {
			LOG.error(
					"DAOException: Ocurrio un error consultando los miembros del caso. El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException:  Ocurrio un error consultando los miembros del caso. El error es: "
					+ e.getMessage());
			res.put("STATUS", errorMessage);
		}
		return res.toString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asignarCasosNuevoResponsable",method = RequestMethod.POST)
	public @ResponseBody String asignarCasosNuevoResponsable(@ModelAttribute HelperWrapperCasoEquipoCasoPK helperWrapperCasoEquipoCasoPK) throws Exception {
		int casosModificados = 0;
		int casosError = 0;
		JSONObject res = new JSONObject();
		CasoEquipoCaso casoEquipoCaso = null;
		List<User> usuario = null;
		boolean isMiembroExistente = false;
		CasoEquipoCasoPK equipoCasoExistentePK = null;
		for(CasoEquipoCasoPK casoEquipoCasoPK : helperWrapperCasoEquipoCasoPK.getCasoEquiposCasoPK()) {
			try {
				Caso caso = casoService.findByPK(new Caso(casoEquipoCasoPK.getCodigo()));
				usuario = equipoCasoService.obtenerAbogadosSeleccionados(helperWrapperCasoEquipoCasoPK.getIdAbogado());
				casoEquipoCaso = casoEquipoCasoService.consultarCasoEquipoCaso(casoEquipoCasoPK);
				if (usuario != null && !usuario.isEmpty()) {
					User user = usuario.get(0);
					for (CasoEquipoCaso iterador : caso.getCasoEquipoCasoSet()) {
						if (user.getId().equals(iterador.getEquipoCaso().getIdentificacion())) {
							isMiembroExistente = true;
							equipoCasoExistentePK = iterador.getCasoEquipoCasoPK();
							break;
						}
					}
					if (isMiembroExistente) {
						asignarTareasMiembroExistente(casoEquipoCaso, equipoCasoExistentePK);
						casosModificados++;
					} else {
						asignarTareasMiembroNuevo(casoEquipoCaso, user);
						casosModificados++;
					}
					casoEquipoCaso.setActivo("N");
					casoEquipoCasoService.guardarCasoEquipoCaso(casoEquipoCaso);
				}
				
			} catch (DAOException e) {
				casosError++;
			} catch (BusinessException e) {
				casosError++;
			}
			
		}
		res.put("STATUS", "SUCCESS");
		res.put("casosModificados", casosModificados);
		res.put("casosError", casosError);
		return res.toString();
		
	}

	private void asignarTareasMiembroNuevo(CasoEquipoCaso casoEquipoCaso, User user)
			throws DAOException, BusinessException {
		CasoEquipoCaso nuevoCasoEquipoCaso = new CasoEquipoCaso();
		EquipoCaso equipoCaso = new EquipoCaso();
		equipoCaso.setUsuario(user);
		equipoCaso.setNombre(user.getNombre());
		equipoCaso.setApellido(user.getApellido());
		equipoCaso.setIdentificacion(user.getId());
		equipoCaso = equipoCasoService.guardarEquipoCaso(equipoCaso);
		CasoEquipoCasoPK nuevoCasoEquipoCasoPK = new CasoEquipoCasoPK(casoEquipoCaso.getCasoEquipoCasoPK().getCodigo(),
				casoEquipoCaso.getCasoEquipoCasoPK().getCodigoEquipoCaso(),
				casoEquipoCaso.getCasoEquipoCasoPK().getMiembro());
		nuevoCasoEquipoCasoPK.setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
		nuevoCasoEquipoCaso.setCasoEquipoCasoPK(nuevoCasoEquipoCasoPK);
		nuevoCasoEquipoCaso.setActivo(casoEquipoCaso.getActivo());
		nuevoCasoEquipoCaso.setDireccion(user.getDireccion());
		nuevoCasoEquipoCaso.setPersonajuridica(casoEquipoCaso.getPersonajuridica());
		nuevoCasoEquipoCaso.setContacto(casoEquipoCaso.getContacto());
		nuevoCasoEquipoCaso = casoEquipoCasoService.guardarCasoEquipoCaso(nuevoCasoEquipoCaso);
		List<ResponsableTarea> tareasPendientes = new ArrayList<ResponsableTarea>();
		for (ResponsableTarea responsable : casoEquipoCaso.getResponsablesTareas()) {
			TareaParticularCaso tarea = new TareaParticularCaso();
			tarea = responsable.getTareasParticularesCaso();
			if (tarea.getActividadParticular().getFinalizada() == null || tarea.getActividadParticular().getFinalizada()
					.equals(Parametros.getEstadoActividadPendiente())) {
				if (tarea.getFinalizada() == null
						|| tarea.getFinalizada().equals(Parametros.getEstadoActividadPendiente())) {
					responsable.getResponsableTareaPK()
							.setCodigoEquipoCaso(nuevoCasoEquipoCaso.getCasoEquipoCasoPK().getCodigoEquipoCaso());
					tareasPendientes.add(responsable);
				}
			}
		}
		nuevoCasoEquipoCaso.setResponsablesTareas(tareasPendientes);
		nuevoCasoEquipoCaso.setActivo(Parametros.getMiembroActivoSi());
		nuevoCasoEquipoCaso = casoEquipoCasoService.guardarCasoEquipoCaso(nuevoCasoEquipoCaso);
	}

	private void asignarTareasMiembroExistente(CasoEquipoCaso casoEquipoCaso, CasoEquipoCasoPK equipoCasoExistentePK)
			throws DAOException, BusinessException {
		CasoEquipoCaso casoEquipoCasoExistente = casoEquipoCasoService.consultarCasoEquipoCaso(equipoCasoExistentePK);
		List<ResponsableTarea> tareasPendientes = new ArrayList<ResponsableTarea>();
		for (ResponsableTarea responsable : casoEquipoCaso.getResponsablesTareas()) {
			TareaParticularCaso tarea = new TareaParticularCaso();
			tarea = responsable.getTareasParticularesCaso();
			if (tarea.getActividadParticular().getFinalizada() == null || 
					tarea.getActividadParticular().getFinalizada().equals(Parametros.getEstadoActividadPendiente())) {
				if (tarea.getFinalizada() == null || 
						tarea.getFinalizada().equals(Parametros.getEstadoActividadPendiente())) {
					responsable.getResponsableTareaPK().setCodigoEquipoCaso(casoEquipoCasoExistente.getCasoEquipoCasoPK().getCodigoEquipoCaso());
					tareasPendientes.add(responsable);
				}
			}
		}
		casoEquipoCasoExistente.setResponsablesTareas(tareasPendientes);
		casoEquipoCasoExistente.setActivo(Parametros.getMiembroActivoSi());
		casoEquipoCasoExistente = casoEquipoCasoService.guardarCasoEquipoCaso(casoEquipoCasoExistente);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/validarCantidadContactos")
	public @ResponseBody String validarCantidadContactos(@RequestParam("codigo") Integer codigo) throws Exception {
		JSONObject res = new JSONObject();
		Caso caso = new Caso(codigo);
		int contadorContactos = 0;
		try {
			caso = casoService.findByPK(caso);
			for (CasoEquipoCaso miembro : caso.getCasoEquipoCasoSet()) {
				if (miembro.getContacto() != null && miembro.getContacto().equals(Parametros.getMiembroContactoSi()))
					contadorContactos++;
			}
			if (contadorContactos > 1)
				res.put("STATUS", "SUCCESS");
			else
				res.put("STATUS", "ERROR");
		} catch (DAOException e) {
			LOG.error(
					"DAOException: Ocurrio un error consultando los miembros del caso. El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException:  Ocurrio un error consultando los miembros del caso. El error es: "
					+ e.getMessage());
			res.put("STATUS", errorMessage);
		}
		return res.toString();
	}

	// metodo para cargar los miembros del caso excepto el que se va a eliminar
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/obtenerDemasMiembrosCaso", method = RequestMethod.GET, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String obtenerDemasMiembrosCaso(
			@ModelAttribute("CasoEquipoCasoPK") CasoEquipoCasoPK casoEquipoCasoPK) {
		List<CasoEquipoCaso> miembros = null;
		JSONObject res = new JSONObject();
		try {
			JSONObject jsO = new JSONObject();
			miembros = casoEquipoCasoService.obtenerMiembrosCaso(casoEquipoCasoPK);
			JSONArray arrayMiembros = new JSONArray();
			for (CasoEquipoCaso miembro : miembros) {
				jsO = new JSONObject();
				jsO.put("codigo", miembro.getCasoEquipoCasoPK().getCodigoEquipoCaso());
				jsO.put("nombre",
						((miembro.getEquipoCaso().getNombre() == null) ? "" : miembro.getEquipoCaso().getNombre()) + " "
								+ ((miembro.getEquipoCaso().getApellido() == null) ? ""
										: miembro.getEquipoCaso().getApellido()));
				jsO.put("tipoMiembro", miembro.getCasoEquipoCasoPK().getMiembro());
				arrayMiembros.add(jsO);
			}
			res.put("miembros", arrayMiembros);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	// metodo para guardar el nuevo contacto que se va a crear.
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/guardarNuevoContacto", method = RequestMethod.GET, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String guardarNuevoContacto(@ModelAttribute("CasoEquipoCaso") CasoEquipoCaso casoEquipoCaso) {
		EquipoCaso equipoCaso = null;
		JSONObject res = new JSONObject();
		try {
			if (casoEquipoCaso.getCasoEquipoCasoPK().getCodigoEquipoCaso() == 0) {
				equipoCaso = casoEquipoCaso.getEquipoCaso();
				equipoCaso = equipoCasoService.guardarEquipoCaso(equipoCaso);
				casoEquipoCaso.setEquipoCaso(equipoCaso);
				casoEquipoCaso.getCasoEquipoCasoPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
			} else {
				casoEquipoCaso = casoEquipoCasoService.consultarCasoEquipoCaso(casoEquipoCaso.getCasoEquipoCasoPK());
				casoEquipoCaso.setContacto(Parametros.getMiembroContactoSi());
			}
			casoEquipoCaso = casoEquipoCasoService.guardarCasoEquipoCaso(casoEquipoCaso);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	// metodo para guardar el nuevo contacto que se va a crear.
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/guardarNuevoMiembro", method = RequestMethod.GET, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String guardarNuevosMiembros(@ModelAttribute("CasoEquipoCaso") Caso caso) {
		JSONObject res = new JSONObject();
		try {
			casoEquipoCasoService.agregarMiembrosEquipoCaso(caso.getCasoEquipoCasoSet());
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			LOG.error("DAOException: Error agregando nuevos miembros del caso.", e);
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			LOG.error("BusinessException: Error agregando nuevos miembros del caso.", e);
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}
	
	
	@RequestMapping(value = "/consultarNombresCasos", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String consultarNombresCasos(Principal principal) {
		JSONObject res = new JSONObject();
		try {
			List<Object[]> casos = null;
			MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
    		Rol rol = menuVistaPermisosRolesWrapper.getRol();
    		if (Integer.parseInt(Parametros.getRolAdmind()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolSocio()) == rol.getCodigo()) {
    			casos = casoService.consultarNombresCasos(null);    			
    		} else {
    			if (Integer.parseInt(Parametros.getRolAbogado()) == rol.getCodigo()) {
    				SocialUserDetails userDetails = Utilidades.getRealPrincipal(principal);
    				EquipoCaso usuarioEnEquipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(userDetails.getId());
    				casos = casoService.consultarNombresCasos(usuarioEnEquipoCaso);
    			}
    		}
			
			JSONArray jsonCasos = new JSONArray();
			for (Object[] caso:casos) {
				jsonCasos.add(crearJsonCaso(caso));
			}
			res.put("STATUS", "SUCCESS");
			res.put("casos", jsonCasos);
		} catch (DAOException e) {
			LOG.error("DAOException: Error consultando los casos creados.", e);
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			LOG.error("DAOException: Error consultando los casos creados.", e);
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("DAOException: Error consultando los casos creados.", e);
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	
	private JSONObject crearJsonCaso(Object[] caso) {
		JSONObject jsO = new JSONObject();
		jsO.put("codigo", caso[0]);
		jsO.put("nombre", caso[1]);
		return jsO;
	}
	
	private ActividadCaso guardarActividadCaso(ActividadCaso actividadCaso) throws DAOException, BusinessException{
		
		//Guardar actividad caso
		List<TareaParticularCaso> tareasParticulares = actividadCaso.getTareaParticularCasoSet();
		actividadCaso.setFechaCreacion(new Date());
		actividadCaso.setFechaUltimaModificacion(new Date());
		actividadCaso.setTareaParticularCasoSet(null);
		actividadCasoService.guardarActividadCasoOtroResponsable(actividadCaso);
		
		
		if (tareasParticulares != null) {
			for(int i = 0; i < tareasParticulares.size(); i++) {
				
				TareaParticularCaso tareaParticular = null;
				tareaParticular = tareasParticulares.get(i);
				List<ResponsableTarea> responsablesTarea = null;
				tareaParticular.setFechaCreacion(new Date());
				tareaParticular.setFechaUltimaModificacion(new Date());
				
				for (ResponsableTarea responsable : tareaParticular.getResponsablesTareas()) {
					if (responsable.getResponsableTareaPK().getCodigoMiembro() == 4) {
						if (responsable.getResponsableTareaPK().getCodigoEquipoCaso() == 0) {
							for (Telefono telefono : responsable.getCasosEquiposCaso().getEquipoCaso().getTelefonoList()) {
								telefono.setCodigoEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
							}
							for (Correo correo : responsable.getCasosEquiposCaso().getEquipoCaso().getCorreoList()) {
								correo.setCodigoEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
							}
							EquipoCaso equipoCaso = actividadCasoService.guardarEquipoCasoOtroResponsable(responsable.getCasosEquiposCaso().getEquipoCaso());
							responsable.getCasosEquiposCaso().setEquipoCaso(equipoCaso);
							
							responsable.getCasosEquiposCaso().setCasoEquipoCasoPK(new CasoEquipoCasoPK(responsable.getResponsableTareaPK().getCodigoCaso(),equipoCaso.getCodigoEquipoCaso(),  responsable.getResponsableTareaPK().getCodigoMiembro()));
							responsable.getResponsableTareaPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
							responsable.getCasosEquiposCaso().setActivo("S");
							responsable.getCasosEquiposCaso().getEquipoCaso().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
							
							actividadCasoService.guardarCasoEquipoCasoOtroResponsable(responsable.getCasosEquiposCaso());
						}
					}
//					else
//						tareaParticular.setResponsablesTareas(null);
				}
				
				
				responsablesTarea = tareaParticular.getResponsablesTareas();
				
				tareaParticular.setActividadParticular(actividadCaso);
				//tareaParticular.setActividadParticular(null);
				
				
				tareaParticular.setResponsablesTareas(null);
				TareaParticularCaso tareaParticularOtroResponsable = actividadCasoService.guardarTareaParticularOtroResponsable(tareaParticular);
				
				if(responsablesTarea != null){
					for (ResponsableTarea responsableTarea : responsablesTarea) {
						responsableTarea.setTareasParticularesCaso(tareaParticularOtroResponsable);
						responsableTarea.getResponsableTareaPK().setCodigoTareaparticular(tareaParticularOtroResponsable.getCodigoTarea());
						responsablesTareaService.guardarResponsableTarea(responsableTarea);
					}
				}
				setFechaFinalizacionTarea(responsablesTarea, tareaParticular);
				
				asignarCodigoTareaResponsable(responsablesTarea, tareaParticularOtroResponsable.getCodigoTarea());
				tareaParticularOtroResponsable.setResponsablesTareas(responsablesTarea);
				//Guardar tarea particular
				
			}
		}	
		//Se guarda de nuevo la actividad del caso ?
		// actividadCasoService.guardarActividadCasoOtroResponsable(actividadCaso);
		
		return actividadCaso;		
	}
	
	
	private void setFechaFinalizacionTarea(List<ResponsableTarea> responsables, TareaParticularCaso tarea) {
		if (Parametros.getActividadFinalizada().equals(tarea.getFinalizada())) {			
			for (ResponsableTarea responsable:responsables) {
				responsable.setFechaFinalizacionTarea(new Date());
			}
		}
	}
	
private void asignarCodigoTareaResponsable(List<ResponsableTarea> responsables, int codigoTarea) {
		
		for (ResponsableTarea responsable:responsables) {			
			responsable.getResponsableTareaPK().setCodigoTareaparticular(codigoTarea);
		}	
	}
	
}

