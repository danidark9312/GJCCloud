package com.gja.gestionCasos.casos.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

/**
 *
 * @author DGM
 */
@Embeddable
public class RadicadoAcumuladoPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="cdradicado", referencedColumnName="cdradicado"),
        @JoinColumn(name="cdcaso", referencedColumnName="cdcaso")
    })
    private Radicado radicado;
    
    @Column(name = "cdradicadoacumulado")
    private String codigoRadicadoAcumulado;
    
    

	public RadicadoAcumuladoPK(Radicado radicado, String codigoRadicadoAcumulado) {
		super();
		this.radicado = radicado;
		this.codigoRadicadoAcumulado = codigoRadicadoAcumulado;
	}
	public RadicadoAcumuladoPK(int codigoCaso, String codigoRadicado, String codigoRadicadoAcumulado) {
		super();
		this.radicado = new Radicado(new RadicadoPK(codigoRadicado, codigoCaso));
		this.codigoRadicadoAcumulado = codigoRadicadoAcumulado;
	}

	public String getCodigoRadicadoAcumulado() {
		return codigoRadicadoAcumulado;
	}

	public void setCodigoRadicadoAcumulado(String codigoRadicadoAcumulado) {
		this.codigoRadicadoAcumulado = codigoRadicadoAcumulado;
	}

	public Radicado getRadicado() {
		return radicado;
	}

	public void setRadicado(Radicado radicado) {
		this.radicado = radicado;
	}


	public RadicadoAcumuladoPK() {
		super();
	}
	

    
}
