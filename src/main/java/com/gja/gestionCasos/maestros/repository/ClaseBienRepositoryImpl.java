package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.maestros.entities.ClaseBien;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

/*ClaseBienRepositoryImpl: Clase para realizar la conexión con la base de datos con la tabla ClasesBienes
* @author: Cristian Mauricio Arenas-Soluciones Futuras  
* @version: Nuevo Caso
* @since: 1.0 
* @FechaCreacion: 14/04/2015 */


@Repository("claseBienRepository")
public class ClaseBienRepositoryImpl extends AbstractRepository<ClaseBien>implements ClaseBienRepository {

	public List<ClaseBien> obtenerClaseBien() throws DAOException,BusinessException
	{
		List<ClaseBien> clasesBienes=null;
		
		clasesBienes=entityManager.createQuery("SELECT c FROM ClaseBien c  ORDER BY c.nombre").getResultList();		
		
		return clasesBienes;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public ClaseBien consultarCodigoClaseBien(ClaseBien claseBien) {
		ClaseBien resultado;
		try {
			resultado =  (ClaseBien) entityManager.createQuery("SELECT DISTINCT b FROM ClaseBien b  WHERE b.nombre = :pDsClaseBien")
					.setParameter("pDsClaseBien",claseBien.getNombre())
					.getSingleResult();
		} catch (Exception e) {
			resultado = null;
		}
			
		return resultado;
	}
	
}
