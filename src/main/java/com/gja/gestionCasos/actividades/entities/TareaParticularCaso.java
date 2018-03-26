package com.gja.gestionCasos.actividades.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.apache.poi.util.ArrayUtil;

import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "tareas_particulares_caso")
@NamedQueries({
    @NamedQuery(name = "TareaParticularCaso.findAll", query = "SELECT t FROM TareaParticularCaso t")})
public class TareaParticularCaso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdtareaparticular")
    private Integer codigoTarea;
    @Basic(optional = false)
    @Column(name = "dstarea")
    private String tarea;
    @Basic(optional = false)
    @Column(name = "dsdetalle")
    private String detalle;
    @Basic(optional = false)
    @Column(name = "felimite")
    @Temporal(TemporalType.DATE)
    private Date fechaLimite;
    @Transient
    private String fechaLimiteString;
    @Basic(optional = false)
    @Column(name = "isfinalizada")
    private String finalizada;
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
    @Basic(optional = false)
    @Column(name = "snactiva")
    private String snActiva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareasParticularesCaso", fetch = FetchType.LAZY)
    private List<ResponsableTarea> responsablesTareas;
    @JoinColumn(name = "cdactividadparticular", referencedColumnName = "cdactividadparticular")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private ActividadCaso actividadParticular;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdtareaparticular", fetch = FetchType.LAZY)
    private Set<Archivo> archivoSet;
    @OneToOne(mappedBy = "tareaParticularCaso", fetch = FetchType.EAGER)
    private AlertaTareaParticular alerta;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareaparticular")
    private Collection<AlertaTareaRol> alertasTareasRolCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareaParticular")
    private List<AlertaTareaVencidaRol> alertasTareasDespuesRolList;
    
    @Column(name = "nmdiasantes")
    private Integer numeroDiasAntesAlertas;
    
    @Column(name = "nmalertaspordia")
    private Integer numeroAlertasPorDia;
    
    
    @Column(name = "sntareapropia")
    private String esTareaPropia;

    public TareaParticularCaso() {
    }

    public TareaParticularCaso(Integer codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    
    public Collection<AlertaTareaRol> getAlertasTareasRolCollection() {
		return alertasTareasRolCollection;
	}

	public void setAlertasTareasRolCollection(Collection<AlertaTareaRol> alertasTareasRolCollection) {
		this.alertasTareasRolCollection = alertasTareasRolCollection;
	}

	public TareaParticularCaso(TareaParticularCaso tarea) {
    	this.codigoTarea = tarea.getCodigoTarea();
    	this.tarea = tarea.getTarea();
		this.detalle = tarea.getDetalle();
		this.fechaLimite = tarea.getFechaLimite();
		this.finalizada = tarea.getFinalizada();
		this.usuarioCreacion = tarea.getUsuarioCreacion();
		this.usuarioUltimaModificacion = tarea.getUsuarioUltimaModificacion();
		this.fechaCreacion = tarea.getFechaCreacion();
		this.fechaUltimaModificacion = tarea.getFechaUltimaModificacion();
		this.numeroAlertasPorDia = tarea.getNumeroAlertasPorDia();
		this.numeroDiasAntesAlertas = tarea.getNumeroDiasAntesAlertas();
		this.esTareaPropia = tarea.getEsTareaPropia();
		this.snActiva = tarea.getSnActiva();
		
		List<ResponsableTarea> responsables = new ArrayList<ResponsableTarea>();
		for (ResponsableTarea responsable:tarea.getResponsablesTareas()) {
			responsables.add((ResponsableTarea)SerializationUtils.clone(responsable));
		}
		this.responsablesTareas = responsables;
		
    }
    
	public List<AlertaTareaVencidaRol> getAlertasTareasDespuesRolList() {
		return alertasTareasDespuesRolList;
	}

	public void setAlertasTareasDespuesRolList(List<AlertaTareaVencidaRol> alertasTareasDespuesRolList) {
		this.alertasTareasDespuesRolList = alertasTareasDespuesRolList;
	}

	public TareaParticularCaso(Integer codigoTarea, String tarea,
			String detalle, Date fechaLimite, String finalizada,
			String usuarioCreacion, String usuarioUltimaModificacion,
			Date fechaCreacion, Date fechaUltimaModificacion,Integer numeroDiasAntesAlertas,Integer numeroAlertasPorDia,String esTareaPropia) {
		super();
		this.codigoTarea = codigoTarea;
		this.tarea = tarea;
		this.detalle = detalle;
		this.fechaLimite = fechaLimite;
		this.finalizada = finalizada;
		this.usuarioCreacion = usuarioCreacion;
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.numeroAlertasPorDia = numeroAlertasPorDia;
		this.numeroDiasAntesAlertas = numeroDiasAntesAlertas;
		this.esTareaPropia = esTareaPropia;
		
	}

	public Integer getCodigoTarea() {
		return codigoTarea;
	}

	public void setCodigoTarea(Integer codigoTarea) {
		this.codigoTarea = codigoTarea;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	
	

	public String getFechaLimiteString() {
		return fechaLimiteString;
	}

	public void setFechaLimiteString(String fechaLimiteString) {
		this.fechaLimiteString = fechaLimiteString;
	}

	public String getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(String finalizada) {
		this.finalizada = finalizada;
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
	public ActividadCaso getActividadParticular() {
		return actividadParticular;
	}

	public void setActividadParticular(ActividadCaso actividadParticular) {
		this.actividadParticular = actividadParticular;
	}

	public Set<Archivo> getArchivoSet() {
		return archivoSet;
	}

	public void setArchivoSet(Set<Archivo> archivoSet) {
		this.archivoSet = archivoSet;
	}
	public Integer getNumeroDiasAntesAlertas() {
		return numeroDiasAntesAlertas;
	}

	public void setNumeroDiasAntesAlertas(Integer numeroDiasAntesAlertas) {
		this.numeroDiasAntesAlertas = numeroDiasAntesAlertas;
	}

	public Integer getNumeroAlertasPorDia() {
		return numeroAlertasPorDia;
	}

	public void setNumeroAlertasPorDia(Integer numeroAlertasPorDia) {
		this.numeroAlertasPorDia = numeroAlertasPorDia;
	}

	public String getEsTareaPropia() {
		return esTareaPropia;
	}

	public void setEsTareaPropia(String esTareaPropia) {
		this.esTareaPropia = esTareaPropia;
	}

	public List<ResponsableTarea> getResponsablesTareas() {
		return responsablesTareas;
	}

	public void setResponsablesTareas(List<ResponsableTarea> responsablesTareas) {
		this.responsablesTareas = responsablesTareas;
	}

	public String getSnActiva() {
		return snActiva;
	}

	public void setSnActiva(String snActiva) {
		this.snActiva = snActiva;
	}

	public AlertaTareaParticular getAlerta() {
		return alerta;
	}

	public void setAlerta(AlertaTareaParticular alerta) {
		this.alerta = alerta;
	}
	
	
   	public TareaParticularCaso clonarTarea() {
   		TareaParticularCaso nuevoObjetoTarea = new TareaParticularCaso();
   		nuevoObjetoTarea.setActividadParticular(this.getActividadParticular());
   		nuevoObjetoTarea.setAlerta(this.getAlerta());
   		nuevoObjetoTarea.setArchivoSet(this.getArchivoSet());
   		nuevoObjetoTarea.setDetalle(this.getDetalle());
   		nuevoObjetoTarea.setEsTareaPropia(this.getEsTareaPropia());
   		nuevoObjetoTarea.setFechaCreacion(this.getFechaCreacion());
   		nuevoObjetoTarea.setFechaLimite(this.getFechaLimite());
   		nuevoObjetoTarea.setFechaUltimaModificacion(this.getFechaUltimaModificacion());
   		nuevoObjetoTarea.setFinalizada(this.getFinalizada());
   		nuevoObjetoTarea.setNumeroAlertasPorDia(this.getNumeroAlertasPorDia());
   		nuevoObjetoTarea.setNumeroDiasAntesAlertas(this.getNumeroDiasAntesAlertas());
   		nuevoObjetoTarea.setResponsablesTareas(this.getResponsablesTareas());
   		nuevoObjetoTarea.setSnActiva(this.getSnActiva());
   		nuevoObjetoTarea.setTarea(this.getTarea());
   		nuevoObjetoTarea.setUsuarioCreacion(this.getUsuarioCreacion());
   		nuevoObjetoTarea.setUsuarioUltimaModificacion(this.getUsuarioUltimaModificacion());
   		return nuevoObjetoTarea;
   	}
	
}
 