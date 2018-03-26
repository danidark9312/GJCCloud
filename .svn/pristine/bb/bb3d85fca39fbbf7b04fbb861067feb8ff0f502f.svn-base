package com.gja.gestionCasos.casos.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gja.gestionCasos.maestros.entities.TipoCaso;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "documentostiposcasos")
public class DocumentoTipoCaso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CDDOCUMENTO")
    private Integer codigo;
    @Column(name = "DSDOCUMENTO")
    private String descripcionDocumento;
    @Column(name = "DSRUTA")
    private String ruta;
    @JoinColumn(name = "CDTIPOCASO", referencedColumnName = "cdtipocaso")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoCaso tipoCaso;

	public DocumentoTipoCaso() {
	}	
    
	public DocumentoTipoCaso(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcionDocumento() {
		return descripcionDocumento;
	}

	public void setDescripcionDocumento(String descripcionDocumento) {
		this.descripcionDocumento = descripcionDocumento;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public TipoCaso getTipoCaso() {
		return tipoCaso;
	}

	public void setTipoCaso(TipoCaso tipoCaso) {
		this.tipoCaso = tipoCaso;
	}
}