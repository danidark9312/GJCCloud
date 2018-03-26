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

import com.gja.gestionCasos.casos.entities.BienAfectado;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "clases_bienes")
@NamedQueries({
    @NamedQuery(name = "ClaseBien.findAll", query = "SELECT DISTINCT c FROM ClaseBien c")})
public class ClaseBien implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdclase")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "dsclase")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clase", fetch = FetchType.LAZY)
    private Set<BienAfectado> bienAfectadoSet;

    public ClaseBien() {
    }

    public ClaseBien(Integer codigo) {
        this.codigo = codigo;
    }

    public ClaseBien(Integer codigo, String nombre, String activo) {
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

	public Set<BienAfectado> getBienAfectadoSet() {
		return bienAfectadoSet;
	}

	public void setBienAfectadoSet(Set<BienAfectado> bienAfectadoSet) {
		this.bienAfectadoSet = bienAfectadoSet;
	}


    
}
