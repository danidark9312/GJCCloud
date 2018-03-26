/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sf.social.signinmvc.user.model;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Desarrollador3
 */
@Entity
@Table(name = "autitoria_change_password")
public class ChangePassword implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ChangePasswordPK changePasswordPK;
    
    @Transient
    private String nuevaContrasenia;
    
    @Transient
    private String confirmarcontrasenia;
    
    @Basic(optional = false)
    @Column(name = "SNESTADO")
    private String usado;
    
    public ChangePassword() {
    }

    public ChangePassword(ChangePasswordPK changePasswordPK) {
        this.changePasswordPK = changePasswordPK;
    }

    public ChangePassword(String dstoken, String dsemail) {
        this.changePasswordPK = new ChangePasswordPK(dstoken, dsemail);
    }

    public ChangePasswordPK getChangePasswordPK() {
        return changePasswordPK;
    }

    public void setChangePasswordPK(ChangePasswordPK changePasswordPK) {
        this.changePasswordPK = changePasswordPK;
    }
    
    

    public String getNuevaContrasenia() {
		return nuevaContrasenia;
	}

	public void setNuevaContrasenia(String nuevaContrasenia) {
		this.nuevaContrasenia = nuevaContrasenia;
	}

	public String getConfirmarcontrasenia() {
		return confirmarcontrasenia;
	}

	public void setConfirmarcontrasenia(String confirmarcontrasenia) {
		this.confirmarcontrasenia = confirmarcontrasenia;
	}

	public String getUsado() {
		return usado;
	}

	public void setUsado(String usado) {
		this.usado = usado;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (changePasswordPK != null ? changePasswordPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChangePassword)) {
            return false;
        }
        ChangePassword other = (ChangePassword) object;
        if ((this.changePasswordPK == null && other.changePasswordPK != null) || (this.changePasswordPK != null && !this.changePasswordPK.equals(other.changePasswordPK))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "julio.ChangePassword[ changePasswordPK=" + changePasswordPK + " ]";
//    }
//    
}
