package com.gja.gestionCasos.casos.entities;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gja.gestionCasos.clientes.entities.Cliente;
import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.sf.social.signinmvc.user.model.User;



/**
 *
 * @author DESARROLLADOR6
 */
@Entity
@Table(name = "equipos_caso")
@NamedQueries({
    @NamedQuery(name = "EquipoCaso.findAll", query = "SELECT e FROM EquipoCaso e")})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class EquipoCaso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdequipocaso")
    private Integer codigoEquipoCaso;
    @Column(name = "cdidentificacion")
    private String identificacion;
    @Basic(optional = false)
    @Column(name = "dsnombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "dsapellido")
    private String apellido;
    @JsonIgnore
    @OneToMany( cascade = CascadeType.REMOVE, mappedBy = "equipoCaso", fetch = FetchType.LAZY)
    private List<CasoEquipoCaso> casoEquipoCasoSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoEquipoCaso", fetch = FetchType.LAZY)
    private List<Celular> celularList;
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "codigoEquipoCaso", fetch = FetchType.LAZY)
    private List<Correo> correoList;
    
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "codigoEquipoCaso", fetch = FetchType.LAZY)
    private List<Telefono> telefonoList;
    
    @JsonIgnore
    @JoinColumn(name = "CDUSUARIO", referencedColumnName = "CDUSUARIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private User usuario;
    
	@Column(name = "DSFOTO")
	private String nombreFoto;
	@JoinColumn(name = "CDCLIENTE", referencedColumnName = "CDCLIENTE")
    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente cliente;
	
	@JoinColumn(name = "CDTIPODOCUMENTO", referencedColumnName = "ID")
    
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoDocumento tipoDocumento;
	
	@Column(name = "FENACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;	  


	public EquipoCaso() {
    }

    public EquipoCaso(Integer codigoEquipoCaso) {
        this.codigoEquipoCaso = codigoEquipoCaso;
    }

    public EquipoCaso(Integer codigoEquipoCaso, String nombre, String apellido, String nombrefoto) {
        this.codigoEquipoCaso = codigoEquipoCaso;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreFoto=nombrefoto;
     }

	public Integer getCodigoEquipoCaso() {
		return codigoEquipoCaso;
	}

	public void setCodigoEquipoCaso(Integer codigoEquipoCaso) {
		this.codigoEquipoCaso = codigoEquipoCaso;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public List<CasoEquipoCaso> getCasoEquipoCasoSet() {
		return casoEquipoCasoSet;
	}

	public void setCasoEquipoCasoSet(List<CasoEquipoCaso> casoEquipoCasoSet) {
		this.casoEquipoCasoSet = casoEquipoCasoSet;
	}

	
	public List<Celular> getCelularList() {
		return celularList;
	}

	public void setCelularList(List<Celular> celularList) {
		this.celularList = celularList;
	}

	public List<Correo> getCorreoList() {
		return correoList;
	}

	public void setCorreoList(List<Correo> correoList) {
		this.correoList = correoList;
	}

	public List<Telefono> getTelefonoList() {
		return telefonoList;
	}

	public void setTelefonoList(List<Telefono> telefonoList) {
		this.telefonoList = telefonoList;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getNombreFoto() {
		return nombreFoto;
	}

	public void setNombreFoto(String nombreFoto) {
		this.nombreFoto = nombreFoto;
	}
	
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
}