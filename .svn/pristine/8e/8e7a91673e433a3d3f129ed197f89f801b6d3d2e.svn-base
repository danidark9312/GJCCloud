package com.gja.gestionCasos.actividades.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.casos.service.TareaParticularCasoService;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Service
public class ValidarVencimientos extends Thread {

	
	private static final Logger LOG = Logger.getLogger(EnvioNotificacion.class);

	@Autowired
	private TareaParticularCasoService tareaParticularCasoService;
	
	@Autowired
	ActividadCasoService actividadCasoService;
	
	public void run() {
		
		try {
			while (true) {
				SimpleDateFormat formatoHora = new SimpleDateFormat("HH");
				Integer hora = Integer.valueOf(Integer.parseInt(formatoHora.format(new Date())));
				
				if (Parametros.getHoraInicioProcesovencimientos() == hora) {					
					List<ActividadCaso> actividadesVencidas = actividadCasoService.obtenerActividadesVencidas();
					for (ActividadCaso actividad:actividadesVencidas) {
						vencerActividad(actividad);
					}
					
					List<ActividadCaso> actividadesPendientes = actividadCasoService.obtenerActividadesPendientes();
					for (ActividadCaso actividad:actividadesPendientes) {
						validarVencimientoActividad(actividad);
					}
					dormirHastaSiguienteEjecucion();
				} else {
					dormirHastaSiguienteEjecucion();
				}
				
			}
		} catch (DAOException e) {
		    LOG.error("Error en el proceso automatico de validación de vencimientos:\n" + e.getMessage());
	    } catch (BusinessException e) {
	    	LOG.error("Error en el proceso automatico de validación de vencimientos:\n" + e.getMessage());
	    } catch (InterruptedException e) {
	    	LOG.error("Error en el proceso automatico de validación de vencimientos:\n" + e.getMessage());
	    } catch (Exception e) {
	    	LOG.error("Error en el proceso automatico de validación de vencimientos:\n" + e.getMessage());
	    }
	}

	private void dormirHastaSiguienteEjecucion() throws InterruptedException {
		Date horaActual = new Date();					
		Calendar horaSiguienteEjecucion = Calendar.getInstance();
		horaSiguienteEjecucion.add(Calendar.DATE, 1);
		horaSiguienteEjecucion.set(Calendar.HOUR_OF_DAY, Parametros.getHoraInicioProcesovencimientos());
		horaSiguienteEjecucion.set(Calendar.MINUTE, Parametros.getMinutoInicioProcesovencimientos());
		sleep(horaSiguienteEjecucion.getTime().getTime() - horaActual.getTime());
	}

	private void validarVencimientoActividad(ActividadCaso actividad) throws DAOException, BusinessException {
		if (actividad.getTareaParticularCasoSet() != null && !actividad.getTareaParticularCasoSet().isEmpty()) {
			int totalTareas = actividad.getTareaParticularCasoSet().size();
			int totalTareasVencidas = 0;
			for (TareaParticularCaso tarea:actividad.getTareaParticularCasoSet()) {
				if (isTareaVencida(tarea))
					totalTareasVencidas++;
			}
			if (totalTareasVencidas == totalTareas)
				vencerActividad(actividad);
			else
				validarVencimientoTareas(actividad.getTareaParticularCasoSet());
		}
	}

	private void vencerActividad(ActividadCaso actividad) throws DAOException, BusinessException {
		actividadCasoService.vencerActividad(actividad);
	}
	
	private boolean isTareaVencida(TareaParticularCaso tareaActividadPendiente) {
		Calendar fechaActual = Calendar.getInstance();
		if (Parametros.getActividadPendiente().equals(tareaActividadPendiente.getFinalizada()) && tareaActividadPendiente.getFechaLimite().before(fechaActual.getTime())) 
			return true;
		else
			return false; 
	}

	private void validarVencimientoTareas(List<TareaParticularCaso> tareasActividadPendiente) throws DAOException, BusinessException {
		for (TareaParticularCaso tarea:tareasActividadPendiente) {
			if (isTareaVencida(tarea))
				vencerTarea(tarea);
		}
		
	}

	private void vencerTarea(TareaParticularCaso tarea) throws DAOException, BusinessException {
		tareaParticularCasoService.vencerTarea(tarea);
	}
}
