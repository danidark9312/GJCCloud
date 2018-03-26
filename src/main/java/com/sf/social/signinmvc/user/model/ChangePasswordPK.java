/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sf.social.signinmvc.user.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Desarrollador3
 */
@Embeddable
public class ChangePasswordPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "DSTOKEN")
    private String token;
    @Basic(optional = false)
    @Column(name = "DSEMAIL")
    private String email;

    public ChangePasswordPK() {
    }

    public ChangePasswordPK(String dstoken, String dsemail) {
        this.token = dstoken;
        this.email = dsemail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (token != null ? token.hashCode() : 0);
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChangePasswordPK)) {
            return false;
        }
        ChangePasswordPK other = (ChangePasswordPK) object;
        if ((this.token == null && other.token != null) || (this.token != null && !this.token.equals(other.token))) {
            return false;
        }
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "julio.ChangePasswordPK[ dstoken=" + dstoken + ", dsemail=" + dsemail + " ]";
//    }
    
}
