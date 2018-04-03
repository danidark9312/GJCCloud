package com.gja.gestionCasos.maestros.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;

@Entity
@Table(name = "tipodocumento")
@NamedQueries({
    @NamedQuery(name = "TipoDocumento.findAll", query = "SELECT p FROM TipoDocumento p")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class TipoDocumento implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "DSDOCUMENTO")
    private String documento;
    
    public TipoDocumento() {    	
    }
    
    public TipoDocumento(Integer codigo){
    	this.codigo = codigo;
    }
    
    public TipoDocumento(Integer codigo, String documento) {
        this.codigo = codigo;
        this.documento = documento;        
    }
    
    public Integer getCodigo() {
		return codigo;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
    
    
}
