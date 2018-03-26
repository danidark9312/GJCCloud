package com.gja.gestionCasos.listaArchivos.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Desarrollador3
 */
@Entity
@Table(name = "lista_archivos")
@NamedQueries({
    @NamedQuery(name = "ListaArchivos.findAll", query = "SELECT l FROM ListaArchivos l")})
public class ListaArchivos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDARCHIVOS")
    private Integer idArchivo;
    @Basic(optional = false)
    @Column(name = "NMAUTOR")
    private String autor;
    @Basic(optional = false)
    @Column(name = "NOMBREARCHIVO")
    private String nombreArchivo;
    @Basic(optional = false)
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "DSDESCRIPCION")
    private String descripcion;
    
    

    public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public ListaArchivos() {
    }
    
    

	public ListaArchivos(Integer idArchivo, String autor, String nombreArchivo, Date fechaCreacion) {
		super();
		this.idArchivo = idArchivo;
		this.autor = autor;
		this.nombreArchivo = nombreArchivo;
		this.fechaCreacion = fechaCreacion;
	}



	public Integer getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(Integer idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
//    public String getDsruta() {
//        return dsruta;
//    }
//
//    public void setDsruta(String dsruta) {
//        this.dsruta = dsruta;
//    }
    
}

