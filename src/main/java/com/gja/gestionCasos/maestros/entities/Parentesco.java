package com.gja.gestionCasos.maestros.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "parentescos")
@NamedQueries({
    @NamedQuery(name = "Parentesco.findAll", query = "SELECT p FROM Parentesco p")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Parentesco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdparentesco")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "dsnombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String activo;
    //@JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "parentesco", fetch = FetchType.LAZY)
    private Set<CasoEquipoCaso> casoEquipoCasoSet;

    public Parentesco() {
    }

    public Parentesco(Integer codigo) {
        this.codigo = codigo;
    }

    public Parentesco(Integer codigo, String nombre, String activo) {
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

	public Set<CasoEquipoCaso> getCasoEquipoCasoSet() {
		return casoEquipoCasoSet;
	}

	public void setCasoEquipoCasoSet(Set<CasoEquipoCaso> casoEquipoCasoSet) {
		this.casoEquipoCasoSet = casoEquipoCasoSet;
	}

    
}
