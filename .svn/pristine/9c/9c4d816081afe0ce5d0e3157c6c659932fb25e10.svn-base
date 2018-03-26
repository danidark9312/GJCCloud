package com.gja.gestionCasos.casos.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "celular")
@NamedQueries({
    @NamedQuery(name = "Celular.findAll", query = "SELECT c FROM Celular c")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Celular implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdcelular")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "nmcelular")
    private String numero;
    @JoinColumn(name = "cdequipocaso", referencedColumnName = "cdequipocaso" )
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonBackReference
    private EquipoCaso codigoEquipoCaso;

    public Celular() {
    }

    public Celular(Integer codigo) {
        this.codigo = codigo;
    }

    public Celular(String numero) {
        this.numero = numero;
    }
    
    public Celular(String numero, Integer codigoEquipoCaso) {
        this.numero = numero;
        this.codigoEquipoCaso = new EquipoCaso(codigoEquipoCaso);
    }

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public EquipoCaso getCodigoEquipoCaso() {
		return codigoEquipoCaso;
	}

	public void setCodigoEquipoCaso(EquipoCaso codigoEquipoCaso) {
		this.codigoEquipoCaso = codigoEquipoCaso;
	}





    
}
