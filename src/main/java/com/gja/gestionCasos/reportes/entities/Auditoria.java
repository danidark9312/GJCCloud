package com.gja.gestionCasos.reportes.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gja.gestionCasos.casos.entities.Caso;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "auditorias")
@NamedQueries({
    @NamedQuery(name = "Auditoria.findAll", query = "SELECT a FROM Auditoria a")})
public class Auditoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdauditoria")
    private Integer cdauditoria;
    @Basic(optional = false)
    @Column(name = "dsusuario")
    private String dsusuario;
    @Basic(optional = false)
    @Column(name = "feactualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feactualizacion;
    @Basic(optional = false)
    @Column(name = "dsjustificacion")
    private String dsjustificacion;
    @Basic(optional = false)
    @Column(name = "dstipoaccion")
    private String dstipoaccion;
    @JoinColumn(name = "cdcaso", referencedColumnName = "cdcaso")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Caso cdcaso;

    public Auditoria() {
    }

    public Auditoria(Integer cdauditoria) {
        this.cdauditoria = cdauditoria;
    }

    public Auditoria(Integer cdauditoria, String dsusuario, Date feactualizacion, String dsjustificacion, String dstipoaccion) {
        this.cdauditoria = cdauditoria;
        this.dsusuario = dsusuario;
        this.feactualizacion = feactualizacion;
        this.dsjustificacion = dsjustificacion;
        this.dstipoaccion = dstipoaccion;
    }

    public Integer getCdauditoria() {
        return cdauditoria;
    }

    public void setCdauditoria(Integer cdauditoria) {
        this.cdauditoria = cdauditoria;
    }

    public String getDsusuario() {
        return dsusuario;
    }

    public void setDsusuario(String dsusuario) {
        this.dsusuario = dsusuario;
    }

    public Date getFeactualizacion() {
        return feactualizacion;
    }

    public void setFeactualizacion(Date feactualizacion) {
        this.feactualizacion = feactualizacion;
    }

    public String getDsjustificacion() {
        return dsjustificacion;
    }

    public void setDsjustificacion(String dsjustificacion) {
        this.dsjustificacion = dsjustificacion;
    }

    public String getDstipoaccion() {
        return dstipoaccion;
    }

    public void setDstipoaccion(String dstipoaccion) {
        this.dstipoaccion = dstipoaccion;
    }

    public Caso getCdcaso() {
        return cdcaso;
    }

    public void setCdcaso(Caso cdcaso) {
        this.cdcaso = cdcaso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdauditoria != null ? cdauditoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auditoria)) {
            return false;
        }
        Auditoria other = (Auditoria) object;
        if ((this.cdauditoria == null && other.cdauditoria != null) || (this.cdauditoria != null && !this.cdauditoria.equals(other.cdauditoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Auditoria[ cdauditoria=" + cdauditoria + " ]";
    }
    
}
