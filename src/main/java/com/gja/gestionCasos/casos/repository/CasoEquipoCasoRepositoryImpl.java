package com.gja.gestionCasos.casos.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.Param;
import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCasoPK;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;

@Repository("casoEquipoCasoRepository")
public class CasoEquipoCasoRepositoryImpl extends AbstractRepository<CasoEquipoCaso> implements CasoEquipoCasoRepository {

	public CasoEquipoCaso guardarCasoEquipoCaso(CasoEquipoCaso casoEquipoCaso)
			throws DAOException, BusinessException {
	
 		casoEquipoCaso = this.merge(casoEquipoCaso);
		return casoEquipoCaso;
	}

	public CasoEquipoCaso consultarCasoEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK)
			throws DAOException, BusinessException {
		
		
		CasoEquipoCaso  casoEquipoCaso = entityManager.find(CasoEquipoCaso.class, casoEquipoCasoPK);
		return casoEquipoCaso;
	}
	public void borrarCasoEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK)
			throws DAOException, BusinessException{
		entityManager.remove(consultarCasoEquipoCaso(casoEquipoCasoPK));
		
	}
	
	public List<CasoEquipoCaso> obtenerMiembrosCaso(
			CasoEquipoCasoPK casoEquipoCasoPK) throws DAOException,
			BusinessException {
		List<CasoEquipoCaso> miembros = entityManager.createQuery("SELECT c FROM CasoEquipoCaso c WHERE c.casoEquipoCasoPK.codigo = :pCodigoCaso "
				+ "AND c.casoEquipoCasoPK.codigoEquipoCaso != :pCodigoEquipoCaso AND c.activo = :pMiembroActivo AND c.casoEquipoCasoPK.miembro != :pTipoAbogado "
				+ "AND c.casoEquipoCasoPK.miembro != :pTipoDemandado")
				.setParameter("pCodigoCaso", casoEquipoCasoPK.getCodigo())
				.setParameter("pCodigoEquipoCaso", casoEquipoCasoPK.getCodigoEquipoCaso()).setParameter("pMiembroActivo", Parametros.getEstadoActivo())
				.setParameter("pTipoAbogado", Integer.parseInt(Parametros.getAbogado()))
				.setParameter("pTipoDemandado", Integer.parseInt(Parametros.getDemandado())).getResultList();
		return miembros;
	}
	
	@Override
	public List<CasoEquipoCaso> obtenerAbogadosDelCaso(Integer codigoCaso) throws DAOException, BusinessException {
		List<CasoEquipoCaso> abogadosDelcaso = new ArrayList<CasoEquipoCaso>();
		abogadosDelcaso = entityManager.createQuery("SELECT a FROM CasoEquipoCaso a JOIN a.equipoCaso b WHERE a.casoEquipoCasoPK.codigo = :pCodigocaso AND a.casoEquipoCasoPK.miembro = :pTipoMiembro")
							.setParameter("pCodigocaso", codigoCaso)
							.setParameter("pTipoMiembro", Integer.parseInt(Parametros.getAbogado()))
//							.setParameter("pTipoMiembroAsociado", Integer.parseInt(Parametros.getRolAsociado())) 
//							.setParameter("pTipoMiembroDependiente", Integer.parseInt(Parametros.getRolDependiente()))
//							.setParameter("pTipoMiembroSocio", Integer.parseInt(Parametros.getRolSocio()))
//							.setParameter("pTipoMiembroOtro", Integer.parseInt(Parametros.getOtro()))
							.getResultList();
		return abogadosDelcaso;
	}
	
}
