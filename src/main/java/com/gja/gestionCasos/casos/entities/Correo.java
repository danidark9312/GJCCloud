package com.gja.gestionCasos.casos.entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

import javax.persistence.Basic;
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
@Table(name = "correos")
@NamedQueries({
    @NamedQuery(name = "Correo.findAll", query = "SELECT c FROM Correo c")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Correo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdcorreo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "dscorreo")
    private String correo;
    @JoinColumn(name = "cdequipocaso", referencedColumnName = "cdequipocaso")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonBackReference
    private EquipoCaso codigoEquipoCaso;

    public Correo() {
    }

    public Correo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public Correo(String correo) {        
        this.correo = correo;
    }

    public Correo(String correo, Integer codigoEquipoCaso) {
        this.correo = correo;
        this.codigoEquipoCaso = new EquipoCaso(codigoEquipoCaso);
    }

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public EquipoCaso getCodigoEquipoCaso() {
		return codigoEquipoCaso;
	}

	public void setCodigoEquipoCaso(EquipoCaso codigoEquipoCaso) {
		this.codigoEquipoCaso = codigoEquipoCaso;
	}



    
}
