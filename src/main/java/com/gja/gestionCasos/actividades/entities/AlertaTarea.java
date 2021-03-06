package com.gja.gestionCasos.actividades.entities;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "alertas_tareas")
public class AlertaTarea implements Serializable {
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
    private Date feprimeraalerta;
    @Column(name = "FEULTIMAALERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaAlerta;
    @JoinColumn(name = "CDTAREA", referencedColumnName = "cdtareaactividad")
    @OneToOne(fetch = FetchType.EAGER)
    private TareaActividad tareaActividad;
    @Column(name = "NMALERTASTOTALES")
    private Integer totalAlertas;
    @Column(name = "NMENVIOSTOTALES")
    private Integer totalEnvios;

    public AlertaTarea() {
    }

    public AlertaTarea(Integer codigo) {
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

	public Date getFeprimeraalerta() {
		return feprimeraalerta;
	}

	public void setFeprimeraalerta(Date feprimeraalerta) {
		this.feprimeraalerta = feprimeraalerta;
	}

	public Date getFechaUltimaAlerta() {
		return fechaUltimaAlerta;
	}

	public void setFechaUltimaAlerta(Date fechaUltimaAlerta) {
		this.fechaUltimaAlerta = fechaUltimaAlerta;
	}

	public TareaActividad getTareaActividad() {
		return tareaActividad;
	}

	public void setTareaActividad(TareaActividad tareaActividad) {
		this.tareaActividad = tareaActividad;
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