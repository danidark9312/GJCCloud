package com.gja.gestionCasos.clientes.controller;

import java.util.List;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;

public interface ListaClientesRepository {
	
	
	List<CasoEquipoCaso> encontrarClientesPorFiltro(CasoFiltro casoFiltro, boolean isCountSelect);
	Long contarClientesPorFiltro(CasoFiltro casoFiltro, boolean isCountSelect);
}
