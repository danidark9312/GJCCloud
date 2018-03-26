/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gja.gestionCasos.actividades.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "actividades")
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Actividad implements Serializable {
    
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdactividad")
    private Integer cdactividad;
    @Basic(optional = false)
    @Column(name = "dsactividad")
    private String dsactividad;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String isactivo;
    @Basic(optional = false)
    @Column(name = "dsdetalle")
    private String dsdetalle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdactividad", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ActividadTipoCaso> actividadTipoCasoList;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdactividad", fetch = FetchType.EAGER, orphanRemoval=true )
    private List<TareaActividad> tareaActividadList;

    public Actividad() {
    }

    public Actividad(Integer cdactividad) {
        this.cdactividad = cdactividad;
    }

    public Actividad(Integer cdactividad, String dsactividad, String isactivo, String dsdetalle) {
        this.cdactividad = cdactividad;
        this.dsactividad = dsactividad;
        this.isactivo = isactivo;
        this.dsdetalle = dsdetalle;
    }

    public Integer getCdactividad() {
        return cdactividad;
    }

    public void setCdactividad(Integer cdactividad) {
        this.cdactividad = cdactividad;
    }

    public String getDsactividad() {
        return dsactividad;
    }

    public void setDsactividad(String dsactividad) {
        this.dsactividad = dsactividad;
    }

    public String getIsactivo() {
        return isactivo;
    }

    public void setIsactivo(String isactivo) {
        this.isactivo = isactivo;
    }

    public String getDsdetalle() {
        return dsdetalle;
    }

    public void setDsdetalle(String dsdetalle) {
        this.dsdetalle = dsdetalle;
    }

    public List<ActividadTipoCaso> getActividadTipoCasoList() {
        return actividadTipoCasoList;
    }

    public void setActividadTipoCasoList(List<ActividadTipoCaso> actividadTipoCasoList) {
        this.actividadTipoCasoList = actividadTipoCasoList;
    }

    public List<TareaActividad> getTareaActividadList() {
        return tareaActividadList;
    }

    public void setTareaActividadList(List<TareaActividad> tareaActividadList) {
        this.tareaActividadList = tareaActividadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdactividad != null ? cdactividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.cdactividad == null && other.cdactividad != null) || (this.cdactividad != null && !this.cdactividad.equals(other.cdactividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Actividad[ cdactividad=" + cdactividad + " ]";
    }
    
}
