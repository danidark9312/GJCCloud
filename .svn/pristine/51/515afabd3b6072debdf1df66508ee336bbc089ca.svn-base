package com.gja.gestionCasos.casos.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Desarrollador3
 */
@Embeddable
public class RadicadoPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "cdradicado")
    private String codigoRadicado;
    @Basic(optional = false)
    @Column(name = "cdcaso")
    private int codigoCaso;

    public RadicadoPK() {
    }

    public RadicadoPK(String codigoRadicado, int codigoCaso) {
        this.codigoRadicado = codigoRadicado;
        this.codigoCaso = codigoCaso;
    }
    

    public String getCodigoRadicado() {
		return codigoRadicado;
	}

	public void setCodigoRadicado(String codigoRadicado) {
		this.codigoRadicado = codigoRadicado;
	}

	public int getCodigoCaso() {
		return codigoCaso;
	}

	public void setCodigoCaso(int codigoCaso) {
		this.codigoCaso = codigoCaso;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoRadicado != null ? codigoRadicado.hashCode() : 0);
        hash += (int) codigoCaso;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RadicadoPK)) {
            return false;
        }
        RadicadoPK other = (RadicadoPK) object;
        if ((this.codigoRadicado == null && other.codigoRadicado != null) || (this.codigoRadicado != null && !this.codigoRadicado.equals(other.codigoRadicado))) {
            return false;
        }
        if (this.codigoCaso != other.codigoCaso) {
            return false;
        }
        return true;
    }
    
}
