package com.gja.gestionCasos.maestros.entities;
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
public class CiudadPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "cdciudad")
    private String codigoCiudad;
    @Basic(optional = false)
    @Column(name = "cddepartamento")
    private String codigoDepartamento;

    public CiudadPK() {
    }

    public CiudadPK(String codigoCiudad, String codigoDepartamento) {
        this.codigoCiudad = codigoCiudad;
        this.codigoDepartamento = codigoDepartamento;
    }

	public String getCodigoCiudad() {
		return codigoCiudad;
	}

	public void setCodigoCiudad(String codigoCiudad) {
		this.codigoCiudad = codigoCiudad;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}



    

 
    
}
