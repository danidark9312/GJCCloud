/**
 * 
 */
package com.gja.gestionCasos.permisos.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import com.sf.roles.Opcion;
import com.sf.roles.Rol;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

/**
 * @author Usuario
 *
 */
@Repository("OpcionesRepository")
public class OpcionesRepositoryImpl extends AbstractRepository<Opcion> implements OpcionesRepository {

	final String URLLOGIN = "/Login";
	
	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.repository.OpcionesRepository#asignOpcionesRol(com.sf.roles.Rol, java.util.List)
	 */
	public boolean asignOpcionesRol(Rol rol, List<Opcion> opciones) throws BusinessException, DAOException {
		//TODO REVISAR SI SE NECESITA O NO METODO
		return false;
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.repository.OpcionesRepository#getOpciones()
	 */
	@SuppressWarnings("unchecked")
	public List<Opcion> getOpciones() throws BusinessException, DAOException {
		List<Opcion> opciones = null;
		
		opciones = entityManager.createQuery("SELECT o FROM Opcion o ORDER BY o.descripcion").getResultList();
		
		return removerOpcion(opciones, URLLOGIN);
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.repository.OpcionesRepository#getOpciones(com.sf.roles.Rol)
	 */
	@SuppressWarnings("unchecked")
	public List<Opcion> getOpciones(Rol rol) throws BusinessException, DAOException {
		List<Opcion> opciones = null;
		
		opciones = entityManager.createQuery("SELECT o FROM Opcion o ORDER BY o.descripcion").getResultList();

		return removerOpcion(opciones, URLLOGIN);
	}
	
	public boolean existsOpcion(Opcion opcion) throws BusinessException, DAOException {
		//TODO REVISAR SI SE NECESITA O NO METODO
		return false;
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.repository.OpcionesRepository#setOpciones(com.sf.roles.Opcion)
	 */
	public boolean setOpciones(Opcion opcion) throws BusinessException, DAOException {
		//TODO REVISAR SI SE NECESITA O NO METODO
		return false;
	}

	/* (non-Javadoc)
	 * @see com.gja.gestionCasos.permisos.repository.OpcionesRepository#unasignOpcionesRol(com.sf.roles.Rol, java.util.List)
	 */
	public boolean unasignOpcionesRol(Rol rol, List<Opcion> opciones) throws BusinessException, DAOException {
		//TODO REVISAR SI SE NECESITA O NO METODO
		return false;
	}

	
	public Opcion getOpciones(String descripcion) throws BusinessException, DAOException {
		Opcion opcion = null;
		
		opcion = (Opcion) entityManager.createQuery("SELECT o FROM Opcion o WHERE o.descripcion = :pDescripcion")
				.setParameter("pDescripcion", descripcion)
				.getSingleResult();

		return opcion;
	}

	
	public String getCodigoOpcion(String url) throws BusinessException, DAOException, NoResultException { 
		url = (String) entityManager.createQuery("SELECT o.codigo FROM Opcion o WHERE o.url = :pUrl")
				.setParameter("pUrl", url)
				.getSingleResult();
		
		return url;
	}
	
	/**
	 * Inicialmente usado para remover la opcion /Login de la parametrizacion
	 * @param opciones
	 * @param url
	 * @return
	 */
	private List<Opcion> removerOpcion(List<Opcion> opciones, String url){
		List<Opcion> opcionesDepuradas = new ArrayList<Opcion>();
		
		for (Opcion opcion : opciones) {
			if(!opcion.getUrl().equalsIgnoreCase(url)) {
				opcionesDepuradas.add(opcion);
			}
		}
		
		return opcionesDepuradas;
	}

	
}
