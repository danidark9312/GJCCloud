package com.gja.gestionCasos.filters;

import java.util.Date;
import java.util.List;

import com.sf.util.Utilidades;

public class CasoFiltro extends GeneralFilter {

	private String codigoCaso;
	private String estadoCaso;
	private String tipoCaso;
	private String estadoProcesal;
	
	
	//Filtros De reportes
	private String audienciaFiltro;
	private String prejudicialFiltro;
	private String recursosPendientesFiltro;
	
	private String estadoTareaFiltro;
	private String nombreCasoFiltro;
	private String victimaFiltro;
	private String radicadoFiltro;
	private String demandadoFiltro;
	private Date fechaInicioFiltro;
	private Date fechaFinFiltro;
	private List<String> responsablesFiltro;
	private String ordenarPor;
	private String nombreCliente;
	private Integer filtroDias;
	private Integer codigoEquipoCaso;
	
	
	public Integer getCodigoEquipoCaso() {
		return codigoEquipoCaso;
	}

	public void setCodigoEquipoCaso(Integer codigoEquipoCaso) {
		this.codigoEquipoCaso = codigoEquipoCaso;
	}

	public Integer getFiltroDias() {
		return filtroDias;
	}

	public void setFiltroDias(Integer filtroDias) {
		this.filtroDias = filtroDias;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = Utilidades.decodificarCaracteres(nombreCliente);
	}

	public String getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(String ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public String getEstadoProcesal() {
		return estadoProcesal;
	}

	public void setEstadoProcesal(String estadoProcesal) {
		this.estadoProcesal = estadoProcesal;
	}

	public String getCodigoCaso() {
		return codigoCaso;
	}

	public void setCodigoCaso(String codigoCaso) {
		this.codigoCaso = codigoCaso;
	}

	public String getEstadoCaso() {
		return estadoCaso;
	}

	public void setEstadoCaso(String estadoCaso) {
		this.estadoCaso = estadoCaso;
	}

	public String getTipoCaso() {
		return tipoCaso;
	}

	public void setTipoCaso(String tipoCaso) {
		this.tipoCaso = tipoCaso;
	}

	public String getRecursosPendientesFiltro() {
		return recursosPendientesFiltro;
	}

	public void setRecursosPendientesFiltro(String recursosPendientesFiltro) {
		this.recursosPendientesFiltro = recursosPendientesFiltro;
	}

	public String getAudienciaFiltro() {
		return audienciaFiltro;
	}

	public void setAudienciaFiltro(String audienciaFiltro) {
		this.audienciaFiltro = audienciaFiltro;
	}

	public String getEstadoTareaFiltro() {
		return estadoTareaFiltro;
	}

	public void setEstadoTareaFiltro(String estadoTareaFiltro) {
		this.estadoTareaFiltro = estadoTareaFiltro;
	}

	public String getNombreCasoFiltro() {
		return nombreCasoFiltro;
	}

	public void setNombreCasoFiltro(String nombreCasoFiltro) {
		this.nombreCasoFiltro = Utilidades.decodificarCaracteres(nombreCasoFiltro);
	}

	public String getVictimaFiltro() {
		return victimaFiltro;
	}

	public void setVictimaFiltro(String victimaFiltro) {
		this.victimaFiltro = Utilidades.decodificarCaracteres(victimaFiltro);
	}

	public String getRadicadoFiltro() {
		return radicadoFiltro;
	}

	public void setRadicadoFiltro(String radicadoFiltro) {
		this.radicadoFiltro = Utilidades.decodificarCaracteres(radicadoFiltro);
	}
	
	public String getDemandadoFiltro() {
		return demandadoFiltro;
	}

	public void setDemandadoFiltro(String demandadoFiltro) {
		this.demandadoFiltro = Utilidades.decodificarCaracteres(demandadoFiltro);
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

	public List<String> getResponsablesFiltro() {
		return responsablesFiltro;
	}

	public void setResponsablesFiltro(List<String> responsablesFiltro) {
		this.responsablesFiltro = responsablesFiltro;
	}

	public String getPrejudicialFiltro() {
		return prejudicialFiltro;
	}

	public void setPrejudicialFiltro(String prejudicialFiltro) {
		this.prejudicialFiltro = Utilidades.decodificarCaracteres(prejudicialFiltro);
	}
}
