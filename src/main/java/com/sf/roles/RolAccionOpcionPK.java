/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sf.roles;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Usuario
 */
@Embeddable
public class RolAccionOpcionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CDROL")
    private int codigoRol;
    @Basic(optional = false)
    @Column(name = "CDOPCION")
    private String codigoOpcion;

    public RolAccionOpcionPK() {
    }

    public RolAccionOpcionPK(int codigoRol, String codigoOpcion) {
        this.codigoRol = codigoRol;
        this.codigoOpcion = codigoOpcion;
    }

	public int getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(int codigoRol) {
		this.codigoRol = codigoRol;
	}

	public String getCodigoOpcion() {
		return codigoOpcion;
	}

	public void setCodigoOpcion(String codigoOpcion) {
		this.codigoOpcion = codigoOpcion;
	}
}
