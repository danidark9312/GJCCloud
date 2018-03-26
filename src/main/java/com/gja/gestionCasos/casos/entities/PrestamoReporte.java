package com.gja.gestionCasos.casos.entities;

public class PrestamoReporte {
	
	private Double totalPrestamo;
	private Double totalIntereses;
	private Double totalPagado;
	private Double saldoCapital;
	private Double saldoInteres;
	private Double saldoTotal;
	public Double getTotalPrestamo() {
		return totalPrestamo;
	}
	public void setTotalPrestamo(Double totalPrestamo) {
		this.totalPrestamo = totalPrestamo;
	}
	public Double getTotalIntereses() {
		return totalIntereses;
	}
	public void setTotalIntereses(Double totalIntereses) {
		this.totalIntereses = totalIntereses;
	}
	public Double getTotalPagado() {
		return totalPagado;
	}
	public void setTotalPagado(Double totalPagado) {
		if (totalPagado == null)
			this.totalPagado = 0.0;
		else
			this.totalPagado = totalPagado;
	}
	
	public Double getSaldoCapital() {
		return saldoCapital;
	}
	public void setSaldoCapital(Double saldoCapital) {
		if(saldoCapital==null)
			this.saldoCapital = 0.0;
		else
			this.saldoCapital = saldoCapital;
	}
	public Double getSaldoInteres() {
		return saldoInteres;
	}

	public void setSaldoInteres(Double saldoInteres) {
		if (saldoInteres == null)
			this.saldoInteres = 0.0;
		else
			this.saldoInteres = saldoInteres;
	}
	public Double getSaldoTotal() {
		return saldoTotal;
	}
	public void setSaldoTotal(Double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}
	
	
}
