package com.gja.gestionCasos.casos.entities;

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

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "abonos")
@NamedQueries({
    @NamedQuery(name = "Abono.findAll", query = "SELECT a FROM Abono a")})
public class Abono implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdabono")
    private Integer cdabono;
    @Basic(optional = false)
    @Column(name = "nmabono")
    private double nmabono;
    @Basic(optional = false)
    @Column(name = "feabono")
    @Temporal(TemporalType.DATE)
    private Date feabono;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String isactivo;
    @JoinColumn(name = "cdprestamo", referencedColumnName = "cdprestamo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Prestamo cdprestamo;

    public Abono() {
    }

    public Abono(Integer cdabono) {
        this.cdabono = cdabono;
    }

    public Abono(Integer cdabono, double nmabono, Date feabono, String isactivo) {
        this.cdabono = cdabono;
        this.nmabono = nmabono;
        this.feabono = feabono;
        this.isactivo = isactivo;
    }

    public Integer getCdabono() {
        return cdabono;
    }

    public void setCdabono(Integer cdabono) {
        this.cdabono = cdabono;
    }

    public double getNmabono() {
        return nmabono;
    }

    public void setNmabono(double nmabono) {
        this.nmabono = nmabono;
    }

    public Date getFeabono() {
        return feabono;
    }

    public void setFeabono(Date feabono) {
        this.feabono = feabono;
    }

    public String getIsactivo() {
        return isactivo;
    }

    public void setIsactivo(String isactivo) {
        this.isactivo = isactivo;
    }

    public Prestamo getCdprestamo() {
        return cdprestamo;
    }

    public void setCdprestamo(Prestamo cdprestamo) {
        this.cdprestamo = cdprestamo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdabono != null ? cdabono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abono)) {
            return false;
        }
        Abono other = (Abono) object;
        if ((this.cdabono == null && other.cdabono != null) || (this.cdabono != null && !this.cdabono.equals(other.cdabono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Abono[ cdabono=" + cdabono + " ]";
    }
    
}
