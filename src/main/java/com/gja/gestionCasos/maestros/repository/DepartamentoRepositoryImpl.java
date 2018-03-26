package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.gja.gestionCasos.maestros.entities.Departamento;
import com.gja.gestionCasos.maestros.entities.Pais;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;



@Repository("DepartamentoRepository")
public class DepartamentoRepositoryImpl extends AbstractRepository<Departamento> implements DepartamentoRepository{
	
	public List<Departamento> obtenerDepartamentoPorPaises(Pais pais) throws BusinessException, DAOException {
		List<Departamento> departamento = null; 
		departamento = entityManager.createQuery("SELECT d FROM Departamento d  WHERE d.pais.codigoPais=:pPais ORDER BY d.nombre").
				setParameter("pPais",pais.getCodigoPais()).getResultList();
		return departamento;

	}
	
	@SuppressWarnings("unchecked")
	public Departamento consultarCodigoDepartamento(Departamento departamento) {
		Departamento objDepartamento= null;
		objDepartamento =  (Departamento) entityManager.createQuery ("SELECT d FROM Departamento d  WHERE d.nombre = :pDsDeparamento")
					.setParameter("pDsDeparamento",departamento.getNombre()).getSingleResult();
		return objDepartamento;
	}
}



