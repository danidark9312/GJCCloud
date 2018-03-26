package com.gja.gestionCasos.clientes.controller;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;

@Transactional
@Service("listaClienteService")
public class ListaClienteServiceImpl implements ListaClienteService {
	
	
	@Autowired
	ListaClientesRepository listaClientesRepository;

	public List<CasoEquipoCaso> encontrarClientesPorFiltro(CasoFiltro casoFiltro, boolean isCountSelect) {
		List<CasoEquipoCaso> listaClientes = listaClientesRepository.encontrarClientesPorFiltro(casoFiltro, isCountSelect);
		for (CasoEquipoCaso casoEquipoCaso:listaClientes) {
			Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getTelefonoList());
			Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getCorreoList());
			Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getCelularList());
			Hibernate.initialize(casoEquipoCaso.getCaso());
		}
		return listaClientes;
	}
	
	public Long contarClientesPorFiltro(CasoFiltro casoFiltro, boolean isCountSelect) {
		return listaClientesRepository.contarClientesPorFiltro(casoFiltro, isCountSelect);
	}

}
