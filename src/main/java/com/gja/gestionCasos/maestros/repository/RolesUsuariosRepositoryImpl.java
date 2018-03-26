package com.gja.gestionCasos.maestros.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("rolesUsuariosRepository")
public class RolesUsuariosRepositoryImpl extends AbstractRepository<RolesUsuarios> implements RolesUsuariosRepository {
	private static final Logger LOG = Logger.getLogger(RolesUsuariosRepositoryImpl.class);

	
	@SuppressWarnings("unchecked")
	public List<RolesUsuarios> getRolesUsuarios() throws BusinessException, DAOException {
		List<RolesUsuarios> rolesUsuarios = null;
		try {
			rolesUsuarios = entityManager.createQuery("SELECT r FROM RolesUsuarios r ORDER BY r.rolesUsuariosPK.codigoRol, r.rolesUsuariosPK.codigoUsuario")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rolesUsuarios;
	}
	
	public RolesUsuarios findByCodigoUsuario(String codigoUsuario) throws BusinessException, DAOException {
		RolesUsuarios rolesUsuarios = new RolesUsuarios();
		
	
		try {
			rolesUsuarios = (RolesUsuarios) entityManager.createQuery("SELECT r FROM RolesUsuarios r WHERE UPPER(r.rolesUsuariosPK.codigoUsuario) = :pCodigoUsuario")
					.setParameter("pCodigoUsuario", codigoUsuario.toUpperCase())
					.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return rolesUsuarios;
	}
	

	public RolesUsuarios findByPK(RolesUsuarios rolesUsuarios) throws DAOException {
		RolesUsuarios rolReturn;

		try {
			rolReturn = entityManager.find(RolesUsuarios.class, rolesUsuarios.getRolesUsuariosPK());
			//TODO no necesito inicializar listas
//			inicializarListas(rolReturn);
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error consultando el rol/usuario por PK " + rolesUsuarios.getRolesUsuariosPK() + ". El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Exception: Error consultando el rol/usuario por PK " + rolesUsuarios.getRolesUsuariosPK() + ". El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rolReturn;
	}
	

	public boolean existRolesUsuarios(String codigoUsuario, int codigoRol) throws DAOException {
		boolean existe = false;
		Long resultado;

		try {
			resultado = entityManager
					.createQuery("SELECT COUNT(r.codigo) FROM RolesUsuarios r WHERE UPPER(r.rolesUsuariosPK.codigoUsuario) = :pCodigoUsuario"
							+ "AND r.rolesUsuariosPK.codigoRol = :pCodigoRol",
							Long.class)
					.setParameter("pCodigoUsuario", codigoUsuario.toUpperCase())
					.setParameter("pCodigoRol", codigoRol)
					.getSingleResult();
			existe = resultado > 0 ? true : false;
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error validando si existe el usuario ya tiene asignado un rol. El error es: "
					+ e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

		return existe;
	}
		
	//TODO eliminar metodo
	@SuppressWarnings("unused")
	@Deprecated
	private void inicializarListas(RolesUsuarios rolReturn) {
		Hibernate.initialize(rolReturn.getRolesUsuariosPK());

//			List<RolesUsuarios> listRolUsuario = rolReturn.getRolesUsuariosList();

//			for (RolesUsuarios objRolUsuario : listRolUsuario) {
//				Hibernate.initialize(objRolUsuario.getUsuario());
//				Hibernate.initialize(objRolUsuario.getUsuario().getNombre());
//			}
	}
	
	public void borrarRolUsuario(String cedulaUsuario) throws BusinessException, DAOException {
		Query query = entityManager.createNativeQuery("DELETE FROM roles_usuarios WHERE CDUSUARIO = :pCedulaUsuario").setParameter("pCedulaUsuario", cedulaUsuario);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<RolesUsuarios>  findByRol(Rol rol) throws BusinessException, DAOException {
		List<RolesUsuarios>  rolesUsuarios = new ArrayList<RolesUsuarios>();
		try {
			rolesUsuarios = (List<RolesUsuarios>) entityManager.createQuery("SELECT r FROM RolesUsuarios r WHERE r.rolesUsuariosPK.codigoRol :=pCodigoRol")
					.setParameter("pCodigoRol", rol.getCodigo())
					.getResultList();
		} catch (Exception e) {
			LOG.error("consultando los usuarios por roles");
		}
		return rolesUsuarios;
	}
	
	@SuppressWarnings("unchecked")
	public	List<Object>  findByNotificacion(Rol rol,TipoCaso tipoCaso) throws BusinessException, DAOException {
		List<Object>  rolesUsuarios = new ArrayList<Object>();
		try {
			rolesUsuarios = (List<Object>) entityManager.createNativeQuery("select  u.DSEMAIL from casos_equipos_caso cec"
					+ " join casos c on cec.cdcaso=c.cdcaso "
					+ " join equipos_caso ec on cec.cdequipocaso =ec.cdequipocaso "
					+ " join usuarios u on  ec.CDUSUARIO =u.CDUSUARIO"
					+ " join roles_usuarios ru on ec.CDUSUARIO=ru.CDUSUARIO "
					+ " where c.cdtipocaso=:pCodigoTipoCaso  and ru.cdrol=:pCodigoRol")
					.setParameter("pCodigoTipoCaso", tipoCaso.getCodigo())
					.setParameter("pCodigoRol", rol.getCodigo())
					.getResultList();
		} catch (Exception e) {
			LOG.error("consultando los usuarios por roles para las notificaciones de escalamiento");
		}
		return rolesUsuarios;
	}
	
}
