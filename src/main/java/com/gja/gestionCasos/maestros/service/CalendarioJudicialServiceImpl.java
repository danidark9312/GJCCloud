package com.gja.gestionCasos.maestros.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.repository.CalendarioJudicialRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("calendarioJudicialService")
@Transactional(readOnly = true)
public class CalendarioJudicialServiceImpl implements CalendarioJudicialService{

	
	@Autowired
	CalendarioJudicialRepository calendarioJudicialRepository;
	


	@Transactional
	public void saveFechaCalendarioJudicial(Date fecha) throws DAOException, BusinessException {
		calendarioJudicialRepository.saveFechaCalendario(fecha);
	}



	@Override
	public int deleteFechaCalendarioJudicial(Date fecha) throws DAOException, BusinessException {
		return calendarioJudicialRepository.deleteFechaCalendario(fecha);
	}
	
	@Override
	public Date[] findAll() throws DAOException, BusinessException {
		return calendarioJudicialRepository.findAll();
	}
		
	
}
