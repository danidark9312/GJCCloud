package com.sf.roles;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Diego Blandón
 */
@Embeddable
public class RolesUsuariosPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "CDUSUARIO")
    private String codigoUsuario;
    @Basic(optional = false)
    @Column(name = "CDROL")
    private int codigoRol;

    public RolesUsuariosPK() {
    }

    public RolesUsuariosPK(String codigoUsuario, int codigoRol) {
        this.codigoUsuario = codigoUsuario;
        this.codigoRol = codigoRol;
    }

	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public int getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(int codigoRol) {
		this.codigoRol = codigoRol;
	}
    
}
