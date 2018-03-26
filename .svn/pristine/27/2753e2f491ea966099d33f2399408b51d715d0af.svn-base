/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gja.gestionCasos.maestros.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.sf.roles.Rol;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "alertas_tareas_rol_detalle")
public class AlertasTareasRolDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CDCONSECUTIVO")
    private Integer codigo;
    @JoinColumn(name = "CDALERTA", referencedColumnName = "CDALERTA")
    @ManyToOne(optional = false)
    private AlertaTareaRol alertaTareaRol;
    @JoinColumn(name = "CDROL", referencedColumnName = "CDROL")
    @ManyToOne(optional = false)
    private Rol rol;

    public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public AlertaTareaRol getAlertaTareaRol() {
		return alertaTareaRol;
	}

	public void setAlertaTareaRol(AlertaTareaRol alertaTareaRol) {
		this.alertaTareaRol = alertaTareaRol;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public AlertasTareasRolDetalle() {
    }

    public AlertasTareasRolDetalle(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCdconsecutivo() {
        return codigo;
    }

    public void setCdconsecutivo(Integer codigo) {
        this.codigo = codigo;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlertasTareasRolDetalle)) {
            return false;
        }
        AlertasTareasRolDetalle other = (AlertasTareasRolDetalle) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }


    
}
