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

import com.gja.gestionCasos.casos.entities.Radicado;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "instancias")
@NamedQueries({
    @NamedQuery(name = "Instancia.findAll", query = "SELECT i FROM Instancia i")})
public class Instancia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cdinstancia")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "dsinstancia")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instancia", fetch = FetchType.LAZY)
    private Set<Radicado> radicadoSet;

    public Instancia() {
    }

    public Instancia(String codigo) {
        this.codigo = codigo;
    }

    public Instancia(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Radicado> getRadicadoSet() {
		return radicadoSet;
	}

	public void setRadicadoSet(Set<Radicado> radicadoSet) {
		this.radicadoSet = radicadoSet;
	}


    
}
