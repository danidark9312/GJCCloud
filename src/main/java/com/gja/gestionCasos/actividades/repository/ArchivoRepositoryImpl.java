package com.gja.gestionCasos.actividades.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.actividades.entities.Archivo;
import com.gja.gestionCasos.maestros.repository.TipoCasoRepositoryImpl;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("archivoRepository")
public class ArchivoRepositoryImpl extends AbstractRepository<Archivo> implements ArchivoRepository {
	private static final Logger LOG = Logger
			.getLogger(TipoCasoRepositoryImpl.class);
	
	
	public List<Archivo> findByPK(Archivo archivo) throws DAOException {
		List<Archivo> archivoReturn;
		try {
			archivoReturn =  entityManager.createQuery("SELECT a FROM Archivo a WHERE a.cdtareaparticular.codigoTarea= :pCodigoTarea",Archivo.class)
					.setParameter("pCodigoTarea", archivo.getCdtareaparticular().getCodigoTarea()).getResultList()
					;
			
			} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error consultando el archivo por PK " + archivo.getCdarchivo() + ". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Exception: Error consultando el archivo por PK " + archivo.getCdarchivo() + ". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		return archivoReturn;
	}
}
