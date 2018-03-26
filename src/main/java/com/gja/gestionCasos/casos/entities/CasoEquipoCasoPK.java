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

/**
 *
 * @author DESARROLLADOR6
 */
@Embeddable
public class CasoEquipoCasoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "cdcaso")
    private int codigo;
    @Basic(optional = false)
    @Column(name = "cdequipocaso")
    private int codigoEquipoCaso;
    @Basic(optional = false)
    @Column(name = "cdmiembro")
    private int miembro;

    public CasoEquipoCasoPK() {
    }

    public CasoEquipoCasoPK(int codigo, int codigoEquipoCaso, int miembro) {
        this.codigo = codigo;
        this.codigoEquipoCaso = codigoEquipoCaso;
        this.miembro = miembro;
    }
  
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigoEquipoCaso() {
		return codigoEquipoCaso;
	}

	public void setCodigoEquipoCaso(int codigoEquipoCaso) {
		this.codigoEquipoCaso = codigoEquipoCaso;
	}

	public int getMiembro() {
		return miembro;
	}

	public void setMiembro(int miembro) {
		this.miembro = miembro;
	}


  


}

