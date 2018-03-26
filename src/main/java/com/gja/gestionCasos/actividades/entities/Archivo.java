package com.gja.gestionCasos.actividades.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
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

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "archivos")
@NamedQueries({
    @NamedQuery(name = "Archivo.findAll", query = "SELECT a FROM Archivo a")})
public class Archivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdarchivo")
    private Integer cdarchivo;
    @Basic(optional = false)
    @Column(name = "dsarchivo")
    private String dsarchivo;
    @Basic(optional = false)
    @Column(name = "dsruta")
    private String dsruta;
    @JoinColumn(name = "cdtareaparticular", referencedColumnName = "cdtareaparticular")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TareaParticularCaso cdtareaparticular;

    public Archivo() {
    }

    public Archivo(Integer cdarchivo) {
        this.cdarchivo = cdarchivo;
    }

    public Archivo(Integer cdarchivo, String dsarchivo, String dsruta) {
        this.cdarchivo = cdarchivo;
        this.dsarchivo = dsarchivo;
        this.dsruta = dsruta;
    }

    public Integer getCdarchivo() {
        return cdarchivo;
    }

    public void setCdarchivo(Integer cdarchivo) {
        this.cdarchivo = cdarchivo;
    }

    public String getDsarchivo() {
        return dsarchivo;
    }

    public void setDsarchivo(String dsarchivo) {
        this.dsarchivo = dsarchivo;
    }

    public String getDsruta() {
        return dsruta;
    }

    public void setDsruta(String dsruta) {
        this.dsruta = dsruta;
    }

    public TareaParticularCaso getCdtareaparticular() {
        return cdtareaparticular;
    }

    public void setCdtareaparticular(TareaParticularCaso cdtareaparticular) {
        this.cdtareaparticular = cdtareaparticular;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdarchivo != null ? cdarchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Archivo)) {
            return false;
        }
        Archivo other = (Archivo) object;
        if ((this.cdarchivo == null && other.cdarchivo != null) || (this.cdarchivo != null && !this.cdarchivo.equals(other.cdarchivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Archivo[ cdarchivo=" + cdarchivo + " ]";
    }
    
}
