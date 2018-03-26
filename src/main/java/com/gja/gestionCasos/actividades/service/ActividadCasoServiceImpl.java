package com.gja.gestionCasos.actividades.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.ActividadCasoRepository;
import com.gja.gestionCasos.actividades.repository.ListaActividades;
import com.gja.gestionCasos.actividades.repository.ResponsablesTareaRepository;
import com.gja.gestionCasos.actividades.repository.TareaParticularCasoRepository;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCasoPK;
import com.gja.gestionCasos.casos.entities.Correo;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Telefono;
import com.gja.gestionCasos.casos.repository.CasoEquipoCasoRepository;
import com.gja.gestionCasos.casos.repository.CasoRepositoryImpl;
import com.gja.gestionCasos.casos.repository.EquipoCasoRepositoryImpl;
import com.gja.gestionCasos.casos.service.ResponsablesTareaService;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.maestros.repository.ActividadTipoCasoRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.ParametrosDocumentos;

@Service("actividadCasoService")
@Transactional(readOnly = true)
public class ActividadCasoServiceImpl implements ActividadCasoService {

	@Autowired
	ActividadCasoRepository actividadCasoRepository;
	
	@Autowired
	EquipoCasoRepositoryImpl equipoCasoRepository;
	@Autowired 
	ActividadTipoCasoRepository actividadTipoCasoRepository;
	
	@Autowired
	TareaParticularCasoRepository tareaParticularCasoRepository;
	
	@Autowired
	ResponsablesTareaRepository responsablesTareaRepository;
	
	@Autowired
	CasoRepositoryImpl casoRepository;

	@Autowired
	CasoEquipoCasoRepository casoEquipoCasoRepository;
	
	
	
	
	private ParametrosDocumentos parametrosDocumentos = new ParametrosDocumentos();
	
	private boolean isTareaRepetida;
	
	
	
	@Transactional
	public ActividadCaso guardarActividadCaso(ActividadCaso actividadCaso, List<CasoEquipoCaso> demandantes)throws DAOException, BusinessException { 
		if (demandantes == null)
			guardarActividadCaso(actividadCaso);
		else
			if (Parametros.getActividadDocumentosRequeridos().equals(actividadCaso.getNombreActividad()))
				crearTareasPorParentesco(actividadCaso, demandantes);
			else
				guardarActividadCaso(actividadCaso);
		return actividadCaso;
	}
	
	@Transactional
	public int updateActividadesCasoOrden(ListaActividades listaActividades)throws DAOException, BusinessException {
		int result = 0;
		for (ActividadCaso actividadCaso : listaActividades.getListaActividadesCaso()) 
			result+=actividadCasoRepository.updateActividadCasoOrden(actividadCaso);
		return result;
	}
	
