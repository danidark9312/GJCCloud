/**
 * 
 */
package com.gja.gestionCasos.permisos.service;

import java.security.Principal;

import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;

/**
 * @author USUARIO
 *
 */
public interface VistaRolesAccionOpciones {

	/***
	 * Usado para obtener las autorizaciones por cada /url (opcion) del sistema 
	 * dependiendo del Rol
	 * @param opcion
	 * @return VistaPermisosRolesWrapper
	 */
	VistaPermisosRolesWrapper getAutorizaciones(String opcion, Principal principal);
	/***
	 * Usado para obtener las autorizaciones por cada /url (opcion) del sistema para el menu
	 * dependiendo del Rol
	 * @return VistaPermisosRolesWrapper
	 */
	MenuVistaPermisosRolesWrapper getMenuAutorizaciones(Principal principal);
	
	/**
	 * @param principal
	 * @return
	 */
	SocialUserDetails getRealPrincipal(Principal principal);
}
