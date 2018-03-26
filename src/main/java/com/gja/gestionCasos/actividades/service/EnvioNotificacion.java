package com.gja.gestionCasos.actividades.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gja.gestionCasos.actividades.entities.AlertaTareaParticular;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.casos.service.CasoService;
import com.gja.gestionCasos.casos.service.TareaParticularCasoService;
import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;
import com.gja.gestionCasos.maestros.entities.AlertasTareasRolDetalle;
import com.gja.gestionCasos.maestros.entities.AlertasTareasVencidaRolDetalle;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.gja.gestionCasos.maestros.service.MaestroEscalamientoNotificacionService;
import com.sf.roles.RolesUsuarios;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Service
public class EnvioNotificacion extends Thread {
	
	private static final Logger LOG = Logger.getLogger(EnvioNotificacion.class);

	@Autowired
	private TareaParticularCasoService tareaParticularCasoService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private CasoService casoService;
	
	@Autowired
	MaestroEscalamientoNotificacionService maestroEscalamientoNotificacionService;
	
	private boolean alertaVencimiento = true;
	
	public void run() {
		
		try {
			while (true) {
				enviarNotificacionEscalmientoAlerta();
				enviarNotificacionEscalmientoAlertaVencida();
				if (alertaVencimiento) {
					alertaVencimiento = false;
					List<TareaParticularCaso> tareasPendientes = tareaParticularCasoService.obtenerTareasVencimientoHoy();
					for (TareaParticularCaso tarea:tareasPendientes) {
						try {
							enviarCorreo(tarea);							
						} catch (MessagingException e) {
							LOG.error("Error en el proceso automatico al intentar enviar el correo:\n" + e.getMessage());
						}
					}
				}
				SimpleDateFormat formatoHora = new SimpleDateFormat("HH");
		        SimpleDateFormat formatoMinuto = new SimpleDateFormat("mm");
		        Integer hora = Integer.valueOf(Integer.parseInt(formatoHora.format(new Date())));
		        List<TareaParticularCaso> tareasPendientes = tareaParticularCasoService.obtenerTareasPendientes();
				if (hora >= Parametros.getHoraInicioProceso() && hora <= Parametros.getHoraFinProceso()) {
					for (TareaParticularCaso tarea:tareasPendientes) {
						if (tarea.getNumeroAlertasPorDia() != null && tarea.getNumeroDiasAntesAlertas() != null) {							
							Calendar fechaLimiteTarea = Calendar.getInstance();
							fechaLimiteTarea.setTime(tarea.getFechaLimite());
							fechaLimiteTarea.set(Calendar.HOUR_OF_DAY, 0);
							fechaLimiteTarea.set(Calendar.MINUTE, 0);
							fechaLimiteTarea.set(Calendar.SECOND, 0);
							fechaLimiteTarea.add(Calendar.DAY_OF_MONTH, (- tarea.getNumeroDiasAntesAlertas()));
							Calendar fechaActual = Calendar.getInstance();
							fechaActual.set(Calendar.HOUR_OF_DAY, 23);
							fechaActual.set(Calendar.MINUTE, 0);
							fechaActual.set(Calendar.SECOND, 0);
							fechaActual.add(Calendar.DAY_OF_MONTH, tarea.getNumeroDiasAntesAlertas());
							if (fechaLimiteTarea.before(fechaActual)) {
								AlertaTareaParticular alertaTarea = tareaParticularCasoService.obtenerAlertaPorTarea(tarea);
								if (validarNotificacionesEnviadas(alertaTarea)) {
									if (alertaTarea != null) {
										if (validarTiempoDeEnvio(tarea, alertaTarea)) {
											try {
												enviarCorreo(tarea, alertaTarea);							
											} catch (MessagingException e) {
												LOG.error("Error en el proceso automatico al intentar enviar el correo:\n" + e.getMessage());
											}
										}
									} else {
										try {
											enviarCorreo(tarea, null);							
										} catch (MessagingException e) {
											LOG.error("Error en el proceso automatico al intentar enviar el correo:\n" + e.getMessage());
										}
									}
								}
							}
						}
					}
				} else {
					// Se para el proceso hasta la asignada de inicio de proceso.
					if (tareasPendientes != null && !tareasPendientes.isEmpty()) {
						for (TareaParticularCaso tarea:tareasPendientes) {
							AlertaTareaParticular alertaTarea = tareaParticularCasoService.obtenerAlertaPorTarea(tarea);
							if (alertaTarea != null) {
								alertaTarea.setNumeroAlertasEnviadas(0);
								tareaParticularCasoService.guardarAlerta(alertaTarea);
							}
						}
					}
					Calendar horaActual = Calendar.getInstance();
					Calendar horaInicioProceso = Calendar.getInstance();
					horaInicioProceso.set(Calendar.HOUR_OF_DAY, Parametros.getHoraInicioProceso());
					horaInicioProceso.set(Calendar.MINUTE, 0);
					horaInicioProceso.set(Calendar.SECOND, 0);
					if (horaActual.after(horaInicioProceso)) {
						horaInicioProceso.add(Calendar.DATE, 1);
					}
					alertaVencimiento = true;
					Long timeMillis = horaInicioProceso.getTimeInMillis() - horaActual.getTimeInMillis();
					sleep(timeMillis);
				}
				sleep(Parametros.getTiempoReabrirProcesoNotificaciones().longValue() * 60000L);
			}
		
		} catch (DAOException e) {
		    LOG.error("Error en el proceso automatico en la capa de persistencia:\n" + e.getMessage());
	    } catch (BusinessException e) {
	    	LOG.error("Error en el proceso automatico en la capa de negocio:\n" + e.getMessage());
	    } catch (InterruptedException e) {
	    	LOG.error("Error en el proceso automatico se ha interrumpido inesperadamente:\n" + e.getMessage());
	    }
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean enviarNotificacionEscalmientoAlertaVencida() throws DAOException, BusinessException{
		boolean result = false;
		try {
			SimpleDateFormat formatoHora = new SimpleDateFormat("HH");
	        Integer hora = Integer.valueOf(Integer.parseInt(formatoHora.format(new Date())));
	        List<AlertaTareaVencidaRol> tareasPendientes = maestroEscalamientoNotificacionService.consultarNotificacionesVencidas();
			if (hora >= Parametros.getHoraInicioProceso() && hora <= Parametros.getHoraFinProceso()) {
				if (null != tareasPendientes){
					for(AlertaTareaVencidaRol objAlerta : tareasPendientes){
						if(validarNotificacionesEnviadasRolVencida(objAlerta) && validarTiempoDeEnvioVencida(objAlerta)){
							if(null == objAlerta.getTotalNotificacion() || objAlerta.getTotalNotificacion() == 0){
								objAlerta.setTotalNotificacion(1);
							}else{
								objAlerta.setTotalNotificacion(objAlerta.getTotalNotificacion()+1);
							}
							enviarCorreoTareaRolVencida(objAlerta);
							maestroEscalamientoNotificacionService.updateNotificacionVencida(objAlerta);
						}
					}
				}
			}
			
		} catch (Exception e) {
			LOG.error("Error enviando las notificaciones de escalamiento:\n" + e.getMessage());
		}
		
		return result;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void enviarCorreoTareaRolVencida(AlertaTareaVencidaRol AlertaTareaRol) throws MessagingException, DAOException, BusinessException {
		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.getFormatoFechaString());
		MimeMessage message = null;
		MimeMessageHelper helper = null;
		message = this.mailSender.createMimeMessage();
		helper = new MimeMessageHelper(message, true);
		List to = new ArrayList();
		try {
			for (AlertasTareasVencidaRolDetalle objDetalle:AlertaTareaRol.getAlertasTareasDespuesRolDetalleList()) {
				List<Object> listUsuarios =maestroEscalamientoNotificacionService.getUsuarioByRol(objDetalle.getRol(),new TipoCaso(AlertaTareaRol.getTipoCaso()));
				if(null != listUsuarios){
					for(Object objt :listUsuarios){
						to.add(objt.toString());
					}
				}
			}
			if (to.size() > 0) {
				StringBuilder mensaje = new StringBuilder();
				mensaje.append("La tarea "+ AlertaTareaRol.getTareaParticular().getTarea()+ " asignada a:" );
				for( ResponsableTarea objResponsable: AlertaTareaRol.getTareaParticular().getResponsablesTareas()){
					if(null != objResponsable.getCasosEquiposCaso().getEquipoCaso().getNombre())
						mensaje.append(objResponsable.getCasosEquiposCaso().getEquipoCaso().getNombre() +" "+objResponsable.getCasosEquiposCaso().getEquipoCaso().getApellido());
				}
				mensaje.append("Se vence en la fecha "+AlertaTareaRol.getTareaParticular().getFechaLimiteString());
				helper.setTo((String[])to.toArray(new String[0]));
				helper.setSubject("Escalamiento de Notficación");
				helper.setText(mensaje.toString());
				this.mailSender.send(message);

			}
		} catch (Exception e) {
			LOG.error("Error; enviando la notificacion de escalamiento vecida");
			e.printStackTrace();
		}

}
	
	
	private boolean validarNotificacionesEnviadasRolVencida(AlertaTareaVencidaRol AlertaTareaRol) throws DAOException, BusinessException {
		boolean result = false;
		try {
			if (null == AlertaTareaRol.getTotalNotificacion() || AlertaTareaRol.getNumeroAlertasDiarias() >= AlertaTareaRol.getTotalNotificacion()) {				
				result = true;
			} else {				
				result = false;
			}
		} catch (Exception e) {
			LOG.error("Error valiando las notificaciones por rol:\n" + e.getMessage());
		}
		return result;
	}
	
	
	
	private boolean validarTiempoDeEnvioVencida(AlertaTareaVencidaRol AlertaTareaRol) throws DAOException, BusinessException {
		boolean resul=false;
		try {
			Integer duracionProceso = Parametros.getHoraFinProceso() - Parametros.getHoraInicioProceso();
			Double tiempoEntreAlertas = duracionProceso.doubleValue() / AlertaTareaRol.getNumeroAlertasDiarias().doubleValue();
			Date horaUltimaAlerta = AlertaTareaRol.getFeUltimaAlerta();
			Calendar horaSiguienteEnvio = Calendar.getInstance();
			horaSiguienteEnvio.setTime(horaUltimaAlerta);
			if (tiempoEntreAlertas.intValue() != 0) {			
				horaSiguienteEnvio.add(Calendar.MILLISECOND, tiempoEntreAlertas.intValue() * 3600000);
			} else {
				resul =  true;
			}
			Calendar horaActual = Calendar.getInstance();
			if (horaActual.after(horaSiguienteEnvio)) {
				resul =  true;
			} else {
				resul = false;
			}
		} catch (Exception e) {
			LOG.error("Error; validando El tiempo de envio de la notificacion");
		}
		return resul;
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean enviarNotificacionEscalmientoAlerta() throws DAOException, BusinessException{
		boolean result = false;
		try {
			SimpleDateFormat formatoHora = new SimpleDateFormat("HH");
	        Integer hora = Integer.valueOf(Integer.parseInt(formatoHora.format(new Date())));
	        List<AlertaTareaRol> tareasPendientes = maestroEscalamientoNotificacionService.consultarNotificacionesAbiertas();
			if (hora >= Parametros.getHoraInicioProceso() && hora <= Parametros.getHoraFinProceso()) {
				if (null != tareasPendientes){
					for(AlertaTareaRol objAlerta : tareasPendientes){
						if(validarNotificacionesEnviadasRol(objAlerta) && validarTiempoDeEnvio(objAlerta)){
							enviarCorreoTareaRol(objAlerta);
						}
						maestroEscalamientoNotificacionService.updateNotificacion(objAlerta);
					}
				}
			}
			
		} catch (Exception e) {
			LOG.error("Error enviando las notificaciones de escalamiento:\n" + e.getMessage());
		}
		
		return result;
		
	}
	
	

	
	private boolean validarNotificacionesEnviadasRol(AlertaTareaRol AlertaTareaRol) throws DAOException, BusinessException {
		boolean result = false;
		try {
			if (null == AlertaTareaRol.getTotalNotificacion() || AlertaTareaRol.getNumeroAlertaDiaria() >= AlertaTareaRol.getTotalNotificacion()) {				
				result = true;
			} else {				
				result = false;
			}
		} catch (Exception e) {
			LOG.error("Error valiando las notificaciones por rol:\n" + e.getMessage());
		}
		return result;
	}
	
	
	
	private boolean validarTiempoDeEnvio(AlertaTareaRol AlertaTareaRol) throws DAOException, BusinessException {
		boolean result = false;
		try {
			Integer duracionProceso = Parametros.getHoraFinProceso() - Parametros.getHoraInicioProceso();
			Double tiempoEntreAlertas = duracionProceso.doubleValue() / AlertaTareaRol.getNumeroAlertaDiaria().doubleValue();
			Date horaUltimaAlerta = AlertaTareaRol.getFePrimeraAlerta();
			Calendar horaSiguienteEnvio = Calendar.getInstance();
			horaSiguienteEnvio.setTime(horaUltimaAlerta);
			if (tiempoEntreAlertas.intValue() != 0) {			
				horaSiguienteEnvio.add(Calendar.MILLISECOND, tiempoEntreAlertas.intValue() * 3600000);
			} else {
				 result =  true;
			}
			Calendar horaActual = Calendar.getInstance();
			if (horaActual.after(horaSiguienteEnvio)) {
				 result =  true;
			} else {
				 result =  false;
			}
		} catch (Exception e) {
			LOG.error("Error; validando tiempo de envio de notificaciones");
			e.printStackTrace();
		}
		return result;

	}
	
	@SuppressWarnings("unchecked")
	private void enviarCorreoTareaRol(AlertaTareaRol AlertaTareaRol) throws MessagingException, DAOException, BusinessException {
		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.getFormatoFechaString());
		MimeMessage message = null;
		MimeMessageHelper helper = null;
		message = this.mailSender.createMimeMessage();
		helper = new MimeMessageHelper(message, true);
		List to = new ArrayList();
		try {
			if(null != AlertaTareaRol.getAlertasTareasRolDetalleCollection()){
				for (AlertasTareasRolDetalle objDetalle:AlertaTareaRol.getAlertasTareasRolDetalleCollection()) {
					List<Object> listUsuarios =maestroEscalamientoNotificacionService.getUsuarioByRol(objDetalle.getRol(),new TipoCaso(AlertaTareaRol.getTipoCaso()));
					if(null != listUsuarios){
						for(Object objt :listUsuarios){
							to.add(objt.toString());
						}
					}
				}
				if (to.size() > 0) {
					StringBuilder mensaje = new StringBuilder();
					mensaje.append("La tarea "+ AlertaTareaRol.getTareaparticular().getTarea()+ " asignada a:" );
					for( ResponsableTarea objResponsable: AlertaTareaRol.getTareaparticular().getResponsablesTareas()){
						if(null != objResponsable.getCasosEquiposCaso().getEquipoCaso().getNombre())
							mensaje.append(objResponsable.getCasosEquiposCaso().getEquipoCaso().getNombre() +" "+objResponsable.getCasosEquiposCaso().getEquipoCaso().getApellido());
					}
					mensaje.append("Se vence en la fecha "+AlertaTareaRol.getTareaparticular().getFechaLimite());
					helper.setTo((String[])to.toArray(new String[0]));
					helper.setSubject("Escalamiento de Notficación");
					helper.setText(mensaje.toString());
					this.mailSender.send(message);
			}
		}
		} catch (Exception e) {
			LOG.error("Error; enviando la notificacion de escalamiento vencidas");
			e.printStackTrace();
		}
	
}
	
	private boolean validarNotificacionesEnviadas(AlertaTareaParticular alertaTarea) throws DAOException, BusinessException {
		boolean result = false;
		try {
			if (alertaTarea == null)
				 result = true;
			else {
				if (alertaTarea.getTotalEnvios() < alertaTarea.getTotalAlertas()) {				
					if (alertaTarea.getNumeroAlertasEnviadas() < alertaTarea.getNumeroAlertasTotales())
						 result = true;
					else
						 result = false;
				} else {				
					 result = false;
				}
			}
		} catch (Exception e) {
			LOG.error("Error; validando las notificaciones enviadas");
			e.printStackTrace();
		}
		return  result;
	}
	
	private boolean validarTiempoDeEnvio(TareaParticularCaso tarea, AlertaTareaParticular alertaTarea) throws DAOException, BusinessException {
		boolean result = false;
		try {
			Integer duracionProceso = Parametros.getHoraFinProceso() - Parametros.getHoraInicioProceso();
			Double tiempoEntreAlertas = duracionProceso.doubleValue() / tarea.getNumeroAlertasPorDia().doubleValue();
			Date horaUltimaAlerta = alertaTarea.getFechaUltimaAlerta();
			Calendar horaSiguienteEnvio = Calendar.getInstance();
			horaSiguienteEnvio.setTime(horaUltimaAlerta);
			if (tiempoEntreAlertas.intValue() != 0) {			
				horaSiguienteEnvio.add(Calendar.MILLISECOND, tiempoEntreAlertas.intValue() * 3600000);
			} else {
				result = true;
			}
			Calendar horaActual = Calendar.getInstance();
			if (horaActual.after(horaSiguienteEnvio)) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			LOG.error("Error; validando el tiempo de envio notificacion escalamiento vencida");
			e.printStackTrace();
		}
	return result;
	}
	
	
	private String construirNotificacion(List<String> parametros, String parametroAuditoria)
			throws DAOException, BusinessException {
		for (int i = 0 ; i < parametros.size(); i++) {
			parametroAuditoria = parametroAuditoria.replace("{" + i + "}", parametros.get(i));
		}
		return parametroAuditoria;
	}
	
	/**
	 * 
	 * @param tarea
	 * @throws MessagingException
	 * @throws DAOException
	 * @throws BusinessException
	 * Metodo que envia la notificación sin guardar ninguna alerta en la base de datos.
	 */
	
	private void enviarCorreo(TareaParticularCaso tarea) throws MessagingException, DAOException, BusinessException {
		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.getFormatoFechaString());
		MimeMessage message = null;
		MimeMessageHelper helper = null;
		message = this.mailSender.createMimeMessage();
		helper = new MimeMessageHelper(message, true);
		List to = new ArrayList();
		for (ResponsableTarea responsable:tarea.getResponsablesTareas()) {
			if (Parametros.getMiembroActivoSi().equals(responsable.getCasosEquiposCaso().getActivo())) {			
				User usuario = responsable.getCasosEquiposCaso().getEquipoCaso().getUsuario();
				if (usuario != null) {				
					to.add(responsable.getCasosEquiposCaso().getEquipoCaso().getUsuario().getEmail());
				}
			}
		}
		if (to.size() > 0) {
			List<String> parametros = new ArrayList<String>();
			parametros.add(tarea.getTarea());
			parametros.add(tarea.getActividadParticular().getNombreActividad());
			String nombreCaso = casoService.getNombreCaso(tarea.getResponsablesTareas().get(0).getResponsableTareaPK().getCodigoCaso());
			parametros.add(nombreCaso);
			String mensaje = Parametros.getMensajeVencimientoTareaHoy();
			mensaje = construirNotificacion(parametros, mensaje);
			helper.setTo((String[])to.toArray(new String[0]));
			helper.setSubject(Parametros.getAsuntoNotificacionTarea());
			helper.setText(mensaje);
			this.mailSender.send(message);
		}
	}
	
