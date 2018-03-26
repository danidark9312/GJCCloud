package com.gja.gestionCasos.maestros.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.gja.gestionCasos.maestros.service.EstadoUsuarioService;
import com.gja.gestionCasos.maestros.service.MaestroActividadesService;
import com.gja.gestionCasos.maestros.service.MaestroRolesService;
import com.gja.gestionCasos.maestros.service.MaestroUsuariosService;
import com.gja.gestionCasos.maestros.service.RolesUsuariosService;
import com.gja.gestionCasos.maestros.service.TipoDocumentoService;
import com.gja.gestionCasos.permisos.service.AccionesService;
import com.gja.gestionCasos.permisos.service.OpcionesService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.roles.RolesUsuariosPK;
import com.sf.roles.UsuarioWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.Utilidades;

@Controller
@RequestMapping(value = { "/maestroUsuarios" })
public class MaestroUsuariosController {

	private static final String ESTADOEXITO = "SUCCESS";
	private static final String ESTADOERROR = "ERROR";
	private static final String ESTADINACTIVO = "INACTIVO_ERROR";
	private static final String USER_EXIST = "USER_EXIST";
	private static final String EMAIL_EXIST = "EMAIL_EXIST";
	private static final String INICIALES_EXIST = "INICIALES_EXIST";
	private static final String STATUS = "STATUS";

	private static final Logger LOG = Logger.getLogger(MaestroUsuariosController.class);

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
	MaestroUsuariosService maestroUsuariosService;
	@Autowired
	RolesUsuariosService rolesUsuariosService;
	@Autowired
	EstadoUsuarioService estadoUsuarioService;
	@Autowired
	TipoDocumentoService tipoDocumentoService;

	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		try {
			List<EstadoUsuario> estados = new ArrayList<EstadoUsuario>();
			List<Rol> roles = new ArrayList<Rol>();
			List<TipoDocumento> tiposDocumentos = new ArrayList<TipoDocumento>();
			MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
			VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();
	
			estados = getEstadosUsuarios();
			menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
			roles = getRoles();
			vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
					httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);
			tiposDocumentos = getTiposDocumentos();
	
