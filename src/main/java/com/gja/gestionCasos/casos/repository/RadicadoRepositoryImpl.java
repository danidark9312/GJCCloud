package com.gja.gestionCasos.casos.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.entities.RadicadoPK;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("radicadoRepository")
public class RadicadoRepositoryImpl extends AbstractRepository<Radicado>  implements RadicadoRepository{

	public Radicado obtenerRadicado(RadicadoPK radicadoPK) throws DAOException, BusinessException {
		
		Radicado radicado= entityManager.find (Radicado.class, radicadoPK);
		return radicado;
	}
	
	public Radicado obtenerRadicado(String codigo) throws DAOException, BusinessException {
		Radicado radicado= entityManager.createQuery("Select r from Radicado r where radicadoPK.codigoRadicado = :pRadicado",Radicado.class)
				.setParameter("pRadicado", codigo)
				.getSingleResult();
		return radicado;
	}
	
public List<Radicado> findAll() throws DAOException, BusinessException {
		List<Radicado> radicados= entityManager.createNamedQuery("Radicado.findAll",Radicado.class).getResultList();
		return radicados;
	}

	public Radicado guardarRadicado(Radicado radicado) throws DAOException,
			BusinessException {
		radicado = this.merge(radicado);
		return radicado;
	} 

	
	public void eliminarRadicado(Radicado radicado) throws DAOException,
			BusinessException {
		entityManager.remove(radicado);
	}
}
