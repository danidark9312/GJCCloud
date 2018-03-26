package com.gja.gestionCasos.casos.entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.gja.gestionCasos.maestros.entities.Parentesco;
import com.gja.gestionCasos.maestros.entities.TipoMiembro;

/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "casos_equipos_caso")
@NamedQueries({
    @NamedQuery(name = "CasoEquipoCaso.findAll", query = "SELECT c FROM CasoEquipoCaso c")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class CasoEquipoCaso implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CasoEquipoCasoPK casoEquipoCasoPK;
    @Column(name = "dsdireccion")
    private String direccion;
    @Column(name = "iscontacto")
    private String contacto;
    @Column(name = "ispersonajuridica")
    private String personajuridica;
    @Column(name = "isactivo")
    private String activo;
    @JoinColumn(name = "cdequipocaso", referencedColumnName = "cdequipocaso", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private EquipoCaso equipoCaso;
    @JoinColumn(name = "cdcaso", referencedColumnName = "cdcaso", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonBackReference
    private Caso caso;
    @JoinColumn(name = "cdmiembro", referencedColumnName = "cdmiembro", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonBackReference
    private TipoMiembro tipoMiembro;
    @JoinColumn(name = "cdparentesco", referencedColumnName = "cdparentesco")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Parentesco parentesco;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "casosEquiposCaso", fetch = FetchType.LAZY)
    private List<ResponsableTarea> responsablesTareas;
    // ingreso de ciudad equipo 
        @JoinColumns({
    	@JoinColumn(name = "cdciudad", referencedColumnName = "cdciudad"),
    	@JoinColumn(name = "cddepartamento", referencedColumnName = "cddepartamento")})
    @ManyToOne(fetch = FetchType.EAGER)
    private Ciudad ciudadEquipoCaso;
   @Column(name = "dsobservacion")
   private String observacion;
   
   @Transient
   public boolean isResponsableTarea;
        
   @Transient
   private boolean hasActividadesPendientes;

   

	public CasoEquipoCaso() {
    }

    public CasoEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK) {
        this.casoEquipoCasoPK = casoEquipoCasoPK;
    }

    public CasoEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK, String contacto, String personajuridica) {
        this.casoEquipoCasoPK = casoEquipoCasoPK;
        this.contacto = contacto;
        this.personajuridica = personajuridica;
    }

    public CasoEquipoCaso(int caso, int codigoEquipoCaso, int miembro) {
        this.casoEquipoCasoPK = new CasoEquipoCasoPK(caso, codigoEquipoCaso, miembro);
    }

	public CasoEquipoCasoPK getCasoEquipoCasoPK() {
		return casoEquipoCasoPK;
	}

	public void setCasoEquipoCasoPK(CasoEquipoCasoPK casoEquipoCasoPK) {
		this.casoEquipoCasoPK = casoEquipoCasoPK;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getPersonajuridica() {
		return personajuridica;
	}

	public void setPersonajuridica(String personajuridica) {
		this.personajuridica = personajuridica;
	}

	public EquipoCaso getEquipoCaso() {
		return equipoCaso;
	}

	public void setEquipoCaso(EquipoCaso equipoCaso) {
		this.equipoCaso = equipoCaso;
	}

	public Caso getCaso() {
		return caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}

//	public Usuario getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}

	public TipoMiembro getTipoMiembro() {
		return tipoMiembro;
	}

	public void setTipoMiembro(TipoMiembro tipoMiembro) {
		this.tipoMiembro = tipoMiembro;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}
//	public List<Prestamo> getPrestamoSet() {
//		return prestamoSet;
//	}
//
//	public void setPrestamoSet(List<Prestamo> prestamoSet) {
//		this.prestamoSet = prestamoSet;
//	}
	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Ciudad getCiudadEquipoCaso() {
		return ciudadEquipoCaso;
	}

	public void setCiudadEquipoCaso(Ciudad ciudadEquipoCaso) {
		this.ciudadEquipoCaso = ciudadEquipoCaso;
	}

	public boolean isHasActividadesPendientes() {
		return hasActividadesPendientes;
	}

	public void setHasActividadesPendientes(boolean hasActividadesPendientes) {
		this.hasActividadesPendientes = hasActividadesPendientes;
	}

	public List<ResponsableTarea> getResponsablesTareas() {
		return responsablesTareas;
	}

	public void setResponsablesTareas(List<ResponsableTarea> responsablesTareas) {
		this.responsablesTareas = responsablesTareas;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public boolean getIsResponsableTarea() {
		return isResponsableTarea;
	}

	public void setIsResponsableTarea(boolean isResponsableTarea) {
		this.isResponsableTarea = isResponsableTarea;
	}
	
    
}
