/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gja.gestionCasos.maestros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "alertas_tareas_rol")
public class AlertaTareaRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDALERTA")
    private Integer codigo;
    @Column(name = "NMALERTASDIARIAS")
    private Integer numeroAlertaDiaria;
    @Column(name = "NMDIAANTES")
    private Integer numeroDiasAntes;
    @Column(name = "FEPRIMERAALERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fePrimeraAlerta;
    @Column(name = "FEULTIMAALERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feUltimaAlerta;
    @Column(name = "NMNOTIFICACIONESENVIADAS")
    private Integer totalNotificacion;
    @Column(name = "CDESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alertaTareaRol")
    private List<AlertasTareasRolDetalle> alertasTareasRolDetalleCollection;
    @JoinColumn(name = "CDTAREA", referencedColumnName = "cdtareaparticular")
    @ManyToOne(optional = false)
    private TareaParticularCaso tareaparticular;
    @Column(name = "CDTIPOCASO")
    private Integer tipoCaso;
    
    

    public Integer getTipoCaso() {
		return tipoCaso;
	}

	public void setTipoCaso(Integer tipoCaso) {
		this.tipoCaso = tipoCaso;
	}

	public AlertaTareaRol() {
    }

    public AlertaTareaRol(Integer codigo) {
        this.codigo = codigo;
    }

    
 

    public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getNumeroAlertaDiaria() {
		return numeroAlertaDiaria;
	}

	public void setNumeroAlertaDiaria(Integer numeroAlertaDiaria) {
		this.numeroAlertaDiaria = numeroAlertaDiaria;
	}

	public Integer getNumeroDiasAntes() {
		return numeroDiasAntes;
	}

	public void setNumeroDiasAntes(Integer numeroDiasAntes) {
		this.numeroDiasAntes = numeroDiasAntes;
	}

	public Date getFePrimeraAlerta() {
		return fePrimeraAlerta;
	}

	public void setFePrimeraAlerta(Date fePrimeraAlerta) {
		this.fePrimeraAlerta = fePrimeraAlerta;
	}

	public Date getFeUltimaAlerta() {
		return feUltimaAlerta;
	}

	public void setFeUltimaAlerta(Date feUltimaAlerta) {
		this.feUltimaAlerta = feUltimaAlerta;
	}


	public Integer getTotalNotificacion() {
		return totalNotificacion;
	}

	public void setTotalNotificacion(Integer totalNotificacion) {
		this.totalNotificacion = totalNotificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public TareaParticularCaso getTareaparticular() {
		return tareaparticular;
	}

	public void setTareaparticular(TareaParticularCaso tareaparticular) {
		this.tareaparticular = tareaparticular;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

    public List<AlertasTareasRolDetalle> getAlertasTareasRolDetalleCollection() {
		return alertasTareasRolDetalleCollection;
	}

	public void setAlertasTareasRolDetalleCollection(List<AlertasTareasRolDetalle> alertasTareasRolDetalleCollection) {
		this.alertasTareasRolDetalleCollection = alertasTareasRolDetalleCollection;
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
        if (!(object instanceof AlertaTareaRol)) {
            return false;
        }
        AlertaTareaRol other = (AlertaTareaRol) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }


}
