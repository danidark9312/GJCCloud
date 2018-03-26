package com.gja.gestionCasos.casos.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
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
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.service.ActividadCasoService;
import com.gja.gestionCasos.casos.entities.Abono;
import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.casos.entities.BienAfectadoPK;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.entities.RadicadoAcumulado;
import com.gja.gestionCasos.casos.service.AbonoService;
import com.gja.gestionCasos.casos.service.BienAfectadoService;
import com.gja.gestionCasos.casos.service.CasoService;
import com.gja.gestionCasos.casos.service.PrestamoService;
import com.gja.gestionCasos.casos.service.RadicadoService;
import com.gja.gestionCasos.casos.service.TareaParticularCasoService;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.gja.gestionCasos.reportes.service.JustificacionService;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Controller
@RequestMapping(value = { "/detalleCaso" })
public class DetalleCasoController {

	private static final Logger LOG = Logger.getLogger(DetalleCasoController.class);
	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";

	@Autowired
	CasoService casoService;
	@Autowired
	ActividadCasoService actividadCasoService;
	@Autowired
	RadicadoService radicadoService;
	@Autowired
	BienAfectadoService bienAfectadoService;
	@Autowired
	TareaParticularCasoService tareaParticularCasoService;
	@Autowired
	JustificacionService justificacionService;
	@Autowired
	PrestamoService prestamoService;
	@Autowired
	AbonoService abonoService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;


	@RequestMapping()
	public String createForm(Model model, Locale loc, Caso caso, boolean actMiembro, boolean actMiembroUpdate,
			boolean actActividad, boolean actTarea, boolean addMiembro, boolean addActividad, boolean actConfindencial, boolean actArchivoActividad, boolean redirectActividad, HttpServletRequest httpRequest,
			Principal principal) {

		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();

		menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
		vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
				httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);

		try {
			
			if (caso != null) {
				
				caso = casoService.findByPK(caso);
				
				List<CasoEquipoCaso> abogados = new ArrayList<CasoEquipoCaso>();
				List<CasoEquipoCaso> demandados = new ArrayList<CasoEquipoCaso>(); 
				List<CasoEquipoCaso> demandantes = new ArrayList<CasoEquipoCaso>();
				List<CasoEquipoCaso> victimas = new ArrayList<CasoEquipoCaso>();
				List<CasoEquipoCaso> testigos = new ArrayList<CasoEquipoCaso>();
				List<CasoEquipoCaso> otros = new ArrayList<CasoEquipoCaso>();
				List<CasoEquipoCaso> referenciadores = new ArrayList<CasoEquipoCaso>();
				
				//EQUIPOCASO
				for (CasoEquipoCaso miembroDelEquipo:caso.getCasoEquipoCasoSet()) {
					if(miembroDelEquipo.getTipoMiembro().getCodigo() != null) {
						Integer codigoMiembro = miembroDelEquipo.getTipoMiembro().getCodigo();
						
						if (codigoMiembro == Integer.parseInt(Parametros.getAbogado())) {
							abogados.add(miembroDelEquipo);							
						} else if (codigoMiembro == Integer.parseInt(Parametros.getDemandado())) {
							demandados.add(miembroDelEquipo);
						} else if (codigoMiembro == Integer.parseInt(Parametros.getDemandante())) {
							demandantes.add(miembroDelEquipo);
						} else if (codigoMiembro == Integer.parseInt(Parametros.getVictima())){
							victimas.add(miembroDelEquipo);
						}else if (codigoMiembro == Integer.parseInt(Parametros.getTestigo())){
							testigos.add(miembroDelEquipo);
						}else if (codigoMiembro == Integer.parseInt(Parametros.getOtro())){
							otros.add(miembroDelEquipo);
						}else if (codigoMiembro == Integer.parseInt(Parametros.getReferenciador())){
							referenciadores.add(miembroDelEquipo);
						}						
					}
					
				}
				
				model.addAttribute("abogados", abogados);
				model.addAttribute("demandados", demandados);
				model.addAttribute("demandantes", demandantes);
				model.addAttribute("victimas", victimas);
				model.addAttribute("testigos", testigos);
				model.addAttribute("referenciadores", referenciadores);
				model.addAttribute("otros", otros);
						
				//ACTIVDADES
				List<ActividadCaso> actividades = actividadCasoService.getActividadByCaso(caso.getCodigo());
				model.addAttribute("caso", caso != null ? caso : "");
				model.addAttribute("actividades", actividades);
				model.addAttribute("actividadGroups", getActividadesByGroup(actividades));
				System.out.println(getActividadesByGroup(actividades));
			}
			if (actMiembro != false) {
				model.addAttribute("actMiembro", "true");
			}
			if (actMiembroUpdate != false) {
				model.addAttribute("actMiembroUpdate", "true");
			}
			if (actActividad != false) {
				model.addAttribute("actActividad", "true");
			}
			if (addActividad != false) {
				model.addAttribute("addActividad", "true");
			}
			if (actTarea != false) {
				model.addAttribute("actTarea", "true");
			}
			if (addMiembro != false) {
				model.addAttribute("addMiembro", "true");
			}
			if(actConfindencial != false){
				model.addAttribute("actConfidencial", "true");
			}
			if(actArchivoActividad != false){
				model.addAttribute("actArchivoActividad", "true");
			}
			if(redirectActividad != false){
				model.addAttribute("redirectActividad", "true");
			}
			model.addAttribute("aniosCaducidad", Parametros.getAnosCaducidad());
			model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
			model.addAttribute("permisos", vistaPermisosRolesWrapper);
			if (vistaPermisosRolesWrapper.getListaRolAccionOpcion() != null) {				
				model.addAttribute("snCrear", vistaPermisosRolesWrapper.getListaRolAccionOpcion().get(0).getSnCrear());
				model.addAttribute("snEliminar", vistaPermisosRolesWrapper.getListaRolAccionOpcion().get(0).getSnEliminar());
				model.addAttribute("snEscritura", vistaPermisosRolesWrapper.getListaRolAccionOpcion().get(0).getSnEscritura());
				model.addAttribute("snLectura", vistaPermisosRolesWrapper.getListaRolAccionOpcion().get(0).getSnLectura());
				model.addAttribute("snRestaurar", vistaPermisosRolesWrapper.getListaRolAccionOpcion().get(0).getSnRestaurar());
			}

		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
		}

