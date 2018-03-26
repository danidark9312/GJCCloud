package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.sf.roles.RolesUsuarios;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("maestroUsuariosRepository")
public class MaestroUsuariosRepositoryImpl extends AbstractRepository<User> implements MaestroUsuariosRepository {
	
	private static final Logger LOG = Logger.getLogger(MaestroUsuariosRepositoryImpl.class);

	public User findByPK(User usuario) throws DAOException {
		User usuarioReturn;
		
		try {
			usuarioReturn = entityManager.find(User.class, usuario.getId());
			inicializarListas(usuarioReturn);
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error consultando el usuario " + usuario.getId() + ". El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Exception: Error consultando el usuario " + usuario.getId() + ". El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return usuarioReturn;
	}
	
	@SuppressWarnings("unchecked")
	public List<EstadoUsuario> getEstados() throws DAOException {
		List<EstadoUsuario> estados = null;
		
		try {
			estados = entityManager.createQuery("SELECT e FROM EstadoUsuario e ORDER BY e.dsestado").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return estados;
	}
	
	@SuppressWarnings("unchecked")
	//Aca se realizar el filtro por estado y roles de usuarios con una barra para buscar los nombres
	public List<User> getUsuarios(String activo, String rol, String sSearch, int displayStart, int displayLength) throws DAOException {
		List<User> usuarios = null;		
		StringBuilder stringBuilder = new StringBuilder();
		//Seleccion de los usuario por HQL
		stringBuilder.append("SELECT u FROM User u, RolesUsuarios r WHERE u.id=r.rolesUsuariosPK.codigoUsuario ");	
		
		//Se escogen los parametros
		if(activo != null && !activo.isEmpty()){
			stringBuilder.append(" AND u.activo = :pEstado ");
		}
		if(rol != null && !rol.isEmpty()){
			stringBuilder.append(" AND r.rolesUsuariosPK.codigoRol = :pRol ");
		}
		//Aca se concatena para la barra de buscar
		if (sSearch != null && !sSearch.isEmpty()) {
			stringBuilder.append(" AND concat(concat(u.nombre, ' '),u.apellido) LIKE :pNombre ");
		}
		 
		//orden a la lista
		if (activo != null && rol != null && !activo.isEmpty() && !rol.isEmpty() && sSearch != null && !sSearch.isEmpty()) {
			stringBuilder.append("ORDER BY u.id DESC");
		} else {
			stringBuilder.append("ORDER BY u.id ASC");
		}
		
		//Se colocan los parametros
		Query query = entityManager.createQuery(stringBuilder.toString());
		 
		if(activo != null && !activo.isEmpty())
			query.setParameter("pEstado", activo);
		if(rol != null && !rol.isEmpty())
			query.setParameter("pRol", Integer.parseInt(rol));
		if (sSearch != null && !sSearch.isEmpty())
			query.setParameter("pNombre", "%" + sSearch + "%");
		 
		usuarios = query.setFirstResult(displayStart).setMaxResults(displayLength).getResultList();
		return usuarios;
			/*
			Filtro viejo, puede que sirva por eso lo dejo aca
			try {
				if (activo != null && rol != null && !activo.isEmpty() && !rol.isEmpty()) {				
						query = entityManager.createQuery("SELECT u FROM User u, RolesUsuarios r WHERE u.activo = :pEstado AND  u.id=r.rolesUsuariosPK.codigoUsuario AND r.rolesUsuariosPK.codigoRol = :pRol ORDER BY u.id")
								.setParameter("pEstado", activo)
								.setParameter("pRol", Integer.parseInt(rol));				
				}
				else{
					if (activo != null && !activo.isEmpty()){
						query = entityManager.createQuery("SELECT u FROM User u WHERE u.activo = :pEstado ORDER BY u.id").setParameter("pEstado", activo);
					}
					if (rol != null && !rol.isEmpty()){
						query = entityManager.createQuery("SELECT u FROM User u WHERE u.rolesUsuariosList.rol.rolesUsuariosPK = :pRol  ORDER BY u.id").setParameter("pRol", rol);
					}
				}
				if (activo == null && rol == null){			
					query = entityManager.createQuery("SELECT u FROM User u ORDER BY u.id");
				}						
				usuarios = query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 */		 		 		
	}

	public boolean existIdUsuario(String idUsuario) throws DAOException {
		boolean existe = false;
		Long resultado;

		try {
			resultado = entityManager
					.createQuery("SELECT COUNT(u.id) FROM User u WHERE UPPER(u.id) = :pId", Long.class)
					.setParameter("pId", idUsuario.toUpperCase())
					.getSingleResult();
			existe = resultado > 0 ? true : false;
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error validando si existe el id del usuario. El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

		return existe;
	}
	
	public boolean existCorreoUsuario(String email) throws DAOException {
		boolean existe = false;
		Long resultado;

		try {
			resultado = entityManager
					.createQuery("SELECT COUNT(u.id) FROM User u WHERE UPPER(u.email) = :pEmail", Long.class)
					.setParameter("pEmail", email.toUpperCase())
					.getSingleResult();
			existe = resultado > 0 ? true : false;
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error validando si existe el id del usuario. El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

		return existe;
	}

	//TODO terminar de realizar los cambios al usuario de rol a user
	private void inicializarListas(User usuarioReturn) {
		Hibernate.initialize(usuarioReturn.getRolesUsuariosList());
		
		

		List<RolesUsuarios> listRolUsuario = usuarioReturn.getRolesUsuariosList();

		for (RolesUsuarios objRolUsuario : listRolUsuario) {
			Hibernate.initialize(objRolUsuario.getUsuario());
			Hibernate.initialize(objRolUsuario.getUsuario().getNombre());
			
		}
		
		for (EquipoCaso equipoCaso : usuarioReturn.getEquipoCasoSet()) {
			Hibernate.initialize(equipoCaso.getCasoEquipoCasoSet());
		}
	}
	
	public String obtenerIdPorEmail(String email) throws BusinessException,
			DAOException {
		String idUsuario = (String)entityManager.createQuery("SELECT u.id FROM User u WHERE u.email = :pEmail ").setParameter("pEmail", email).getSingleResult();
		return idUsuario;
	}

	@SuppressWarnings("unchecked")
	public Long getCountUsuario(String activo, String rol, String sSearch) throws DAOException {
		Long usuarios = null;		
		StringBuilder stringBuilder = new StringBuilder();
		//Seleccion de los usuario por HQL
		stringBuilder.append("SELECT Count(u) FROM User u, RolesUsuarios r WHERE u.id=r.rolesUsuariosPK.codigoUsuario ");	
		
		//Se escogen los parametros
		if(activo != null && !activo.isEmpty()){
			stringBuilder.append(" AND u.activo = :pEstado ");
		}
		if(rol != null && !rol.isEmpty()){
			stringBuilder.append(" AND r.rolesUsuariosPK.codigoRol = :pRol ");
		}
		//Aca se concatena para la barra de buscar
		if (sSearch != null && !sSearch.isEmpty()) {
			stringBuilder.append(" AND concat(concat(u.nombre, ' '),u.apellido) LIKE :pNombre ");
		}
		 
		//orden a la lista
		if (activo != null && rol != null && !activo.isEmpty() && !rol.isEmpty() && sSearch != null && !sSearch.isEmpty()) {
			stringBuilder.append("ORDER BY u.id DESC");
		} else {
			stringBuilder.append("ORDER BY u.id ASC");
		}
		
		//Se colocan los parametros
		Query query = entityManager.createQuery(stringBuilder.toString());
		 
		if(activo != null && !activo.isEmpty())
			query.setParameter("pEstado", activo);
		if(rol != null && !rol.isEmpty())
			query.setParameter("pRol", Integer.parseInt(rol));
		if (sSearch != null && !sSearch.isEmpty())
			query.setParameter("pNombre", "%" + sSearch + "%");
		 
		usuarios = (Long)query.getSingleResult();
		return usuarios;
	}


}
