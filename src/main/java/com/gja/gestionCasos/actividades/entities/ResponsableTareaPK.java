package com.gja.gestionCasos.actividades.entities;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Usuario
 */
@Embeddable
public class ResponsableTareaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "cdtareaparticular")
    private int codigoTareaparticular;
    @Basic(optional = false)
    @Column(name = "cdequipocaso")
    private int codigoEquipoCaso;
    @Basic(optional = false)
    @Column(name = "cdmiembro")
    private int codigoMiembro;
    @Basic(optional = false)
    @Column(name = "cdcaso")
    private int codigoCaso;

    public ResponsableTareaPK() {
    }

    public ResponsableTareaPK(int codigoTareaparticular, int codigoEquipoCaso, int codigoMiembro, int codigoCaso) {
        this.codigoTareaparticular = codigoTareaparticular;
        this.codigoEquipoCaso = codigoEquipoCaso;
        this.codigoMiembro = codigoMiembro;
        this.codigoCaso = codigoCaso;
    }

	public int getCodigoTareaparticular() {
		return codigoTareaparticular;
	}

	public void setCodigoTareaparticular(int codigoTareaparticular) {
		this.codigoTareaparticular = codigoTareaparticular;
	}

	public int getCodigoEquipoCaso() {
		return codigoEquipoCaso;
	}

	public void setCodigoEquipoCaso(int codigoEquipoCaso) {
		this.codigoEquipoCaso = codigoEquipoCaso;
	}

	public int getCodigoMiembro() {
		return codigoMiembro;
	}

	public void setCodigoMiembro(int codigoMiembro) {
		this.codigoMiembro = codigoMiembro;
	}

	public int getCodigoCaso() {
		return codigoCaso;
	}

	public void setCodigoCaso(int codigoCaso) {
		this.codigoCaso = codigoCaso;
	}


	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof ResponsableTareaPK)) {
	        return false;
	    }

	    ResponsableTareaPK that = (ResponsableTareaPK) other;

	    // Custom equality check here.
	    return this.codigoTareaparticular == that.codigoTareaparticular 
	        && this.codigoEquipoCaso == that.codigoEquipoCaso
	        && this.codigoMiembro == that.codigoMiembro
	        && this.codigoTareaparticular == that.codigoTareaparticular;
	}

}

