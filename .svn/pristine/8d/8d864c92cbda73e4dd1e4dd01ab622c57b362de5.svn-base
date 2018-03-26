package com.sf.roles;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sf.social.signinmvc.user.model.User;

/**
 *
 * @author Diego Blandón
 */
@Entity
@Table(name = "roles_usuarios")
@XmlRootElement
public class RolesUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RolesUsuariosPK rolesUsuariosPK;
    @Column(name = "FEASIGNACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feasignacion;
    @JoinColumn(name = "CDUSUARIO", referencedColumnName = "CDUSUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User usuario;
    @JoinColumn(name = "CDROL", referencedColumnName = "CDROL", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Rol rol;

    public RolesUsuarios() {
    }

    public RolesUsuarios(RolesUsuariosPK rolesUsuariosPK) {
        this.rolesUsuariosPK = rolesUsuariosPK;
    }

    public RolesUsuarios(String cdusuario, int cdrol) {
        this.rolesUsuariosPK = new RolesUsuariosPK(cdusuario, cdrol);
    }

	public RolesUsuariosPK getRolesUsuariosPK() {
		return rolesUsuariosPK;
	}

	public void setRolesUsuariosPK(RolesUsuariosPK rolesUsuariosPK) {
		this.rolesUsuariosPK = rolesUsuariosPK;
	}

	public Date getFeasignacion() {
		return feasignacion;
	}

	public void setFeasignacion(Date feasignacion) {
		this.feasignacion = feasignacion;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
    
}
