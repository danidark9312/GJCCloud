/**
 * 
 */
package com.gja.gestionCasos.permisos.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.gja.gestionCasos.maestros.controller.MaestroRolesController;
import com.gja.gestionCasos.permisos.service.AccionesService;
import com.gja.gestionCasos.permisos.service.OpcionesService;
import com.gja.gestionCasos.permisos.service.RolesService;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.Opcion;
import com.sf.roles.Rol;
import com.sf.roles.RolAccionOpcion;
import com.sf.roles.RolAccionOpcionPK;
import com.sf.roles.RolesUsuarios;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

/**
 * Sirve para controlar, buscar y retornar los permisos (acciones) sobre las
 * opciones existentes en la aplicacion y que son leidas desde la vista
 * 
 * @author USUARIO
 */
@Service("vistaRolesAccionOpciones")
public class VistaRolesAccionOpcionesImpl implements VistaRolesAccionOpciones {

	private static final Logger LOG = Logger.getLogger(MaestroRolesController.class);
	private static final String NO = Parametros.getParametroNoCorto();
	private static final String SI = Parametros.getParametroSiCorto();

	@Autowired
	AccionesService accionesService;
	@Autowired
	OpcionesService opcionesService;
	@Autowired
	RolesService rolesService;

	/***
	 * Usado para obtener las autorizaciones por cada /url (opcion) del sistema
	 * dependiendo del Rol
	 * 
	 * @param opcion
	 * @return VistaPermisosRolesWrapper
	 */
	// TODO completar todos de este metodo
	public VistaPermisosRolesWrapper getAutorizaciones(String opcion, Principal principal) {
		List<RolAccionOpcion> listaRolAccionOpcion = new ArrayList<RolAccionOpcion>();
		Rol rol = new Rol();
		RolAccionOpcion rolAccionOpcion = new RolAccionOpcion();
//		RolesUsuarios rolesUsuarios = new RolesUsuarios();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();

		vistaPermisosRolesWrapper.setRoles(getRolesAutorizadosPorOpcion(opcion));
		vistaPermisosRolesWrapper.setListaRolAccionOpcion(getAccionesPorOpcion(opcion));
		
		/**
		 * Obtiene rol de principal
		 */
		rol = getRolDePrincipal(principal);
		
		/**
		 * Obtiene las acciones por opcion del rol Los permisos necesarios para
		 * el jsp
		 */
		if (!getOpcionDeUrl(opcion).isEmpty()) {
			rolAccionOpcion = getAccionesOpcionesRol(getOpcionDeUrl(opcion), rol);
		}

		/**
		 * Si encuentra opciones configuradas para este rol y esta opcion carga
		 * el rol en el wrapper
		 */
		if (SI.equals(rolAccionOpcion.getSnLectura())) {
			vistaPermisosRolesWrapper.setRol(rol);
		}

		listaRolAccionOpcion.add(rolAccionOpcion);
		vistaPermisosRolesWrapper.setListaRolAccionOpcion(listaRolAccionOpcion);

		return vistaPermisosRolesWrapper;
	}

	public MenuVistaPermisosRolesWrapper getMenuAutorizaciones(Principal principal) {
		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		Rol rol = new Rol();
		
		menuVistaPermisosRolesWrapper.setRoles(getRolesAutorizadosPorOpcion(""));
		menuVistaPermisosRolesWrapper.setListaRolAccionOpcion(getAccionesPorOpcion(""));
		
		/**
		 * Obtener rol de principal
		 */
		rol = getRolDePrincipal(principal);
		
		/**
		 * Obtiene todas las opciones existentes
		 */
		menuVistaPermisosRolesWrapper.setOpciones(getListaOpciones());
		/**
		 * Obtiene todas las opciones y acciones para el rol
		 */
		menuVistaPermisosRolesWrapper.setListaRolAccionOpcion(getListaRolAccionOpciones(menuVistaPermisosRolesWrapper.getOpciones(), rol));
		
		/**
		 * Si encuentra opciones configuradas para este rol y esta opcion carga
		 * el rol en el wrapper
		 */
		menuVistaPermisosRolesWrapper.setRol(rol);

		return menuVistaPermisosRolesWrapper;
	}

	private Rol getRolDePrincipal(Principal principal) {
		Rol rol = new Rol();
		RolesUsuarios rolesUsuarios = new RolesUsuarios();
		
		/**
		 * Obtiene el id del usuario
		 */
		SocialUserDetails userDetails = getRealPrincipal(principal);
		/**
		 * Obtiene relacion rol usuario
		 */
		rolesUsuarios = getRolesUsuarios(userDetails.getId());
		/**
		 * Obtiene la informacion del rol con la relacion roles usuarios
		 */
		rol = getRol(rolesUsuarios);
		
		return rol;
	}

