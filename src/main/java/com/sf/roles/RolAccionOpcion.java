/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sf.roles;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "rol_accionopcion")
public class RolAccionOpcion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RolAccionOpcionPK rolAccionOpcionPK;
    @Column(name = "SNLECTURA")
    private String snLectura;
    @Column(name = "SNESCRITURA")
    private String snEscritura;
    @Column(name = "SNCREAR")
    private String snCrear;
    @Column(name = "SNELIMINAR")
    private String snEliminar;
    @Column(name = "SNRESTAURAR")
    private String snRestaurar;
    @JoinColumn(name = "CDROL", referencedColumnName = "CDROL", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Rol rol;
    @JoinColumn(name = "CDOPCION", referencedColumnName = "CDOPCION", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Opcion opcion;

    public RolAccionOpcionPK getRolAccionOpcionPK() {
		return rolAccionOpcionPK;
	}

	public void setRolAccionOpcionPK(RolAccionOpcionPK rolAccionOpcionPK) {
		this.rolAccionOpcionPK = rolAccionOpcionPK;
	}

	/**
	 * @return the rol
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return the opcion
	 */
	public Opcion getOpcion() {
		return opcion;
	}

	/**
	 * @param opcion the opcion to set
	 */
	public void setOpcion(Opcion opcion) {
		this.opcion = opcion;
	}

	public RolAccionOpcion() {
    }

    public RolAccionOpcion(RolAccionOpcionPK rolAccionOpcionPK) {
        this.rolAccionOpcionPK = rolAccionOpcionPK;
    }

    public RolAccionOpcion(int cdrol, String cdopcion) {
        this.rolAccionOpcionPK = new RolAccionOpcionPK(cdrol, cdopcion);
    }

	
    public String getSnLectura() {
		return snLectura;
	}

	
    public void setSnLectura(String snLectura) {
		this.snLectura = snLectura;
	}
    

	public String getSnEscritura() {
		return snEscritura;
	}

	
	public void setSnEscritura(String snEscritura) {
		this.snEscritura = snEscritura;
	}
	

	public String getSnCrear() {
		return snCrear;
	}
	

	public void setSnCrear(String snCrear) {
		this.snCrear = snCrear;
	}
	

	public String getSnEliminar() {
		return snEliminar;
	}
	

	public void setSnEliminar(String snEliminar) {
		this.snEliminar = snEliminar;
	}
	

	public String getSnRestaurar() {
		return snRestaurar;
	}
	

	public void setSnRestaurar(String snRestaurar) {
		this.snRestaurar = snRestaurar;
	}
    
}
