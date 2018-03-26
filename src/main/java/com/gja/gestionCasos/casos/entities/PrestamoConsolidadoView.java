package com.gja.gestionCasos.casos.entities;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *Vista Relacionada con el reporte de prestamos
 */
/**
 * @author daniel
 *
 */
@Entity
@Table(name = "prestamoconsolidadoview")

public class PrestamoConsolidadoView{
 
	@Id
	@Basic(optional = false)
	private Integer codigoPrestamo;
	
	
	@Column(name = "nombreCaso")
    private String nombreCaso;
	@Column(name = "identificacionDeudor")
	private String identificacionDeudor;
	@Column(name = "nombreDeudor")
    private String nombreDeudor;
	@Column(name = "valorPrestamo")
    private Double valorprestamo;
	@Column(name = "fechaPrestamo")
    private Date fechaprestamo;
	@Column(name = "interes")
    private Float interes;
	@Column(name = "abonoCapital")
    private Double abonoCapital;
	@Column(name = "abonoInteres")
    private Double abonoInteres;
	@Column(name = "saldoCapital")
    private Double saldoCapital;
	@Column(name = "saldoInteres")
    private Double saldoInteres;
	@Column(name = "fechaUltimoAbono")
    private Date fechaUltimoAbono;
	public Integer getCodigoPrestamo() {
		return codigoPrestamo;
	}
	public void setCodigoPrestamo(Integer codigoPrestamo) {
		this.codigoPrestamo = codigoPrestamo;
	}
	public String getNombreCaso() {
		return nombreCaso;
	}
	public void setNombreCaso(String nombreCaso) {
		this.nombreCaso = nombreCaso;
	}
	public String getIdentificacionDeudor() {
		return identificacionDeudor;
	}
	public void setIdentificacionDeudor(String identificacionDeudor) {
		this.identificacionDeudor = identificacionDeudor;
	}
	public String getNombreDeudor() {
		return nombreDeudor;
	}
	public void setNombreDeudor(String nombreDeudor) {
		this.nombreDeudor = nombreDeudor;
	}
	public double getValorprestamo() {
		return valorprestamo;
	}
	public void setValorprestamo(double valorprestamo) {
		this.valorprestamo = valorprestamo;
	}
	public Date getFechaprestamo() {
		return fechaprestamo;
	}
	public void setFechaprestamo(Date fechaprestamo) {
		this.fechaprestamo = fechaprestamo;
	}
	public Float getInteres() {
		return interes;
	}
	public void setInteres(Float interes) {
		this.interes = interes;
	}
	public Double getAbonoCapital() {
		return abonoCapital;
	}
	public void setAbonoCapital(Double abonoCapital) {
		this.abonoCapital = abonoCapital;
	}
	public Double getAbonoInteres() {
		return abonoInteres;
	}
	public void setAbonoInteres(Double abonoInteres) {
		this.abonoInteres = abonoInteres;
	}
	public Double getSaldoCapital() {
		return saldoCapital;
	}
	public void setSaldoCapital(Double saldoCapital) {
		this.saldoCapital = saldoCapital;
	}
	public Double getSaldoInteres() {
		return saldoInteres;
	}
	public void setSaldoInteres(Double saldoInteres) {
		this.saldoInteres = saldoInteres;
	}
	public Date getFechaUltimoAbono() {
		return fechaUltimoAbono;
	}
	public void setFechaUltimoAbono(Date fechaUltimoAbono) {
		this.fechaUltimoAbono = fechaUltimoAbono;
	}
	


}
