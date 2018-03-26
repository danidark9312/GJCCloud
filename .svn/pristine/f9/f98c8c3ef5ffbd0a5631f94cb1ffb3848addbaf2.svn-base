package com.gja.gestionCasos.casos.repository;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("bienAfectadoRepository")
public class BienAfectadoRepositoryImpl extends AbstractRepository<BienAfectado> implements BienAfectadoRepository{
	
	public BienAfectado guardarBienAfectado(BienAfectado bienAfectado)
			throws DAOException, BusinessException {
		bienAfectado = entityManager.merge(bienAfectado);
		return bienAfectado;
	}
	 
	public BienAfectado obtenerBienAfectado(BienAfectado bienAfectado)
			throws DAOException, BusinessException {
		bienAfectado = entityManager.find(BienAfectado.class, bienAfectado.getBienAfectadoPK());
		Hibernate.initialize(bienAfectado.getClase());
		return bienAfectado;
	}	
	
	
	
}
