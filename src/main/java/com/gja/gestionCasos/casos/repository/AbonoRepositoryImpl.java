package com.gja.gestionCasos.casos.repository;


import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.casos.entities.Abono;
import com.gja.gestionCasos.casos.entities.ArchivoAbono;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;

@Repository("abonoRepository")
public class AbonoRepositoryImpl extends AbstractRepository<Abono> implements AbonoRepository{

	public Abono guardarAbono(Abono abono) throws DAOException,
			BusinessException {
		
		abono=this.merge(abono);
		return abono;
	}
	
	public ArchivoAbono guardarArchivoAbono(ArchivoAbono archivoAbono) throws DAOException,
	BusinessException {
		ArchivoAbono saveArchivo = this.entityManager.merge(archivoAbono);
		return saveArchivo;
	}
	
	public List<Abono> findAbonosByPrestamo(Prestamo prestamo) throws DAOException, BusinessException {
		Query query = this.entityManager.createQuery("select a from Abono a where prestamo.codigoPrestamo = :prestamo order by fecha",Abono.class);
		query.setParameter("prestamo", prestamo.getCodigoPrestamo());
		List<Abono> abonos = query.getResultList();
		return abonos;
	}
	public void deleteAbono(Abono abono) throws DAOException, BusinessException {
		Abono abonoDB = this.entityManager.find(Abono.class, abono.getCodigo());
		this.entityManager.remove(abonoDB);
	}
	public Double getTotalPagado(Prestamo prestamo) throws DAOException, BusinessException {
		Query createNativeQuery = this.entityManager.createNativeQuery("select (sum(vlabonocapital)+sum(vlabonointeres)) as totalPagado from abonos where cdprestamo=:prestamo");
		createNativeQuery.setParameter("prestamo", prestamo.getCodigoPrestamo());
		
		return (Double) createNativeQuery.getSingleResult();
	}
	public Double getInteresesPagados(Prestamo prestamo) throws DAOException, BusinessException {
		Query createNativeQuery = this.entityManager.createNativeQuery("select (sum(vlabonointeres)) as interesesPagados from abonos where cdprestamo=:prestamo");
		createNativeQuery.setParameter("prestamo", prestamo.getCodigoPrestamo());
		
		return (Double) createNativeQuery.getSingleResult();
	}
	
	public Abono findUltimoAbono(Prestamo prestamo) throws DAOException, BusinessException {
		Query query = this.entityManager.createQuery(
				"select a from Abono a where a.fecha = (select max(a2.fecha)"
				+ " from Abono a2 where a2.prestamo.codigoPrestamo = :prestamo) and a.prestamo.codigoPrestamo = :prestamo order by codigo desc",Abono.class);
		query.setParameter("prestamo", prestamo.getCodigoPrestamo());
		List<Abono> resultList = query.getResultList();
		
		return resultList.size()>0?resultList.get(0):null;
	}

	
	
	
	public Abono obtenerAbono(Abono abono)
			throws DAOException, BusinessException {
		abono = entityManager.find(Abono.class, abono.getCodigo());
		
		return abono;
	}	
}
