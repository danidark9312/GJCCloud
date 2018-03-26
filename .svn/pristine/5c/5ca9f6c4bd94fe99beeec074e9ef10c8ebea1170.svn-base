package com.gja.gestionCasos.clientes.entities;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.gja.gestionCasos.maestros.entities.Parentesco;

/**
 *
 * @author Diego Blandón
 */
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CDCLIENTE")
    private Integer codigo;
    @Column(name = "DSNOMBRE")
    private String nombres;
    @Column(name = "DSAPELLIDO")
    private String apellidos;
    @Column(name = "CDDOCUMENTO")
    private String documento;
    @Column(name = "DSDIRECCION")
    private String direccion;
    @JoinColumn(name = "CDPARENTESCO", referencedColumnName = "cdparentesco")
    @ManyToOne(fetch = FetchType.EAGER)
    private Parentesco parentesco;
    @JoinColumns({
        @JoinColumn(name = "CDCIUDAD", referencedColumnName = "cdciudad"),
        @JoinColumn(name = "CDDEPARTAMENTO", referencedColumnName = "cddepartamento")})
    @ManyToOne(fetch = FetchType.EAGER)
    private Ciudad ciudades;
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<EquipoCaso> equiposCasoList;

    public Cliente() {
    }

    public Cliente(Integer codigo) {
        this.codigo = codigo;
    }

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public Ciudad getCiudades() {
		return ciudades;
	}

	public void setCiudades(Ciudad ciudades) {
		this.ciudades = ciudades;
	}

	public List<EquipoCaso> getEquiposCasoList() {
		return equiposCasoList;
	}

	public void setEquiposCasoList(List<EquipoCaso> equiposCasoList) {
		this.equiposCasoList = equiposCasoList;
	}
    
}