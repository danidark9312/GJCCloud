/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sf.roles;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertasTareasVencidaRolDetalle;
import com.gja.gestionCasos.maestros.entities.AlertasTareasRolDetalle;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "roles")
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CDROL")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "DSROL")
    private String descripcion;
    
	@JoinColumn(name = "cdestado", referencedColumnName = "cdestado")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
	private EstadoRol estado;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol", fetch = FetchType.EAGER)
    private List<RolesUsuarios> rolesUsuariosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol", fetch = FetchType.LAZY)
    private List<RolAccionOpcion> rolAccionOpcionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
    private Collection<AlertasTareasRolDetalle> alertasTareasRolDetalleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
    private List<AlertasTareasVencidaRolDetalle> alertasTareasDespuesRolDetalleList;

    public Rol() {
    }

    public Rol(Integer codigo) {
        this.codigo = codigo;
    }

    public Rol(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    


	public List<AlertasTareasVencidaRolDetalle> getAlertasTareasDespuesRolDetalleList() {
		return alertasTareasDespuesRolDetalleList;
	}

	public void setAlertasTareasDespuesRolDetalleList(
			List<AlertasTareasVencidaRolDetalle> alertasTareasDespuesRolDetalleList) {
		this.alertasTareasDespuesRolDetalleList = alertasTareasDespuesRolDetalleList;
	}

	public Collection<AlertasTareasRolDetalle> getAlertasTareasRolDetalleCollection() {
		return alertasTareasRolDetalleCollection;
	}

	public void setAlertasTareasRolDetalleCollection(
			Collection<AlertasTareasRolDetalle> alertasTareasRolDetalleCollection) {
		this.alertasTareasRolDetalleCollection = alertasTareasRolDetalleCollection;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<RolAccionOpcion> getRolAccionOpcionList() {
		return rolAccionOpcionList;
	}

	public void setRolAccionOpcionList(List<RolAccionOpcion> rolAccionOpcionList) {
		this.rolAccionOpcionList = rolAccionOpcionList;
	}

	public List<RolesUsuarios> getRolesUsuariosList() {
		return rolesUsuariosList;
	}

	public void setRolesUsuariosList(List<RolesUsuarios> rolesUsuariosList) {
		this.rolesUsuariosList = rolesUsuariosList;
	}

	
	public EstadoRol getEstado() {
		return estado;
	}

	
	public void setEstado(EstadoRol estado) {
		this.estado = estado;
	}
}
