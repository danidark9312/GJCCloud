package com.gja.gestionCasos.maestros.repository;

import java.util.Date;

public interface CalendarioJudicialRepository {
	void saveFechaCalendario(Date fecha);
	int deleteFechaCalendario(Date fecha);
	Date[] findAll();

}
