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

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tareas_actividad")
@NamedQueries({
    @NamedQuery(name = "TareaActividad.findAll", query = "SELECT t FROM TareaActividad t")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class TareaActividad implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdtareaactividad")
    private Integer cdtareaactividad;
    @Basic(optional = false)
    @Column(name = "dsdetalle")
    private String dsdetalle;
    @Basic(optional = false)
    @Column(name = "dstarea")
    private String dstarea;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String isactivo;
    @JoinColumn(name = "cdactividad", referencedColumnName = "cdactividad")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Actividad cdactividad;
    
    @Column(name = "snobligatoriofechavencimiento")
    private String snObligatorioFechaVencimiento;

    
    
    public String getSnObligatorioFechaVencimiento() {
		return snObligatorioFechaVencimiento;
	}

	public void setSnObligatorioFechaVencimiento(String snObligatorioFechaVencimiento) {
		this.snObligatorioFechaVencimiento = snObligatorioFechaVencimiento;
	}

	public TareaActividad() {
    }

    public TareaActividad(Integer cdtareaactividad) {
        this.cdtareaactividad = cdtareaactividad;
    }

    public TareaActividad(Integer cdtareaactividad, String dsdetalle, String dstarea, String isactivo) {
        this.cdtareaactividad = cdtareaactividad;
        this.dsdetalle = dsdetalle;
        this.dstarea = dstarea;
        this.isactivo = isactivo;
    }

    public Integer getCdtareaactividad() {
        return cdtareaactividad;
    }

    public void setCdtareaactividad(Integer cdtareaactividad) {
        this.cdtareaactividad = cdtareaactividad;
    }

    public String getDsdetalle() {
        return dsdetalle;
    }

    public void setDsdetalle(String dsdetalle) {
        this.dsdetalle = dsdetalle;
    }

    public String getDstarea() {
        return dstarea;
    }

    public void setDstarea(String dstarea) {
        this.dstarea = dstarea;
    }

    public String getIsactivo() {
        return isactivo;
    }

    public void setIsactivo(String isactivo) {
        this.isactivo = isactivo;
    }

    public Actividad getCdactividad() {
        return cdactividad;
    }

    public void setCdactividad(Actividad cdactividad) {
        this.cdactividad = cdactividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdtareaactividad != null ? cdtareaactividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareaActividad)) {
            return false;
        }
        TareaActividad other = (TareaActividad) object;
        if ((this.cdtareaactividad == null && other.cdtareaactividad != null) || (this.cdtareaactividad != null && !this.cdtareaactividad.equals(other.cdtareaactividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gja.TareaActividad[ cdtareaactividad=" + cdtareaactividad + " ]";
    }
    
}
