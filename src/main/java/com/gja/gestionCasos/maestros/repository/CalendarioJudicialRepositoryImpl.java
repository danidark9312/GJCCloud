package com.gja.gestionCasos.maestros.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.sf.util.repository.AbstractRepository;

@Repository("calendarioJudicialRepository")

public class CalendarioJudicialRepositoryImpl extends AbstractRepository<TipoCaso> implements CalendarioJudicialRepository{
	


@Override
public void saveFechaCalendario(Date fecha) {
	 entityManager.createNativeQuery("insert into dias_inhabiles (fecha) values(:pDate)")
	 .setParameter("pDate", fecha)
	 .executeUpdate();
}

@Override
public int deleteFechaCalendario(Date fecha) {
	return entityManager.createNativeQuery("delete from dias_inhabiles where fecha = :pDate")
			.setParameter("pDate", fecha)
			.executeUpdate();
}

@Override
public Date[] findAll() {
	List<Date> fechas = new ArrayList<Date>();
	List<Object[]> resultList = entityManager.createNativeQuery("select * from dias_inhabiles").getResultList();
	for (Object[] object : resultList) {
		fechas.add((Date) object[1]);
	}
	return fechas.toArray(new Date[0]);
}


}
