package com.gja.gestionCasos.casos.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gja.gestionCasos.maestros.entities.EntidadFinanciera;
import com.gja.gestionCasos.maestros.entities.TipoCuenta;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdprestamo")
    private Integer codigoPrestamo;
    @Column(name = "dsprestamo")
    private String descripcionPrestamo;
    @Basic(optional = false)
    @Column(name = "feprestamo")
    @Temporal(TemporalType.DATE)
    private Date fechaPrestamo;
    @Basic(optional = false)
    @Column(name = "nmmonto")
    private Double monto;
    @Basic(optional = false)
    @Column(name = "nmporcentajeinteres")
    private Float porcentajeInteres;
    @Basic(optional = false)
    @Column(name = "nmintereses")
    private Double intereses;
    @Basic(optional = false)
    @Column(name = "nmsaldo")
    private Double saldo;
	@Basic(optional = false)
    @Column(name = "nmcuenta")
    private String numeroCuenta;
    @Basic(optional = false)
    @Column(name = "dstitulo")
    private String titulo;
    @Column(name = "dsarchivo")
    private String archivo;
    @Basic(optional = false)
    @Column(name = "isactivo")
    private String activo;
    @Basic(optional = false)
    @Column(name = "iscancelado")
    private String cancelado;
    @Basic(optional = false)
    @Column(name = "cdusuariocreacion")
    private String usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "cdusuarioultimamodificacion")
    private String usuarioUltimaModificacion;
    @Basic(optional = false)
    @Column(name = "fecreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "feusuarioultimamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaModificacion;
    @JoinColumn(name = "cdentidadfinaciera", referencedColumnName = "cdentidadfinaciera")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntidadFinanciera entidadFinaciera;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prestamo", fetch = FetchType.LAZY)
    private Set<Abono> abono;
    @Column(name = "cdidentificacion")
    private String identificacion;
    @Column(name = "dsnombredeudor")
    private String nombreDeudor;
    @JoinColumn(name = "cdcaso", referencedColumnName = "cdcaso")
    @ManyToOne(fetch = FetchType.LAZY)
	private Caso caso;
	@JoinColumn(name = "cdtipocuenta", referencedColumnName = "cdtipocuenta")
	@ManyToOne(fetch = FetchType.EAGER)
	private TipoCuenta tipoCuenta;

    public Prestamo() {
    }

    public Prestamo(Integer codigoPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
    }



	public Prestamo(Integer codigoPrestamo, String descripcionPrestamo,
			Date fechaPrestamo, double monto, float porcentajeInteres,
			double intereses, double saldo, String numeroCuenta, String tipocuenta,
			String titulo, String archivo, String activo, String cancelado,
			String usuarioCreacion, String usuarioUltimaModificacion,
			Date fechaCreacion, Date fechaUltimaModificacion) {
	
		this.codigoPrestamo = codigoPrestamo;
		this.descripcionPrestamo = descripcionPrestamo;
		this.fechaPrestamo = fechaPrestamo;
		this.monto = monto;
		this.porcentajeInteres = porcentajeInteres;
		this.intereses = intereses;
		this.saldo = saldo;
		this.numeroCuenta = numeroCuenta;
		this.titulo = titulo;
		this.archivo = archivo;
		this.activo = activo;
		this.cancelado = cancelado;
		this.usuarioCreacion = usuarioCreacion;
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Integer getCodigoPrestamo() {
		return codigoPrestamo;
	}

	public void setCodigoPrestamo(Integer codigoPrestamo) {
		this.codigoPrestamo = codigoPrestamo;
	}

	public String getDescripcionPrestamo() {
		return descripcionPrestamo;
	}

	public void setDescripcionPrestamo(String descripcionPrestamo) {
		this.descripcionPrestamo = descripcionPrestamo;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getCancelado() {
		return cancelado;
	}

	public void setCancelado(String cancelado) {
		this.cancelado = cancelado;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioUltimaModificacion() {
		return usuarioUltimaModificacion;
	}

	public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public EntidadFinanciera getEntidadFinaciera() {
		return entidadFinaciera;
	}

	public void setEntidadFinaciera(EntidadFinanciera entidadFinaciera) {
		this.entidadFinaciera = entidadFinaciera;
	}

	public Set<Abono> getAbono() {
		return abono;
	}

	public void setAbono(Set<Abono> abono) {
		this.abono = abono;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombreDeudor() {
		return nombreDeudor;
	}

	public void setNombreDeudor(String nombreDeudor) {
		this.nombreDeudor = nombreDeudor;
	}

	public Caso getCaso() {
		return caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}
    public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Float getPorcentajeInteres() {
		return porcentajeInteres;
	}

	public void setPorcentajeInteres(Float porcentajeInteres) {
		this.porcentajeInteres = porcentajeInteres;
	}

	public Double getIntereses() {
		return intereses;
	}

	public void setIntereses(Double intereses) {
		this.intereses = intereses;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
}
