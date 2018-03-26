package com.gja.gestionCasos.casos.entities;

import static com.sf.util.Utilidades.empty;

import java.util.Date;

public class ParametroReportePrestamo {
	private String nombreCasoFiltro;
	private String identificadorDeudor;
	private String nombreDeudor;
	private Date fechaInicioFiltro;
	private Date fechaFinFiltro;
	public String getnombreCasoFiltro() {
		return nombreCasoFiltro;
	}
	public void setnombreCasoFiltro(String nombreCasoFiltro) {
		this.nombreCasoFiltro = nombreCasoFiltro;
	}
	public String getIdentificadorDeudor() {
		return identificadorDeudor;
	}
	public void setIdentificadorDeudor(String identificadorDeudor) {
		this.identificadorDeudor = identificadorDeudor;
	}
	public String getNombreDeudor() {
		return nombreDeudor;
	}
	public void setNombreDeudor(String nombreDeudor) {
		this.nombreDeudor = nombreDeudor;
	}
	public Date getFechaInicioFiltro() {
		return fechaInicioFiltro;
	}
	public void setFechaInicioFiltro(Date fechaInicioFiltro) {
		this.fechaInicioFiltro = fechaInicioFiltro;
	}
	public Date getFechaFinFiltro() {
		return fechaFinFiltro;
	}
	public void setFechaFinFiltro(Date fechaFinFiltro) {
		this.fechaFinFiltro = fechaFinFiltro;
	}
	
	public boolean hasFiltros() {
		if(this.fechaInicioFiltro!=null && this.fechaFinFiltro!=null) {
			return true;
		}else if(!empty(this.nombreCasoFiltro))
			return true;
		else if(!empty(this.identificadorDeudor)) 
			return true;
		else if(!empty(this.nombreDeudor))
			return true;
		return false;
	}
	

}
