package com.gja.gestionCasos.casos.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.casos.entities.ParametroReportePrestamo;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.PrestamoConsolidadoView;
import com.gja.gestionCasos.casos.entities.PrestamoReporte;
import com.gja.gestionCasos.casos.repository.PrestamoRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("prestamoService")
@Transactional(readOnly = true)
public class PrestamoServiceImpl implements PrestamoService {
	
	enum TipoCalculoInteres{
		COMPLETO,SOLO_DEUDA;
	}
	
	@Autowired
	PrestamoRepositoryImpl PrestamoRepository;
	
	
	public Prestamo obtenerPrestamo(Prestamo prestamo)
			throws DAOException, BusinessException {
		prestamo = PrestamoRepository.obtenerPrestamo(prestamo);
		Hibernate.initialize(prestamo.getTipoCuenta());
		return prestamo;
	}

	@Transactional
	public Prestamo actualizarPrestamo(Prestamo prestamo) throws DAOException, BusinessException{
		return  PrestamoRepository.guardarPrestamo(prestamo);
		
	}
	
	
	public List<PrestamoReporte> loadPrestamoReporte(ParametroReportePrestamo parametroReportePrestamo) throws DAOException, BusinessException{
		List<PrestamoReporte> prestamosReportes = new ArrayList<PrestamoReporte>();
		List<PrestamoConsolidadoView> loadPrestamoReporte = PrestamoRepository.loadPrestamoReporte(parametroReportePrestamo);
		
		for (PrestamoConsolidadoView pcv : loadPrestamoReporte) {
			PrestamoReporte prestamoReporte = new PrestamoReporte();
			prestamoReporte.setSaldoCapital(pcv.getSaldoCapital());
			prestamoReporte.setSaldoInteres(pcv.getSaldoInteres());
			Double saldoInteres = calcularInteres(pcv,TipoCalculoInteres.SOLO_DEUDA);
			prestamoReporte.setSaldoInteres(saldoInteres);
			if(pcv.getSaldoCapital()!=null && pcv.getSaldoInteres()!=null) {
				prestamoReporte.setSaldoTotal(pcv.getSaldoCapital()+pcv.getSaldoInteres());
				prestamoReporte.setSaldoCapital(pcv.getSaldoCapital());
			}else { 
				prestamoReporte.setSaldoCapital(pcv.getValorprestamo());
				prestamoReporte.setSaldoTotal(saldoInteres+pcv.getValorprestamo());
			}
			
			if(pcv.getAbonoCapital()!=null && pcv.getAbonoInteres()!=null)
				prestamoReporte.setTotalPagado(pcv.getAbonoCapital()+pcv.getAbonoInteres());
			else
				prestamoReporte.setTotalPagado(0.0);
			prestamoReporte.setTotalIntereses(calcularInteres(pcv,TipoCalculoInteres.COMPLETO)); //Sumar intereses a la fecha de hoy
			prestamoReporte.setTotalPrestamo(pcv.getValorprestamo());
			prestamosReportes.add(prestamoReporte);
			
		}
		
		return  prestamosReportes;
		
	}

	private Double calcularInteres(PrestamoConsolidadoView pcv, TipoCalculoInteres tipoCalculoInteres) {
		Double saldoIntres = 0.0;
		
		if ((tipoCalculoInteres.equals(TipoCalculoInteres.COMPLETO))) {
			if (pcv.getSaldoInteres() != null)
				saldoIntres += pcv.getSaldoInteres();
			if (pcv.getAbonoInteres() != null)
				saldoIntres += pcv.getAbonoInteres();
		}
		
		double interes = pcv.getInteres()/100;
		double interesDiario = (interes)/30; 
		
		int diasTranscurridos = calcularDiasTranscurridos(pcv.getFechaUltimoAbono()==null?pcv.getFechaprestamo():pcv.getFechaUltimoAbono());
		
		double valorInteresHoy = interesDiario*diasTranscurridos;
		if(pcv.getSaldoCapital()!=null && pcv.getSaldoCapital()!= 0.0) 
			valorInteresHoy*=pcv.getSaldoCapital();
		else
			valorInteresHoy*=pcv.getValorprestamo();
		
		
		saldoIntres+=valorInteresHoy;
		
		
		return Math.floor(saldoIntres);
	}
	
	private int calcularDiasTranscurridos(Date fechaInicial) {
		Date fechaFinal = new Date();
		
		long fechainicialms = fechaInicial.getTime();
		long fechafinalms = fechaFinal.getTime();
		long diferencia = fechafinalms - fechainicialms;
		double dias = Math.floor(diferencia / 86400000L);// 3600*24*1000 
		
		return (int)dias;
	}


	
}
