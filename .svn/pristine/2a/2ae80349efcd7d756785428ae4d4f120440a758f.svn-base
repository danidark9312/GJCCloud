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
import javax.persistence.Table;

import com.sf.roles.Rol;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "alertas_tareas_vencida_rol_detalle")
public class AlertasTareasVencidaRolDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CDCONSECUTIVO")
    private Integer codigo;
    @JoinColumn(name = "CDROL", referencedColumnName = "CDROL")
    @ManyToOne(optional = false)
    private Rol rol;
    @JoinColumn(name = "CDALERTA", referencedColumnName = "CDALERTA")
    @ManyToOne(optional = false)
    private AlertaTareaVencidaRol tareaParticularRol;

    public AlertasTareasVencidaRolDetalle() {
    }

    public AlertasTareasVencidaRolDetalle(Integer codigo) {
        this.codigo = codigo;
    }

    
    public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public AlertaTareaVencidaRol getTareaParticularRol() {
		return tareaParticularRol;
	}

	public void setTareaParticularRol(AlertaTareaVencidaRol tareaParticularRol) {
		this.tareaParticularRol = tareaParticularRol;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
        if (!(object instanceof AlertasTareasVencidaRolDetalle)) {
            return false;
        }
        AlertasTareasVencidaRolDetalle other = (AlertasTareasVencidaRolDetalle) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }
    
}