		return "casos/detalleCaso"; // carpeta y el jsp

	}

	private Map<String, List<ActividadCaso>> getActividadesByGroup(List<ActividadCaso> actividades) {
		Map<String, List<ActividadCaso>> actividadesGroup = new HashMap<>();
		
		List<ActividadCaso> completadas = new ArrayList<ActividadCaso>();
		List<ActividadCaso> pendientes = new ArrayList<ActividadCaso>();
		List<ActividadCaso> vencidas = new ArrayList<ActividadCaso>();
		
		actividadesGroup.put("completadas", completadas);
		actividadesGroup.put("pendientes", pendientes);
		actividadesGroup.put("vencidas", vencidas);
		
		for (ActividadCaso actividad : actividades) {
			if(actividad.getFinalizada().equals("S"))
				completadas.add(actividad);
			else if(actividad.getFinalizada().equals("N")) {
				pendientes.add(actividad);
				Collections.sort(pendientes, new Comparator<ActividadCaso>() {
					@Override
					public int compare(ActividadCaso o1, ActividadCaso o2) {
						return o1.getOrden()-o2.getOrden();
					}
				});
			}
				
			else if(actividad.getFinalizada().equals("V"))
				vencidas.add(actividad);
		}
		return actividadesGroup;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarCaso", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody Caso consulta(@ModelAttribute("caso") Caso caso) {
		JSONObject res;
		try {
			caso = casoService.findByPK(caso);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return caso;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarRadicados", produces = "application/json; charset=utf-8")
	public @ResponseBody List<String> consultarRadicados() {
		List<String> strRadicados = new ArrayList<String>();
		try {
			List<Radicado> radicados = radicadoService.findAll();
			for (Radicado radicado : radicados) {
				strRadicados.add(radicado.getRadicadoPK().getCodigoRadicado());
			}
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			e.printStackTrace();
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			e.printStackTrace();
		}
		return strRadicados;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllTable", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getAllTable(CasoFiltro casoFiltro, Caso caso,
			@RequestParam("iDisplayStart") int displayStart, @RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho, HttpServletRequest request) {
		int cantidad = 0;
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		JSONArray casos = new JSONArray();
		try {
			caso = casoService.findByPK(caso);
			List<BienAfectado> listBienAfectado = caso.getBienAfectadoSet();
			cantidad = listBienAfectado.size();
			String accion = "";
			for (BienAfectado objBienAfectado : listBienAfectado) {
				if (objBienAfectado.getActivo() != null
						&& !objBienAfectado.getActivo().equals(Parametros.getCodigoBienActivoNo())) {
					accion = "";
					jsO = new JSONObject();
					jsO.put("codigoBienAfectdo", objBienAfectado.getBienAfectadoPK().getCodigo());
					jsO.put("tipoBienAfectdo",
							(objBienAfectado.getClase() == null) ? "" : objBienAfectado.getClase().getNombre());
					jsO.put("detalleBienAfectado", objBienAfectado.getDetalle());
					jsO.put("tituloBienAfectado", objBienAfectado.getTitulo());
					accion = accion
							+ "<SPAN title='Modificar Bien Afectado'><a class='btn btn-circle btn-info' data-toggle='modal' href='#modal-modificarBienAfectado'  onclick='consultarBienAfectado("
							+ objBienAfectado.getBienAfectadoPK().getCodigo() + ","
							+ objBienAfectado.getBienAfectadoPK().getCodigoCaso()
							+ ")'><i  id='verDetalle' class='glyphicon glyphicon-pencil'></i></a></SPAN>";
					if (request.isUserInRole("ROLE_SUPER_USER") || request.isUserInRole("ROLE_ADMIN")
							|| request.isUserInRole("ROLE_SOCIO") || request.isUserInRole("ROLE_ASOCIADO")
							|| request.isUserInRole("ROLE_JUNIOR") || request.isUserInRole("ROLE_DEPENDIENTE")) {
						accion = accion + "<span title='Eliminar'><a onclick=\'irConfirmacionBienAfectado(\""
								+ objBienAfectado.getBienAfectadoPK().getCodigo()
								+ "\");\' class='btn btn-circle btn-danger'><i class='glyphicon glyphicon-trash'></i></a>";
					}
					jsO.put("acciones", accion);
					casos.add(jsO);
				}
			}
			res.put("sEcho", sEcho);
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", casos);
			res.put("STATUS", estadoExito);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllTablePrestamos", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getAllTablePrestamos(CasoFiltro casoFiltro, Caso caso,
			@RequestParam("iDisplayStart") int displayStart, @RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho, HttpServletRequest request) {
		int cantidad = 0;
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		JSONArray casos = new JSONArray();
		try {
			caso = casoService.findByPK(caso);
			List<Prestamo> listPrestamo = caso.getPrestamosList();
			cantidad = listPrestamo.size();
			String accion = "";
			StringBuilder dateToStr = new StringBuilder();
			for (Prestamo objPrestamo : listPrestamo) {
				if (objPrestamo.getActivo() != null
						&& !objPrestamo.getActivo().equals(Parametros.getCodigoBienActivoNo())) {
					accion = "";
					dateToStr = new StringBuilder();
					SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
					jsO = new JSONObject();
					jsO.put("nombreDeudorPrestamo", objPrestamo.getNombreDeudor());
					jsO.put("identificacionDeudorPrestamo", objPrestamo.getIdentificacion());
					if (objPrestamo.getFechaPrestamo() != null)
						dateToStr.append(dataFormat.format(objPrestamo.getFechaPrestamo()));
					else
					dateToStr.append("");
					jsO.put("fechaPrestamo", dateToStr.toString());
					jsO.put("interesPrestamo", objPrestamo.getIntereses());
					jsO.put("saldoPrestamo", objPrestamo.getSaldo());
					jsO.put("archivoPrestamo", objPrestamo.getArchivo());
					jsO.put("activoPrestamo", objPrestamo.getActivo());
					jsO.put("canceladoPrestamo", objPrestamo.getCancelado());
					jsO.put("codigoCaso", objPrestamo.getCaso().getCodigo());
					jsO.put("codigoPrestamo", objPrestamo.getCodigoPrestamo());
					jsO.put("descripcionPrestamo", objPrestamo.getDescripcionPrestamo());
					String DatePrestamo = dataFormat.format(objPrestamo.getFechaCreacion());
					jsO.put("fechaCreacionPrestamo", DatePrestamo);
					if (objPrestamo.getEntidadFinaciera() != null)
						jsO.put("nombreEntidadPrestamo",
								objPrestamo.getEntidadFinaciera().getNombreEntidadfinanciera());
					else
						jsO.put("nombreEntidadPrestamo", "");
					if (objPrestamo.getEntidadFinaciera() != null)
						jsO.put("codigoEntidadPrestamo", objPrestamo.getEntidadFinaciera().getCodigoEntidadfinaciera());
					else
						jsO.put("codigoEntidadPrestamo", "");
					jsO.put("montoPrestamo", objPrestamo.getMonto());
					if (objPrestamo.getTipoCuenta() != null)
						jsO.put("tipoCuentaPrestamo", objPrestamo.getTipoCuenta().getCodigoTipoCuenta());
					else
						jsO.put("tipoCuentaPrestamo", "");
					jsO.put("porcentajeInteresPrestamo", objPrestamo.getPorcentajeInteres());
					jsO.put("numeroCuentaPrestamo", objPrestamo.getNumeroCuenta());
					String tabla = "<table style='width: 100%;' class='table table-bordered'>";
					if (objPrestamo.getEntidadFinaciera() != null) {
						tabla = tabla + "<thead>";
						tabla = tabla + "<tr>";
						tabla = tabla + "<th style='width: 30%;'>Tipo &nbsp;&nbsp;&nbsp;&nbsp;</th>";
						tabla = tabla + "<th style='width: 40%;'>Entidad &nbsp;&nbsp;&nbsp;&nbsp;</th>";
						tabla = tabla + "<th style='width: 30%;'>Cuenta &nbsp;&nbsp;&nbsp;&nbsp;</th>";
						tabla = tabla + "</tr>";
						tabla = tabla + "</thead>";
						tabla = tabla + "<tbody>";
						tabla = tabla + "<tr>";
						tabla = tabla + "<td>"
								+ (objPrestamo.getTipoCuenta() == null ? ""
										: objPrestamo.getTipoCuenta().getDescripcioTipocuenta())
								+ "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
						tabla = tabla + "<td>"
								+ (objPrestamo.getEntidadFinaciera() == null ? ""
										: objPrestamo.getEntidadFinaciera().getNombreEntidadfinanciera())
								+ "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
						tabla = tabla + "<td>"
								+ (objPrestamo.getNumeroCuenta() == null ? "" : objPrestamo.getNumeroCuenta())
								+ "&nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
						tabla = tabla + "</tr>";
						tabla = tabla + "</ tbody>";
					} else {
						tabla = tabla + "<tr>";
						tabla = tabla + "<th>El pr\u00E9stamo no tiene informaci\u00F3n financiera</th>";
						tabla = tabla + "</tr>";
					}
					tabla = tabla + "</table>";
					jsO.put("informacionFinancieraDetalle", tabla);
					String accionDetalle = "";
					accionDetalle = accionDetalle
							+ "<SPAN title='Ver detalles'><a name='verDetallePrestamo'><i  id='verDetalle' class='glyphicon glyphicon-plus'></i></a></SPAN>";
					jsO.put("accionDetalle", accionDetalle);
					
					String accionDetalleAbono = "<SPAN title='Ver abonos'><a name='verAbonosPrestamo'><i  id='verAbonos' class='glyphicon glyphicon-plus'></i></a></SPAN>";
					jsO.put("accionDetalle", accionDetalle);
					jsO.put("accionDetalleAbono", accionDetalleAbono);

					String DatePrestamoModificacion = dataFormat.format(objPrestamo.getFechaUltimaModificacion());
					jsO.put("fechaUltimaModificacionPrestamo", DatePrestamoModificacion);
					if (request.isUserInRole("ROLE_SUPER_USER") || request.isUserInRole("ROLE_ADMIN")
							|| request.isUserInRole("ROLE_SOCIO")) {
						accion = accion
								+ "<SPAN title='Modificar Prestamo'><a class='btn btn-circle btn-info' data-toggle='modal' href='#modal-modificarPrestamo'  onclick='consultarPrestamo("
								+ objPrestamo.getCodigoPrestamo() + "," + objPrestamo.getCaso().getCodigo()
								+ ")'><i  id='verDetalle' class='glyphicon glyphicon-pencil'></i></a></SPAN>";
					}
					if (request.isUserInRole("ROLE_SUPER_USER") || request.isUserInRole("ROLE_ADMIN")
							|| request.isUserInRole("ROLE_SOCIO") || request.isUserInRole("ROLE_ASOCIADO")
							|| request.isUserInRole("ROLE_JUNIOR") || request.isUserInRole("ROLE_DEPENDIENTE")) {
						accion = accion
								+ "<span title='Asociar Archivo'><a onclick=\'mostrarModalAsociarArchivoPrestamo("
								+ objPrestamo.getCodigoPrestamo() + "," + objPrestamo.getCaso().getCodigo()
								+ ");\' class='btn btn-circle btn-success'><i class='glyphicon glyphicon-paperclip'></i></a>";
					}
					accion+="<SPAN title='Adicionar abono'><a class='btn btn-circle btn-info' data-toggle='modal' href='#modal-adicionarAbono'  onclick='consultarPrestamoAbono("
							+ objPrestamo.getCodigoPrestamo() + "," + objPrestamo.getCaso().getCodigo()
							+ ")'><i  id='addAbono' class='glyphicon glyphicon-plus'></i></a></SPAN>";
					
					jsO.put("acciones", accion);
					casos.add(jsO);
				}
			}
			res.put("sEcho", sEcho);
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", casos);
			res.put("STATUS", estadoExito);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllTableRadicado", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getAllTableRadicado(CasoFiltro casoFiltro, Caso caso,
			@RequestParam("iDisplayStart") int displayStart, @RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho, HttpServletRequest request) {
		int cantidad = 0;
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		JSONArray casos = new JSONArray();
		String accion = "";
		try {
			caso = casoService.findByPK(caso);
			List<Radicado> listRadicado = caso.getRadicadoSet();
			cantidad = listRadicado.size();
			for (Radicado objRadicado : listRadicado) {
				if (objRadicado.getActivo() != null
						&& !objRadicado.getActivo().equals(Parametros.getCodigoRadicadoActivoNo())) {
					accion = "";
					jsO = new JSONObject();
					jsO.put("estadoRadicado", objRadicado.getActivo());
					jsO.put("codigoRadicado", objRadicado.getRadicadoPK().getCodigoRadicado());
					jsO.put("radicadosAcumulados", getRadicadosAcumuladosAsJson(objRadicado));
					jsO.put("instanciaRadicado", objRadicado.getInstancia().getNombre());
					if (objRadicado.getAcumulado() != null) {
						jsO.put("esaAcumulado",
								objRadicado.getAcumulado().equals(Parametros.getCodigoRadicadoAcumuladoSi())
										? Parametros.getParametroSi() : Parametros.getParametroNo());
					} else {
						jsO.put("esaAcumulado", "");
					}
					accion = accion
							+ "<SPAN title='Modificar Radicado'><a class='btn btn-circle btn-info' data-toggle='modal' href='#modal-modificarRadicado' onclick='consultarRadicado(this)'><i  id='verDetalle' class='glyphicon glyphicon-pencil'></i></a></SPAN>";
					if (request.isUserInRole("ROLE_SUPER_USER") || request.isUserInRole("ROLE_ADMIN")
							|| request.isUserInRole("ROLE_SOCIO") || request.isUserInRole("ROLE_ASOCIADO")
							|| request.isUserInRole("ROLE_JUNIOR") || request.isUserInRole("ROLE_DEPENDIENTE")) {
						accion = accion + "<a onclick=\'irConfirmacionRadicado(\""
								+ objRadicado.getRadicadoPK().getCodigoRadicado()
								+ "\");\' class='btn btn-circle btn-danger' title='Eliminar'><i class='glyphicon glyphicon-trash'></i></a>";
					}
					jsO.put("accionEditar", /*accion*/"");
					casos.add(jsO);
				}
			}
			res.put("sEcho", sEcho);
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", casos);
			res.put("STATUS", estadoExito);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}

	private JSONArray getRadicadosAcumuladosAsJson(Radicado objRadicado) {
		JSONArray array = new JSONArray();
		JSONObject jsonRadicado;
		for(RadicadoAcumulado radicadoAcumulado : objRadicado.getRadicadosAcumulados()) {
			jsonRadicado = new JSONObject();
			jsonRadicado.put("tipo", radicadoAcumulado.getTipoRadicado());
			jsonRadicado.put("radicado", radicadoAcumulado.getRadicadoPK().getCodigoRadicadoAcumulado());
			array.add(jsonRadicado);
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarRadicado")
	public @ResponseBody String obtenerRadicado(@ModelAttribute("Radicado") Radicado radicado) {
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();

		try {
			radicado = radicadoService.obtenerRadicado(radicado.getRadicadoPK());
			jsO = new JSONObject();
			jsO.put("estadoRadicado", radicado.getActivo());
			jsO.put("codigoRadicado", radicado.getRadicadoPK().getCodigoRadicado());
			jsO.put("instanciaRadicado", radicado.getInstancia().getCodigo());
			jsO.put("esaAcumulado", radicado.getAcumulado());

			res.put("radicado", jsO);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}

		return res.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modificarRadicado")
	public @ResponseBody String modificarRadicado(@ModelAttribute("caso") Caso caso,
			@RequestParam(value = "numeroRadicadoActualizar") String numeroRadicadoActualizar) {
		JSONObject res = new JSONObject();
		try {
			List<Radicado> radicado = radicadoService.modificarRadicados(caso.getRadicadoSet(),
					numeroRadicadoActualizar);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error modificando los radicados. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error modificando los radicados. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
			res.put("mensajeError", e.getMessage());
		}

		return res.toJSONString();
	}

	// metodo encargado de eliminar un radicado.
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminarRadicado", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String eliminarRadicado(@ModelAttribute("Radicado") Radicado radicado,
			@ModelAttribute("justificacion") Justificacion justificacion) {
		List<String> parametros = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.getFormatoFechaString());
		JSONObject res = new JSONObject();
		try {
			radicado = radicadoService.eliminarRadicado(radicado);
			parametros.add(radicado.getRadicadoPK().getCodigoRadicado());
			parametros.add(sdf.format(radicado.getFechaCreacion()));
			String informacion = Parametros.getAuditoriaRadicado();
			informacion = justificacionService.construirInformacionEliminada(parametros, informacion);
			justificacion.setInformacionEliminada(informacion);
			justificacion.setTipoAccion(Parametros.getTipoAccionEliminacion());
			justificacion.setCampoModificado(Parametros.getCampoModificadoRadicado());
			justificacionService.guardarJustificacion(justificacion);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarBienAfectado")
	public @ResponseBody String obtenerBienAfectado(@RequestParam("codigoBienAfectado") Integer codigoBienAfectado,
			@RequestParam("codigoCaso") Integer codigoCaso) {
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		BienAfectado bienAfectado = new BienAfectado();
		BienAfectadoPK bienAfectadoPk = new BienAfectadoPK();
		bienAfectadoPk.setCodigo(codigoBienAfectado);
		bienAfectadoPk.setCodigoCaso(codigoCaso);
		bienAfectado.setBienAfectadoPK(bienAfectadoPk);
		try {
			bienAfectado = bienAfectadoService.obtenerBienAfectado(bienAfectado);
			if (bienAfectado != null) {
				jsO = new JSONObject();
				jsO.put("codigoBienAfectado", bienAfectado.getBienAfectadoPK().getCodigo());
				jsO.put("nombreBienAfectado", bienAfectado.getTitulo());
				jsO.put("descripcionBienAfectado", bienAfectado.getDetalle());
				jsO.put("tipoBienAfectado",
						(bienAfectado.getClase() == null) ? "" : bienAfectado.getClase().getCodigo());
				jsO.put("codigoCaso", bienAfectado.getBienAfectadoPK().getCodigoCaso());
				res.put("bienAfectado", jsO);
			}
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}

		return res.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarPrestamo", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String obtenerPrestamo(@ModelAttribute("prestamo") Prestamo prestamo) {
		SimpleDateFormat dataFormat = new SimpleDateFormat(Parametros.getFormatoAnioMesDiaFechaString());
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		Prestamo objPrestamo = new Prestamo();
		try {
			objPrestamo = prestamoService.obtenerPrestamo(prestamo);
			if (objPrestamo != null) {
				jsO = new JSONObject();				
				jsO.put("nombreDeudorPrestamo", objPrestamo.getNombreDeudor());
				jsO.put("identificacionDeudorPrestamo", objPrestamo.getIdentificacion());
				String dateToStr = "";
				if (objPrestamo.getFechaPrestamo() != null)
					dateToStr = dataFormat.format(objPrestamo.getFechaPrestamo());
				jsO.put("fechaPrestamo", dateToStr);
				jsO.put("interesPrestamo", objPrestamo.getIntereses());
				jsO.put("saldoPrestamo", objPrestamo.getSaldo());
				jsO.put("archivoPrestamo", objPrestamo.getArchivo());
				jsO.put("activoPrestamo", objPrestamo.getActivo());
				jsO.put("canceladoPrestamo", objPrestamo.getCancelado());
				jsO.put("codigoCaso", objPrestamo.getCaso().getCodigo());
				jsO.put("codigoPrestamo", objPrestamo.getCodigoPrestamo());
				jsO.put("descripcionPrestamo", objPrestamo.getDescripcionPrestamo());
				String fechaPrestamo = "";
				if (objPrestamo.getFechaCreacion() != null)
					fechaPrestamo = dataFormat.format(objPrestamo.getFechaCreacion());
				jsO.put("fechaCreacionPrestamo", fechaPrestamo);
				jsO.put("nombreEntidadPrestamo", objPrestamo.getEntidadFinaciera() == null ? ""
						: objPrestamo.getEntidadFinaciera().getNombreEntidadfinanciera());
				jsO.put("codigoEntidadPrestamo", objPrestamo.getEntidadFinaciera() == null ? ""
						: objPrestamo.getEntidadFinaciera().getCodigoEntidadfinaciera());
				jsO.put("montoPrestamo", objPrestamo.getMonto());
				jsO.put("tipoCuentaPrestamo",
						objPrestamo.getTipoCuenta() == null ? "" : objPrestamo.getTipoCuenta().getCodigoTipoCuenta());
				jsO.put("porcentajeInteresPrestamo", objPrestamo.getPorcentajeInteres());
				jsO.put("numeroCuentaPrestamo", objPrestamo.getNumeroCuenta());
				jsO.put("tituloPrestamo", objPrestamo.getTitulo());
				String fechaPrestamoModificacion = "";
				if (objPrestamo.getFechaUltimaModificacion() != null)
					fechaPrestamoModificacion = dataFormat.format(objPrestamo.getFechaUltimaModificacion());
				jsO.put("fechaUltimaModificacionPrestamo", fechaPrestamoModificacion);
				jsO.put("cdusuariocreacion", objPrestamo.getUsuarioCreacion());
				jsO.put("archivo", objPrestamo.getActivo());
				consultarSaldos(jsO,prestamo);

				res.put("prestamo", jsO);
			}
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando el caso . El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}

		return res.toJSONString();
	}

	private void consultarSaldos(JSONObject jsO, Prestamo prestamo) throws DAOException, BusinessException {
		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.getFormatoAnioMesDiaFechaString());
		Abono ultimoAbono = abonoService.findUltimoAbono(prestamo);
		Double totalPagado = abonoService.getTotalPagado(prestamo);
		Double interesesPagados = abonoService.getInteresesPagados(prestamo);
		
		jsO.put("existeAbono", ultimoAbono!=null);
		if(ultimoAbono!=null) {
			jsO.put("fechaUltimoAbono", sdf.format(ultimoAbono.getFecha()));
			jsO.put("saldoCapital", ultimoAbono.getSaldoCapital());
			jsO.put("saldoInteres", ultimoAbono.getSaldoInteres());
			jsO.put("interesesPagados", interesesPagados);
			jsO.put("totalPagado", totalPagado);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/actualizarBienAfectado", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String actualizarBienAfectado(@ModelAttribute("BienAfectado") BienAfectado bienAfectado) {
		JSONObject res = new JSONObject();

		try {
			bienAfectado.setActivo(Parametros.getCodigoBienActivoSi());
			bienAfectado = bienAfectadoService.actualizarBienAfectado(bienAfectado);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error actualizando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error(
					"BusinessException: Ocurrio un error consultando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/actualizarPrestamo", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String actualizarPrestamo(@ModelAttribute("Prestamo") Prestamo prestamo) {
		JSONObject res = new JSONObject();
		Prestamo consultaPrestamo = new Prestamo();
		try {
			consultaPrestamo = prestamoService.obtenerPrestamo(prestamo);
			prestamo.setActivo(consultaPrestamo.getActivo());
			prestamo.setCancelado(consultaPrestamo.getCancelado());
			prestamo.setFechaCreacion(consultaPrestamo.getFechaCreacion());
			prestamo.setUsuarioCreacion(consultaPrestamo.getUsuarioCreacion());
			prestamo.setArchivo(consultaPrestamo.getArchivo());
			Date date = new Date();
			prestamo.setFechaUltimaModificacion(date);
			prestamo = prestamoService.actualizarPrestamo(prestamo);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error actualizando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error(
					"BusinessException: Ocurrio un error consultando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/guardarPrestamo", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String guardarPrestamo(@ModelAttribute("Prestamo") Prestamo prestamo) {
		JSONObject res = new JSONObject();
		try {
			if (prestamo != null) {
				Date fechaActual = new Date();
				prestamo.setActivo(Parametros.getPrestamoActivo());
				prestamo.setCancelado(Parametros.getPrestamoCanceladoNO());
				prestamo.setFechaCreacion(fechaActual);
				prestamo.setUsuarioCreacion(prestamo.getUsuarioUltimaModificacion());
				prestamo.setFechaUltimaModificacion(fechaActual);
				prestamo = prestamoService.actualizarPrestamo(prestamo);
				res.put("STATUS", estadoExito);
			} else {
				res.put("STATUS", estadoError);
			}
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error actualizando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error(
					"BusinessException: Ocurrio un error consultando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadListaAbonos", method = RequestMethod.GET, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String loadListaAbonos(@ModelAttribute("Prestamo") Prestamo prestamo) {
		JSONObject res = new JSONObject();
		try {
				List<Abono> abonos = abonoService.findAbonosByPrestamo(prestamo);
				JSONArray jsonArray = new JSONArray();
				for (Abono abono : abonos) {
					JSONObject jsonObject =  convertAbonoToJson(abono);
					jsonArray.add(jsonObject);
				}
				res.put("abonos", jsonArray);
				res.put("STATUS", estadoExito);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error actualizando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error(
					"BusinessException: Ocurrio un error consultando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminarAbono", method = RequestMethod.GET, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String eliminarAbono(@ModelAttribute("Abono") Abono abono) {
		JSONObject res = new JSONObject();
		try {
			System.out.println("Abono a eliminar: "+abono.getCodigo());
			abonoService.deleteAbono(abono);
			res.put("STATUS", estadoExito);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error actualizando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error(
					"BusinessException: Ocurrio un error consultando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUltimoAbono", method = RequestMethod.GET, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String getUltimoAbono(@ModelAttribute("Prestamo") Prestamo prestamo,@RequestParam(value = "fechaAbono",required = false) Date fechaAbono) {
		JSONObject res = new JSONObject();
		try {
			Abono abono = abonoService.findUltimoAbono(prestamo);
			JSONObject jsonAbono = convertAbonoToJson(abono);
			Date fechaUltimoAbono = null;
			if(abono==null)
				fechaUltimoAbono=prestamo.getFechaPrestamo();
			else
				fechaUltimoAbono=abono.getFecha();
			
			jsonAbono.put("diasTranscurridos",calcularDiasTranscurridos(fechaUltimoAbono,fechaAbono));
			
			res.put("abono", jsonAbono);
			res.put("STATUS", estadoExito);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error actualizando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error(
					"BusinessException: Ocurrio un error consultando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}
		
	private int calcularDiasTranscurridos(Date fechaInicial,Date fechaFinal) {
		fechaFinal = fechaFinal == null ? new Date() : fechaFinal;
		
		long fechainicialms = fechaInicial.getTime();
		long fechafinalms = fechaFinal.getTime();
		long diferencia = fechafinalms - fechainicialms;
		double dias = Math.floor(diferencia / 86400000L);// 3600*24*1000 
		
		return (int)dias;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/guardarAbono", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String guardarAbono(@ModelAttribute("Abono") Abono abono) {
		JSONObject res = new JSONObject();
		 try {
			if (abono != null) {
				abono = abonoService.guardarAbono(abono);
				res.put("STATUS", estadoExito);
			} else {
				res.put("STATUS", estadoError);
			}
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error actualizando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error(
					"BusinessException: Ocurrio un error consultando el bien afectado. El error es: " + e.getMessage());
			res.put("STATUS", estadoError);
		}
		return res.toJSONString();
	}

	// metodo encargado de eliminar un bien Afectado.
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminarBienAfectado", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String eliminarBienAfectado(@ModelAttribute("BienAfectado") BienAfectado bienAfectado,
			@ModelAttribute("justificacion") Justificacion justificacion) {
		List<String> parametros = new ArrayList<String>();
		JSONObject res = new JSONObject();
		try {
			bienAfectado = bienAfectadoService.eliminarBienAfectado(bienAfectado);
			parametros.add(bienAfectado.getTitulo());
			parametros.add(bienAfectado.getClase() == null ? "" : bienAfectado.getClase().getNombre());
			String informacion = Parametros.getAuditoriaBienAfectado();
			informacion = justificacionService.construirInformacionEliminada(parametros, informacion);
			justificacion.setInformacionEliminada(informacion);
			justificacion.setTipoAccion(Parametros.getTipoAccionEliminacion());
			justificacion.setCampoModificado(Parametros.getCampoModificadoBienAfectado());
			justificacionService.guardarJustificacion(justificacion);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	// metodo encargado de eliminar una tarea.
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminarTarea", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String eliminarTarea(@ModelAttribute("TareaParticularCaso") TareaParticularCaso tarea,
			@ModelAttribute("justificacion") Justificacion justificacion) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		List<String> parametros = new ArrayList<String>();
		JSONObject res = new JSONObject();
		try {
			tarea = tareaParticularCasoService.eliminarTareaParticularCaso(tarea);
			parametros.add(tarea.getDetalle());
			parametros.add(Integer.toString(tarea.getCodigoTarea()));
			parametros.add(sdf.format(tarea.getFechaCreacion()));
			parametros.add(sdf.format(tarea.getFechaLimite()));
			parametros.add(
					tarea.getActividadParticular() == null ? "" : tarea.getActividadParticular().getNombreActividad());
			parametros.add(tarea.getActividadParticular() == null ? ""
					: Integer.toString(tarea.getActividadParticular().getCodigoActividadParticular()));
			String informacion = Parametros.getAuditoriaTarea();
			informacion = justificacionService.construirInformacionEliminada(parametros, informacion);
			justificacion.setInformacionEliminada(informacion);
			justificacion.setTipoAccion(Parametros.getTipoAccionEliminacion());
			justificacion.setCampoModificado(Parametros.getCampoModificadoTarea());
			justificacionService.guardarJustificacion(justificacion);
			enviarCorreoAuditoria(tarea, justificacion);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR");
		} catch (MessagingException e) {
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminarActividad", method = RequestMethod.POST, produces = "aplication/json; charset=utf-8")
	public @ResponseBody String eliminarActividad(@ModelAttribute("TareaParticularCaso") ActividadCaso actividad,
			@ModelAttribute("justificacion") Justificacion justificacion) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		List<String> parametros = new ArrayList<String>();
		JSONObject res = new JSONObject();
		try {
			actividad = actividadCasoService.eliminarActividadCaso(actividad);
			parametros.add(actividad.getNombreActividad());
			parametros.add(Integer.toString(actividad.getCodigoActividadParticular()));
			parametros.add(sdf.format(actividad.getFechaCreacion()));
			parametros.add(sdf.format(actividad.getFechaLimite()));
			StringBuilder strBuilder = new StringBuilder();
			getClass();
			String tareas = "";
			for (TareaParticularCaso tarea : actividad.getTareaParticularCasoSet()) {
				strBuilder.append(Integer.toString(tarea.getCodigoTarea()) + " " + tarea.getDetalle() + ", ");
			}
			if (strBuilder != null)
				tareas = strBuilder.toString().substring(0, strBuilder.toString().length() - 2);
			parametros.add(tareas);
			String informacion = Parametros.getAuditoriaActividad();
			informacion = justificacionService.construirInformacionEliminada(parametros, informacion);
			justificacion.setInformacionEliminada(informacion);
			justificacion.setTipoAccion(Parametros.getTipoAccionEliminacion());
			justificacion.setCampoModificado(Parametros.getCampoModificadoActividad());
			justificacionService.guardarJustificacion(justificacion);
			enviarCorreoAuditoria(actividad, justificacion);
			res.put("STATUS", "SUCCESS");
		} catch (DAOException e) {
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			res.put("STATUS", "ERROR");
		} catch (MessagingException e) {
			res.put("STATUS", "ERROR");
		}

		return res.toString();
	}

	private void enviarCorreoAuditoria(Object obj, Justificacion justificacion)
			throws MessagingException, BusinessException, DAOException {
		ActividadCaso actividad = null;
		TareaParticularCaso tarea = null;
		try {
			if (obj instanceof ActividadCaso) {
				actividad = (ActividadCaso) obj;
			}
			if (obj instanceof TareaParticularCaso) {
				tarea = (TareaParticularCaso) obj;
			}
			StringBuilder cuerpoMensaje = new StringBuilder();
			List<User> listaUsuarios = new ArrayList<User>();
			listaUsuarios = casoService.consultarCorreoAdmind();
			MimeMessage message = null;
			MimeMessageHelper helper = null;
			message = this.mailSender.createMimeMessage();
			helper = new MimeMessageHelper(message, true);
			List to = new ArrayList();
			for (User objUser : listaUsuarios) {
				to.add(objUser.getEmail());
			}
			String nombreCaso = "";
			if (justificacion.getCodigoCaso() != null) {
				nombreCaso = casoService.getNombreCaso(justificacion.getCodigoCaso().getCodigo());
			}
			cuerpoMensaje.append("Nombre Caso: " + nombreCaso + "\n");
			if (actividad != null) {
				cuerpoMensaje.append("Actividad: " + actividad.getNombreActividad() + "\n");
				helper.setSubject(Parametros.getAsuntoEliminacionActividad() + " " + actividad.getNombreActividad());
			}
			if (tarea != null) {
				cuerpoMensaje.append("Tarea: " + tarea.getTarea() + "\n");
				helper.setSubject(Parametros.getAsuntoEliminacionTarea() + " " + tarea.getTarea());
			}
			cuerpoMensaje.append("Justificaci�n: " + justificacion.getJustificacion() + "\n");
			helper.setTo((String[]) to.toArray(new String[0]));
			helper.setText(cuerpoMensaje.toString());
			this.mailSender.send(message);
		} catch (Exception e) {
			LOG.error("ERROR; AL ENVIAR LA NOTIFICACION DE ELIMINAR ACTIVIDAD");
		}

	}
	
	@SuppressWarnings("unchecked")
	private JSONObject convertAbonoToJson(Abono abono) {
		JSONObject jsonAbono = new JSONObject(); 
		if(abono != null) {
			jsonAbono.put("fecha", abono.getFecha().toString());
			jsonAbono.put("codigo", abono.getCodigo());
			jsonAbono.put("descripcion", abono.getDescripcion());
			jsonAbono.put("prestamo", abono.getCodigoPrestamo());
			jsonAbono.put("saldoCapital", abono.getSaldoCapital());
			jsonAbono.put("saldoInteres", abono.getSaldoInteres());
			jsonAbono.put("abonoCapital", abono.getAbonoCapital());
			jsonAbono.put("abonoInteres", abono.getAbonoInteres());
		}
		
		return jsonAbono;
	}
}