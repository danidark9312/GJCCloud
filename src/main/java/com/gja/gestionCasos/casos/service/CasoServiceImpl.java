
package com.gja.gestionCasos.casos.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.repository.ActividadCasoRepository;
import com.gja.gestionCasos.actividades.repository.ListaActividades;
import com.gja.gestionCasos.actividades.service.ActividadCasoService;
import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.ListaPrestamos;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.entities.RadicadoAcumulado;
import com.gja.gestionCasos.casos.repository.CasoRepositoryImpl;
import com.gja.gestionCasos.casos.repository.PrestamoRepository;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.maestros.entities.EstadoCaso;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.gja.gestionCasos.maestros.service.TipoCasoService;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.gja.gestionCasos.reportes.service.JustificacionService;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sun.org.apache.xpath.internal.operations.Bool;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Service("casoService")
@Transactional(readOnly = true)
public class CasoServiceImpl implements CasoService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	CasoRepositoryImpl casoRepository;
	
	
	// @Autowired
	// EquipoCasoRepositoryImpl equipoCasoRepository;
	@Autowired
	BienAfectadoService bienAfectadoService;
	@Autowired
	EquipoCasoService equipoCasoService;
	@Autowired
	TareaParticularCasoService tareaParticularCasoService;
	@Autowired
	ActividadCasoRepository actividadCasoRepository;
	@Autowired
	PrestamoRepository prestamoRepository;
	@Autowired
	JustificacionService justificacionService;
	@Autowired
	ActividadCasoService actividadCasoService;
	@Autowired
	TipoCasoService tipoCasoService;
	
	@Autowired
	CasoEquipoCasoService casoEquipoCasoService;

	public static boolean FETCH_ESTADO_PROCESAL = Boolean.TRUE;
	
	
	@Transactional
	// public Caso guardarCaso(Caso caso, ActividadCaso actividadCaso) throws
	// DAOException, BusinessException
	public Caso guardarCaso(Caso caso, ListaPrestamos prestamos, ListaActividades actividades)
			throws DAOException, BusinessException {
		List<BienAfectado> bienesAfectados = caso.getBienAfectadoSet();// Lista de bienes Afectados
		List<Radicado> radicados = caso.getRadicadoSet();

		List<CasoEquipoCaso> casosEquiposCasos = new ArrayList<CasoEquipoCaso>();// Lista  de equipos caso
		if(caso.getTipoorigen() != null ){
			if (("OF").equals(caso.getTipoorigen())){
				TipoCaso objTipoCaso = new TipoCaso();				
				objTipoCaso = tipoCasoService.obtenerActividades(caso.getTipoCaso());
				List<ActividadCaso> objtActividadCaso = new ArrayList<ActividadCaso>();
				ActividadCaso objtActividad = null;
				
				for(ActividadTipoCaso objtTipoCasoActividad : objTipoCaso.getActividadTipoCasoList()){
					
					Date fechaActual= new Date();
					
					objtActividad = new ActividadCaso();
					objtActividad.setNombreActividad(objtTipoCasoActividad.getCdactividad().getDsdetalle());
					objtActividad.setFechaLimite(fechaActual);
					objtActividad.setUsuarioCreacion("");
					objtActividad.setUsuarioUltimaModificacion("");
					objtActividad.setFechaCreacion(fechaActual);
					objtActividad.setFechaUltimaModificacion(fechaActual);
					objtActividad.setEsActividadPropia(objtActividad.getEsActividadPropia());
					objtActividad.setSnActiva(objtTipoCasoActividad.getCdactividad().getIsactivo());
					objtActividadCaso.add(objtActividad);
				}
				if(objtActividadCaso !=null)
					caso.setActividades(objtActividadCaso);
			}
			
		}
		caso.setFechaCreacion(new Date());
		caso.setFechaUltimaActualizacion(new Date());
		caso.setFechaUsuarioUltimaModificacion(new Date());

		caso.setBienAfectadoSet(null);

		caso.setRadicadoSet(null);
		casosEquiposCasos.addAll(caso.getCasoEquipoCasoSet());// casos equipos caso
		caso.setCasoEquipoCasoSet(null);
		
		
		
		caso = casoRepository.merge(caso);
		if (bienesAfectados != null) {
			bienesAfectados = bienAfectadoService.asignarClavePrimaria(bienesAfectados, caso);
		}
		

		List<CasoEquipoCaso> abogados = new ArrayList<CasoEquipoCaso>();
		List<CasoEquipoCaso> demandantes = new ArrayList<CasoEquipoCaso>();
		for (CasoEquipoCaso casoEquipoCaso : casosEquiposCasos) {
			// ingresar equipo del caso
			if (null != casoEquipoCaso.getEquipoCaso().getIdentificacion() && !casoEquipoCaso.getEquipoCaso().getIdentificacion().isEmpty()) {
				if (casoEquipoCaso.getIsResponsableTarea())
					abogados.add(casoEquipoCaso);
				EquipoCaso equipoCaso = casoEquipoCaso.getEquipoCaso();
				if (casoEquipoCaso.getCasoEquipoCasoPK().getMiembro() == Integer.parseInt(Parametros.getAbogado()) || 
					casoEquipoCaso.getCasoEquipoCasoPK().getMiembro() == Integer.parseInt(Parametros.getRolSocio()) || 
					casoEquipoCaso.getCasoEquipoCasoPK().getMiembro() == Integer.parseInt(Parametros.getRolDependiente()) ||
					casoEquipoCaso.getCasoEquipoCasoPK().getMiembro() == Integer.parseInt(Parametros.getRolAsociado()) ) {
					
					User usuario = new User();
					usuario.setId(equipoCaso.getIdentificacion());
					equipoCaso.setUsuario(usuario);
				}
				equipoCaso = equipoCasoService.guardarEquipoCaso(equipoCaso);
				casoEquipoCaso.getCasoEquipoCasoPK().setCodigo(caso.getCodigo());
				casoEquipoCaso.getCasoEquipoCasoPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
				casoEquipoCaso.setCaso(null);
				casoEquipoCaso.setEquipoCaso(equipoCaso);
			} else {
				EquipoCaso equipoCaso = casoEquipoCaso.getEquipoCaso();
				equipoCaso = equipoCasoService.guardarEquipoCaso(equipoCaso);
				casoEquipoCaso.getCasoEquipoCasoPK().setCodigo(caso.getCodigo());
				casoEquipoCaso.getCasoEquipoCasoPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
				casoEquipoCaso.setCaso(null);
				casoEquipoCaso.setEquipoCaso(equipoCaso);
			}
		}
		caso.setRadicadoSet(radicados);
		caso.setBienAfectadoSet(bienesAfectados);

		caso.setCasoEquipoCasoSet(casosEquiposCasos);
		
		if (radicados != null) {
			for (Radicado radicado : radicados) {
				// se ingresan las fechas de creación y ultima modificación
				radicado.setCaso(caso);
				radicado.getRadicadoPK().setCodigoCaso(caso.getCodigo());
				radicado.setFechaCreacion(new Date());
				radicado.setFechaUltimaModificacion(new Date());
				radicado.getInstancia().setCodigo(radicado.getInstancia().getCodigo().replaceAll(",", ""));
				
				for(RadicadoAcumulado radicadoAcumulado : radicado.getRadicadosAcumulados()) {
					radicadoAcumulado.setRadicado(radicado);
					radicadoAcumulado.getRadicadoPK().setRadicado(radicado);
					//Radicado radicadoAcumuladoDB = radicadoService.obtenerRadicado(radicadoAcumulado.getRadicadoPK().getRadicadoAcumulado().getRadicadoPK().getCodigoRadicado());
					//radicadoAcumulado.getRadicadoPK().setRadicadoAcumulado(radicadoAcumuladoDB);
				}
				//radicadoService.guardarRadicado(radicado);
			}
		}
		
		
		
		caso = casoRepository.merge(caso);

		if (prestamos != null) {
			List<Prestamo> listaPrestamos = prestamos.getListaPrestamos();
			if (listaPrestamos != null) {
				for (Prestamo prestamo : listaPrestamos) {
					prestamo.setFechaCreacion(new Date());
					prestamo.setFechaUltimaModificacion(new Date());
					prestamo.setCaso(caso);
					prestamo = prestamoRepository.guardarPrestamo(prestamo);
				}
			}
		}
		for (CasoEquipoCaso casoEquipoCaso : caso.getCasoEquipoCasoSet()) {
			if (casoEquipoCaso.getCasoEquipoCasoPK().getMiembro() == Integer.parseInt(Parametros.getAbogado()))
				abogados.add(casoEquipoCaso);

			if (Parametros.getDemandante().equals(Integer.toString(casoEquipoCaso.getCasoEquipoCasoPK().getMiembro())) ||
					Parametros.getVictima().equals(Integer.toString(casoEquipoCaso.getCasoEquipoCasoPK().getMiembro())))
				demandantes.add(casoEquipoCaso);
		}
		
		if (actividades != null && actividades.getListaActividadesCaso() != null) {
			for (ActividadCaso actividad : actividades.getListaActividadesCaso()) {
				
				if (actividad != null && actividad.getTareaParticularCasoSet()!=null) {
					for (TareaParticularCaso tarea : actividad.getTareaParticularCasoSet()) {
						for (ResponsableTarea responsable : tarea.getResponsablesTareas()) {
							for (CasoEquipoCaso abogado : abogados) {
								if (null != abogado.getEquipoCaso().getIdentificacion() && !abogado.getEquipoCaso().getIdentificacion().isEmpty()) {
									if (responsable.getResponsableTareaPK().getCodigoEquipoCaso() == Integer.parseInt(abogado.getEquipoCaso().getIdentificacion())) {
										responsable.getResponsableTareaPK().setCodigoEquipoCaso(abogado.getEquipoCaso().getCodigoEquipoCaso());
										responsable.getResponsableTareaPK().setCodigoMiembro(abogado.getCasoEquipoCasoPK().getMiembro());
										responsable.getResponsableTareaPK().setCodigoCaso(caso.getCodigo());
									}
								}
							}
						}
					}
					 actividad = actividadCasoService.guardarActividadCaso(actividad,demandantes);
				}
				if (Parametros.getCodigoActividadPropiaNo().equals(actividad.getEsActividadPropia())) {					
					if (Parametros.getActividadFinalizada().equals(actividad.getFinalizada())) {
						caso.setEstadoProcesal(actividad.getNombreActividad());
						caso = casoRepository.merge(caso);
					}
				}
			}

		}
		return caso;
	}

	public Caso findByPK(Caso caso) throws DAOException {
		return this.findByPK(caso, Boolean.FALSE);
	}
	
	public Caso findByPK(Caso caso,boolean fetchEstadoProcesal) throws DAOException {
		Caso casoFindByPK = null;
		casoFindByPK = casoRepository.findByPK(caso);
		
		casoFindByPK.setEstadoProcesal(getEstadoProcesalByCaso(caso));
		
		return casoFindByPK;
	}

	private String getEstadoProcesalByCaso(Caso caso) throws DAOException {
		List<ActividadCaso> actividadByCaso = actividadCasoRepository.getActividadPendienteByCaso(caso.getCodigo());
		if(actividadByCaso!=null && actividadByCaso.size()>0) {
			return actividadByCaso.get(0).getNombreActividad();
		}else {
			return "";
		}
	}

	public Long getCountFilter(CasoFiltro caso) {
		Long cantidad = null;
		cantidad = casoRepository.getCountFilter(caso);

		return cantidad;
	}

	public List<Caso> encontrarCasoPorFiltro(CasoFiltro casoFiltro) throws DAOException {

		List<Caso> casos = null;
		casos = casoRepository.encontrarCasoPorFiltro(casoFiltro);
		for (Caso caso : casos) {
			caso.setEstadoProcesal(getEstadoProcesalByCaso(caso));
			Hibernate.initialize(caso.getRadicadoSet());
			Hibernate.initialize(caso.getCasoEquipoCasoSet());
			for (CasoEquipoCaso casoEquipoCaso : caso.getCasoEquipoCasoSet()) {
				Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getTelefonoList());
				Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getCelularList());
				Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getCorreoList());
				Hibernate.initialize(casoEquipoCaso.getResponsablesTareas());

			}

		}
		return casos;

	}
	
	public void enviarCorreoCambioFechaCaducidad(Caso caso, SocialUserDetails user) throws DAOException, BusinessException, MessagingException {
		List<CasoEquipoCaso> equipoCasosAbogados = casoEquipoCasoService.obtenerAbogadosDelCaso(caso.getCodigo());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<String> correos = new ArrayList<>();
		for (CasoEquipoCaso casoEquipoCaso : equipoCasosAbogados) {
			correos.add(casoEquipoCaso.getEquipoCaso().getUsuario().getEmail());
		}
		
		MimeMessage message = null;
		MimeMessageHelper helper = null;
		message = this.mailSender.createMimeMessage();
		helper = new MimeMessageHelper(message, true);
		List<String> to = new ArrayList<>();
		List<User> correosAdmin = casoRepository.consultarCorreoAdmind();
		for (User objUser : correosAdmin) {
			to.add(objUser.getEmail());
		}
		to.addAll(correos);
		
		StringBuilder cuerpoMensaje = new StringBuilder();
		
		cuerpoMensaje.append("Se le informa que el caso "+caso.getNombre()+" con codigo: "+caso.getCodigo()+" fue modificado y ahora su fecha de caducidad sera "+simpleDateFormat.format(caso.getFechaCaducidad())+"\n\n");
		cuerpoMensaje.append("La modificación fue realizada por "+user.getNombre()+" "+user.getApellido());
		
		helper.setTo((String[]) to.toArray(new String[0]));
		helper.setSubject(Parametros.getAsuntoModificacionFechas() + " " + caso.getNombre());
		helper.setText(cuerpoMensaje.toString());
		this.mailSender.send(message);
		
		
		
	}
	// public void obtenerCodigoEquipo(Caso caso,List<TareaParticularCaso>
	// tareas){

	public JSONArray obtenerCodigoEquipo(Caso caso) {
		JSONArray codigosEquipos = new JSONArray();

		for (CasoEquipoCaso casoEquipoCaso : caso.getCasoEquipoCasoSet()) {
			JSONObject jsO = new JSONObject();
			jsO.put("identificacion", casoEquipoCaso.getEquipoCaso().getIdentificacion());
			jsO.put("codigoEquipoCaso", casoEquipoCaso.getEquipoCaso().getCodigoEquipoCaso());
			jsO.put("codigoMiembro", casoEquipoCaso.getCasoEquipoCasoPK().getMiembro());
			codigosEquipos.add(jsO);
		}
		return codigosEquipos;

	}

	public Long getCountByTipoCasoCaso(TipoCaso tipoCaso) throws DAOException, BusinessException {
		Long cantidad = casoRepository.getCountByTipoCasoCaso(tipoCaso.getCodigo());
		return cantidad;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public Caso modificarEstadoCaso(Caso caso, Justificacion justificacion, HttpServletRequest request)
			throws DAOException, BusinessException, MessagingException {
		
		Caso casoModificado = casoRepository.findByPK(caso);
//		String estadoDelCaso = casoModificado.getEstadoCaso().getNombre();		
		
		Integer codigoCasoNumero = caso.getCodigo();
		List<String> parametros = new ArrayList<String>();
		
		parametros.add(String.valueOf(codigoCasoNumero));
//		parametros.add(String.valueOf(estadoDelCaso));
		casoModificado.setEstadoCaso(caso.getEstadoCaso());
		casoModificado.setFechaUltimaActualizacion(new Date());
		casoModificado.setUsuarioUltimaModificacion(caso.getUsuarioUltimaModificacion());
		casoModificado = casoRepository.merge(casoModificado);
		
		String informacion = Parametros.getAuditoriaCaso();
		informacion = justificacionService.construirInformacionEliminada(parametros, informacion);
		
		justificacion.setInformacionEliminada(informacion);
		justificacion.setTipoAccion(Parametros.getTipoAccionModificacion());
		justificacion.setCampoModificado(Parametros.getCampoModificadoCaso());
		justificacion.setCodigoCaso(caso);
		justificacionService.guardarJustificacion(justificacion);

		if (request.isUserInRole("ROLE_SOCIO") || request.isUserInRole("ROLE_ASOCIADO")
				|| request.isUserInRole("ROLE_JUNIOR") || request.isUserInRole("ROLE_DEPENDIENTE")) {
			StringBuilder cuerpoMensaje = new StringBuilder();
			List<User> listaUsuarios = new ArrayList<User>();
			listaUsuarios = casoRepository.consultarCorreoAdmind();
			MimeMessage message = null;
			MimeMessageHelper helper = null;
			message = this.mailSender.createMimeMessage();
			helper = new MimeMessageHelper(message, true);
			List to = new ArrayList();
			for (User objUser : listaUsuarios) {
				to.add(objUser.getEmail());
			}
			String estado = casoRepository.getEstadoCaso(casoModificado);
			cuerpoMensaje.append("Nombre Caso: " + casoModificado.getNombre() + "\n");
			cuerpoMensaje.append("Estado: " + (estado != null ? estado : "") + "\n");
			cuerpoMensaje.append("Justificación: " + justificacion.getJustificacion() + "\n");
			helper.setTo((String[]) to.toArray(new String[0]));
			helper.setSubject(Parametros.getAsuntoActualizacion() + " " + casoModificado.getNombre());
			helper.setText(cuerpoMensaje.toString());
			this.mailSender.send(message);
		}
		return casoModificado;
	}

	@Transactional
	public Caso modificarDetalleCaso(Caso caso) throws DAOException, BusinessException {

		Caso casoModificado = casoRepository.findByPK(caso);

		casoModificado.setTipoCaso(caso.getTipoCaso());
		casoModificado.setNombre(caso.getNombre());
		casoModificado.setFechaHecho(caso.getFechaHecho());
		casoModificado.setFechaFinHecho(caso.getFechaFinHecho());
		casoModificado.setFechaCaducidad(caso.getFechaCaducidad());
		casoModificado.setCiudadHechos(caso.getCiudadHechos());
		casoModificado.setDireccionHechos(caso.getDireccionHechos());
		casoModificado.setResumenHechos(caso.getResumenHechos());
		casoModificado.setCiudadProceso(caso.getCiudadProceso());
		casoModificado.setNumeroDespacho(caso.getNumeroDespacho());
		casoModificado.setNombreFuncionario(caso.getNombreFuncionario());
		casoModificado.setDireccionProceso(caso.getDireccionProceso());
		casoModificado.setFechaUltimaActualizacion(new Date());
		casoModificado.setUsuarioUltimaModificacion(caso.getUsuarioUltimaModificacion());
		casoModificado.setComentario(caso.getComentario());
		casoModificado = casoRepository.merge(casoModificado);

		return casoModificado;
	}

	public List<User> consultarCorreoAdmind() throws DAOException, BusinessException {
		List<User> listaUser = new ArrayList<User>();
		return listaUser = casoRepository.consultarCorreoAdmind();
	}

	public String getNombreCaso(Integer codigoCaso) throws DAOException, BusinessException {
		String nombreCaso = casoRepository.getNombreCaso(codigoCaso);
		return nombreCaso;
	}
	
	@Override
	public List<Object[]> consultarCasosPorTipoCaso(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		return casoRepository.consultarCasosPorTipoCaso(equipoCaso);
	}
	
	
	@Override
	public List<Object[]> consultarCasosActividadesPrincipales(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		return casoRepository.consultarCasosActividadesPrincipales(equipoCaso);
	}
	
	@Override
	public List<Object[]> consultarCasosPorEstadoCaso(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		return casoRepository.consultarCasosPorEstadoCaso(equipoCaso);
	}
	
	@Override
	public String getDemandadosCasoString(String codigoCaso)
			throws DAOException, BusinessException {
		String demandadosString = null;
		List<Object[]> demandadosList = casoRepository.getDemandadosCasoString(codigoCaso);
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("");
		for (Object[] obj:demandadosList) {
			strBuilder.append(obj[0] + " ");
			strBuilder.append((obj[1] !=  null ? obj[1] : "") + ", ");
		}
		demandadosString = strBuilder.toString();
		if (demandadosList.size() > 0)
			demandadosString = demandadosString.substring(0, demandadosString.length() - 2);
		return demandadosString;
	}
	
	
	@Override
	public List<Object[]> consultarNombresCasos(EquipoCaso equipoCaso) throws DAOException, BusinessException {
		return casoRepository.consultarNombresCasos(equipoCaso);
	}
}
