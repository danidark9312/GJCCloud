package com.gja.gestionCasos.actividades.entities;




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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "alertas_actividades")
public class AlertaActividad implements Serializable {
	
	
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CDALERTA")
    private Integer codigo;
    @Column(name = "NMALERTASDIARIAS")
    private Integer numeroAlertasTotales;
    @Column(name = "NMALERTASENVIADAS")
    private Integer numeroAlertasEnviadas;
    @Column(name = "FEPRIMERAALERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPrimeraAlerta;
    @Column(name = "FEULTIMAALERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaAlerta;
    @JoinColumn(name = "CDACTIVIDAD", referencedColumnName = "cdactividad")
    @OneToOne(fetch = FetchType.EAGER)
    private Actividad actividad;
    @Column(name = "NMALERTASTOTALES")
    private Integer totalAlertas;
    @Column(name = "NMENVIOSTOTALES")
    private Integer totalEnvios;
    
    
    public AlertaActividad() {
	}
    
    public AlertaActividad(Integer codigo) {
        this.codigo = codigo;
    }
    
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getNumeroAlertasTotales() {
		return numeroAlertasTotales;
	}
	public void setNumeroAlertasTotales(Integer numeroAlertasTotales) {
		this.numeroAlertasTotales = numeroAlertasTotales;
	}
	public Integer getNumeroAlertasEnviadas() {
		return numeroAlertasEnviadas;
	}
	public void setNumeroAlertasEnviadas(Integer numeroAlertasEnviadas) {
		this.numeroAlertasEnviadas = numeroAlertasEnviadas;
	}
	public Date getFechaPrimeraAlerta() {
		return fechaPrimeraAlerta;
	}
	public void setFechaPrimeraAlerta(Date fechaPrimeraAlerta) {
		this.fechaPrimeraAlerta = fechaPrimeraAlerta;
	}
	public Date getFechaUltimaAlerta() {
		return fechaUltimaAlerta;
	}
	public void setFechaUltimaAlerta(Date fechaUltimaAlerta) {
		this.fechaUltimaAlerta = fechaUltimaAlerta;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Integer getTotalAlertas() {
		return totalAlertas;
	}

	public void setTotalAlertas(Integer totalAlertas) {
		this.totalAlertas = totalAlertas;
	}

	public Integer getTotalEnvios() {
		return totalEnvios;
	}

	public void setTotalEnvios(Integer totalEnvios) {
		this.totalEnvios = totalEnvios;
	}
}