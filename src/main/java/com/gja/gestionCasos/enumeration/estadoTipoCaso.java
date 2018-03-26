package com.gja.gestionCasos.enumeration;

public enum estadoTipoCaso {
	S("S","Si"),
	N("N","No");
	
	private String codigo;
	private String descripcion;
	
	estadoTipoCaso(String codigo, String descripcion){
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}


	
	
}
