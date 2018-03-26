package com.gja.gestionCasos.maestros.service;

import java.util.Date;

import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface CalendarioJudicialService {

	void saveFechaCalendarioJudicial(Date fecha) throws DAOException,BusinessException;
	int deleteFechaCalendarioJudicial(Date fecha) throws DAOException,BusinessException;
	Date[] findAll() throws DAOException, BusinessException;
	
}
