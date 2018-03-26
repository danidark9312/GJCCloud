package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.Instancia;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;


@Repository("instanciaRepository")
public class InstanciaRepositoryImpl extends AbstractRepository<Instancia> implements InstanciaRepository{
	
	public List<Instancia> obtenerInstancia() throws DAOException,BusinessException
	{
		List<Instancia> instancias=null;
		
		instancias=entityManager.createQuery("SELECT i FROM Instancia i  ORDER BY i.nombre").getResultList();
		return instancias;
	}
	

	@SuppressWarnings("unchecked")
	public Instancia consultarCodigoInstancia(Instancia instancia) throws BusinessException {
		Instancia resultado;
		try {
			resultado =  (Instancia) entityManager.createQuery("SELECT i FROM Instancia i  WHERE i.nombre = :pDsInstancia")
					.setParameter("pDsInstancia", instancia.getNombre())
					.getSingleResult();
		} catch (NoResultException e) {
			throw new BusinessException("No existe instancia de radicado con nombre: " + instancia.getNombre());
		}
			
		return resultado;
	}
	
}
