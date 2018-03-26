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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "estados_caso")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class EstadoCaso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdestadocaso")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "dsestadocaso")
    private String nombre;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoCaso", fetch = FetchType.LAZY)
    private Set<Caso> casoSet;

    public EstadoCaso() {
    }

    public EstadoCaso(Integer codigo) {
        this.codigo = codigo;
    }

    public EstadoCaso(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

	public Set<Caso> getCasoSet() {
		return casoSet;
	}

	public void setCasoSet(Set<Caso> casoSet) {
		this.casoSet = casoSet;
	}




    
}
