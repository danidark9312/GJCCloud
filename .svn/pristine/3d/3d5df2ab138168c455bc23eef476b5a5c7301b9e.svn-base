package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("estadoUsuarioRepository")
public class EstadoUsuarioRepositoryImpl extends AbstractRepository<EstadoUsuario>implements EstadoUsuarioRepository {
	
	@SuppressWarnings("unchecked")
	public List<EstadoUsuario> getEstadosUsuarios() throws BusinessException, DAOException {
		List<EstadoUsuario> estadoUsuario = null;

		estadoUsuario = entityManager.createQuery("SELECT e FROM EstadoUsuario e ORDER BY e.dsestado").getResultList();

		return estadoUsuario;
	}

	public EstadoUsuario getEstadoUsuario(Integer codigo) throws BusinessException, DAOException {
		EstadoUsuario estadoUsuario = new EstadoUsuario();

		estadoUsuario = (EstadoUsuario) entityManager
				.createQuery("SELECT e FROM EstadoUsuario e WHERE e.cdestado = :pCodigo")
				.setParameter("pCodigo", codigo).getSingleResult();

		return estadoUsuario;
	}

	public EstadoUsuario getEstadoUsuario(String descripcion) throws BusinessException, DAOException {
		EstadoUsuario estadoUsuario = new EstadoUsuario();

		estadoUsuario = (EstadoUsuario) entityManager
				.createQuery("SELECT e FROM EstadoUsuario e WHERE UPPER(e.dsestado) = :pDescripcion")
				.setParameter("pDescripcion", descripcion.toUpperCase()).getSingleResult();

		return estadoUsuario;
	}

}
