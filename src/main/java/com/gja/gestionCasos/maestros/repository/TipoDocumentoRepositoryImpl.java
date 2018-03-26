package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.Parentesco;
import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("tipoDocumentoRepository")
public class TipoDocumentoRepositoryImpl extends AbstractRepository<TipoDocumento> implements TipoDocumentoRepository{

	private static final Logger LOG = Logger
			.getLogger(TipoCasoRepositoryImpl.class);
	
	public List<TipoDocumento> obtenerTiposDeDocumentos() throws DAOException,BusinessException{
		
		List<TipoDocumento> tiposDocumento=null;
		
		tiposDocumento = entityManager.createQuery("SELECT p FROM TipoDocumento p  ORDER BY p.documento").getResultList();
		return tiposDocumento;
	}
	
	
	@SuppressWarnings("unchecked")
	public TipoDocumento  obtenerCodigoTipoDocumento(TipoDocumento tipoDocumento) throws DAOException {
		TipoDocumento  resultado;
		try {
			resultado =  (TipoDocumento) entityManager.createQuery("SELECT pt FROM TipoDocumento pt WHERE UPPER(pt.documento) = :pNombreTipoCaso")
					.setParameter("pNombreTipoCaso", tipoDocumento.getDocumento())
					.getSingleResult();
		} catch (Exception e) {
			resultado = null;
		}
		return resultado;
	}
}