	/**
	 * @param tarea
	 * @param alerta
	 * @throws MessagingException
	 * @throws DAOException
	 * @throws BusinessException
	 * Metodo que envia la notificación, y guarda la alerta en base de datos.
	 */
	
	private void enviarCorreo(TareaParticularCaso tarea, AlertaTareaParticular alerta) throws MessagingException, DAOException, BusinessException {

		SimpleDateFormat sdf = new SimpleDateFormat(Parametros.getFormatoFechaString());
		MimeMessage message = null;
		MimeMessageHelper helper = null;
		message = this.mailSender.createMimeMessage();
		helper = new MimeMessageHelper(message, true);
		List to = new ArrayList();
		for (ResponsableTarea responsable:tarea.getResponsablesTareas()) {
			if (Parametros.getMiembroActivoSi().equals(responsable.getCasosEquiposCaso().getActivo())) {			
				User usuario = responsable.getCasosEquiposCaso().getEquipoCaso().getUsuario();
				if (usuario != null) {				
					to.add(responsable.getCasosEquiposCaso().getEquipoCaso().getUsuario().getEmail());
				}
			}
		}
		if (to.size() > 0) {
			List<String> parametros = new ArrayList<String>();
			parametros.add(tarea.getTarea());
			parametros.add(tarea.getActividadParticular().getNombreActividad());
			String nombreCaso = casoService.getNombreCaso(tarea.getResponsablesTareas().get(0).getResponsableTareaPK().getCodigoCaso());
			parametros.add(nombreCaso);
			parametros.add(sdf.format(tarea.getFechaLimite()));
			String mensaje = Parametros.getMensajeNotificacionVencimientoTarea();
			mensaje = construirNotificacion(parametros, mensaje);
			helper.setTo((String[])to.toArray(new String[0]));
			helper.setSubject(Parametros.getAsuntoNotificacionTarea());
			helper.setText(mensaje);
			this.mailSender.send(message);
			if (alerta == null) {
				alerta = new AlertaTareaParticular();
				alerta.setFechaPrimeraAlerta(new Date());
				alerta.setFechaUltimaAlerta(new Date());
				alerta.setNumeroAlertasTotales(tarea.getNumeroAlertasPorDia());
				alerta.setNumeroAlertasEnviadas(1);
				int totalAlertas = tarea.getNumeroAlertasPorDia() * tarea.getNumeroDiasAntesAlertas();
				alerta.setTotalAlertas(totalAlertas);
				alerta.setTotalEnvios(1);
				alerta.setTareaParticularCaso(tarea);
				tareaParticularCasoService.guardarAlerta(alerta);
			} else {
				alerta.setFechaUltimaAlerta(new Date());
				Integer alertasEnviadas = alerta.getNumeroAlertasEnviadas();
				alerta.setNumeroAlertasEnviadas(alertasEnviadas + 1);
				alerta.setTotalEnvios(alerta.getTotalEnvios() + 1);
				tareaParticularCasoService.guardarAlerta(alerta);
			}
		}
	}
}


