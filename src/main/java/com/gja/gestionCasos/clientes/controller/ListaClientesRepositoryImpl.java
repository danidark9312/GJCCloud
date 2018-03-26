package com.gja.gestionCasos.clientes.controller;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;

@Repository("listaClientesRepository")
public class ListaClientesRepositoryImpl extends AbstractRepository<EquipoCaso> implements ListaClientesRepository {

	public List<CasoEquipoCaso> encontrarClientesPorFiltro(CasoFiltro casoFiltro, boolean isCountSelect) {
		Query query = crearQuery(casoFiltro, isCountSelect);
		query.setFirstResult(casoFiltro.getiDisplayStart());
		query.setMaxResults(casoFiltro.getiDisplayLength());
		
		List<CasoEquipoCaso> listaClientes = query.getResultList(); 
		return listaClientes;
	}
	
	public Long contarClientesPorFiltro(CasoFiltro casoFiltro, boolean isCountSelect) {
		Query query = crearQuery(casoFiltro, isCountSelect);
		Long cantidad = (Long)query.getSingleResult();
		return cantidad;
	}
	
	private Query crearQuery(CasoFiltro casoFiltro, boolean isCountSelect) {
		StringBuilder strBuilder = new StringBuilder();
		if (isCountSelect)
			strBuilder.append("SELECT count(a) ");
		else
			strBuilder.append("SELECT a ");
		
		strBuilder.append(" FROM CasoEquipoCaso a, EquipoCaso b WHERE a.casoEquipoCasoPK.codigoEquipoCaso = b.codigoEquipoCaso ");
		strBuilder.append(" AND a.tipoMiembro.codigo IN (:pVictima, :pDemandante) ");
		if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().equals(""))
			strBuilder.append(" AND a.caso.nombre LIKE :pNombreCaso ");

		
		if (casoFiltro != null && casoFiltro.getNombreCliente() != null && !casoFiltro.getNombreCliente().equals(""))
			strBuilder.append(" AND concat(concat(b.nombre, ' '), b.apellido) LIKE :pNombreCliente ");
		
		Query query = entityManager.createQuery(strBuilder.toString());
		 
		if (casoFiltro != null && casoFiltro.getNombreCasoFiltro() != null && !casoFiltro.getNombreCasoFiltro().equals(""))
			query.setParameter("pNombreCaso", "%" + casoFiltro.getNombreCasoFiltro() + "%");

		
		if (casoFiltro != null && casoFiltro.getNombreCliente() != null && !casoFiltro.getNombreCliente().equals(""))
			query.setParameter("pNombreCliente", "%" + casoFiltro.getNombreCliente() + "%");
		
		
		query.setParameter("pVictima", Integer.parseInt(Parametros.getVictima()));
		query.setParameter("pDemandante", Integer.parseInt(Parametros.getDemandante()));
		
		return query;
	}
	
}
