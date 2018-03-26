package com.gja.gestionCasos.casos.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 *
 * @author DESARROLLADOR6
 */
@Embeddable
public class BienAfectadoPK implements Serializable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdbienafectado")
    private int codigo;
    @Basic(optional = false)
    @Column(name = "cdcaso")
    private int codigoCaso;

    public BienAfectadoPK() {
    }

    public BienAfectadoPK(int codigo, int codigoCaso) {
        this.codigo = codigo;
        this.codigoCaso = codigoCaso;
    }

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigoCaso() {
		return codigoCaso;
	}

	public void setCodigoCaso(int codigoCaso) {
		this.codigoCaso = codigoCaso;
	}




    
}
