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
    private Integer codigo;
    
    @JoinColumn(name = "cdprestamo", referencedColumnName = "cdprestamo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Prestamo prestamo;
    
    @Basic(optional = false)
    @Column(name = "feabono")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Basic(optional = false)
    @Column(name = "vlabonocapital")
    private double abonoCapital;
    
    @Basic(optional = false)
    @Column(name = "vlabonointeres")
    private double abonoInteres;
    
    @Basic(optional = false)
    @Column(name = "vlsaldocapital")
    private double saldoCapital;
    
    @Basic(optional = false)
    @Column(name = "vlsaldointeres")
    private double saldoInteres;
    
    @Column(name = "dsabono")
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String isactivo;

    @Basic(optional = false)
    @Column(name = "cdprestamo", insertable=false,updatable=false)
    private Integer codigoPrestamo;
    
    
    
	public Abono(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigoPrestamo() {
		return codigoPrestamo;
	}

	public void setCodigoPrestamo(Integer codigoPrestamo) {
		this.codigoPrestamo = codigoPrestamo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getAbonoCapital() {
		return abonoCapital;
	}

	public void setAbonoCapital(double abonoCapital) {
		this.abonoCapital = abonoCapital;
	}

	public double getAbonoInteres() {
		return abonoInteres;
	}

	public void setAbonoInteres(double abonoInteres) {
		this.abonoInteres = abonoInteres;
	}

	public double getSaldoCapital() {
		return saldoCapital;
	}

	public void setSaldoCapital(double saldoCapital) {
		this.saldoCapital = saldoCapital;
	}

	public double getSaldoInteres() {
		return saldoInteres;
	}

	public void setSaldoInteres(double saldoInteres) {
		this.saldoInteres = saldoInteres;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIsactivo() {
		return isactivo;
	}

	public void setIsactivo(String isactivo) {
		this.isactivo = isactivo;
	}

	public Abono() {
		super();
	}


    
}
