package com.sf.social.signinmvc.common.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.service.CasoService;
import com.gja.gestionCasos.casos.service.EquipoCasoService;
import com.gja.gestionCasos.casos.service.ResponsablesTareaService;
import com.gja.gestionCasos.casos.service.TareaParticularCasoService;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.Utilidades;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @author Petri Kainulainen
 */
@Controller
@SessionAttributes( {"EmpleadoLogueado","tipoConexion","UserProfile" })
@RequestMapping(value = {"/home"})
public class HomeController {

	private static final Logger LOG = Logger.getLogger(HomeController.class);
	private final String errorMessage = "ERROR";

	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";

    @Autowired
	private ConnectionRepository connectionRepository;
    
    @Autowired
	private VistaRolesAccionOpciones vistaRolesAccionOpciones;
    
    @Autowired
	private CasoService casoService;
    
    @Autowired
	private EquipoCasoService equipoCasoService;
    
    @Autowired
	private TareaParticularCasoService tareaParticularCasoService;
    
    @Autowired
	private ResponsablesTareaService responsablesTareaService;
    
    protected static final String VIEW_NAME_HOMEPAGE = "home";
    
    
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
		model.addAttribute("diasProximoVencimiento", Parametros.getDiasPorDefectoVencimientoTareas());
		return VIEW_NAME_HOMEPAGE; // carpeta y el jsp
	}
    
    
    @RequestMapping(value = "/consultarCasosPorTipoCaso", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody String consultarCasosPorTipoCaso(Principal principal) {
    	JSONObject res = new JSONObject();
    	List<Object[]> casosPorTipoCaso = new ArrayList<Object[]>();
    	try {
    		JSONArray jsonArray = new JSONArray();
    		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
    		Rol rol = menuVistaPermisosRolesWrapper.getRol();
    		if (Integer.parseInt(Parametros.getRolAdmind()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolSocio()) == rol.getCodigo()) {
    			casosPorTipoCaso = casoService.consultarCasosPorTipoCaso(null);    			
    		} else {
    			if (Integer.parseInt(Parametros.getRolAbogado()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolDependiente()) == rol.getCodigo()) {
    				SocialUserDetails userDetails = Utilidades.getRealPrincipal(principal);
    				EquipoCaso usuarioEnEquipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(userDetails.getId());
    				casosPorTipoCaso = casoService.consultarCasosPorTipoCaso(usuarioEnEquipoCaso);
    			}
    		}
    		
    		
			for (Object[] caso: casosPorTipoCaso) {
				jsonArray.add(crearJsonGraficos(caso));
			}
			res.put("casosPorTipoCaso", jsonArray);
    	} catch (DAOException e) {
    		LOG.error("DAOException: Error agregando nuevos miembros del caso.", e);
			res.put("STATUS", "ERROR");
    	} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return res.toString();
    }
    
	
	@RequestMapping(value = "/consultarCasosActividadesPrincipales", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody String consultarCasosActividadesPrincipales(Principal principal) {
    	JSONObject res = new JSONObject();
    	List<Object[]> casosActividadesPrincipales = new ArrayList<Object[]>();
    	try {
    		JSONArray jsonArray = new JSONArray();
    		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
    		Rol rol = menuVistaPermisosRolesWrapper.getRol();
    		if (Integer.parseInt(Parametros.getRolAdmind()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolSocio()) == rol.getCodigo()) {
    			casosActividadesPrincipales = casoService.consultarCasosActividadesPrincipales(null);    			
    		} else {
    			if (Integer.parseInt(Parametros.getRolAbogado()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolDependiente()) == rol.getCodigo()) {
    				SocialUserDetails userDetails = Utilidades.getRealPrincipal(principal);
    				EquipoCaso usuarioEnEquipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(userDetails.getId());
    				casosActividadesPrincipales = casoService.consultarCasosActividadesPrincipales(usuarioEnEquipoCaso);
    			}
    		}
    		
    		
			for (Object[] caso: casosActividadesPrincipales) {
				jsonArray.add(crearJsonGraficos(caso));
			}
			res.put("casosActividadesPrincipales", jsonArray);
    	} catch (DAOException e) {
    		LOG.error("DAOException: Error consultando casos con actividades principales.", e);
			res.put("STATUS", "ERROR");
    	} catch (BusinessException e) {
    		LOG.error("BusinessException: Error consultando casos con actividades principales.", e);
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			LOG.error("Exception: Error consultando casos con actividades principales.", e);
			res.put("STATUS", "ERROR");// TODO: handle exception
		}
    	return res.toString();
    }
	
    @RequestMapping(value = "/consultarCasosPorEstadoCaso", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody String consultarCasosPorEstadoCaso(Principal principal) {
    	JSONObject res = new JSONObject();
    	List<Object[]> casosPorEstadoCaso = new ArrayList<Object[]>();
    	try {
    		JSONArray jsonArray = new JSONArray();
    		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
    		Rol rol = menuVistaPermisosRolesWrapper.getRol();
    		if (Integer.parseInt(Parametros.getRolAdmind()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolSocio()) == rol.getCodigo()) {
    			casosPorEstadoCaso = casoService.consultarCasosPorEstadoCaso(null);    			
    		} else {
    			if (Integer.parseInt(Parametros.getRolAbogado()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolDependiente()) == rol.getCodigo()) {
    				SocialUserDetails userDetails = Utilidades.getRealPrincipal(principal);
    				EquipoCaso usuarioEnEquipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(userDetails.getId());
    				casosPorEstadoCaso = casoService.consultarCasosPorEstadoCaso(usuarioEnEquipoCaso);
    			}
    		}
    		
			for (Object[] caso: casosPorEstadoCaso) {
				jsonArray.add(crearJsonGraficos(caso));
			}
			res.put("casosPorEstadoCaso", jsonArray);
    	} catch (DAOException e) {
    		LOG.error("DAOException: Error consultando los casos por estado.", e);
			res.put("STATUS", "ERROR");
    	} catch (BusinessException e) {
    		LOG.error("BusinessException: Error consultando los casos por estado.", e);
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			LOG.error("Exception: Error consultando los casos por estado.", e);
			res.put("STATUS", "ERROR");// TODO: handle exception
		}
    	return res.toString();
    }
    
	
	@RequestMapping(value = "/consultarActividadesPorEstado", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody String consultarActividadesPorEstado(Principal principal) {
    	JSONObject res = new JSONObject();
    	List<Object[]> tareasPorEstado = new ArrayList<Object[]>();
    	try {
    		JSONArray jsonArray = new JSONArray();
    		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
    		Rol rol = menuVistaPermisosRolesWrapper.getRol();
    		if (Integer.parseInt(Parametros.getRolAdmind()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolSocio()) == rol.getCodigo()) {
    			tareasPorEstado = tareaParticularCasoService.consultarTareasPorEstado(null);    			
    		} else {
    			if (Integer.parseInt(Parametros.getRolAbogado()) == rol.getCodigo() || Integer.parseInt(Parametros.getRolDependiente()) == rol.getCodigo()) {
    				SocialUserDetails userDetails = Utilidades.getRealPrincipal(principal);
    				EquipoCaso usuarioEnEquipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(userDetails.getId());
    				tareasPorEstado = tareaParticularCasoService.consultarTareasPorEstado(usuarioEnEquipoCaso);
    			}
    		}
    		
    		
    		
			for (Object[] tarea: tareasPorEstado) {
				jsonArray.add(crearJsonGraficos(tarea));
			}
			res.put("actividadesPorEstado", jsonArray);
    	} catch (DAOException e) {
    		LOG.error("DAOException: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");
    	} catch (BusinessException e) {
    		LOG.error("BusinessException: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			LOG.error("Exception: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");// TODO: handle exception
		}
    	return res.toString();
    }
	
	
	@RequestMapping(value = "/consultarPorcentajesTareasPorCasoYEstado", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody String consultarPorcentajesTareasPorCasoYEstado(CasoFiltro casoFiltro) {
    	JSONObject res = new JSONObject();
    	List<Object[]> tareasPorEstado = new ArrayList<Object[]>();
    	try {
    		JSONArray jsonArray = new JSONArray();
    		tareasPorEstado = tareaParticularCasoService.consultarPorcentajesTareasPorCasoYEstado(casoFiltro);
			for (Object[] tarea: tareasPorEstado) {
				jsonArray.add(crearJsonGraficos(tarea));
			}
			res.put("actividadesPorEstado", jsonArray);
    	} catch (DAOException e) {
    		LOG.error("DAOException: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");
    	} catch (BusinessException e) {
    		LOG.error("BusinessException: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			LOG.error("Exception: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");// TODO: handle exception
		}
    	return res.toString();
    }
	
	
	@RequestMapping(value = "/consultarResponsablesPorCaso", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody String consultarTareasPorCasoYEstado(CasoFiltro casoFiltro) {
    	JSONObject res = new JSONObject();
    	List<ResponsableTarea> responsablesTareas = new ArrayList<ResponsableTarea>();
    	try {
    		JSONArray jsonArray = new JSONArray();
    		responsablesTareas = responsablesTareaService.consultarResponsablesPorCaso(casoFiltro);
			for (ResponsableTarea responsable: responsablesTareas) {
				jsonArray.add(crearJsonResponsables(responsable));
			}
			res.put("responsables", jsonArray);
    	} catch (DAOException e) {
    		LOG.error("DAOException: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");
    	} catch (BusinessException e) {
    		LOG.error("BusinessException: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			LOG.error("Exception: Error consultando las tareas por estado.", e);
			res.put("STATUS", "ERROR");// TODO: handle exception
		}
    	return res.toString();
    }
    
	private Object crearJsonResponsables(ResponsableTarea responsable) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat(Parametros.getFormatoFechaDiaMesAnio());
		JSONObject jsO = new JSONObject();
		EquipoCaso equipoCaso = responsable.getCasosEquiposCaso().getEquipoCaso();
		String nombre = equipoCaso.getNombre() + " " + (equipoCaso.getApellido() != null ? equipoCaso.getApellido() : "");
		String fechaLimite = responsable.getTareasParticularesCaso().getFechaLimite() != null ? formatoFecha.format(responsable.getTareasParticularesCaso().getFechaLimite()) : "";
		String fechaCumplimiento = responsable.getFechaFinalizacionTarea() != null ? formatoFecha.format(responsable.getFechaFinalizacionTarea()) : "";
		
		jsO.put("responsable", nombre);
		jsO.put("tarea", responsable.getTareasParticularesCaso().getTarea());
		jsO.put("fechaLimite", fechaLimite);
		jsO.put("fechaCumplimiento", fechaCumplimiento);
		jsO.put("semaforo", pintarSemaforo(responsable));
		return jsO;
	}

	/** 
	 * metodo que recibe una tarea y compara la fecha limite de la tarea y devuelve un string
	 *  con el nombre de la clase css correspondiente, dependiendo el numero de días que faltan para la finalización de la tarea.
	 * 
	 * @param tareasParticularesCaso
	 * @return String nombre de la clase css que se pondra a la fila 
	 */
	
	
	private String pintarSemaforo(ResponsableTarea responsable) {
		TareaParticularCaso tarea = responsable.getTareasParticularesCaso();
		String nombreClaseSemaforo = "";
		
		if (responsable.getFechaFinalizacionTarea() != null) {
			nombreClaseSemaforo = crearStringClaseSegunCumplimiento(tarea, responsable.getFechaFinalizacionTarea());
		} else {
			nombreClaseSemaforo = crearStringClaseSegunVencimiento(tarea);
		}
		
		
		return nombreClaseSemaforo;
	}


	
	
	private String crearStringClaseSegunVencimiento(TareaParticularCaso tarea) {
		String nombreClase = null;
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaActual = df.parse(df.format(new Date()));
			long diffTime = (tarea.getFechaLimite().getTime()) - fechaActual.getTime();
			int daysDiff = (int) (diffTime/(1000*60*60*24));
			
			if (daysDiff < 0)
				nombreClase = "semaforo-rojo";
			else if (daysDiff <= Parametros.getDiasSemaforoColorNaranja())
				nombreClase = "semaforo-naranja";
			else if (daysDiff <= Parametros.getDiasSemaforoColorAmarillo())
				nombreClase = "semaforo-amarillo";
			else
				nombreClase = "semaforo-verde";
			
		} catch(ParseException e) {
			
		}
		return nombreClase;
	}


	private String crearStringClaseSegunCumplimiento(TareaParticularCaso tarea, Date fechaCumplimientoTarea) {
		String nombreClase = null;
		try {
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaVencimientoSinHoras = df.parse(df.format(tarea.getFechaLimite()));
			Calendar fechaVencimiento = Calendar.getInstance();
			fechaVencimiento.setTime(fechaVencimientoSinHoras);
			fechaVencimiento.add(Calendar.DATE, 1);
			if (fechaCumplimientoTarea.before(fechaVencimiento.getTime()))
				nombreClase = "semaforo-verde glyphicon glyphicon-ok";
			else
				nombreClase = "semaforo-rojo glyphicon glyphicon-ok";
		} catch(ParseException e) {
			
		}
		return nombreClase;
	}


	private JSONObject crearJsonGraficos(Object[] obj) {
		JSONObject jsO = new JSONObject();
		jsO.put("cantidad", obj[0]);
		jsO.put("nombre", obj[1]);
		return jsO;
	}
	
}