			model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
			model.addAttribute("estados", estados);
			model.addAttribute("permisos", vistaPermisosRolesWrapper);
			model.addAttribute("roles", roles);
			//el primero es el nombre del parametro que tambien ira en e jsp, la variable que pasa el parametro
			model.addAttribute("tiposDocumentos", tiposDocumentos);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "maestros/maestroUsuarios";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarUsuarios", method = RequestMethod.POST, produces = "application/json; charset=utf-8", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody String mostrarUsuarios(@RequestParam("iDisplayStart") int displayStart, String activo, String rol,
			@RequestParam("iDisplayLength") int displayLength, @RequestParam("sSearch") String sSearch,
			@RequestParam("iSortCol_0") int sCortColumn0, @RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho) throws BusinessException, DAOException {
		List<User> usuarios = null;
		Long cantidad = (long) 0;
		JSONArray listaUsuarios = new JSONArray();
		JSONObject jsO = new JSONObject();
		JSONObject res = new JSONObject();
		
		try {
			
			usuarios = obtenerUsuarios(activo,  rol, sSearch, displayStart, displayLength);
			cantidad = maestroUsuariosService.getCountUsuario(activo, rol, sSearch);
			
			if (usuarios.size() > 0) {
				for (User usuario : usuarios) {
					jsO = new JSONObject();
					
					jsO.put("apellidos", usuario.getApellido());
					jsO.put("cedula", usuario.getId());
					jsO.put("correoElectronico", usuario.getEmail());
					jsO.put("estado", getEstadoUsuario(usuario.getActivo()));
					jsO.put("nombres", usuario.getNombre());
					jsO.put("tarjetaProfesional", usuario.getNumeroTarjetaProfesional());
					jsO.put("rol", getRolPorUsuario(usuario.getId()));					
					jsO.put("celular", usuario.getNumerocelular());
					jsO.put("direccion", usuario.getDireccion());
					jsO.put("numeroTelefono", usuario.getNumeroTelefono());
//					jsO.put("abreviacionAbogado", usuario.getAbreviacionAbogado()); 
					jsO.put("tipoDocumento", usuario.getTipoDocumento().getCodigo()); 
					
					listaUsuarios.add(jsO);
				}
				
			}
			res.put("sEcho", sEcho);
			res.put("iTottalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", listaUsuarios);
			res.put("STATUS", ESTADOEXITO);
		} catch (Exception e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", "ERROR");
		}
		return res.toJSONString();
	}

	@RequestMapping(value = "/consultarUsuario", method = RequestMethod.POST)
	public @ResponseBody String consultarUsuario(User usuario) {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		JSONObject res = new JSONObject();

		bundle = findUsuarioById(usuario);

		res = (JSONObject) bundle.get("res");
		usuario = (User) bundle.get("usuario");

		res = setJsonUsuario(usuario);

		return res.toString();
	}

	@RequestMapping("/existUsuario")
	public @ResponseBody String existUsuario(@ModelAttribute("usuario") User usuario) {
		JSONObject res = new JSONObject();

		res = ifExistIdUsuario(usuario);
		if (((String)res.get("STATUS")).equals(ESTADOEXITO)){
			res.put("STATUS", USER_EXIST);
			res.put("STATUS", INICIALES_EXIST);
		}
		else {			
			res = ifExistCorreoUsuario(usuario);
			if (((String)res.get("STATUS")).equals(ESTADOEXITO))
				res.put("STATUS", EMAIL_EXIST);
			else
				res.put("STATUS", ESTADOEXITO);
		}
		return res.toString();
	}

	@RequestMapping(value = "/guardarUsuario", method = RequestMethod.POST,  headers="Accept=*/*")
	public @ResponseBody String guardarNuevo(UsuarioWrapper usuarioWrapper) {
		JSONObject res = new JSONObject();
		try {			
			if (usuarioWrapper.isNuevo()) {
				if (maestroUsuariosService.existIdUsuario(usuarioWrapper.getUsuario()))
					res.put("STATUS", USER_EXIST);
				else {
					if (maestroUsuariosService.existCorreoUsuario(usuarioWrapper.getUsuario()))
						res.put("STATUS", EMAIL_EXIST);
					else
						res = guardarUsuarioNuevo(usuarioWrapper.getUsuario(), usuarioWrapper.getRolesUsuariosPk());
				}
			} else {
				if (usuarioWrapper.getUsuario().getEmail().equals(usuarioWrapper.getCorreoAntiguo()))
					res = guardarUsuario(usuarioWrapper.getUsuario(), usuarioWrapper.getRolesUsuariosPk());
				else {
					if (maestroUsuariosService.existCorreoUsuario(usuarioWrapper.getUsuario()))
						res.put("STATUS", EMAIL_EXIST);
					else
						res = guardarUsuarioNuevo(usuarioWrapper.getUsuario(), usuarioWrapper.getRolesUsuariosPk());
				}
			}
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el usuario. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el usuario. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		} catch (Exception e) {
			LOG.error("Exception: Ocurrio un error guardando el usuario. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		}
		return res.toString();
	}

	@RequestMapping(value = "/cambiarContrasena", method = RequestMethod.POST)
	public @ResponseBody String cambiarContrasena(@RequestParam("contrasenaAnterior") String contrasenaAnterior,
			@RequestParam("contrasenaNueva") String contrasenaNueva, Principal principal) throws BusinessException, DAOException {
		JSONObject res = new JSONObject();
		User usuario = new User();
		User usuarioTemp = new User();
		
		SocialUserDetails datosUsuario = Utilidades.getRealPrincipal(principal);
		usuario.setId(datosUsuario.getId());
		
		//Consulta a maestro de usuarios para que traiga la contraseña anterior
		usuarioTemp = maestroUsuariosService.findByPK(usuario);
//		usuarioTemp.setFechangepassword(new Date());
//		usuarioTemp.setFirstSession(Parametros.getValue("session.first.no"));
		String nuevaContrasena = "";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		nuevaContrasena = passwordEncoder.encode(usuarioTemp.getPassword());
		CharSequence chatSeq = contrasenaNueva;
		boolean isIgual = passwordEncoder.matches(chatSeq, usuarioTemp.getPassword());
			
		if(isIgual == false){
			if (contrasenaAnterior != contrasenaNueva && validarContrasena(contrasenaNueva)
					&& validarContrasenaAntigua(contrasenaAnterior)) {
				usuario.setId(vistaRolesAccionOpciones.getRealPrincipal(principal).getId());
				usuario = (User) findUsuarioById(usuario).get("usuario");
				res = guardarUsuario(usuario, contrasenaNueva);
			}
		}

		return res.toString();
	}
	
	private boolean validarContrasenaAntigua(String contrasenaAnterior) {
		// TODO Validar si es igual a la contrasena anterior
		return true;
	}

	private boolean validarContrasena(String contrasenaNueva) {
		// TODO Si formato contrasena es correcto retorne true
		return true;
	}

	private RolesUsuarios setRolesUsuario(RolesUsuariosPK rolesUsuariosPk) {
		RolesUsuarios rolesUsuario = new RolesUsuarios();

		rolesUsuario.setRolesUsuariosPK(rolesUsuariosPk);

		return rolesUsuario;
	}

	@SuppressWarnings("unchecked")
	private JSONObject guardarUsuario(User usuario, String contrasenaNueva) {
		JSONObject res = new JSONObject();

		try {
			usuario = maestroUsuariosService.saveUsuario(usuario, contrasenaNueva);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando la contrasena. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando la contrasena. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		}

		if (!ESTADOERROR.equals(res.get(STATUS)))
			res.put(STATUS, ESTADOEXITO);

		return res;
	}

	@SuppressWarnings("unchecked")
	private JSONObject guardarUsuario(User usuario, RolesUsuariosPK rolesUsuariosPk) {
		JSONObject res = new JSONObject();
		try {			
			usuario = maestroUsuariosService.saveUsuario(usuario, setRolesUsuario(rolesUsuariosPk));
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando los usuarios. El error es: " + e.getMessage());
			res.put(STATUS, ESTADINACTIVO);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando los usuarios. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		}

		if (!ESTADOERROR.equals(res.get(STATUS)))
			res.put(STATUS, ESTADOEXITO);

		return res;
	}

	@SuppressWarnings("unchecked")
	private JSONObject guardarUsuarioNuevo(User usuario, RolesUsuariosPK rolesUsuariosPk) {
		JSONObject res = new JSONObject();

		try {
			usuario = maestroUsuariosService.saveNewUsuario(usuario, setRolesUsuario(rolesUsuariosPk));
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando los usuarios. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando los usuarios. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		}

		if (!ESTADOERROR.equals(res.get(STATUS)))
			res.put(STATUS, ESTADOEXITO);

		return res;
	}

	@SuppressWarnings("unchecked")
	private JSONObject setJsonUsuario(User usuario) {
		JSONObject jsonUsuario = new JSONObject();
		SimpleDateFormat dataFormat = new SimpleDateFormat(Parametros.getFormatoAnioMesDiaFechaSlashes());
//		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");

		jsonUsuario.put("apellidos", usuario.getApellido());
		jsonUsuario.put("cedula", usuario.getId());
		jsonUsuario.put("correoElectronico", usuario.getEmail());
		jsonUsuario.put("estado", getEstadoUsuario(usuario.getActivo()));
		jsonUsuario.put("nombres", usuario.getNombre());
		jsonUsuario.put("tarjetaProfesional", usuario.getNumeroTarjetaProfesional());
		jsonUsuario.put("celular", usuario.getNumerocelular());
		jsonUsuario.put("rol", getRolPorUsuario(usuario.getId()));
		jsonUsuario.put("telefono", usuario.getNumeroTelefono());
		jsonUsuario.put("direccion", usuario.getDireccion());
		jsonUsuario.put("abreviacionAbogado", usuario.getAbreviacionAbogado());
		String nacimientoUsuario = "";
		if(usuario.getNacimientoUsuario() != null)
			nacimientoUsuario = dataFormat.format(usuario.getNacimientoUsuario());
		jsonUsuario.put("nacimientoUsuario", nacimientoUsuario);
		jsonUsuario.put("tipoDocumento", usuario.getTipoDocumento().getCodigo());
		jsonUsuario.put("abogadoTieneCasoActivo", abogadoTieneCasoActivo(usuario));
		
		

		return jsonUsuario;
	}

	private boolean abogadoTieneCasoActivo(User usuario) {
		boolean hasCasoAbogadoActivo = false;
		
		for(EquipoCaso equipoCaso : usuario.getEquipoCasoSet()) {
			for(CasoEquipoCaso casoEquipoCaso : equipoCaso.getCasoEquipoCasoSet()){
					if (casoEquipoCaso.getCaso().getEstadoCaso().getNombre().equals("ACTIVO") && 
							casoEquipoCaso.getTipoMiembro().getCodigo().equals(Integer.parseInt(Parametros.getAbogado()))) {
						hasCasoAbogadoActivo = true;
						break;
					}
			}
			
		}
		return hasCasoAbogadoActivo;
	}

	@SuppressWarnings("unchecked")
	private JSONObject ifExistIdUsuario(User usuario) {
		JSONObject res = new JSONObject();

		try {
			if (maestroUsuariosService.existIdUsuario(usuario))
				res.put(STATUS, ESTADOEXITO);
			else
				res.put(STATUS, ESTADOERROR);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando el id del usuario. El error es: "
					+ e.getMessage());
			res.put(STATUS, ESTADOERROR);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando los id del usuario. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		}

		if (!ESTADOERROR.equals(res.get(STATUS)))
			res.put(STATUS, ESTADOEXITO);

		return res;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject ifExistCorreoUsuario(User usuario) {
		JSONObject res = new JSONObject();
		try {
			if (maestroUsuariosService.existCorreoUsuario(usuario))
				res.put(STATUS, ESTADOEXITO);
			else
				res.put(STATUS, ESTADOERROR);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando el id del usuario. El error es: "
					+ e.getMessage());
			res.put(STATUS, ESTADOERROR);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando los id del usuario. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		}

		if (!ESTADOERROR.equals(res.get(STATUS)))
			res.put(STATUS, ESTADOEXITO);

		return res;
	}

	/***
	 * @param user
	 * @return HashMap<String, Object> res (informacion con el STATUS de
	 *         transaccion )
	 * @return HashMap<String, Object> usuario (User)
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> findUsuarioById(User usuario) {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		JSONObject res = new JSONObject();

		try {
			usuario = maestroUsuariosService.findByPK(usuario);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando el usuario. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando el usuario. El error es: " + e.getMessage());
			res.put(STATUS, ESTADOERROR);
		}

		if (!ESTADOERROR.equals(res.get(STATUS)))
			res.put(STATUS, ESTADOEXITO);

		bundle.put("res", res);
		bundle.put("usuario", usuario);

		return bundle;
	}

	private List<User> obtenerUsuarios(String activo, String rol, String sSearch, int displayStart, int displayLength) {
		List<User> usuarios = new ArrayList<User>();
		try {
			usuarios = maestroUsuariosService.getUsuarios(activo, rol, sSearch, displayStart, displayLength);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usuarios;
	}

	private List<EstadoUsuario> getEstadosUsuarios() {
		List<EstadoUsuario> estados = new ArrayList<EstadoUsuario>();

		try {
			estados = maestroUsuariosService.getEstados();
			for (EstadoUsuario estadoUsuario : estados) {
				if (estadoUsuario.getDsestado().equalsIgnoreCase(Parametros.getParametroSiCorto()))
					estadoUsuario.setDsestado(Parametros.getParametroActivo());
				else
					estadoUsuario.setDsestado(Parametros.getParametroInactivo());
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return estados;
	}

	private String getRolPorUsuario(String id) {
		Rol rol = new Rol();
		RolesUsuarios rolesUsuarios = new RolesUsuarios();

		rolesUsuarios = getRolUsuario(id);

		rol = getRolPorId(rolesUsuarios.getRolesUsuariosPK().getCodigoRol());

		return rol.getDescripcion();
	}
	

	private Rol getRolPorId(Integer codigoRol) {
		Rol rol = new Rol();
		try {
			rol = maestroRolesService.findByIdRol(codigoRol);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rol;
	}

	private RolesUsuarios getRolUsuario(String id) {
		RolesUsuarios rolesUsuarios = new RolesUsuarios();

		try {
			rolesUsuarios = rolesUsuariosService.findByCodigoUsuario(id);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rolesUsuarios;
	}

	private List<TipoDocumento> getTiposDocumentos() throws DAOException, BusinessException {
		List<TipoDocumento> tiposDocumentos  = new ArrayList<TipoDocumento>();
		tiposDocumentos = tipoDocumentoService.obtenerTiposDeDocumentos();
		return tiposDocumentos;
	}
		
	@SuppressWarnings("unused")
	private String getEstadoUsuario(Integer codigoEstado) {
		EstadoUsuario estadoUsuario = new EstadoUsuario();
		String estadoActivo = Parametros.getParametroSiCorto();

		try {
			estadoUsuario = estadoUsuarioService.getEstadoUsuario(codigoEstado);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return estadoActivo.equalsIgnoreCase(estadoUsuario.getDsestado()) ? Parametros.getParametroActivo()
				: Parametros.getParametroInactivo();
	}

	private String getEstadoUsuario(String codigoEstado) {
		EstadoUsuario estadoUsuario = new EstadoUsuario();
		String estadoActivo = Parametros.getParametroSiCorto();

		try {
			estadoUsuario = estadoUsuarioService.getEstadoUsuario(codigoEstado);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return estadoActivo.equalsIgnoreCase(estadoUsuario.getDsestado()) ? Parametros.getParametroActivo()
				: Parametros.getParametroInactivo();
	}

	private List<Rol> getRoles() {
		List<Rol> roles = new ArrayList<Rol>();

		try {
			roles = maestroRolesService.getAllRoles(null, null); 
		} catch (BusinessException e) {
			LOG.error(
					"BusinessException: Ocurrio un error consultando los estados/rol. El error es: " + e.getMessage());
			e.printStackTrace();
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando los estados/rol. El error es: " + e.getMessage());
			e.printStackTrace();
		}

		return roles;
	}

}
