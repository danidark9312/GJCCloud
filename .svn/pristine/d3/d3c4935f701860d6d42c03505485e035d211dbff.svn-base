package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.repository.CasoRepository;
import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.gja.gestionCasos.maestros.entities.Departamento;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;


@Repository("ciudadRepository")

public class CiudadRepositoryImpl extends AbstractRepository<Ciudad> implements CiudadRepository{
	private static final Logger LOG = Logger
			.getLogger(CasoRepository.class);
	public List<Ciudad> ciudadPorDepartamento(Departamento departamento){
		
		List<Ciudad> ciudad=null;
		
		ciudad = entityManager.createQuery("SELECT c FROM Ciudad c  WHERE c.departamento.codigoDepartamento=:dDepartamento ORDER BY c.nombre").
				setParameter("dDepartamento",departamento.getCodigoDepartamento()).getResultList();
		
		
		return ciudad; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Ciudad> consultarCodigoCiudad(Ciudad dsCiudad) {
		List resultado;
			resultado = entityManager.createQuery("SELECT c FROM Ciudad c  WHERE c.nombre = :pDsCiudad")
					.setParameter("pDsCiudad",dsCiudad.getNombre())
					.getResultList();
	
		
		return resultado;
	}
	
}
