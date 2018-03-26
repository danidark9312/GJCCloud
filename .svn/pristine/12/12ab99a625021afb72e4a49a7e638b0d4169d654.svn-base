/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gja.gestionCasos.maestros.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tipos_de_caso")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class TipoCaso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdtipocaso")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "dstipocaso")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String activo;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdtipocaso", fetch = FetchType.EAGER)
    private List<ActividadTipoCaso> actividadTipoCasoList;

    public TipoCaso() {
    }

    public TipoCaso(Integer codigo) {
        this.codigo = codigo;
    }

    public TipoCaso(Integer codigo, String nombre, String activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.activo = activo;
    }

    
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public List<ActividadTipoCaso> getActividadTipoCasoList() {
		return actividadTipoCasoList;
	}

	public void setActividadTipoCasoList(
			List<ActividadTipoCaso> actividadTipoCasoList) {
		this.actividadTipoCasoList = actividadTipoCasoList;
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
        if (!(object instanceof TipoCaso)) {
            return false;
        }
        TipoCaso other = (TipoCaso) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gja.TipoCaso[ cdtipocaso=" + codigo + " ]";
    }

    
}
