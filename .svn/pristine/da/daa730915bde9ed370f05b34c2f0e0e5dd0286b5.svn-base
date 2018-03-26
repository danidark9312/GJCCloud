package com.gja.gestionCasos.maestros.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.sitemesh.webapp.contentfilter.io.RoutablePrintWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.maestros.service.MaestroActividadesService;
import com.gja.gestionCasos.maestros.service.MaestroRolesService;
import com.gja.gestionCasos.maestros.service.RolAccionOpcionService;
import com.gja.gestionCasos.permisos.service.AccionesService;
import com.gja.gestionCasos.permisos.service.OpcionesService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.EstadoRol;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Opcion;
import com.sf.roles.PermisosWrapper;
import com.sf.roles.Rol;
import com.sf.roles.RolAccionOpcion;
import com.sf.roles.RolAccionOpcionPK;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Controller
@RequestMapping(value = {"/maestroRoles" }) 
public class MaestroRolesController {

	private final String ACCIONACTUALIZAR = "ACTUALIZAR";
	private final String ACCIONCREAR = "CREAR";
	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";
	private final String status = "STATUS";
	
	private static final Logger LOG = Logger.getLogger(MaestroRolesController.class);
	
	@Autowired
	AccionesService accionesService;
	@Autowired
	MaestroActividadesService maestroActividadService;
	@Autowired
	MaestroRolesService maestroRolesService;
	@Autowired
	OpcionesService opcionesService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;
	@Autowired 
	RolAccionOpcionService rolAccionOpcionService;
	
	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal){
		try {
			MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
			VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();
			
			List<EstadoRol> estados = maestroRolesService.getEstados();
			vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);
			menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);

			model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
			model.addAttribute("estados", estados);
			model.addAttribute("permisos", vistaPermisosRolesWrapper);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando los estados/rol. El error es: " + e.getMessage());
			e.printStackTrace();
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando los estados/rol. El error es: " + e.getMessage());
			e.printStackTrace();
		}
		
		return "maestros/maestroRoles";
	}
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarRol", method = RequestMethod.POST)
	public @ResponseBody String consultarRol(@ModelAttribute("rol") Rol rol) {
		JSONArray arrayEstado = new JSONArray();
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		JSONObject estado = new JSONObject();
		JSONObject res = new JSONObject();				
		
		bundle = findRolByPk(rol);

		res = (JSONObject) bundle.get("res");
		rol = (Rol) bundle.get("rol");

		res.put("codigo", rol.getCodigo());
		res.put("descripcion", rol.getDescripcion());
		estado.put("cdestado", rol.getEstado().getCdestado());
		estado.put("dsestado", rol.getEstado().getDsestado());
		arrayEstado.add(estado);
		res.put("estado", arrayEstado);

		return res.toString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarAccionesRol", method = RequestMethod.POST)
	public @ResponseBody String consultarAccionesRol(@ModelAttribute("rol") Rol rol) {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		JSONObject res = new JSONObject();
		
		bundle = getAccionesRol(rol);

		res = (JSONObject) bundle.get("res");
		
		res.put("codigo", rol.getCodigo());
		
		res.put("listaRolAccionOpcion", addListaRolAccionOpcionAArray(bundle, (List<RolAccionOpcion>) bundle.get("listaRolAccionOpcion")));
		
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consultarRoles", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String consultarEstados(@ModelAttribute("rol") Rol rol) {
		JSONArray arrayActividades = new JSONArray();
		JSONObject res = new JSONObject();
		
		try {
			rol = maestroRolesService.findByPK(rol);
			
			res.put("codigo", rol.getCodigo());
			res.put("nombre", rol.getDescripcion());
			res.put("activo", rol.getEstado());

			res.put("actividadTipoCasoList", arrayActividades);

		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando los estados rol. El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando los estados rol. El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		}
		
		return res.toString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminarRol", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String eliminarActividad(@ModelAttribute("rol") Rol rol) {
		JSONObject res = new JSONObject();
		
		try {
			rol = maestroRolesService.findByPK(rol);
			
			if(maestroRolesService.deleteRol(rol) == 1)
				res.put("STATUS", estadoExito);
			else
				res.put("STATUS", estadoError);
			
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error eliminando el rol. El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error eliminando el rol. El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		}
	
		return res.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarRoles", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String getAllTable(@RequestParam("iDisplayStart") int displayStart, @RequestParam("activo") String activo, 
			@RequestParam("iDisplayLength") int displayLength, @RequestParam("sSearch") String sSearch,
			@RequestParam("iSortCol_0") int sCortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho) {
		List<Rol> roles = null;
		Long cantidad = (long) 0;
		JSONArray arrayRoles = new JSONArray();
		JSONObject jsO = new JSONObject();
		JSONObject res = new JSONObject();
		
		try {
			roles = maestroRolesService.getRoles(activo, sSearch, displayStart, displayLength);
			cantidad = maestroRolesService.getCountRoles(activo, sSearch, displayStart, displayLength);
			
			for (Rol rol : roles) {
				jsO = new JSONObject();
				String stringPapelera = "<a data-toggle='modal' class='btn btn-success btn-circle .btn-xs' style='background-color: red; border:0;' "
						+ "onclick='eliminarRol(" + rol.getCodigo()
						+ ")' title='Eliminar'><i class='glyphicon glyphicon-trash' style='color: white;'></i></a>";

				jsO.put("codigo", rol.getCodigo());
				jsO.put("descripcion", rol.getDescripcion());
				jsO.put("estado", rol.getEstado().getDsestado());
				jsO.put("papelera", stringPapelera);
				
				arrayRoles.add(jsO);
			}
			
			res.put("sEcho", sEcho);
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", arrayRoles);
			res.put("STATUS", estadoExito);
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando los Roles. El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error consultando los Roles. El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		}
	
		return res.toJSONString();
	}
	
	@RequestMapping("/guardarRol")
	public @ResponseBody String guardar(@ModelAttribute("rol") Rol rol) throws Exception {
		JSONObject res = new JSONObject();

		res = guardarRol(rol);
		
		return res.toString();
	}
	
	@RequestMapping(value = "/crearRolPermisos", method = RequestMethod.POST)
	public @ResponseBody String crearRolPermiso(Rol rol ) throws BusinessException, DAOException {
		JSONObject res = new JSONObject();
		PermisosWrapper permisosWrapper = new PermisosWrapper(); 
		try {
			if (rol.getCodigo().intValue() < 0) {
				res = ifExistNameRol(rol);
				if (estadoError.equals(res.get(status))) {
					LOG.error("BusinessException: Ocurrio un error creando el rol. El error ya existe");
				} else {
					permisosWrapper.setRolAccionOpcion(rol.getRolAccionOpcionList());
					rol.setRolAccionOpcionList(null);
					res = guardarRol(rol);
				}
			}
			if (estadoExito.equals(res.get(status))) {
				res = guardarAccionesOpcionesRol(ACCIONCREAR, permisosWrapper, rol);
			}
		} catch (Exception e) {
			LOG.error("Error; guardando los roles con sus respectivas opciones");
		}

		
		return res.toString();
	}
	
	@RequestMapping(value = "/actualizarRolPermisos", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody String actualizarRolPermiso(@RequestBody PermisosWrapper permisosWrapper) {
		JSONObject res = new JSONObject();
		Rol rol = new Rol();
		
		if (permisosWrapper.getRol().getCodigo().intValue() > 0) {
			rol = permisosWrapper.getRol();
			res = ifExistNameRol(rol);
			res = guardarAccionesOpcionesRol(ACCIONACTUALIZAR, permisosWrapper, rol);
//			if(!estadoExito.equals(res.get(status))) {			
//				LOG.error("BusinessException: Ocurrio un error creando el rol. El error ya existe");
//			} else {
//				res = guardarAccionesOpcionesRol(ACCIONACTUALIZAR, permisosWrapper, rol);
//			}
		}
		
		return res.toString();
	}
		
	@RequestMapping("/existNameRol")
	public @ResponseBody String existNameRol(@ModelAttribute("rol") Rol rol) {
		JSONObject res = new JSONObject();

		res = ifExistNameRol(rol);
		
		return res.toString();
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray addListaRolAccionOpcionAArray(HashMap<String, Object> bundle, List<RolAccionOpcion> list) {
		List<RolAccionOpcion> listaRolAccionOpcion = new ArrayList<RolAccionOpcion>();
		JSONArray arrayRolAccionOpcion = new JSONArray();
		
		listaRolAccionOpcion = (List<RolAccionOpcion>) bundle.get("listaRolAccionOpcion");
	
		for (RolAccionOpcion rolAccionOpcion : listaRolAccionOpcion) {
			JSONObject objJsonRolAccion = new JSONObject();
			JSONObject objJsonRolAccionPk = new JSONObject();
			
			objJsonRolAccion.put("snCrear", rolAccionOpcion.getSnCrear());
			objJsonRolAccion.put("snEliminar", rolAccionOpcion.getSnEliminar());
			objJsonRolAccion.put("snEscritura", rolAccionOpcion.getSnEscritura());
			objJsonRolAccion.put("snLectura", rolAccionOpcion.getSnLectura());
			objJsonRolAccionPk.put("codigoOpcion", rolAccionOpcion.getRolAccionOpcionPK().getCodigoOpcion());
			objJsonRolAccionPk.put("codigoRol", rolAccionOpcion.getRolAccionOpcionPK().getCodigoRol());
			objJsonRolAccion.put("rolAccionOpcionPK", objJsonRolAccionPk);
			
			arrayRolAccionOpcion.add(objJsonRolAccion);
		}
		
		return arrayRolAccionOpcion;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject guardarRol(Rol rol) {
		JSONObject res = new JSONObject();
		
		try {
			rol = maestroRolesService.saveRol(rol);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando los Roles. El error es: " + e.getMessage());
			res.put(status, estadoError);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando los Roles. El error es: " + e.getMessage());
			res.put(status, estadoError);
		}
		
		if(!estadoError.equals(res.get(status)))
			res.put(status, estadoExito);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject ifExistNameRol(Rol rol) {
		JSONObject res = new JSONObject();

		try {
			if (maestroRolesService.existNameRol(rol)) {
				res.put(status, estadoError);
			}
			else {
				res.put(status, estadoExito);
			}
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando los roles por nombre . El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando los roles por nombre . El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		}
				
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject guardarAccionesOpcionesRol(String accion, PermisosWrapper permisosWrapper, Rol rol) {
		JSONObject res = new JSONObject();
		try {
			maestroRolesService.saveRol(rol);
			for (int i = 0; i < permisosWrapper.getRolAccionOpcion().size(); i++) {
				Opcion opcion = new Opcion();
				RolAccionOpcionPK rolAccionPk = new RolAccionOpcionPK();

				rolAccionPk.setCodigoRol(rol.getCodigo());
				rolAccionPk.setCodigoOpcion(permisosWrapper.getRolAccionOpcion().get(i).getRolAccionOpcionPK().getCodigoOpcion());
				opcion.setCodigo(rolAccionPk.getCodigoOpcion());

				permisosWrapper.getRolAccionOpcion().get(i).setRolAccionOpcionPK(rolAccionPk);
				permisosWrapper.getRolAccionOpcion().get(i).setOpcion(opcion);
				permisosWrapper.getRolAccionOpcion().get(i).setRol(rol);


				if (ACCIONCREAR.equals(accion)) {
					@SuppressWarnings("unused")
					RolAccionOpcion rolAccionOpcion = accionesService.asignAccionesRol(permisosWrapper.getRolAccionOpcion().get(i));
				} else {
					@SuppressWarnings("unused")
					RolAccionOpcion rolAccionOpcion = accionesService.reasignAccionesRol(permisosWrapper.getRolAccionOpcion().get(i));
				}
			}
		
		if (!estadoError.equals(res.get(status)))
			res.put(status, estadoExito);
		
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando uno o mas permisos. El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando uno o mas permisos. El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		}
		return res;
	}
	
	/***
	 * @param rol
	 * @return HashMap<String, Object> res (informacion con el status de transaccion )
	 * @return HashMap<String, Object> listaRolAccionOpcion (List<RolAccionOpcion>) 
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getAccionesRol(Rol rol) {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		List<RolAccionOpcion> listaRolAccionOpcion = new ArrayList<RolAccionOpcion>();
		JSONObject res = new JSONObject();
		List<Opcion> opciones = new ArrayList<Opcion>();
					
		bundle = getOpciones();
		opciones = (List<Opcion>) bundle.get("opciones");
		res = (JSONObject) bundle.get("res");
		bundle.clear();
		
		bundle = getAccionesOpcionesRol(opciones, rol);
		listaRolAccionOpcion = (List<RolAccionOpcion>) bundle.get("listaRolAccionOpcion");
		res = (JSONObject) bundle.get("res");
		bundle.clear();
						
		if(!estadoError.equals(res.get(status)))
			res.put(status, estadoExito);
		
		bundle.put("listaRolAccionOpcion", listaRolAccionOpcion);
		bundle.put("res", res);
		
		return bundle;
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getAccionesOpcionesRol(List<Opcion> opciones, Rol rol){
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		List<RolAccionOpcion> listaRolAccionOpcion = new ArrayList<RolAccionOpcion>();
		JSONObject res = new JSONObject();
		RolAccionOpcionPK rolAccionOpcionPk = new RolAccionOpcionPK();
		
		rolAccionOpcionPk.setCodigoRol(rol.getCodigo());
		
		for (Opcion opcion : opciones) {
			rolAccionOpcionPk.setCodigoOpcion(opcion.getCodigo());
			try {
				RolAccionOpcion rolAccionOpcion = new RolAccionOpcion();
				rolAccionOpcion = (RolAccionOpcion) accionesService.getAcciones(rolAccionOpcionPk);
				if(!rolAccionOpcion.equals(null) && rolAccionOpcion.getRolAccionOpcionPK() != null)
					listaRolAccionOpcion.add(rolAccionOpcion);
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando las acciones disponibles. El error es: "
						+ e.getMessage());
				res.put(status, estadoError);
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando las acciones disponibles. El error es: "
						+ e.getMessage());
				res.put(status, estadoError);
			}
		}
		
		if(!estadoError.equals(res.get(status)))
			res.put(status, estadoExito);
		
		bundle.put("listaRolAccionOpcion", listaRolAccionOpcion);
		bundle.put("res", res);
		
		return bundle;
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getOpciones() {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		List<Opcion> opciones = new ArrayList<Opcion>();
		JSONObject res = new JSONObject();
		
		try {
			opciones = opcionesService.getOpciones();
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las opciones disponibles. El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las opciones disponibles. El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		}
		
		if(!estadoError.equals(res.get(status)))
			res.put(status, estadoExito);
		
		bundle.put("opciones", opciones);
		bundle.put("res", res);
		
		return bundle;
	}

	/***
	 * @param rol
	 * @return HashMap<String, Object> res (informacion con el status de transaccion )
	 * @return HashMap<String, Object> rol (Rol) 
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> findRolByPk(Rol rol) {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		JSONObject res = new JSONObject();
		
		try {
			rol = maestroRolesService.findByPK(rol);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando el rol. El error es: "
					+ e.getMessage());
			res.put(status, estadoError);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando el rol. El error es: " + e.getMessage());
			res.put(status, estadoError);
		}
		
		if(!estadoError.equals(res.get(status)))
			res.put(status, estadoExito);
		
		bundle.put("res", res);
		bundle.put("rol", rol);
		
		return bundle;
	}
}
