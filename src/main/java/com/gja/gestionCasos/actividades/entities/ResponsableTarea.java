package com.gja.gestionCasos.actividades.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "responsablestareas")
public class ResponsableTarea implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResponsableTareaPK responsableTareaPK;
    @Column(name = "fefinalizaciontarea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacionTarea;
    @Column(name = "cdeqrealizotarea")
    private Integer codigoEquipoCasoRealizoTarea;
    @JoinColumn(name = "cdtareaparticular", referencedColumnName = "cdtareaparticular", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TareaParticularCaso tareasParticularesCaso;
    @JoinColumns({
        @JoinColumn(name = "cdequipocaso", referencedColumnName = "cdequipocaso", insertable = false, updatable = false),
        @JoinColumn(name = "cdmiembro", referencedColumnName = "cdmiembro", insertable = false, updatable = false),
        @JoinColumn(name = "cdcaso", referencedColumnName = "cdcaso", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private CasoEquipoCaso casosEquiposCaso;
    
    @Transient
    private boolean isOtroResponsable;
    
    

    public ResponsableTarea() {
    }

    public ResponsableTarea(ResponsableTareaPK responsableTareaPK) {
        this.responsableTareaPK = responsableTareaPK;
    }

    public ResponsableTarea(int codigoTareaparticular, int codigoEquipoCaso, int codigoMiembro, int codigoCaso) {
        this.responsableTareaPK = new ResponsableTareaPK(codigoTareaparticular, codigoEquipoCaso, codigoMiembro, codigoCaso);
    }

    public Date getFechaFinalizacionTarea() {
		return fechaFinalizacionTarea;
	}

	public void setFechaFinalizacionTarea(Date fechaFinalizacionTarea) {
		this.fechaFinalizacionTarea = fechaFinalizacionTarea;
	}

	public Integer getCodigoEquipoCasoRealizoTarea() {
		return codigoEquipoCasoRealizoTarea;
	}

	public void setCodigoEquipoCasoRealizoTarea(Integer codigoEquipoCasoRealizoTarea) {
		this.codigoEquipoCasoRealizoTarea = codigoEquipoCasoRealizoTarea;
	}

	public TareaParticularCaso getTareasParticularesCaso() {
		return tareasParticularesCaso;
	}

	public void setTareasParticularesCaso(TareaParticularCaso tareasParticularesCaso) {
		this.tareasParticularesCaso = tareasParticularesCaso;
	}

	public CasoEquipoCaso getCasosEquiposCaso() {
		return casosEquiposCaso;
	}

	public void setCasosEquiposCaso(CasoEquipoCaso casosEquiposCaso) {
		this.casosEquiposCaso = casosEquiposCaso;
	}

	public ResponsableTareaPK getResponsableTareaPK() {
        return responsableTareaPK;
    }

    public void setResponsableTareaPK(ResponsableTareaPK responsableTareaPK) {
        this.responsableTareaPK = responsableTareaPK;
    }

	public boolean isOtroResponsable() {
		return isOtroResponsable;
	}

	public void setOtroResponsable(boolean isOtroResponsable) {
		this.isOtroResponsable = isOtroResponsable;
	}

    


    
}

