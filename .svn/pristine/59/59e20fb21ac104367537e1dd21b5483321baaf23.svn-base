package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.sf.roles.EstadoRol;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("maestroRolRepository")
public class MaestroRolesRepositoryImpl extends AbstractRepository<Rol> implements MaestroRolesRepository {
	private static final Logger LOG = Logger.getLogger(MaestroRolesRepositoryImpl.class);

	public Rol findByPK(Rol rol) throws DAOException {
		Rol rolReturn;

		try {
			rolReturn = entityManager.find(Rol.class, rol.getCodigo());
			inicializarListas(rolReturn);
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error consultando el rol por PK " + rol.getCodigo() + ". El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Exception: Error consultando el rol por PK " + rol.getCodigo() + ". El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rolReturn;
	}
	
	public Rol findByIdRol(Integer codigoRol) throws BusinessException, DAOException {
		Rol rolReturn;

		try {
			rolReturn = entityManager.find(Rol.class, codigoRol);
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error consultando el rol por PK " + codigoRol + ". El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Exception: Error consultando el rol por PK " + codigoRol + ". El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rolReturn;
	}

	@SuppressWarnings("unchecked")
	public Long getCountRoles(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws DAOException {
		Long roles = null;
		Query query = null;
		try {
			if (activo != null && !activo.isEmpty() && busquedaDescripcion != null && !busquedaDescripcion.isEmpty()) {
				query = entityManager.createQuery("SELECT count(r) FROM Rol r WHERE r.estado.cdestado = :pEstado AND r.descripcion LIKE :pDescripcion  ORDER BY r.descripcion")
						.setParameter("pEstado", Integer.parseInt(activo))
						.setParameter("pDescripcion", "%" + busquedaDescripcion + "%");
			} else {				
				if (activo != null && !activo.isEmpty()) {
					query = entityManager.createQuery("SELECT count(r) FROM Rol r WHERE r.estado.cdestado = :pEstado  ORDER BY r.descripcion")
							.setParameter("pEstado", Integer.parseInt(activo));
				} else {
					if (busquedaDescripcion != null && !busquedaDescripcion.isEmpty()) {
						query = entityManager.createQuery("SELECT count(r) FROM Rol r WHERE  r.descripcion LIKE :pDescripcion  ORDER BY r.descripcion")
								.setParameter("pDescripcion", "%" + busquedaDescripcion + "%");
					} else {					
						query = entityManager.createQuery("SELECT count(r) FROM Rol r  ORDER BY r.descripcion");
					}
				}
			}
			roles = (Long)query.getSingleResult();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}
	public List<Rol> getRoles(String activo, String busquedaDescripcion, int displayStart, int displayLength) throws DAOException {
		List<Rol> roles = null;
		Query query = null;
		try {
			if (activo != null && !activo.isEmpty() && busquedaDescripcion != null && !busquedaDescripcion.isEmpty()) {
				query = entityManager.createQuery("SELECT r FROM Rol r WHERE r.estado.cdestado = :pEstado AND r.descripcion LIKE :pDescripcion  ORDER BY r.descripcion")
						.setParameter("pEstado", Integer.parseInt(activo))
						.setParameter("pDescripcion", "%" + busquedaDescripcion + "%");
			} else {				
				if (activo != null && !activo.isEmpty()) {
					query = entityManager.createQuery("SELECT r FROM Rol r WHERE r.estado.cdestado = :pEstado  ORDER BY r.descripcion")
							.setParameter("pEstado", Integer.parseInt(activo));
				} else {
					if (busquedaDescripcion != null && !busquedaDescripcion.isEmpty()) {
						query = entityManager.createQuery("SELECT r FROM Rol r WHERE  r.descripcion LIKE :pDescripcion  ORDER BY r.descripcion")
								.setParameter("pDescripcion", "%" + busquedaDescripcion + "%");
					} else {					
						query = entityManager.createQuery("SELECT r FROM Rol r  ORDER BY r.descripcion");
					}
				}
			}
			
			roles = query.setFirstResult(displayStart).setMaxResults(displayLength).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roles;
	}
	
	public List<Rol> getAllRoles(String activo, String busquedaDescripcion) throws DAOException {
		List<Rol> roles = null;
		Query query = null;
		try {
						
			query = entityManager.createQuery("SELECT r FROM Rol r  ORDER BY r.descripcion");
						
			roles = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roles;
	}

	public boolean existNameRol(String nombre) throws DAOException {
		boolean existe = false;
		Long resultado;

		try {
			resultado = entityManager
					.createQuery("SELECT COUNT(r.codigo) FROM Rol r WHERE UPPER(r.descripcion) = :pDescripcion",
							Long.class)
					.setParameter("pDescripcion", nombre.toUpperCase()).getSingleResult();
			existe = resultado > 0 ? true : false;
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error validando si existe el nombre del rol. El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

		return existe;
	}

	private void inicializarListas(Rol rolReturn) {
		Hibernate.initialize(rolReturn.getRolesUsuariosList());

		List<RolesUsuarios> listRolUsuario = rolReturn.getRolesUsuariosList();

		for (RolesUsuarios objRolUsuario : listRolUsuario) {
			Hibernate.initialize(objRolUsuario.getUsuario());
			Hibernate.initialize(objRolUsuario.getUsuario().getNombre());
		}
	}

	@SuppressWarnings("unchecked")
	public List<EstadoRol> getEstados() throws DAOException, BusinessException {
		List<EstadoRol> estados = null;

		try {
			estados = entityManager.createQuery("SELECT r FROM EstadoRol r").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return estados;
	}
	

}
