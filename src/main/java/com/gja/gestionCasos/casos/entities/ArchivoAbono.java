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


@Entity
@Table(name = "archivosabonos")
@NamedQueries({
    @NamedQuery(name = "ArchivoAbono.findAll", query = "SELECT a FROM ArchivoAbono a")})
public class ArchivoAbono implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarchivo")
    private Integer codigo;
    
    @JoinColumn(name = "cdabono", referencedColumnName = "cdabono")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Abono abono;
    
    @Column(name = "dsarchivo")
    private String nombreArchivo;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Abono getAbono() {
		return abono;
	}

	public void setAbono(Abono abono) {
		this.abono = abono;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	@Override
	public String toString() {
		return "ArchivoAbono [codigo=" + codigo + ", abono=" + abono + ", nombreArchivo=" + nombreArchivo + "]";
	}
    
    
    
        
}
