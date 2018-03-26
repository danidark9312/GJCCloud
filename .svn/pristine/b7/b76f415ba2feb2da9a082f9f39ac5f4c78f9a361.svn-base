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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "tipos_miembro")
@NamedQueries({
    @NamedQuery(name = "TipoMiembro.findAll", query = "SELECT t FROM TipoMiembro t")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class TipoMiembro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdmiembro")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "dsmiembro")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoMiembro", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<CasoEquipoCaso> casoEquipoCasoSet;

    public TipoMiembro() {
    }

    public TipoMiembro(Integer codigo) {
        this.codigo = codigo;
    }

    public TipoMiembro(Integer codigo, String nombre) {
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

	public Set<CasoEquipoCaso> getCasoEquipoCasoSet() {
		return casoEquipoCasoSet;
	}

	public void setCasoEquipoCasoSet(Set<CasoEquipoCaso> casoEquipoCasoSet) {
		this.casoEquipoCasoSet = casoEquipoCasoSet;
	}


    
}
