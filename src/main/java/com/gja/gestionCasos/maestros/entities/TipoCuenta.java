package com.gja.gestionCasos.maestros.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gja.gestionCasos.casos.entities.Prestamo;

/**
 *
 * @author Desarrollador3
 */
@Entity
@Table(name = "tiposdecuenta")
@NamedQueries({
    @NamedQuery(name = "TipoCuenta.findAll", query = "SELECT t FROM TipoCuenta t")})
public class TipoCuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cdtipocuenta")
    private Integer codigoTipoCuenta;
    @Basic(optional = false)
    @Column(name = "dstipocuenta")
    private String descripcioTipocuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoCuenta", fetch = FetchType.EAGER)
    private List<Prestamo> prestamosList;

    public TipoCuenta() {
	
	}

	public TipoCuenta(Integer codigoTipoCuenta, String descripcioTipocuenta,
			List<Prestamo> prestamosList) {
		super();
		this.codigoTipoCuenta = codigoTipoCuenta;
		this.descripcioTipocuenta = descripcioTipocuenta;
		this.prestamosList = prestamosList;
	}

	public Integer getCodigoTipoCuenta() {
		return codigoTipoCuenta;
	}

	public void setCodigoTipoCuenta(Integer codigoTipoCuenta) {
		this.codigoTipoCuenta = codigoTipoCuenta;
	}

	public String getDescripcioTipocuenta() {
		return descripcioTipocuenta;
	}

	public void setDescripcioTipocuenta(String descripcioTipocuenta) {
		this.descripcioTipocuenta = descripcioTipocuenta;
	}

	public List<Prestamo> getPrestamosList() {
		return prestamosList;
	}

	public void setPrestamosList(List<Prestamo> prestamosList) {
		this.prestamosList = prestamosList;
	}


	
    
}
