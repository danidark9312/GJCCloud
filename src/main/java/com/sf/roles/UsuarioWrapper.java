/**
 * 
 */
package com.sf.roles;

import com.sf.social.signinmvc.user.model.User;

/**
 * @author USUARIO
 *
 */
public class UsuarioWrapper {
	private User usuario;
	private RolesUsuariosPK rolesUsuariosPk;
	private boolean nuevo;
	private String correoAntiguo;
	
	
	
	
	
	public String getCorreoAntiguo() {
		return correoAntiguo;
	}

	public void setCorreoAntiguo(String correoAntiguo) {
		this.correoAntiguo = correoAntiguo;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public User getUsuario() {
		return usuario;
	}
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	public RolesUsuariosPK getRolesUsuariosPk() {
		return rolesUsuariosPk;
	}
	
	public void setRolesUsuariosPk(RolesUsuariosPK rolesUsuariosPk) {
		this.rolesUsuariosPk = rolesUsuariosPk;
	}
	
}
