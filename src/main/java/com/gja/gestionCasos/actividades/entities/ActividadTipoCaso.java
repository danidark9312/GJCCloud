/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gja.gestionCasos.actividades.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gja.gestionCasos.maestros.entities.TipoCaso;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "actividades_tipo_caso")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class ActividadTipoCaso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdactividadtipocaso")
    private Integer cdactividadtipocaso;
    @JoinColumn(name = "cdactividad", referencedColumnName = "cdactividad")
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Actividad cdactividad;
    @JsonBackReference
    @JoinColumn(name = "cdtipocaso", referencedColumnName = "cdtipocaso")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoCaso cdtipocaso;
    
    @Column(name = "snobligatoriofechavencimiento")
    private String snObligatorioFechaVencimiento;

    public ActividadTipoCaso() {
    }

    
    
    public String getSnObligatorioFechaVencimiento() {
		return snObligatorioFechaVencimiento;
	}



	public void setSnObligatorioFechaVencimiento(String snObligatorioFechaVencimiento) {
		this.snObligatorioFechaVencimiento = snObligatorioFechaVencimiento;
	}



	public ActividadTipoCaso(Integer cdactividadtipocaso) {
        this.cdactividadtipocaso = cdactividadtipocaso;
    }

    public Integer getCdactividadtipocaso() {
        return cdactividadtipocaso;
    }

    public void setCdactividadtipocaso(Integer cdactividadtipocaso) {
        this.cdactividadtipocaso = cdactividadtipocaso;
    }

    public Actividad getCdactividad() {
        return cdactividad;
    }

    public void setCdactividad(Actividad cdactividad) {
        this.cdactividad = cdactividad;
    }

    public TipoCaso getCdtipocaso() {
        return cdtipocaso;
    }

    public void setCdtipocaso(TipoCaso cdtipocaso) {
        this.cdtipocaso = cdtipocaso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdactividadtipocaso != null ? cdactividadtipocaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadTipoCaso)) {
            return false;
        }
        ActividadTipoCaso other = (ActividadTipoCaso) object;
        if ((this.cdactividadtipocaso == null && other.cdactividadtipocaso != null) || (this.cdactividadtipocaso != null && !this.cdactividadtipocaso.equals(other.cdactividadtipocaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.ActividadTipoCaso[ cdactividadtipocaso=" + cdactividadtipocaso + " ]";
    }
    
}