	private void crearTareasPorParentesco(ActividadCaso actividadCaso, List<CasoEquipoCaso> demandantes) throws DAOException, BusinessException {
		List<TareaParticularCaso> tareasDocumentosRequeridos = new ArrayList<TareaParticularCaso>();
		List<TareaParticularCaso> tareasNorepetidas = new ArrayList<TareaParticularCaso>();
		for (TareaParticularCaso tarea:actividadCaso.getTareaParticularCasoSet()) {
			isTareaRepetida = false;
			for (CasoEquipoCaso equipoCaso:demandantes) {
				if (equipoCaso.getParentesco() != null) {
					String codigoParentesco = equipoCaso.getParentesco().getCodigo().toString();
					
					if (parametrosDocumentos.getParentescoEsposo().equals(codigoParentesco)) {
						String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoEsposo().split(",");
						if (documentosRequeridos.length > 0)
							cambiarNombresTareas(tareasDocumentosRequeridos, documentosRequeridos, tarea, equipoCaso.getEquipoCaso());
						continue;
					}
					
					if (parametrosDocumentos.getParentescoHijo().equals(codigoParentesco)) {
						String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoHijo().split(",");
						if (documentosRequeridos.length > 0)
							cambiarNombresTareas(tareasDocumentosRequeridos, documentosRequeridos, tarea, equipoCaso.getEquipoCaso());
						continue;
					}
					
					if (parametrosDocumentos.getParentescoPadre().equals(codigoParentesco) ||
							parametrosDocumentos.getParentescoMadre().equals(codigoParentesco)) {
						String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoMadrePadre().split(",");
						if (documentosRequeridos.length > 0)
							cambiarNombresTareas(tareasDocumentosRequeridos, documentosRequeridos, tarea, equipoCaso.getEquipoCaso());
						continue;
					}

					if (parametrosDocumentos.getParentescoHermano().equals(codigoParentesco)) {
						String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoHermano().split(",");
						if (documentosRequeridos.length > 0)
							cambiarNombresTareas(tareasDocumentosRequeridos, documentosRequeridos, tarea, equipoCaso.getEquipoCaso());
						continue;
					}
					
					if (parametrosDocumentos.getParentescoAbuelo().equals(codigoParentesco)) {
						String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoAbuelo().split(",");
						if (documentosRequeridos.length > 0)
							cambiarNombresTareas(tareasDocumentosRequeridos, documentosRequeridos, tarea, equipoCaso.getEquipoCaso());
						continue;
					}
					
					if (parametrosDocumentos.getParentescoOtro().equals(codigoParentesco)) {
						String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoOtro().split(",");
						if (documentosRequeridos.length > 0)
							cambiarNombresTareas(tareasDocumentosRequeridos, documentosRequeridos, tarea, equipoCaso.getEquipoCaso());
						continue;
					}
					
				}
			}
			if (!isTareaRepetida) {
				tareasNorepetidas.add(tarea);
			}
		}
		tareasDocumentosRequeridos.addAll(tareasNorepetidas);
		actividadCaso.setTareaParticularCasoSet(tareasDocumentosRequeridos);
		guardarActividadCaso(actividadCaso);
	}
	
