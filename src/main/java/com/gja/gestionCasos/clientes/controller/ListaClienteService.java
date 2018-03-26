package com.gja.gestionCasos.clientes.controller;

import java.util.List;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;

public interface ListaClienteService {
	
//	Cliente cliente(Cliente listaCliente) throws DAOException, BusinessException;
//	Cliente findByPK(Cliente listaCliente)  throws DAOException, BusinessException;
//	Long getCountFilter(CasoFiltro caso);
	List<CasoEquipoCaso> encontrarClientesPorFiltro(CasoFiltro casoFiltro, boolean isCountSelect);
	Long contarClientesPorFiltro(CasoFiltro casoFiltro, boolean isCountSelect);
}
