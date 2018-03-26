/**
 * 
 */
package com.gja.gestionCasos.maestros.entities;


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
import com.sf.social.signinmvc.user.model.User;
/**
 * @author USUARIO
 *
 */
@Entity
@Table(name = "estados_usuarios")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class EstadoUsuario implements Serializable{

	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdestado")
    private Integer cdestado;

	@Basic(optional = false)
    @Column(name = "dsestado")
    private String dsestado;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "activo", fetch = FetchType.LAZY)
	private List<User> usuarios;
		
	/**
	 * @return the cdestado
	 */
	public Integer getCdestado() {
		return cdestado;
	}

	/**
	 * @param cdestado the cdestado to set
	 */
	public void setCdestado(Integer cdestado) {
		this.cdestado = cdestado;
	}

	/**
	 * @return the dsestado
	 */
	public String getDsestado() {
		return dsestado;
	}

	/**
	 * @param dsestado the dsestado to set
	 */
	public void setDsestado(String dsestado) {
		this.dsestado = dsestado;
	}

	/**
	 * @return the usuarios
	 */
	public List<User> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

}
