package com.gja.gestionCasos.casos.entities;

import java.util.List;

public class HelperWrapperCasoEquipoCasoPK{
	private List<CasoEquipoCasoPK> casoEquiposCasoPK;
	private String idAbogado;
	
	

	public String getIdAbogado() {
		return idAbogado;
	}

	public void setIdAbogado(String idAbogado) {
		this.idAbogado = idAbogado;
	}

	public List<CasoEquipoCasoPK> getCasoEquiposCasoPK() {
		return casoEquiposCasoPK;
	}

	public void setCasoEquiposCasoPK(List<CasoEquipoCasoPK> casoEquiposCasoPK) {
		this.casoEquiposCasoPK = casoEquiposCasoPK;
	}
	
	
	
}
