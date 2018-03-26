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

import com.gja.gestionCasos.casos.entities.Prestamo;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "entidades_financieras")
@NamedQueries({
    @NamedQuery(name = "EntidadFinanciera.findAll", query = "SELECT e FROM EntidadFinanciera e")})
public class EntidadFinanciera implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cdentidadfinaciera")
    private String codigoEntidadfinaciera;
    @Basic(optional = false)
    @Column(name = "dsentidadfinanciera")
    private String nombreEntidadfinanciera;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoPrestamo", fetch = FetchType.LAZY)
    private Set<Prestamo> prestamoSet;

    public EntidadFinanciera() {
    }

    public EntidadFinanciera(String codigoEntidadfinaciera) {
        this.codigoEntidadfinaciera = codigoEntidadfinaciera;
    }

    public EntidadFinanciera(String codigoEntidadfinaciera, String nombreEntidadfinanciera, String activo) {
        this.codigoEntidadfinaciera = codigoEntidadfinaciera;
        this.nombreEntidadfinanciera = nombreEntidadfinanciera;
        this.activo = activo;
    }

	public String getCodigoEntidadfinaciera() {
		return codigoEntidadfinaciera;
	}

	public void setCodigoEntidadfinaciera(String codigoEntidadfinaciera) {
		this.codigoEntidadfinaciera = codigoEntidadfinaciera;
	}

	public String getNombreEntidadfinanciera() {
		return nombreEntidadfinanciera;
	}

	public void setNombreEntidadfinanciera(String nombreEntidadfinanciera) {
		this.nombreEntidadfinanciera = nombreEntidadfinanciera;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Set<Prestamo> getPrestamoSet() {
		return prestamoSet;
	}

	public void setPrestamoSet(Set<Prestamo> prestamoSet) {
		this.prestamoSet = prestamoSet;
	}

	
   
    
}
