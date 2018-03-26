/**
 * 
 */
package com.sf.roles;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Usuario
 *
 */
@Entity
@Table(name = "estados_roles")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class EstadoRol implements Serializable{

	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdestado")
    private Integer cdestado;

	@Basic(optional = false)
    @Column(name = "dsestado")
    private String dsestado;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "estado", fetch = FetchType.LAZY)
	private List<Rol> roles;
	
	public Integer getCdestado() {
		return cdestado;
	}

	public void setCdestado(Integer cdestado) {
		this.cdestado = cdestado;
	}

	public String getDsestado() {
		return dsestado;
	}

	public void setDsestado(String dsestado) {
		this.dsestado = dsestado;
	}

	public List<Rol> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

}