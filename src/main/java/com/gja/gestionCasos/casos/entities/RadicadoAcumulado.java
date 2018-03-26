package com.gja.gestionCasos.casos.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author DGM
 */
@Entity
@Table(name = "radicados_acumulados")
@NamedQueries({
	@NamedQuery(name = "RadicadoAcumulado.findAll", query = "SELECT r FROM RadicadoAcumulado r")})
public class RadicadoAcumulado implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RadicadoAcumuladoPK radicadoPK;
    
    
    @ManyToOne
    @JoinColumns({
    	@JoinColumn(name="cdradicado", referencedColumnName="cdradicado",insertable = false, updatable = false) ,
    	@JoinColumn(name="cdcaso", referencedColumnName="cdcaso",insertable = false, updatable = false)
    })
    private Radicado radicado;
    
    @Basic(optional = false)
    @Column(name = "cdtiporadicado")
    private String tipoRadicado;
    
    @Basic(optional = false)
    @Column(name = "dsobservacion")
    private String observacion;
  
    

	public Radicado getRadicado() {
		return radicado;
	}



	public void setRadicado(Radicado radicado) {
		this.radicado = radicado;
	}



	public RadicadoAcumulado(RadicadoAcumuladoPK radicadoPK, String tipoRadicado) {
		super();
		this.radicadoPK = radicadoPK;
		this.tipoRadicado = tipoRadicado;
	}



	public RadicadoAcumulado(RadicadoAcumuladoPK radicadoPK) {
		super();
		this.radicadoPK = radicadoPK;
	}



	public RadicadoAcumulado(RadicadoAcumuladoPK radicadoPK, String tipoRadicado, String observacion) {
		super();
		this.radicadoPK = radicadoPK;
		this.tipoRadicado = tipoRadicado;
		this.observacion = observacion;
	}



	public RadicadoAcumuladoPK getRadicadoPK() {
		return radicadoPK;
	}



	public void setRadicadoPK(RadicadoAcumuladoPK radicadoPK) {
		this.radicadoPK = radicadoPK;
	}



	public String getTipoRadicado() {
		return tipoRadicado;
	}



	public void setTipoRadicado(String tipoRadicado) {
		this.tipoRadicado = tipoRadicado;
	}



	public String getObservacion() {
		return observacion;
	}



	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}



	public RadicadoAcumulado() {
		super();
	}
	
	
	
    
}
