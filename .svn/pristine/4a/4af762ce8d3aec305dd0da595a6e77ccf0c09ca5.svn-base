package com.gja.gestionCasos.reportes.entities;


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
import javax.persistence.Transient;

import com.gja.gestionCasos.casos.entities.Caso;
import com.sf.roles.UsuarioWrapper;
import com.sf.social.signinmvc.user.model.User;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "justificaciones")

public class Justificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdjustificaciones")
    private Integer codigoJustificacion;
    @Column(name = "dstipoaccion")
    private String tipoAccion;
    @Column(name = "dscampomodificado")
    private String campoModificado;
    @Basic(optional = false)
    @Column(name = "femodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "dsinformacionelimada")
    private String informacionEliminada;
    @Column(name = "dsjustificacion")
    private String justificacion;
    @JoinColumn(name = "cdcodigocaso", referencedColumnName = "cdcaso")
    @ManyToOne(fetch = FetchType.LAZY)
    private Caso codigoCaso;
    @JoinColumn(name = "dsusuariomodificacion", referencedColumnName = "CDUSUARIO")
    @ManyToOne(optional = false)
    private User usuarioModificacion;

    @Transient
    private String mensajeCorreo;
    
    @Transient
    private String fechaActualizada;
    
    @Transient
    private String fechaAnterior;
    
    @Transient
    private Integer codigoDelCaso;
    
    public Justificacion() {
    }

    public Justificacion(Integer codigoJustificacion) {
        this.codigoJustificacion = codigoJustificacion;
    }

    public Justificacion(Integer codigoJustificacion, Date fechaModificacion) {
        this.codigoJustificacion = codigoJustificacion;        
        this.fechaModificacion = fechaModificacion;
    }

	public User getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(User usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Integer getCodigoJustificacion() {
		return codigoJustificacion;
	}

	public void setCodigoJustificacion(Integer codigoJustificacion) {
		this.codigoJustificacion = codigoJustificacion;
	}

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public String getCampoModificado() {
		return campoModificado;
	}

	public void setCampoModificado(String campoModificado) {
		this.campoModificado = campoModificado;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getInformacionEliminada() {
		return informacionEliminada;
	}

	public void setInformacionEliminada(String informacionEliminada) {
		this.informacionEliminada = informacionEliminada;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public Caso getCodigoCaso() {
		return codigoCaso;
	}

	public void setCodigoCaso(Caso codigoCaso) {
		this.codigoCaso = codigoCaso;
	}

	public String getMensajeCorreo() {
		return mensajeCorreo;
	}

	public void setMensajeCorreo(String mensajeCorreo) {
		this.mensajeCorreo = mensajeCorreo;
	}

	public String getFechaActualizada() {
		return fechaActualizada;
	}

	public void setFechaActualizada(String fechaActualizada) {
		this.fechaActualizada = fechaActualizada;
	}

	public String getFechaAnterior() {
		return fechaAnterior;
	}

	public void setFechaAnterior(String fechaAnterior) {
		this.fechaAnterior = fechaAnterior;
	}

	public Integer getCodigoDelCaso() {
		return codigoDelCaso;
	}

	public void setCodigoDelCaso(Integer codigoDelCaso) {
		this.codigoDelCaso = codigoDelCaso;
	}
	
	
}