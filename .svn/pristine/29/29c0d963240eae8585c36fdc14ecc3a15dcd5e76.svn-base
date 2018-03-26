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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "paises")
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Pais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cdpais")
    private String codigoPais;
    @Basic(optional = false)
    @Column(name = "dspais")
    private String nombre;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pais", fetch = FetchType.LAZY)
    private Set<Departamento> departamentoSet;

    public Pais() {
    }

    public Pais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public Pais(String codigoPais, String nombre) {
        this.codigoPais = codigoPais;
        this.nombre = nombre;
    }

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Departamento> getDepartamentoSet() {
		return departamentoSet;
	}

	public void setDepartamentoSet(Set<Departamento> departamentoSet) {
		this.departamentoSet = departamentoSet;
	}




   
    
}