	private List<Opcion> getListaOpciones() {
		List<Opcion> opciones = new ArrayList<Opcion>();
		
		try {
			opciones = opcionesService.getOpciones();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return opciones;
	}

	private List<RolAccionOpcion> getListaRolAccionOpciones(List<Opcion> opciones, Rol rol) {
		List<RolAccionOpcion> listaRolAccionOpciones = new ArrayList<RolAccionOpcion>();
		
		for (Opcion opcion : opciones) {
			listaRolAccionOpciones.add(getAccionesOpcionesRol(opcion.getCodigo(), rol));
		}
		
		return listaRolAccionOpciones;
	}

	private RolesUsuarios getRolesUsuarios(String idUsuario) {
		RolesUsuarios rolesUsuarios = new RolesUsuarios();

		try {
			rolesUsuarios = rolesService.getRolesUsuarios(idUsuario);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando el rol. El error es: " + e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando el rol. El error es: " + e.getMessage());
		} catch (NullPointerException e) {
			LOG.error("NullPointerException: Ocurrio un error consultando el rol. El error es: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rolesUsuarios;
	}

	/**
	 * 
	 * @param rolesUsuarios
	 * @return
	 */
	private Rol getRol(RolesUsuarios rolesUsuarios) {
		Rol rol = new Rol();

		try {
			rol = rolesService.getRol(rolesUsuarios);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando el rol. El error es: " + e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando el rol. El error es: " + e.getMessage());
		}

		return rol;
	}

	/**
	 * 
	 * @param principal
	 * @return
	 */
	public SocialUserDetails getRealPrincipal(Principal principal) {
		if (principal instanceof SocialUserDetails) {
			return (SocialUserDetails) principal;
		}
		if (principal instanceof Authentication) {
			Object innerPrincipal = ((Authentication) principal).getPrincipal();
			if (innerPrincipal instanceof SocialUserDetails)
				return (SocialUserDetails) innerPrincipal;
		}

		return null;
	}

	/**
	 * 
	 * @param opcion
	 * @return
	 */
	private List<RolAccionOpcion> getAccionesPorOpcion(String opcion) {
		// TODO completar o eliminar metodo private getAccionesPorOpcion
		return null;
	}

	/***
	 * 
	 * @param opcion
	 * @param rol
	 * @return
	 */
	@SuppressWarnings("unused")
	private VistaPermisosRolesWrapper getAcccionesPorOpcionYRol(String opcion, Rol rol) {
		// TODO completar o eliminar metodo private getAcccionesPorOpcionYRol
		return null;
	}

	@SuppressWarnings("unused")
	private VistaPermisosRolesWrapper getOpcionesPorRol(Rol rol) {
		// TODO completar o eliminar metodo private getOpcionesPorRol
		return null;
	}

	/***
	 * 
	 * @param opcion
	 * @return
	 */
	private List<Rol> getRolesAutorizadosPorOpcion(String opcion) {
		// TODO completar o eliminar metodo private getRolesAutorizadosPorOpcion
		return null;
	}

	/**
	 * 
	 * @param opcion
	 * @param rol
	 * @return
	 */
	private RolAccionOpcion getAccionesOpcionesRol(String opcion, Rol rol) {
		RolAccionOpcion rolAccionOpcion = new RolAccionOpcion();
		RolAccionOpcionPK rolAccionOpcionPk = new RolAccionOpcionPK();

		rolAccionOpcionPk.setCodigoOpcion(opcion);
		rolAccionOpcionPk.setCodigoRol(rol.getCodigo());

		try {
			/**
			 * Si no encuentra las acciones y opciones asociadas agrega el
			 * rolaccionopcion con todas las opciones en N por defecto
			 */
			rolAccionOpcion = accionesService.getAcciones(rolAccionOpcionPk);
			if (rolAccionOpcion.getRolAccionOpcionPK().getCodigoOpcion().equals(null)) {
				rolAccionOpcion.getRolAccionOpcionPK().setCodigoOpcion(rolAccionOpcionPk.getCodigoOpcion());
				rolAccionOpcion.getRolAccionOpcionPK().setCodigoRol(rolAccionOpcionPk.getCodigoRol());
				rolAccionOpcion.setSnCrear(NO);
				rolAccionOpcion.setSnEliminar(NO);
				rolAccionOpcion.setSnEscritura(NO);
				rolAccionOpcion.setSnLectura(NO);
				rolAccionOpcion.setSnRestaurar(NO);
			}
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando las acciones por opciones. El error es: "
					+ e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando las acciones por opciones. El error es: "
					+ e.getMessage());
		} catch (NullPointerException e) {
			LOG.error("NUllPointerException: Ocurrio un error consultando las acciones por opciones. El error es: "
					+ e.getMessage());
		}

		return rolAccionOpcion;
	}

	private String getOpcionDeUrl(String url) {
		String antiguaUrl = url;

		try {
			url = opcionesService.getCodigoOpcion(url);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error consultando la opcion. El error es: " + e.getMessage());
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error consultando la opcion. El error es: " + e.getMessage());
		} catch (NoResultException e) {
			LOG.error("NoResultException: Ocurrio un error consultando la opcion. El error es: " + e.getMessage());
		}

		/**
		 * Si no encuentra una url porque la opcion no existe en db no retorna
		 * un error en vez retorna una url vacia como no encontrada
		 */
		if (antiguaUrl.equals(url)) {
			url = "";
		}

		return url;
	}

}