	private void cambiarNombresTareas(List<TareaParticularCaso> tareasDocumentosRequeridos, String[] documentosRequeridos,
			TareaParticularCaso tarea, EquipoCaso equipoCaso) {
		
		for (String documento:documentosRequeridos) {
			if (Parametros.getActividadPoder().equals(tarea.getTarea()) && parametrosDocumentos.getDocumentoPoder().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadPoderProcuraduria().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoPoderProcuraduria().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadFotocopiaCC().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoFotocopiaCC().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadContratoMandato().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoContratoMandato().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadJuntaMedica().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoJuntaMed().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadTransito().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoProcesoTransito().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadPreclusion().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoSentencia().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadBoletaLibertad().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoBoletaLibertad().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadHistoria().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoHistoriaClinica().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadRelatoHechos().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoRelatoHechos().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadPartidaBautismo().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoPartidaBautismo().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadPartidaMatrimonio().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoPartidaMatrimonio().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadMatrimonio().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoRegistroMatrimonio().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadRegistroNaciento().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoRegistroCivil().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadRegistroDefuncion().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoRegistroDefuncion().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
			
			if (Parametros.getActividadOtros().equals(tarea.getTarea().trim()) && parametrosDocumentos.getDocumentoOtros().equals(documento)) {
				agregarTareasLista(tareasDocumentosRequeridos, tarea, equipoCaso);
				break;
			}
		}
		
	}
	
	private void agregarTareasLista(List<TareaParticularCaso> tareasDocumentosRequeridos, TareaParticularCaso tarea, EquipoCaso equipoCaso) {
		StringBuilder nombreTarea = new StringBuilder();
		nombreTarea.append(tarea.getTarea() + " " + equipoCaso.getNombre() + " " + equipoCaso.getApellido());
		TareaParticularCaso tareaNueva = new TareaParticularCaso(tarea);
		tareaNueva.setTarea(nombreTarea.toString());
		tareasDocumentosRequeridos.add(tareaNueva);
		isTareaRepetida = true;
	}
	
	//Bloque donde se guarda las actividades particulares del caso por primera vez
	@Transactional
	public ActividadCaso guardarActividadCaso(ActividadCaso actividadCaso)throws DAOException, BusinessException {

		List<TareaParticularCaso> tareasParticulares = actividadCaso.getTareaParticularCasoSet();
		actividadCaso.setFechaCreacion(new Date());
		actividadCaso.setFechaUltimaModificacion(new Date());
		actividadCaso.setTareaParticularCasoSet(null);
		actividadCaso = actividadCasoRepository.guardarActividadCaso(actividadCaso);
		if (tareasParticulares != null) {
			for (int i = 0; i < tareasParticulares.size(); i++) {
				TareaParticularCaso tareaParticular = null;
				tareaParticular = tareasParticulares.get(i);
				List<ResponsableTarea> responsablesTarea = null;
				tareaParticular.setFechaCreacion(new Date());
				tareaParticular.setFechaUltimaModificacion(new Date());
				tareaParticular.setActividadParticular(actividadCaso);
				responsablesTarea = tareaParticular.getResponsablesTareas();
				
				tareaParticular.setResponsablesTareas(null);
				
				tareaParticular = tareaParticularCasoRepository.guardarTareaParticularCaso(tareaParticular);
				setFechaFinalizacionTarea(responsablesTarea, tareaParticular); 
				asignarCodigoTareaResponsable(responsablesTarea, tareaParticular.getCodigoTarea());
				tareaParticular.setResponsablesTareas(responsablesTarea);
				tareasParticulares.set(i, tareaParticular);
				
			/*	for (ResponsableTarea responsableTareaSave : responsablesTarea) {
					responsablesTareaRepository.guardarResponsableTarea(responsableTareaSave);	
				}*/
				
				//Guarda Una tarea nueva con un responsable y un nuevo otro
				for (ResponsableTarea responsableTarea : responsablesTarea) {
					responsableTarea.setTareasParticularesCaso(tareaParticular);
						if(responsableTarea.getResponsableTareaPK().getCodigoEquipoCaso() == 0 && responsableTarea.getResponsableTareaPK().getCodigoMiembro() == 4){						
							EquipoCaso equipoCaso = null;
							for (ResponsableTarea responsable:responsablesTarea) {
								for (ResponsableTarea nuevoResponsable:responsablesTarea) {
										if(responsable.getResponsableTareaPK().getCodigoMiembro() == 4 && responsable.getResponsableTareaPK().equals(nuevoResponsable.getResponsableTareaPK())) {
											if(responsable.getResponsableTareaPK().getCodigoEquipoCaso() == 0){
			 								for (Telefono telefono : responsable.getCasosEquiposCaso().getEquipoCaso().getTelefonoList()) {
			// 									responsable.getCasosEquiposCaso().getEquipoCaso().setTelefonoList(null);
			 									telefono.setCodigoEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
			 								}
			 								for (Correo correo : responsable.getCasosEquiposCaso().getEquipoCaso().getCorreoList()) {
			// 									responsable.getCasosEquiposCaso().getEquipoCaso().setCorreoList(null);
			 									correo.setCodigoEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
			 								}
											equipoCaso = equipoCasoRepository.guardarEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
											responsable.getCasosEquiposCaso().setCasoEquipoCasoPK(new CasoEquipoCasoPK(responsable.getResponsableTareaPK().getCodigoCaso(), equipoCaso.getCodigoEquipoCaso(), responsable.getResponsableTareaPK().getCodigoMiembro()));
											responsable.getCasosEquiposCaso().setActivo("S");
											responsable.getCasosEquiposCaso().getEquipoCaso().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
											CasoEquipoCaso casoEquipoCaso = casoEquipoCasoRepository.guardarCasoEquipoCaso(responsable.getCasosEquiposCaso());
											responsable.getResponsableTareaPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
											}
										}
									}
							}
						}									
				}
			}
		}
		
		actividadCaso.setTareaParticularCasoSet(tareasParticulares);
		validarFinalizacionActividad(actividadCaso);
		
		
		actividadCaso = actividadCasoRepository.guardarActividadCaso(actividadCaso);
		
	
	return actividadCaso;
}
	
	private void setFechaFinalizacionTarea(List<ResponsableTarea> responsables, TareaParticularCaso tarea) {
		if (Parametros.getActividadFinalizada().equals(tarea.getFinalizada())) {			
			for (ResponsableTarea responsable:responsables) {
				responsable.setFechaFinalizacionTarea(new Date());
			}
		}
	}
	
	private void validarFinalizacionActividad(ActividadCaso actividadCaso) {
		int cantidadTareas = 0;
		int cantidadTareasFinalizadas = 0;
		if (actividadCaso.getTareaParticularCasoSet() != null && !actividadCaso.getTareaParticularCasoSet().isEmpty()) {
			cantidadTareas = actividadCaso.getTareaParticularCasoSet().size();
			for (TareaParticularCaso tarea:actividadCaso.getTareaParticularCasoSet()) {
				if (Parametros.getActividadFinalizada().equals(tarea.getFinalizada()))
						cantidadTareasFinalizadas++;
			}
			if (cantidadTareas == cantidadTareasFinalizadas)
				actividadCaso.setFinalizada(Parametros.getEstadoActividadFinalizada());
			else
				actividadCaso.setFinalizada(Parametros.getEstadoActividadPendiente());
		}		
	}

	private void asignarCodigoTareaResponsable(List<ResponsableTarea> responsables, int codigoTarea) {
		
		for (ResponsableTarea responsable:responsables) {			
			responsable.getResponsableTareaPK().setCodigoTareaparticular(codigoTarea);
		}	
	}
	
	public List<ActividadCaso>  getActividadByCaso(Integer codigoCaso) throws DAOException, BusinessException{
		List<ActividadCaso> actividades=null;
		EquipoCaso equipoCaso = null;
		actividades = actividadCasoRepository.getActividadByCaso(codigoCaso);
		for (ActividadCaso actividad:actividades) {
			Calendar fechaCalendar = Calendar.getInstance();
			fechaCalendar.set(Calendar.HOUR_OF_DAY, 23);
			fechaCalendar.set(Calendar.MINUTE, 59);
			fechaCalendar.add(Calendar.DATE, -1);
			Date fechaActual = fechaCalendar.getTime();
 			if (fechaActual.after(actividad.getFechaLimite())) {
				actividad.setFinalizada(Parametros.getEstadoActividadVencida());
				actividad = actividadCasoRepository.guardarActividadCaso(actividad);
			}
			for (TareaParticularCaso tarea:actividad.getTareaParticularCasoSet()) {
				Hibernate.initialize(tarea.getResponsablesTareas());
				if (fechaActual.after(tarea.getFechaLimite())) {
					tarea.setFinalizada(Parametros.getEstadoActividadVencida());					
				}
			}
		}
		
		return actividades;
	}

	public List<ActividadTipoCaso> obtenerActividadesTipoCaso(
			Integer codigoTipoCaso) throws DAOException, BusinessException {
		List<ActividadTipoCaso> actividadesTipoCaso=null;
		
		actividadesTipoCaso = actividadTipoCasoRepository.obtenerActividadesTipoCaso(codigoTipoCaso);
		return actividadesTipoCaso;
	}
	@Transactional
	public ActividadCaso modificarActividadCaso(ActividadCaso actividadCaso) throws DAOException, BusinessException {

		if (actividadCaso.getCodigoActividadParticular() != null) {
			ActividadCaso actividad = actividadCasoRepository.consultarActividad(actividadCaso.getCodigoActividadParticular());
			Date fecreacionActividad = actividad.getFechaCreacion();
			String estadoAnterior = actividad.getFinalizada();
			
			actividad.setFinalizada(actividadCaso.getFinalizada());
			actividad.setFechaLimite(actividadCaso.getFechaLimite());
			actividad.setFechaUltimaModificacion(new Date());
			actividad.setUsuarioUltimaModificacion(actividadCaso.getUsuarioUltimaModificacion());
			actividad.setNombreActividad(actividadCaso.getNombreActividad());
			actividad.setNumeroAlertasPorDia(actividadCaso.getNumeroAlertasPorDia());
			actividad.setNumeroDiasAntesAlertas(actividadCaso.getNumeroDiasAntesAlertas());
			
			boolean hasActividadesPendientes = false;
			
			for (TareaParticularCaso tareasCaso:actividadCaso.getTareaParticularCasoSet()) {
				if (tareasCaso.getCodigoTarea() != null) {
					TareaParticularCaso tareaParticularParaModificar = tareaParticularCasoRepository.consultarTareaParticular(tareasCaso.getCodigoTarea());
					String estadoTareaAnterior = tareaParticularParaModificar.getFinalizada();					
					
					tareasCaso.setFechaCreacion(tareaParticularParaModificar.getFechaCreacion());
					tareasCaso.setFechaUltimaModificacion(new Date());
					// tareaParticularParaModificar.setFechaCreacion(new Date());
					
					tareaParticularParaModificar.setUsuarioUltimaModificacion(tareasCaso.getUsuarioUltimaModificacion());
					
					tareaParticularParaModificar.setTarea(tareasCaso.getTarea());
					tareaParticularParaModificar.setDetalle(tareasCaso.getDetalle());
					for (ResponsableTarea responsable:tareaParticularParaModificar.getResponsablesTareas()) {						
						boolean eliminarResponsable = true;
						for (ResponsableTarea nuevoResponsable:tareasCaso.getResponsablesTareas()) {
							if (responsable.getResponsableTareaPK().equals(nuevoResponsable.getResponsableTareaPK())) {
								nuevoResponsable.setFechaFinalizacionTarea(responsable.getFechaFinalizacionTarea());
								eliminarResponsable = false;
								break;
							}
						}
																		
						if (eliminarResponsable)
							responsablesTareaRepository.removerResponsableTarea(responsable.getResponsableTareaPK());							
					}
									
					tareaParticularParaModificar.setFinalizada(tareasCaso.getFinalizada());
					if (tareaParticularParaModificar.getFinalizada().equals(Parametros.getActividadPendiente())) {
						hasActividadesPendientes = true;
					}
					tareasCaso.setActividadParticular(actividadCaso);
					List<ResponsableTarea> responsablesTarea = tareasCaso.getResponsablesTareas();
					tareaParticularParaModificar.setFechaLimite(tareasCaso.getFechaLimite());
					tareaParticularParaModificar.setNumeroAlertasPorDia(tareasCaso.getNumeroAlertasPorDia());
					tareaParticularParaModificar.setNumeroDiasAntesAlertas(tareasCaso.getNumeroDiasAntesAlertas());
					if (Parametros.getActividadPendiente().equals(estadoTareaAnterior))
						setFechaFinalizacionTarea(responsablesTarea, tareaParticularParaModificar);
					tareaParticularParaModificar.setResponsablesTareas(responsablesTarea);

					for (ResponsableTarea responsable:tareasCaso.getResponsablesTareas()) {						
						if (responsable.getResponsableTareaPK().getCodigoMiembro() == 4) {
							EquipoCaso equipoCaso = null;
							if (responsable.getResponsableTareaPK().getCodigoEquipoCaso() == 0) {
								for (Telefono telefono : responsable.getCasosEquiposCaso().getEquipoCaso().getTelefonoList()) {
									telefono.setCodigoEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
								}
								for (Correo correo : responsable.getCasosEquiposCaso().getEquipoCaso().getCorreoList()) {
									correo.setCodigoEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
								}
								equipoCaso = equipoCasoRepository.guardarEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
								responsable.getCasosEquiposCaso().setCasoEquipoCasoPK(new CasoEquipoCasoPK(responsable.getResponsableTareaPK().getCodigoCaso(), equipoCaso.getCodigoEquipoCaso(), responsable.getResponsableTareaPK().getCodigoMiembro()));
								responsable.getCasosEquiposCaso().setActivo("S");
								responsable.getCasosEquiposCaso().getEquipoCaso().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
								CasoEquipoCaso casoEquipoCaso = casoEquipoCasoRepository.guardarCasoEquipoCaso(responsable.getCasosEquiposCaso());
								responsable.getResponsableTareaPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
							}
						}
					}			
					tareaParticularParaModificar.setResponsablesTareas(responsablesTarea);
					tareaParticularParaModificar = tareaParticularCasoRepository.guardarTareaParticularCaso(tareaParticularParaModificar);
					
					
				} else {
					
					EquipoCaso equipoCaso = null;
					
 					for (ResponsableTarea responsable:tareasCaso.getResponsablesTareas()) {
 						for (ResponsableTarea nuevoResponsable:tareasCaso.getResponsablesTareas()) {
 							if(responsable.getResponsableTareaPK().getCodigoMiembro() == 4 && responsable.getResponsableTareaPK().equals(nuevoResponsable.getResponsableTareaPK())) {
 								if(responsable.getResponsableTareaPK().getCodigoEquipoCaso() == 0){
	 								for (Telefono telefono : responsable.getCasosEquiposCaso().getEquipoCaso().getTelefonoList()) {
	// 									responsable.getCasosEquiposCaso().getEquipoCaso().setTelefonoList(null);
	 									telefono.setCodigoEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
	 								}
	 								for (Correo correo : responsable.getCasosEquiposCaso().getEquipoCaso().getCorreoList()) {
	// 									responsable.getCasosEquiposCaso().getEquipoCaso().setCorreoList(null);
	 									correo.setCodigoEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
	 								}
									equipoCaso = equipoCasoRepository.guardarEquipoCaso(responsable.getCasosEquiposCaso().getEquipoCaso());
									responsable.getCasosEquiposCaso().setCasoEquipoCasoPK(new CasoEquipoCasoPK(responsable.getResponsableTareaPK().getCodigoCaso(), equipoCaso.getCodigoEquipoCaso(), responsable.getResponsableTareaPK().getCodigoMiembro()));
									responsable.getCasosEquiposCaso().setActivo("S");
									responsable.getCasosEquiposCaso().getEquipoCaso().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
									CasoEquipoCaso casoEquipoCaso = casoEquipoCasoRepository.guardarCasoEquipoCaso(responsable.getCasosEquiposCaso());
									responsable.getResponsableTareaPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
 								}
 							}
 						}
					}
					
					List<ResponsableTarea> responsablesTarea = null;
					tareasCaso.setActividadParticular(actividadCaso);
					tareasCaso.setFechaUltimaModificacion(new Date());
					tareasCaso.setFechaCreacion(tareasCaso.getFechaUltimaModificacion());
//					tareasCaso.getActividadParticular().setFechaCreacion(tareasCaso.getFechaUltimaModificacion());
					if (tareasCaso.getFinalizada().equals(Parametros.getActividadPendiente())) {
						hasActividadesPendientes = true;
					}
					
//					for (ResponsableTarea responsable:tareasCaso.getResponsablesTareas()) {
						tareasCaso.setActividadParticular(actividadCaso);
						responsablesTarea = tareasCaso.getResponsablesTareas();
						tareasCaso.setResponsablesTareas(null);
//					}
					
					tareasCaso = tareaParticularCasoRepository.guardarTareaParticularCaso(tareasCaso);
					asignarCodigoTareaResponsable(responsablesTarea, tareasCaso.getCodigoTarea());
				
					for (ResponsableTarea responsable:responsablesTarea) {
						if (equipoCaso != null)
							responsable.getResponsableTareaPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
					}

					tareasCaso.setResponsablesTareas(responsablesTarea);
					setFechaFinalizacionTarea(responsablesTarea, tareasCaso);
					tareasCaso = tareaParticularCasoRepository.guardarTareaParticularCaso(tareasCaso);

//					List<ResponsableTarea> responsablesTarea = null;
//					tareasCaso.setFechaUltimaModificacion(new Date());
//					tareasCaso.setFechaCreacion(tareasCaso.getFechaUltimaModificacion());
//					if (tareasCaso.getFinalizada().equals(Parametros.getActividadPendiente())) {
//						hasActividadesPendientes = true;
//					}
//					tareasCaso.setActividadParticular(actividadCaso);
//					tareasCaso = tareaParticularCasoRepository.guardarTareaParticularCaso(tareasCaso);
//					responsablesTarea = tareasCaso.getResponsablesTareas();
//					tareasCaso.setResponsablesTareas(null);
//					tareasCaso = tareaParticularCasoRepository.guardarTareaParticularCaso(tareasCaso);
//					asignarCodigoTareaResponsable(responsablesTarea, tareasCaso.getCodigoTarea());
//					tareasCaso.setResponsablesTareas(responsablesTarea);
//					setFechaFinalizacionTarea(responsablesTarea, tareasCaso);					
//					tareasCaso = tareaParticularCasoRepository.guardarTareaParticularCaso(tareasCaso);
				
				}
			}
			if (!hasActividadesPendientes) {
				actividad.setFinalizada(Parametros.getActividadFinalizada());
			}
			else {
				actividad.setFinalizada(Parametros.getActividadPendiente());
			}
			if (estadoAnterior.equals(Parametros.getEstadoActividadPendiente())) {
				if (actividad.getEsActividadPropia().equals(Parametros.getCodigoActividadPropiaNo())) {
					if (actividad.getFinalizada().equals(Parametros.getActividadFinalizada())) {
						if (actividad.getNombreActividad().equalsIgnoreCase(Parametros.getActividadSolicitudPrejudicial())) {
							if (actividad.getTareaParticularCasoSet() != null && !actividad.getTareaParticularCasoSet().isEmpty()) {
								if (actividad.getTareaParticularCasoSet().get(0).getResponsablesTareas() != null 
										&& !actividad.getTareaParticularCasoSet().get(0).getResponsablesTareas().isEmpty()) {
									
									Long diferenciaDias = actividad.getFechaCreacion().getTime() - actividad.getFechaUltimaModificacion().getTime();
									Integer codigoCaso = actividad.getTareaParticularCasoSet().get(0).getResponsablesTareas().get(0).getResponsableTareaPK().getCodigoCaso();
									Caso caso = casoRepository.findByPK(new Caso(codigoCaso));
									Date fechaCaducidadActualizada = new Date(caso.getFechaCaducidad().getTime() + diferenciaDias);
									caso.setFechaCaducidad(fechaCaducidadActualizada);
									for (CasoEquipoCaso equipoCaso:caso.getCasoEquipoCasoSet()) {
										equipoCaso.setResponsablesTareas(null);
									}
									caso = casoRepository.merge(caso);
								}
								
							}
						}
						if (actividad.getTareaParticularCasoSet() != null && !actividad.getTareaParticularCasoSet().isEmpty()) {
							if (actividad.getTareaParticularCasoSet().get(0).getResponsablesTareas() != null
									&& !actividad.getTareaParticularCasoSet().get(0).getResponsablesTareas().isEmpty()) {					
								Integer codigoCaso = actividad.getTareaParticularCasoSet().get(0).getResponsablesTareas().get(0).getResponsableTareaPK().getCodigoCaso();
								Caso caso = casoRepository.findByPK(new Caso(codigoCaso));
								caso.setEstadoProcesal(actividad.getNombreActividad());
								caso.setFechaEstadoProcesal(new Date());
								for (CasoEquipoCaso equipoCaso:caso.getCasoEquipoCasoSet()) {
									equipoCaso.setResponsablesTareas(null);
								}
								caso = casoRepository.merge(caso);
							}
						}
					}
				}
			}
			actividad.setFechaUltimaModificacion(new Date());
			actividad.setFechaCreacion(fecreacionActividad);
			actividad = actividadCasoRepository.guardarActividadCaso(actividad);
			
			
		}
		return actividadCaso;
	}
	
	
	@Transactional
	public ActividadCaso eliminarActividadCaso(ActividadCaso actividadCaso)
			throws DAOException, BusinessException {
		actividadCaso = actividadCasoRepository.consultarActividad(actividadCaso.getCodigoActividadParticular());
		actividadCaso.setSnActiva(Parametros.getCodigoActividadActivoNo());
		for (TareaParticularCaso tarea:actividadCaso.getTareaParticularCasoSet()) {
			tarea.setSnActiva(Parametros.getCodigoTareaActivoNo());
		}
//		actividadCaso = actividadCasoRepository.guardarActividadCaso(actividadCaso);
		return actividadCaso;
	}
	
	public List<ActividadCaso> obtenerActividadesInactivas(CasoFiltro casoFiltro)
			throws DAOException, BusinessException {
		List<ActividadCaso> actividadesInactivas = actividadCasoRepository.obtenerFiltroActividadesInactivas(casoFiltro);
		
		for (ActividadCaso actividadCaso:actividadesInactivas) {
			for (TareaParticularCaso tarea:actividadCaso.getTareaParticularCasoSet()) {
				Hibernate.initialize(tarea.getResponsablesTareas());
				for (ResponsableTarea responsable:tarea.getResponsablesTareas()) {
					Hibernate.initialize(responsable.getCasosEquiposCaso().getCaso());
				}
			}
		}
		return actividadesInactivas;
	}
	

	@Transactional
	public ActividadCaso activarActividadCaso(ActividadCaso actividadCaso)
			throws DAOException, BusinessException {
		actividadCaso = actividadCasoRepository.consultarActividad(actividadCaso.getCodigoActividadParticular());
		actividadCaso.setSnActiva(Parametros.getCodigoActividadActivoSi());
		for (TareaParticularCaso tarea:actividadCaso.getTareaParticularCasoSet()) {
			tarea.setSnActiva(Parametros.getCodigoTareaActivoSi());
		}
//		actividadCaso = actividadCasoRepository.guardarActividadCaso(actividadCaso);
		return actividadCaso;
	}
	
	@Transactional
	public void borradoFisicoActividad(ActividadCaso actividadCaso) throws DAOException, BusinessException {
		actividadCaso = actividadCasoRepository.consultarActividad(actividadCaso.getCodigoActividadParticular());
		actividadCasoRepository.borradoFisicoActividad(actividadCaso);
	}
	
	public List<Actividad> obtenerEstadosProcesales()
			throws DAOException, BusinessException {
		List<Actividad> estadosProcesales = actividadCasoRepository.obtenerEstadosProcesales();
		return estadosProcesales;
	}

	public List<ActividadCaso> obtenerTotalActividadesInactivas(
			CasoFiltro casoFiltro) throws DAOException, BusinessException {
		List<ActividadCaso> actividadesInactivas = actividadCasoRepository.obtenerTotalActividadesInactivas(casoFiltro);
		return actividadesInactivas;
	}

	@Override
	public List<ActividadCaso> obtenerActividadesVencidas() throws DAOException, BusinessException {
		return actividadCasoRepository.obtenerActividadesVencidas();
	}
	
	@Override
	public List<ActividadCaso> obtenerActividadesPendientes() throws DAOException, BusinessException {
		return actividadCasoRepository.obtenerActividadesPendientes();
	}
	
	@Override
	@Transactional
	public void vencerActividad(ActividadCaso actividadCaso) throws DAOException, BusinessException {
		actividadCaso.setFinalizada(Parametros.getEstadoActividadVencida());
		for (TareaParticularCaso tarea:actividadCaso.getTareaParticularCasoSet()) {
			if (!Parametros.getActividadFinalizada().equals(tarea.getFinalizada()))
				tarea.setFinalizada(Parametros.getEstadoActividadVencida());
		}
		actividadCasoRepository.guardarActividadCaso(actividadCaso);
	}
	
	@Transactional
	public ActividadCaso guardarActividadCasoOtroResponsable(ActividadCaso actividadCaso) throws DAOException, BusinessException {
		actividadCaso = actividadCasoRepository.guardarActividadCaso(actividadCaso);		
		validarFinalizacionActividad(actividadCaso);
		return actividadCaso;
	}
	
	@Transactional
	public EquipoCaso guardarEquipoCasoOtroResponsable(EquipoCaso equipoCaso) throws DAOException, BusinessException {					
		equipoCaso = equipoCasoRepository.guardarEquipoCaso(equipoCaso);		
		return equipoCaso;
	}
	
	@Transactional
	public CasoEquipoCaso guardarCasoEquipoCasoOtroResponsable(CasoEquipoCaso casoEquipoCaso) throws DAOException, BusinessException {
		casoEquipoCaso = casoEquipoCasoRepository.guardarCasoEquipoCaso(casoEquipoCaso);
		return casoEquipoCaso;
	}
	
	@Transactional
	public TareaParticularCaso guardarTareaParticularOtroResponsable(TareaParticularCaso tareaParticularCasos) throws DAOException, BusinessException {
		
		tareaParticularCasos = tareaParticularCasoRepository.guardarTareaParticularCaso(tareaParticularCasos);
		return tareaParticularCasos;
	}
	

}
