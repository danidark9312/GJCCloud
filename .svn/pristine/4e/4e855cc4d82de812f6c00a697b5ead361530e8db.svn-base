package com.sf.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gja.gestionCasos.actividades.service.EnvioNotificacion;
import com.gja.gestionCasos.actividades.service.ValidarVencimientos;



@Service
public class Init {

	
	@Autowired
	private EnvioNotificacion envioNotificacion;
	@Autowired
	private ValidarVencimientos validarVencimientos;
	
	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		envioNotificacion.start();
		validarVencimientos.start();
	}
}
