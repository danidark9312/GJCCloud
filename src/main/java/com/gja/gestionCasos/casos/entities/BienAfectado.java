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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import com.gja.gestionCasos.maestros.entities.ClaseBien;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "bienes_afectados")
@NamedQueries({
    @NamedQuery(name = "BienAfectado.findAll", query = "SELECT b FROM BienAfectado b")})
public class BienAfectado implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BienAfectadoPK bienAfectadoPK;
    @Column(name = "dsbienafectado")
    private String titulo;
    @Column(name = "dsdetalle")
    private String detalle;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String activo;
    @JoinColumn(name = "cdcaso", referencedColumnName = "cdcaso", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Caso caso;
    @JoinColumn(name = "cdclase", referencedColumnName = "cdclase")
    @ManyToOne(fetch = FetchType.LAZY)
    private ClaseBien clase;

    public BienAfectado() {
    }

    public BienAfectado(BienAfectadoPK bienAfectadoPK) {
        this.bienAfectadoPK = bienAfectadoPK;
    }

    public BienAfectado(BienAfectadoPK bienAfectadoPK, String titulo, String detalle, String activo) {
        this.bienAfectadoPK = bienAfectadoPK;
        this.titulo = titulo;
        this.detalle = detalle;
        this.activo = activo;
    }

    public BienAfectado(int codigoBienAfectado, int codigoCaso) {
        this.bienAfectadoPK = new BienAfectadoPK(codigoBienAfectado, codigoCaso);
    }

	public BienAfectadoPK getBienAfectadoPK() {
		return bienAfectadoPK;
	}

	public void setBienAfectadoPK(BienAfectadoPK bienAfectadoPK) {
		this.bienAfectadoPK = bienAfectadoPK;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Caso getCaso() {
		return caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}

	public ClaseBien getClase() {
		return clase;
	}

	public void setClase(ClaseBien clase) {
		this.clase = clase;
	}


    
}
