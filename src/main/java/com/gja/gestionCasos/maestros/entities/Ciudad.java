package com.gja.gestionCasos.maestros.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gja.gestionCasos.casos.entities.Caso;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "ciudades")
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Ciudad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CiudadPK ciudadPK;
    @Basic(optional = false)
    @Column(name = "dsciudad")
    private String nombre;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudadProceso", fetch = FetchType.LAZY)
    private Set<Caso> casoSet;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudadHechos", fetch = FetchType.LAZY)
    private Set<Caso> casoSet1;
    @JoinColumn(name = "cddepartamento", referencedColumnName = "cddepartamento", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Departamento departamento;

    public Ciudad() {
    }

    public Ciudad(CiudadPK ciudadPK) {
        this.ciudadPK = ciudadPK;
    }

    public Ciudad(CiudadPK ciudadPK, String nombre) {
        this.ciudadPK = ciudadPK;
        this.nombre = nombre;
    }

    public Ciudad(String cdciudad, String cddepartamento) {
        this.ciudadPK = new CiudadPK(cdciudad, cddepartamento);
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Caso> getCasoSet() {
		return casoSet;
	}

	public void setCasoSet(Set<Caso> casoSet) {
		this.casoSet = casoSet;
	}

	public Set<Caso> getCasoSet1() {
		return casoSet1;
	}

	public void setCasoSet1(Set<Caso> casoSet1) {
		this.casoSet1 = casoSet1;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public CiudadPK getCiudadPK() {
		return ciudadPK;
	}

	public void setCiudadPK(CiudadPK ciudadPK) {
		this.ciudadPK = ciudadPK;
	}


    
}
