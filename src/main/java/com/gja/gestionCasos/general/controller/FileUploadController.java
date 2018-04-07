package com.gja.gestionCasos.general.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadCaso;
import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.actividades.entities.Archivo;
import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.ResponsableTareaPK;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.service.ActividadCasoService;
import com.gja.gestionCasos.actividades.service.ArchivoService;
import com.gja.gestionCasos.casos.entities.Abono;
import com.gja.gestionCasos.casos.entities.ArchivoAbono;
import com.gja.gestionCasos.casos.entities.BienAfectado;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCasoPK;
import com.gja.gestionCasos.casos.entities.Celular;
import com.gja.gestionCasos.casos.entities.Correo;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Prestamo;
import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.entities.RadicadoPK;
import com.gja.gestionCasos.casos.entities.Telefono;
import com.gja.gestionCasos.casos.service.AbonoService;
import com.gja.gestionCasos.casos.service.CasoService;
import com.gja.gestionCasos.casos.service.EquipoCasoService;
import com.gja.gestionCasos.casos.service.PrestamoService;
import com.gja.gestionCasos.casos.service.TareaParticularCasoService;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.gja.gestionCasos.maestros.entities.ClaseBien;
import com.gja.gestionCasos.maestros.entities.Departamento;
import com.gja.gestionCasos.maestros.entities.EstadoCaso;
import com.gja.gestionCasos.maestros.entities.Instancia;
import com.gja.gestionCasos.maestros.entities.Pais;
import com.gja.gestionCasos.maestros.entities.Parentesco;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.gja.gestionCasos.maestros.entities.TipoMiembro;
import com.gja.gestionCasos.maestros.service.CiudadService;
import com.gja.gestionCasos.maestros.service.ClaseBienService;
import com.gja.gestionCasos.maestros.service.DepartamentoService;
import com.gja.gestionCasos.maestros.service.EstadoCasoService;
import com.gja.gestionCasos.maestros.service.InstanciaService;
import com.gja.gestionCasos.maestros.service.PaisService;
import com.gja.gestionCasos.maestros.service.ParentescoService;
import com.gja.gestionCasos.maestros.service.TipoCasoService;
import com.gja.gestionCasos.maestros.service.TipoDocumentoService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.ParametrosDocumentos;
import com.sf.util.Utilidades;

import net.minidev.json.JSONArray;


@Controller
@RequestMapping(value = { "/fileUpload" })
public class FileUploadController {

	private static final Logger LOG = Logger.getLogger(FileUploadController.class);
	private static final String ERROR_FECHA_VENCIMIENTO = "ERROR: La fecha de vencimiento es obligatoria.";
	private static final String ERROR_RESPONSABLE_TAREA = "ERROR: El responsable de la tarea es obligatorio.";
//	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";
	private String infoError = "";
	private Map<String, XSSFCell> celdasResponsables = null;
	private Map<String, XSSFCell> celdasVencimientos = null;
	private Map<String, XSSFCell> celdasTareas = null;
	private List<String> nombresTareas = null;
	private String fechaVencimientoActividadString = "";
	private String abogadosString = "";
	private final String caracterMarca = "X";
	private int codigoTipoCasoGlobal = 0;
	private int codigoActividadGlobal = 0;
	

	private ParametrosDocumentos parametrosDocumentos = new ParametrosDocumentos();
	
	
	@Autowired
	ClaseBienService claseBienService;
	
	@Autowired	
	TipoCasoService tipoCasoService;

	@Autowired
	PaisService paisService;

	@Autowired
	CiudadService ciudadService;

	@Autowired
	DepartamentoService DepartamentoService;
	
	@Autowired
	AbonoService abonoService;

	@Autowired
	ParentescoService parentescoService;
	@Autowired
	EquipoCasoService equipoCasoService;
	@Autowired
	CasoService casoService;
	@Autowired
	TareaParticularCasoService tareaParticularCasoService;
	@Autowired
	ActividadCasoService actividadCasoService;
	@Autowired
	ArchivoService archivoService;
	@Autowired
	PrestamoService prestamoService;
	@Autowired
	EstadoCasoService estadoCasoService;
	@Autowired
	InstanciaService instanciaService;
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;
	
	@Autowired
	TipoDocumentoService tipoDocumentoService;

	@RequestMapping(method = { RequestMethod.GET })
	public void getInitialMessage() {
	}

	@RequestMapping
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();

		menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
		vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
				httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);

		model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
		model.addAttribute("permisos", vistaPermisosRolesWrapper);

		return "greet";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/importarExcel" }, method = { RequestMethod.POST }, produces = { "application/json; charset=utf-8" }, headers = { "Accept=*/*" })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String importarExcel(@RequestParam("file") MultipartFile file, HttpServletRequest context, @RequestParam(value = "cdusuarioLogeado", required = false) String cdusuarioLogeado) {
		JSONObject res = new JSONObject();
		String nombreArchivo = "";
		Date fecha = new Date();
		ServletContext servletContext = context.getSession().getServletContext();
		String relativeWebPath = Parametros.getRutaCargarExcel();
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
		int contadorNotas = 0;
		try {
			if (!file.isEmpty()) {
				nombreArchivo = file.getOriginalFilename();

				File archivo = new File(Parametros.getRutaCargaArchivos());
				if (!archivo.exists()) {
					archivo.mkdir();
				}
				File archivoEnAplicativo = new File(absoluteDiskPath);
				if (!archivoEnAplicativo.exists()) {
					archivoEnAplicativo.mkdir();
				}
				Date identificador = new Date();
				archivo = new File(Parametros.getRutaCargarExcel()
						+ identificador.getTime() + ""
						+ file.getOriginalFilename());
				archivoEnAplicativo = new File(archivoEnAplicativo + "/"
						+ identificador.getTime() + ""
						+ file.getOriginalFilename());
				file.transferTo(archivo);
				String ruta = archivo.toString();

				Caso casoInformacion = new Caso();
				Caso lugarHecho = new Caso();
				Caso lugarProceso = new Caso();
				List<Object> casoGrupoFamiliar = new ArrayList<Object>();
				List<CasoEquipoCaso> abogados = new ArrayList<CasoEquipoCaso>();
				Caso caso = new Caso();
				
				HashMap<String, Object> bundle = new HashMap<String, Object>();
				
				List<CasoEquipoCaso> listaIntegrantesEquipo = new ArrayList<CasoEquipoCaso>();
				List<BienAfectado> listaBienAfectado = new ArrayList<BienAfectado>();
				List<BienAfectado> listaReferenciador = new ArrayList<BienAfectado>();
				List<Radicado> listaRadicado = new ArrayList<Radicado>();
				List<ActividadCaso> listaTareaParticular = new ArrayList<ActividadCaso>();
				BienAfectado objetoBienAfectado = new BienAfectado();
				FileInputStream fis = new FileInputStream(ruta);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				int k = 0;
				String resumenInfo = "";
				XSSFSheet sheet = wb.getSheetAt(0); // HOJA CONTROL PROCESOS GJA
				//XSSFSheet sheet2 = wb.getSheetAt(1);// HOJA INFORMACIÓN ADICIONAL
				
				@SuppressWarnings("rawtypes")
				Iterator iterator = sheet.rowIterator();
				while (iterator.hasNext()) {
					int pContador = 0;
					Calendar cal = Calendar.getInstance();
					XSSFRow row = sheet.getRow(8);
					casoInformacion = LlenarInformacionCaso(row);// Informacion	Basica del caso
					
					caso.setNombre(casoInformacion.getNombre());
					caso.setTipoCaso(casoInformacion.getTipoCaso());
					caso.setFechaHecho(casoInformacion.getFechaHecho());
					caso.setFefin(casoInformacion.getFefin());
					caso.setEstadoCaso(casoInformacion.getEstadoCaso());
					caso.setFechaFinHecho(casoInformacion.getFefin());
					caso.setFechaCreacion(fecha);
					
					cal.setTime(casoInformacion.getFefin());
					cal.add(Calendar.YEAR, Integer.parseInt(Parametros.getAnosCaducidad()));
					Date date = cal.getTime();
					caso.setFechaCaducidad(date);
					XSSFRow resumenHechos = sheet.getRow(9);
					XSSFCell resumen = resumenHechos.getCell(10);
					if (!resumen.getStringCellValue().isEmpty() && resumen != null) {
						caso.setResumenHechos(resumen.getStringCellValue());// RESUMEN DE LOS HECHOS
					}
					XSSFRow rowLugarHechos = sheet.getRow(10);
					lugarHecho = LlenarLugarHechos(rowLugarHechos);// LUGAR DE LOS HECHOS
					
					caso.setCiudadHechos(lugarHecho.getCiudadHechos());
					caso.setDireccionHechos(lugarHecho.getDireccionHechos());
					XSSFRow rowLugarProceso = sheet.getRow(12);
					lugarProceso = LlenarLugarProceso(rowLugarProceso); // LUGAR DEL PROCESO
					
					caso.setCiudadProceso(lugarProceso.getCiudadProceso());
					caso.setDireccionProceso(lugarProceso.getDireccionProceso());
					XSSFRow rowAbogado = sheet.getRow(27);
					abogados = LlenarAbogados(rowAbogado, null);// ABOGADOS DEL CASO
					
					listaIntegrantesEquipo.addAll(abogados);
					
					/** RADICADOS */
					bundle = getRadicados(caso, pContador, sheet, cdusuarioLogeado, listaRadicado);
					caso = (Caso) bundle.get("caso");
					pContador = (Integer) bundle.get("pContador");
					bundle.clear();
					
					 
					/** Grupo Familiar / Demandantes */
					
					bundle = getGrupoFamiliarDemandates(k, sheet,
							abogados, cdusuarioLogeado, casoGrupoFamiliar, listaIntegrantesEquipo, listaTareaParticular);
					
					
					listaIntegrantesEquipo = (List<CasoEquipoCaso>) bundle.get("listaIntegrantesEquipo");
					listaTareaParticular = (List<ActividadCaso>) bundle.get("listaTareaParticular");
					k = (Integer) bundle.get("k");
					bundle.clear();
					
					
					/** Victimas */
					bundle = getVictimas(k, sheet, abogados, 
							cdusuarioLogeado, listaIntegrantesEquipo, listaTareaParticular);
					listaIntegrantesEquipo = (List<CasoEquipoCaso>) bundle.get("listaIntegrantesEquipo");
					listaTareaParticular = (List<ActividadCaso>) bundle.get("listaTareaParticular");
					k = (Integer) bundle.get("k");
					bundle.clear();
					
					
					/** Testigos */
					bundle = getTestigos(k, contadorNotas, sheet, caso, abogados, 
							cdusuarioLogeado, listaIntegrantesEquipo);
					caso = (Caso) bundle.get("caso");
					k = (Integer) bundle.get("k");
					listaIntegrantesEquipo = (List<CasoEquipoCaso>) bundle.get("listaIntegrantesEquipo");
					bundle.clear();

					
					/** Testigos */
					bundle = getReferenciador(k, contadorNotas, sheet, caso, abogados, 
							cdusuarioLogeado, listaIntegrantesEquipo);
					caso = (Caso) bundle.get("caso");
					k = (Integer) bundle.get("k");
					listaIntegrantesEquipo = (List<CasoEquipoCaso>) bundle.get("listaIntegrantesEquipo");
					bundle.clear();					
					
					/** Demandado */
					bundle = getDemandados(pContador, listaIntegrantesEquipo, sheet);
					pContador = (Integer) bundle.get("pContador");
					listaIntegrantesEquipo = (List<CasoEquipoCaso>) bundle.get("listaIntegrantesEquipo");
					bundle.clear();
					
					/** BIENES AFECTADOS */
					bundle = getBienesAfectados(caso, sheet, pContador, objetoBienAfectado, listaBienAfectado);
					caso = (Caso) bundle.get("caso");
					pContador = (Integer) bundle.get("pContador");
					bundle.clear();						
					
					/** RADICADOS */
					//bundle = getRadicados(caso, pContador, sheet, cdusuarioLogeado, listaRadicado);
					//caso = (Caso) bundle.get("caso");
					//pContador = (Integer) bundle.get("pContador");
					//bundle.clear();
					
					/** DESPACHO */
					bundle = getDespacho(caso, sheet, listaIntegrantesEquipo);
					caso = (Caso) bundle.get("caso");
					bundle.clear();
					
					/** Notas */
					bundle = getNotas(caso, resumenInfo, listaTareaParticular, res);
					caso = (Caso) bundle.get("caso");
					bundle.clear();

			
					
					
					res.put("casoCodigo", caso.getCodigo());
					break;
				}
				res.put("STATUS", "SUCCESS");
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
			res.put("MENSAJE", infoError);
		} catch (DAOException e) {
			e.printStackTrace();
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
			res.put("MENSAJE", infoError);
		} catch (BusinessException e) {
			e.printStackTrace();
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
			res.put("MENSAJE", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
			res.put("MENSAJE", "Error al tratar de realizar la carga de archivos para el documento: " + nombreArchivo);
		} 
		return res.toString();
	}
	
	/** Grupo Familiar / Demandantes 
	 * @throws BusinessException 
	 * @throws DAOException */
	private HashMap<String, Object> getGrupoFamiliarDemandates(int k, XSSFSheet sheet, 
			List<CasoEquipoCaso> abogados,
			String cdusuarioLogeado,
			List<Object> casoGrupoFamiliar, 
			List<CasoEquipoCaso> listaIntegrantesEquipo,
			List<ActividadCaso> listaTareaParticular
			) throws DAOException, BusinessException {
		HashMap<String, Object> retorno = new HashMap<String, Object>();
		
		XSSFRow rowDemandados = sheet.getRow(29+k);
				
		XSSFCell etiquetaTestigos = rowDemandados.getCell(0);
		
		String etiquetaInfoTestigo = etiquetaTestigos.getStringCellValue();
		
		celdasTareas = new HashMap<String, XSSFCell>();
		celdasResponsables = new HashMap<String, XSSFCell>();
		celdasVencimientos = new HashMap<String, XSSFCell>();
		nombresTareas = new ArrayList<String>();
		
		while (!etiquetaInfoTestigo.equalsIgnoreCase("VICTIMA")) {
			rowDemandados = sheet.getRow(29 + k);
			etiquetaTestigos = rowDemandados.getCell(0);
						
			etiquetaInfoTestigo = etiquetaTestigos.getStringCellValue();
			
			if (!etiquetaInfoTestigo.equalsIgnoreCase("VICTIMA") && !etiquetaInfoTestigo.equalsIgnoreCase("DEMANDANTES/GRUPO FAMILIAR")) {
				
				casoGrupoFamiliar = LlenarFamiliares(rowDemandados, cdusuarioLogeado, abogados);

				if (!casoGrupoFamiliar.isEmpty() && casoGrupoFamiliar != null) {
					listaIntegrantesEquipo.add((CasoEquipoCaso) casoGrupoFamiliar.get(0));
					listaTareaParticular.add((ActividadCaso) casoGrupoFamiliar.get(1));
				}
			}
			k ++;
		}
		retorno.put("k", k);
		retorno.put("listaIntegrantesEquipo", listaIntegrantesEquipo);
		retorno.put("listaTareaParticular", listaTareaParticular);
				
		return retorno;
	}

	/** Testigos 
	 * @throws BusinessException */
	private HashMap<String, Object> getTestigos(int k, int contadorNotas, XSSFSheet sheet, Caso caso,
			List<CasoEquipoCaso> abogados,
			String cdusuarioLogeado, 
			List<CasoEquipoCaso> listaIntegrantesEquipo
			) throws BusinessException {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		
		
		XSSFRow rowTestigos = sheet.getRow(29 + k);
		XSSFCell etiquetaVictima = rowTestigos.getCell(0);
		String etiquetaInfoVictima = etiquetaVictima.getStringCellValue();
		CasoEquipoCaso equipoCasoTestigpReturn = null;
		
		while (!etiquetaInfoVictima.equalsIgnoreCase("TESTIGOS") &&  !etiquetaInfoVictima.equalsIgnoreCase("REFERENCIADOR") && etiquetaInfoVictima != null) {
			rowTestigos = sheet.getRow(29 + k);
			etiquetaVictima = rowTestigos.getCell(0);
			
			if (etiquetaVictima != null) {				
				etiquetaInfoVictima = etiquetaVictima.getStringCellValue();
				if (!etiquetaInfoVictima.equalsIgnoreCase("")
						&& !etiquetaInfoVictima.equalsIgnoreCase("NOMBRES COMPLETOS *")
						&& !etiquetaInfoVictima.equalsIgnoreCase("REFERENCIADOR")) {
					equipoCasoTestigpReturn = LlenarTestigos(rowTestigos);
					listaIntegrantesEquipo.add(equipoCasoTestigpReturn);
				}
				if (contadorNotas == 0) {
					XSSFCell infoComentario = rowTestigos.getCell(14);
					if (infoComentario != null) {
						caso.setComentario(infoComentario.getStringCellValue());
					}
				}
				contadorNotas++;
			}
			k++;
		}
		bundle.put("caso", caso);
		bundle.put("listaIntegrantesEquipo", listaIntegrantesEquipo);
		bundle.put("k", k);
		
		return bundle;
	}
	
	/** Victimas 
	 * @throws BusinessException 
	 * @throws DAOException */
	private HashMap<String, Object> getVictimas(int k, XSSFSheet sheet, 
			List<CasoEquipoCaso> abogados,
			String cdusuarioLogeado, 
			List<CasoEquipoCaso> listaIntegrantesEquipo,
			List<ActividadCaso> listaTareaParticular
			) throws BusinessException, DAOException{
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		

		//XSSFRow rowVictimas = sheet.getRow(33 + k);
		XSSFRow rowVictimas = sheet.getRow(29 + k);
		XSSFCell etiquetaDemandados = rowVictimas.getCell(0);
		List<Object> casoGrupoFamiliar = new ArrayList<Object>();
		String etiquetaInfoDemandados = etiquetaDemandados.getStringCellValue();
		
		CasoEquipoCaso equipoCasoVictimaReturn = null;
		while (!etiquetaInfoDemandados.equalsIgnoreCase("TESTIGOS") && !etiquetaInfoDemandados.equalsIgnoreCase("VICTIMA") 
				&& !etiquetaInfoDemandados.equalsIgnoreCase("REFERENCIADOR") && etiquetaDemandados != null ) {
			rowVictimas = sheet.getRow(29+k);
			etiquetaDemandados = rowVictimas.getCell(0);
			
			
			if (etiquetaDemandados != null) {
				etiquetaInfoDemandados = etiquetaDemandados.getStringCellValue();
				if (!etiquetaInfoDemandados.equalsIgnoreCase("NOMBRES COMPLETOS *")
						&& !etiquetaInfoDemandados.equalsIgnoreCase("")
						&& !etiquetaInfoDemandados.equalsIgnoreCase("TESTIGOS")) {
					casoGrupoFamiliar = LlenarFamiliares(rowVictimas, cdusuarioLogeado, abogados);
					equipoCasoVictimaReturn = LlenarVictimas(rowVictimas);
					listaIntegrantesEquipo.add(equipoCasoVictimaReturn);
					
					if (!casoGrupoFamiliar.isEmpty() && casoGrupoFamiliar != null) {
//						listaIntegrantesEquipo.add((CasoEquipoCaso) casoGrupoFamiliar.get(0));
						listaTareaParticular.add((ActividadCaso) casoGrupoFamiliar.get(1));
					}
				}
			}
			k++;
		}
		
		bundle.put("k", k);
		bundle.put("listaIntegrantesEquipo", listaIntegrantesEquipo);
		bundle.put("listaTareaParticular", listaTareaParticular);

		
		return bundle;
	}
	
	
	private HashMap<String, Object> getReferenciador(int k, int contadorNotas, XSSFSheet sheet, Caso caso,
			List<CasoEquipoCaso> abogados,
			String cdusuarioLogeado, 
			List<CasoEquipoCaso> listaIntegrantesEquipo
			) throws BusinessException {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		
		XSSFRow rowReferenciador = sheet.getRow(29 + k);
		XSSFCell etiquetaReferenciador = rowReferenciador.getCell(0);
		
		String etiquetaInfoReferenciador = etiquetaReferenciador.getStringCellValue();
		CasoEquipoCaso equipoCasoTestigpReturn = null;
		
		
		while (etiquetaInfoReferenciador!=null) {
			rowReferenciador = sheet.getRow(29 + k);
			if(rowReferenciador.getCell(0)!=null){
				etiquetaReferenciador = rowReferenciador.getCell(0);
				if (etiquetaReferenciador != null) {				
					etiquetaInfoReferenciador = etiquetaReferenciador.getStringCellValue();
					if (!etiquetaInfoReferenciador.equalsIgnoreCase("VICTIMA") 
							&& !etiquetaInfoReferenciador.equalsIgnoreCase("REFERENCIADOR") && !etiquetaInfoReferenciador.equalsIgnoreCase("")
							&& !etiquetaInfoReferenciador.equalsIgnoreCase("NOMBRES COMPLETOS *")
							&& !etiquetaInfoReferenciador.equalsIgnoreCase("TESTIGOS")) {
						equipoCasoTestigpReturn = LlenarReferenciador(rowReferenciador);
						listaIntegrantesEquipo.add(equipoCasoTestigpReturn);
					}
					if (contadorNotas == 0) {
						XSSFCell infoComentario = rowReferenciador.getCell(12);
						if (infoComentario != null) {
							caso.setComentario(infoComentario.getStringCellValue());
						}
					}
					contadorNotas++;
				}
				k++;
			}else
				break;
		}
		bundle.put("caso", caso);
		bundle.put("listaIntegrantesEquipo", listaIntegrantesEquipo);
		bundle.put("k", k);
		
		return bundle;
	}	

	/** Demandado 
	 * @throws BusinessException */
	private HashMap<String, Object> getDemandados(int pContador, List<CasoEquipoCaso> listaIntegrantesEquipo,
			XSSFSheet sheet) throws BusinessException{
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		
		XSSFRow rowDemandado = sheet.getRow(15);
		XSSFCell etiquetaDemandado = rowDemandado.getCell(0);
		String etiquetaInfoDemandado = etiquetaDemandado.getStringCellValue();
		CasoEquipoCaso equipoCasoDemandadoReturn = null;
		
		while (!etiquetaInfoDemandado.equalsIgnoreCase("BIENES AFECTADOS")) {
			rowDemandado = sheet.getRow(15 + pContador);
			etiquetaDemandado = rowDemandado.getCell(0);
			etiquetaInfoDemandado = etiquetaDemandado.getStringCellValue();
			if (!etiquetaInfoDemandado.equalsIgnoreCase("TIPO DE PERSONA *")
					&& !etiquetaInfoDemandado.equalsIgnoreCase("BIENES AFECTADOS")
					&& !etiquetaInfoDemandado.equalsIgnoreCase("DEMANDADOS")) {
				equipoCasoDemandadoReturn = LlenarDemandados(rowDemandado);
				listaIntegrantesEquipo.add(equipoCasoDemandadoReturn);
			}
			pContador++;
		}
		
		bundle.put("pContador", pContador); 
		bundle.put("listaIntegrantesEquipo", listaIntegrantesEquipo);
				
		return bundle;
	}
	
	/** BIENES AFECTADOS */
	private HashMap<String, Object> getBienesAfectados(Caso caso, XSSFSheet sheet, int pContador, 
			BienAfectado objetoBienAfectado, List<BienAfectado> listaBienAfectado){
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		XSSFRow rowBienAfectado = sheet.getRow(15 + pContador);
		XSSFCell etiquetaBienAfectado = rowBienAfectado.getCell(0);
		String etiquetaRadicado = etiquetaBienAfectado.getStringCellValue();
		
		while (!etiquetaRadicado.equalsIgnoreCase("EQUIPO DEL CASO")) {
			rowBienAfectado = sheet.getRow(15 + pContador);
			etiquetaBienAfectado = rowBienAfectado.getCell(0);
			etiquetaRadicado = etiquetaBienAfectado.getStringCellValue();
			if (!etiquetaRadicado.equalsIgnoreCase("BIENES AFECTADOS")
					&& !etiquetaRadicado.equalsIgnoreCase("RADICADOS")) {
				objetoBienAfectado = LlenarBienesAfectado(rowBienAfectado);
				if (objetoBienAfectado != null) {
					objetoBienAfectado.setActivo(Parametros.getCodigoBienActivoSi());
					listaBienAfectado.add(objetoBienAfectado);
				}
			}
			pContador++;
		}
		bundle.put("pContador", pContador);

		if (listaBienAfectado != null)
			caso.setBienAfectadoSet(listaBienAfectado);
		
		bundle.put("caso", caso);
		
		return bundle;
	}
	

	
	/** RADICADOS 
	 * @throws BusinessException 
	 * @throws DAOException */
	private HashMap<String, Object> getRadicados(Caso caso, int pContador, XSSFSheet sheet, String cdusuarioLogeado, List<Radicado> listaRadicado) 
			throws DAOException, BusinessException{
		
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		
		XSSFRow rowRadicado = sheet.getRow(15 + pContador);
				
		XSSFCell etiquetaVacia = rowRadicado.getCell(0);
		String etiquetaVaciaString = etiquetaVacia.getStringCellValue();
		Radicado objRadicado = new Radicado();
		
		while (!etiquetaVaciaString.equalsIgnoreCase("") && rowRadicado != null && rowRadicado.getCell(0) != null && !etiquetaVaciaString.equalsIgnoreCase("DEMANDADOS")) {
						
			rowRadicado = sheet.getRow(15 + pContador);
			
			if (rowRadicado != null && rowRadicado.getCell(0) != null) {
				etiquetaVacia = rowRadicado.getCell(0);
				etiquetaVaciaString = etiquetaVacia.getStringCellValue();
				if (!etiquetaVaciaString.equalsIgnoreCase("") && !etiquetaVaciaString.equalsIgnoreCase("RADICADOS")) {
					objRadicado = LlenarRadicados(rowRadicado);
					if (objRadicado != null && objRadicado.getInstancia() != null) {
						objRadicado.setActivo(Parametros.getCodigoRadicadoActivoSi());
						objRadicado.setUsuarioCreacion(cdusuarioLogeado);
						objRadicado.setUsuarioUltimaModificacion(cdusuarioLogeado);
						listaRadicado.add(objRadicado);
					}
				}
			}
			
			pContador++;
		}
		
		if (listaRadicado != null)
			caso.setRadicadoSet(listaRadicado);
		
		bundle.put("pContador", pContador);
		bundle.put("caso", caso);
		
		return bundle;
	}
	
	
	/** DESPACHO */
	private HashMap<String, Object> getDespacho(Caso caso, XSSFSheet sheet, List<CasoEquipoCaso> listaIntegrantesEquipo){
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		
		XSSFRow rowDespacho = sheet.getRow(14);
		if (rowDespacho != null) {
			if (rowDespacho.getCell(3) != null) {
				XSSFCell entidadDesapacho = rowDespacho.getCell(3);
				caso.setNumeroDespacho(entidadDesapacho.getStringCellValue());
			}
			if (rowDespacho.getCell(7) != null) {
				XSSFCell funcionario = rowDespacho.getCell(7);
				caso.setNombreFuncionario(funcionario.getStringCellValue());
			}
		}

		if (listaIntegrantesEquipo != null)
			caso.setCasoEquipoCasoSet(listaIntegrantesEquipo);
		
		bundle.put("caso", caso);
		
		return bundle;
	}
	
	/** NOTAS 
	 * @throws BusinessException 
	 * @throws DAOException 
	 * @throws ParseException */
	private HashMap<String, Object> getNotas(Caso caso, String cdusuarioLogeado, 
			List<ActividadCaso> listaTareaParticular, JSONObject res) throws DAOException, BusinessException, ParseException {
		HashMap<String, Object> bundle = new HashMap<String, Object>();
		ActividadCaso actividadCaso = new ActividadCaso();
		List<TareaParticularCaso> tareasCaso = new ArrayList<TareaParticularCaso>();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		
		caso.setUsuarioUltimaModificacion(cdusuarioLogeado);
		caso.setUsuarioCreacion(cdusuarioLogeado);
		caso.setTipoorigen(Parametros.getTipoCasoOffLine());
		caso = casoService.guardarCaso(caso, null, null);
		
		List<CasoEquipoCaso> demandantes = new ArrayList<CasoEquipoCaso>();
		
		for (CasoEquipoCaso casoEquipoCaso:caso.getCasoEquipoCasoSet()) {
			if (Parametros.getDemandante().equals(Integer.toString(casoEquipoCaso.getCasoEquipoCasoPK().getMiembro())))
				demandantes.add(casoEquipoCaso);
		}
		
		for (ActividadCaso objActividad : listaTareaParticular) {
			actividadCaso.setNombreActividad(objActividad.getNombreActividad());
			actividadCaso.setUsuarioCreacion(objActividad.getUsuarioCreacion());
			actividadCaso.setUsuarioUltimaModificacion(objActividad.getUsuarioUltimaModificacion());
			actividadCaso.setFechaLimite(formatoDelTexto.parse(fechaVencimientoActividadString));
			if (objActividad.getTareaParticularCasoSet() != null) {
				for (TareaParticularCaso tarea : objActividad.getTareaParticularCasoSet()) {
					for (ResponsableTarea responsable : tarea.getResponsablesTareas()) {
						responsable.getResponsableTareaPK().setCodigoCaso(caso.getCodigo());
					}
				}
				tareasCaso.addAll(objActividad.getTareaParticularCasoSet());
			}
		}
		actividadCaso.setTareaParticularCasoSet(tareasCaso);
		actividadCaso.setEsActividadPropia(Parametros.getCodigoActividadPropiaNo());
/* Parametro para que sea una actividad propia
 * 		actividadCaso.setEsActividadPropia(Parametros.getCodigoActividadPropiaSi());
 */
		actividadCaso.setSnActiva(Parametros.getCodigoActividadActivoSi());
		if (actividadCaso.getNombreActividad().equalsIgnoreCase("")) {
			infoError = "Este tipo de Caso no tiene Asociado la Actividad Documentos Requeridos ";
		} else {
			actividadCaso = actividadCasoService.guardarActividadCaso(actividadCaso, null);
		}
		
		bundle.put("caso", caso);
		
		return bundle;
	}
	
	
	
	
	public Caso LlenarInformacionCaso(XSSFRow row) {
		Caso caso = new Caso();
		EstadoCaso estadoCasoObj = new EstadoCaso();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date fecha = null;
		TipoCaso tipoCasoObjeto = new TipoCaso();
		XSSFCell nombreCaso = null;
		XSSFCell tipoCaso = null;
		XSSFCell fechaInicio = null;
		XSSFCell fechaFin = null;
		XSSFCell estadoCaso = null;
		if (row != null) {
			nombreCaso = row.getCell(1);
			tipoCaso = row.getCell(3);
			fechaInicio = row.getCell(5);
			fechaFin = row.getCell(7);
			estadoCaso = row.getCell(9);
			if (!nombreCaso.getStringCellValue().isEmpty()
					&& nombreCaso != null)
				caso.setNombre(nombreCaso.getStringCellValue());
			if (!tipoCaso.getStringCellValue().isEmpty() && tipoCaso != null) {
				tipoCasoObjeto.setNombre(tipoCaso.getStringCellValue());
				try {
					Integer codigo = tipoCasoService.consultarCodigoTipoCaso(tipoCasoObjeto);
					tipoCasoObjeto.setCodigo(codigo);
					codigoTipoCasoGlobal = codigo;
				} catch (DAOException e) {
					LOG.error("DAOException: Ocurrio un error consultando codigo del tipo de caso. El error es: "
							+ e.getMessage());
					infoError = " El tipo de Caso de la Hoja de Control no es valido";
				} catch (BusinessException e) {
					LOG.error("BusinessException: Ocurrio un error consultando codigo de tipo de caso . El error es: "
							+ e.getMessage());
				}
				caso.setTipoCaso(tipoCasoObjeto);
			}

			if (!estadoCaso.getStringCellValue().isEmpty() && estadoCaso != null) {
				estadoCasoObj.setNombre(estadoCaso.getStringCellValue());
				try {
					Integer codigo = estadoCasoService
							.consultarCodigoEstadoCaso(estadoCasoObj);
					estadoCasoObj.setCodigo(codigo);
					caso.setEstadoCaso(estadoCasoObj);
				} catch (DAOException e) {
					LOG.error("DAOException: Ocurrio un error consultando codigo del estado del caso. El error es: "
							+ e.getMessage());
					infoError = "El estado de Caso de la Hoja de Control no es valido";
				} catch (BusinessException e) {
					LOG.error("BusinessException: Ocurrio un error consultando codigo del estado de caso . El error es: "
							+ e.getMessage());
				}
			} else {
				infoError = "Los campos marcados con (*) son requeridos.";
			}

			if (!fechaInicio.getStringCellValue().isEmpty() && fechaInicio != null) {
				try {
					if (!fechaInicio.getStringCellValue().equals("")
							&& fechaInicio.getStringCellValue() != null) {
						fecha = formatoDelTexto.parse(fechaInicio
								.getStringCellValue());
						caso.setFechaHecho(fecha);
					}
				} catch (ParseException e) {
					LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Inicio del caso. El error es: "
							+ e.getMessage());
					infoError = " Verifique la fecha de Inicio el formato no corresponde";
				}
			} else {
				infoError = "Los campos marcados con (*) son requeridos.";
			}
			if (!fechaFin.getStringCellValue().isEmpty() && fechaFin != null) {
				try {
					if (!fechaFin.getStringCellValue().equals("")
							&& fechaFin.getStringCellValue() != null) {
						fecha = formatoDelTexto.parse(fechaFin
								.getStringCellValue());
						caso.setFefin(fecha);
					}
				} catch (ParseException e) {
					LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Fin del caso. El error es: "
							+ e.getMessage());
					infoError = " Verifique la Fecha de Fin el formato no corresponde";
				}
			} else {
				infoError = "Los campos marcados con (*) son requeridos.";
			}
		} else {
			infoError = "Los campos marcados con (*) son requeridos.";
		}

		return caso;
	}

	public static Caso LlenarFechaCaso(XSSFRow rowFechas) {
		Caso casoFechas = new Caso();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date fecha = null;
		XSSFCell fechaInicio = null;
		XSSFCell fechaFin = null;
		XSSFCell resumen = null;
		if (rowFechas != null) {
			fechaInicio = rowFechas.getCell(2);
			fechaFin = rowFechas.getCell(6);
			resumen = rowFechas.getCell(8);

			if (!fechaInicio.getStringCellValue().isEmpty()
					&& fechaInicio != null) {
				try {
					if (!fechaInicio.getStringCellValue().equals("")
							&& fechaInicio.getStringCellValue() != null) {
						fecha = formatoDelTexto.parse(fechaInicio
								.getStringCellValue());
						casoFechas.setFechaHecho(fecha);
					}
				} catch (ParseException e) {
					LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Inicio del caso. El error es: "
							+ e.getMessage());
				}
			}
			if (!fechaFin.getStringCellValue().isEmpty() && fechaFin != null) {
				try {
					if (!fechaFin.getStringCellValue().equals("")
							&& fechaFin.getStringCellValue() != null) {
						fecha = formatoDelTexto.parse(fechaFin
								.getStringCellValue());
						casoFechas.setFefin(fecha);
					}
				} catch (ParseException e) {
					LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Fin del caso. El error es: "
							+ e.getMessage());
				}
			}
			if (resumen != null)
				casoFechas.setResumenHechos(resumen.getStringCellValue());
		}
		return casoFechas;
	}

	/** METODO PARA LLENAR LA INFORMACION DEL LUGAR DE LOS HECHOS */
	public Caso LlenarLugarHechos(XSSFRow rowLugarHechos) {
		Caso casoHechos = new Caso();
		Ciudad ciudadObjeto = new Ciudad();
		Departamento departamentoObjeto = new Departamento();
		Pais paisObjeto = new Pais();
		Pais objPais = null;
		List<Ciudad> listCiudad = null;
		Departamento objDepartamento = null;
		XSSFCell ciudad = null;
		XSSFCell departemento = null;
		XSSFCell pais = null;
		XSSFCell descripcion = null;
		if (rowLugarHechos != null) {
			ciudad = rowLugarHechos.getCell(1);
			departemento = rowLugarHechos.getCell(3);
			pais = rowLugarHechos.getCell(5);
			descripcion = rowLugarHechos.getCell(7);
			if (!descripcion.getStringCellValue().isEmpty()
					&& descripcion != null)
				casoHechos.setDireccionHechos(descripcion.getStringCellValue());
			if (!ciudad.getStringCellValue().isEmpty() && ciudad != null) {
				ciudadObjeto.setNombre(ciudad.getStringCellValue());
				try {
					listCiudad = ciudadService
							.consultarCodigoCiudad(ciudadObjeto);
					for (Ciudad obj : listCiudad) {
						casoHechos.setCiudadHechos(obj);
					}
				} catch (DAOException e) {
					LOG.error("DAOException: Ocurrio un error consultando codigos de la ciudad. El error es: "
							+ e.getMessage());
					infoError = " La ciudad seleccionada no existe";

				} catch (BusinessException e) {
					LOG.error("BusinessException: Ocurrio un error consultando los codigos de la ciudad . El error es: "
							+ e.getMessage());
				}
			} else {
				if (!departemento.getStringCellValue().isEmpty()
						&& departemento != null) {
					departamentoObjeto.setNombre(departemento
							.getStringCellValue());
					try {
						objDepartamento = DepartamentoService
								.consultarCodigoDepartamento(departamentoObjeto);
						ciudadObjeto.setDepartamento(objDepartamento);
						casoHechos.setCiudadHechos(ciudadObjeto);
					} catch (DAOException e) {
						LOG.error("DAOException: Ocurrio un error consultando codigos del departamento. El error es: "
								+ e.getMessage());
						infoError = " El Departamento seleccionado no existe";
					} catch (BusinessException e) {
						LOG.error("BusinessException: Ocurrio un error consultando los codigos del departamento . El error es: "
								+ e.getMessage());
					}
				} else {
					if (!pais.getStringCellValue().isEmpty() && pais != null) {
						paisObjeto.setNombre(pais.getStringCellValue());
						try {
							objPais = paisService
									.consultarCodigoPais(paisObjeto);
							departamentoObjeto.setPais(objPais);
							ciudadObjeto.setDepartamento(departamentoObjeto);
							casoHechos.setCiudadHechos(ciudadObjeto);
						} catch (DAOException e) {
							LOG.error("DAOException: Ocurrio un error consultando codigos del pais. El error es: "
									+ e.getMessage());
							infoError = " El Pais seleccionado no existe";
						} catch (BusinessException e) {
							LOG.error("BusinessException: Ocurrio un error consultando los codigos del pais. El error es: "
									+ e.getMessage());
						}
					} else {
						infoError = "Los campos marcados con (*) son requeridos.";
					}
				}

			}

		} else {
			infoError = "Los campos marcados con (*) son requeridos.";
		}
		return casoHechos;
	}

	/** METODO PARA LA INFORMACION DEL LUGAR DE LOS HECHOS */
	public Caso LlenarLugarProceso(XSSFRow rowLugarProceso) {
		Caso casoProceso = new Caso();
		Ciudad ciudadObjeto = new Ciudad();
		List<Ciudad> listaciudad = null;
		Departamento departamentoObjeto = new Departamento();
		Pais paisObjeto = new Pais();
		Pais objPais = null;
//		List<Ciudad> listCiudad = null;
		Departamento objDepartamento = null;
		XSSFCell ciudad = null;
		XSSFCell departemento = null;
		XSSFCell pais = null;
		XSSFCell descripcion = null;
		if (rowLugarProceso != null) {
			ciudad = rowLugarProceso.getCell(1);
			departemento = rowLugarProceso.getCell(3);
			pais = rowLugarProceso.getCell(5);
			descripcion = rowLugarProceso.getCell(7);

			if (!descripcion.getStringCellValue().isEmpty()
					&& descripcion != null)
				casoProceso.setDireccionProceso(descripcion
						.getStringCellValue());
			if (!ciudad.getStringCellValue().isEmpty() && ciudad != null) {
				ciudadObjeto.setNombre(ciudad.getStringCellValue());
				try {
					listaciudad = ciudadService
							.consultarCodigoCiudad(ciudadObjeto);
					for (Ciudad obj : listaciudad) {
						casoProceso.setCiudadProceso(obj);
					}
				} catch (DAOException e) {
					LOG.error("DAOException: Ocurrio un error consultando codigos de la ciudad. El error es: "
							+ e.getMessage());
					infoError = " La ciudad seleccionada no existe";
				} catch (BusinessException e) {
					LOG.error("BusinessException: Ocurrio un error consultando los codigos de la ciudad . El error es: "
							+ e.getMessage());
				}

			} else {
				if (!departemento.getStringCellValue().isEmpty()
						&& departemento != null) {
					departamentoObjeto.setNombre(departemento
							.getStringCellValue());
					try {
						objDepartamento = DepartamentoService
								.consultarCodigoDepartamento(departamentoObjeto);
						ciudadObjeto.setDepartamento(objDepartamento);
						casoProceso.setCiudadHechos(ciudadObjeto);
					} catch (DAOException e) {
						LOG.error("DAOException: Ocurrio un error consultando codigos del departamento. El error es: "
								+ e.getMessage());
						infoError = " El Departemento seleccionado no existe";
					} catch (BusinessException e) {
						LOG.error("BusinessException: Ocurrio un error consultando los codigos del departamento . El error es: "
								+ e.getMessage());
					}
				} else {
					if (!pais.getStringCellValue().isEmpty() && pais != null) {
						paisObjeto.setNombre(pais.getStringCellValue());
						try {
							objPais = paisService
									.consultarCodigoPais(paisObjeto);
							departamentoObjeto.setPais(objPais);
							ciudadObjeto.setDepartamento(departamentoObjeto);
							casoProceso.setCiudadHechos(ciudadObjeto);
						} catch (DAOException e) {
							LOG.error("DAOException: Ocurrio un error consultando codigos del pais. El error es: "
									+ e.getMessage());
							infoError = " El Pais seleccionado no existe";
						} catch (BusinessException e) {
							LOG.error("BusinessException: Ocurrio un error consultando los codigos del pais. El error es: "
									+ e.getMessage());
						}
					} else {
						infoError = "Los campos marcados con (*) son requeridos.";
					}
				}
			}

		} else {
			infoError = "Los campos marcados con (*) son requeridos.";
		}
		return casoProceso;
	}

	/** METODO PARA CARGAR LA INFORMACION DE LOS ABOGADOS DEL CASO */
	private List<CasoEquipoCaso> LlenarAbogados(XSSFRow rowAbogado,
			String cellAbogados) throws DAOException, BusinessException {
		EquipoCaso equipoCaso = new EquipoCaso();
		CasoEquipoCaso casoEquipoCaso = new CasoEquipoCaso();
		List<CasoEquipoCaso> listaCasoEquipo = new ArrayList<CasoEquipoCaso>();
		Caso casoAbogado = new Caso();
		TipoMiembro tipoMiembro = new TipoMiembro();
		XSSFCell abogado = null;
		if (rowAbogado != null)
			abogado = rowAbogado.getCell(0);
		List<User> listaUser = null;
		if (rowAbogado != null) {
			XSSFCell fechaActividad = rowAbogado.getCell(40);
			if (rowAbogado != null && fechaActividad != null) {
				fechaVencimientoActividadString = fechaActividad.getStringCellValue();
			}
			if (!abogado.getStringCellValue().isEmpty() && abogado != null) {
				abogadosString = abogado.getStringCellValue();
				if (abogado.getStringCellValue().contains(";")) {
					String abogados[] = abogado.getStringCellValue().split(";");
					for (int i = 0; i < abogados.length; i++) {
						casoEquipoCaso = new CasoEquipoCaso();
						equipoCaso = new EquipoCaso();
						try {
							listaUser = equipoCasoService.obtenerAbogadosIniciales(abogados[i]);

							for (User objUser : listaUser) {
								equipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(objUser.getId());
								if (equipoCaso == null) {
									equipoCaso = new EquipoCaso();
									equipoCaso.setApellido(objUser.getApellido());
									equipoCaso.setNombre(objUser.getNombre());
									equipoCaso.setIdentificacion(objUser.getId());
								}
								tipoMiembro.setCodigo(Integer.parseInt(Parametros.getAbogado()));
								casoEquipoCaso.setTipoMiembro(tipoMiembro);
								casoEquipoCaso.setDireccion(objUser.getDireccion());
								casoEquipoCaso.setActivo(objUser.getActivo());
							}

							CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
							casoPk.setMiembro(Integer.parseInt(Parametros.getAbogado()));
							if (equipoCaso.getCodigoEquipoCaso() != null) {
								casoPk.setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
							}
							casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
							casoEquipoCaso.setEquipoCaso(equipoCaso);
							casoEquipoCaso.setCaso(casoAbogado);
							listaCasoEquipo.add(casoEquipoCaso);
						} catch (DAOException e) {
							LOG.error("DAOException: Ocurrio un error consultando los abogado. El error es: "
									+ e.getMessage());
							infoError = " Los abogados no coinciden";
							throw e;
							
						} catch (BusinessException e) {
							LOG.error("BusinessException: Ocurrio un error consultando los abogado. El error es: "
									+ e.getMessage());
							throw e;
						}
					}
				} else {
					try {
						listaUser = equipoCasoService.obtenerAbogadosIniciales(abogado.getStringCellValue());
						for (User objUser : listaUser) {
							equipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(objUser.getId());
							if (equipoCaso == null) {
								equipoCaso = new EquipoCaso();
								equipoCaso.setApellido(objUser.getApellido());
								equipoCaso.setNombre(objUser.getNombre());
								equipoCaso.setIdentificacion(objUser.getId());
							}
							tipoMiembro.setCodigo(Integer.parseInt(Parametros.getAbogado()));
							casoEquipoCaso.setTipoMiembro(tipoMiembro);
							casoEquipoCaso.setDireccion(objUser.getDireccion());
							casoEquipoCaso.setActivo(objUser.getActivo());
						}
						CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
						casoPk.setMiembro(Integer.parseInt(Parametros.getAbogado()));
						casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
						casoEquipoCaso.setEquipoCaso(equipoCaso);
						casoEquipoCaso.setCaso(casoAbogado);
						listaCasoEquipo.add(casoEquipoCaso);
					} catch (DAOException e) {
						LOG.error("DAOException: Ocurrio un error consultando los abogado. El error es: "
								+ e.getMessage());
						infoError = " Los abogados no coinciden";
						throw e;
					} catch (BusinessException e) {
						LOG.error("BusinessException: Ocurrio un error consultando los abogado. El error es: "
								+ e.getMessage());
						throw e;
					}

				}
			}
		} else if (cellAbogados != null) {
			if (!cellAbogados.equals("")) {
				if (cellAbogados.contains(";")) {
					String abogados[] = cellAbogados.split(";");
					for (int i = 0; i < abogados.length; i++) {
						casoEquipoCaso = new CasoEquipoCaso();
						equipoCaso = new EquipoCaso();
						try {
							listaUser = equipoCasoService.obtenerAbogadosIniciales(abogados[i]);
							for (User objUser : listaUser) {
								equipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(objUser.getId());
								if (equipoCaso == null) {
									equipoCaso = new EquipoCaso();
									equipoCaso.setApellido(objUser.getApellido());
									equipoCaso.setNombre(objUser.getNombre());
									equipoCaso.setIdentificacion(objUser.getId());
								}
								tipoMiembro.setCodigo(Integer.parseInt(Parametros.getAbogado()));
								casoEquipoCaso.setTipoMiembro(tipoMiembro);
								casoEquipoCaso.setDireccion(objUser.getDireccion());
								casoEquipoCaso.setActivo(objUser.getActivo());
							}
							CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
							casoPk.setMiembro(Integer.parseInt(Parametros.getAbogado()));
							if (equipoCaso.getCodigoEquipoCaso() != null) {
								casoPk.setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
							}
							casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
							casoEquipoCaso.setEquipoCaso(equipoCaso);
							casoEquipoCaso.setCaso(casoAbogado);
							listaCasoEquipo.add(casoEquipoCaso);
						} catch (DAOException e) {
							LOG.error("DAOException: Ocurrio un error consultando los abogado. El error es: "
									+ e.getMessage());
							infoError = " Los abogados no coinciden";
						} catch (BusinessException e) {
							LOG.error("BusinessException: Ocurrio un error consultando los abogado. El error es: "
									+ e.getMessage());
						}
					}
				} else {
					casoEquipoCaso = new CasoEquipoCaso();
					equipoCaso = new EquipoCaso();
					try {
						listaUser = equipoCasoService.obtenerAbogadosIniciales(cellAbogados);
						for (User objUser : listaUser) {
							equipoCaso = equipoCasoService.findEquipoCasoByIdentificacion(objUser.getId());
							if (equipoCaso == null) {
								equipoCaso = new EquipoCaso();
								equipoCaso.setApellido(objUser.getApellido());
								equipoCaso.setNombre(objUser.getNombre());
								equipoCaso.setIdentificacion(objUser.getId());
							}
							tipoMiembro.setCodigo(Integer.parseInt(Parametros.getAbogado()));
							casoEquipoCaso.setTipoMiembro(tipoMiembro);
							casoEquipoCaso.setDireccion(objUser.getDireccion());
							casoEquipoCaso.setActivo(objUser.getActivo());
						}
						CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
						casoPk.setMiembro(Integer.parseInt(Parametros.getAbogado()));
						if (equipoCaso.getCodigoEquipoCaso() != null) {
							casoPk.setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
						}
						casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
						casoEquipoCaso.setEquipoCaso(equipoCaso);
						casoEquipoCaso.setCaso(casoAbogado);
						listaCasoEquipo.add(casoEquipoCaso);
					} catch (DAOException e) {
						LOG.error("DAOException: Ocurrio un error consultando los abogado. El error es: "
								+ e.getMessage());
						infoError = "Los abogados no coinciden";
					} catch (BusinessException e) {
						LOG.error("BusinessException: Ocurrio un error consultando los abogado. El error es: "
								+ e.getMessage());
					}
				}
			}
		} else {
			infoError = "Los campos marcados con (*) son requeridos.";
		}
		return listaCasoEquipo;
	}

	/**
	 * METODO PARA CARGAR LA INFORMACION DE LOS FAMILIARES DE LA VICTIMA DEL
	 * CASO
	 */
	public List<Object> LlenarFamiliares(XSSFRow rowDemandados, String cdusuarioLogeado,
			List<CasoEquipoCaso> abogados) throws DAOException, BusinessException {
		XSSFCell etiquetaTestigos = null;
		String etiquetaInfoTestigo = "";
		List<CasoEquipoCaso> abogadosResponsables = new ArrayList<CasoEquipoCaso>();
		XSSFCell etiquetaSw = rowDemandados.getCell(10);
		List<Object> objetoReturn = new ArrayList<Object>();
		CasoEquipoCaso casoEquipoCaso = new CasoEquipoCaso();
		if (rowDemandados != null)
			etiquetaTestigos = rowDemandados.getCell(0);
		if (rowDemandados != null)
			etiquetaInfoTestigo = etiquetaTestigos.getStringCellValue();
		
 		if (etiquetaInfoTestigo.equalsIgnoreCase("NOMBRES COMPLETOS *")) {
			celdasVencimientos.put("fechaVencimientoPoder", rowDemandados.getCell(24));
			celdasVencimientos.put("fechaVencimientoPoderProcuraduria", rowDemandados.getCell(26));
			celdasVencimientos.put("fechaVencimientoFotoCopia", rowDemandados.getCell(28));
			celdasVencimientos.put("fechaVencimientoCMandato", rowDemandados.getCell(30));
			celdasVencimientos.put("fechaVencimientoJuntaM", rowDemandados.getCell(32));
			celdasVencimientos.put("fechaVencimientoCPTransito", rowDemandados.getCell(34));
			celdasVencimientos.put("fechaVencimientoPreclusion", rowDemandados.getCell(36));
			celdasVencimientos.put("fechaVencimientoBLibertad", rowDemandados.getCell(38));
			celdasVencimientos.put("fechaVencimientoHClinica", rowDemandados.getCell(40));
			celdasVencimientos.put("fechaVencimientoRHecho", rowDemandados.getCell(42));
			celdasVencimientos.put("fechaVencimientoPBautismo", rowDemandados.getCell(44));
			celdasVencimientos.put("fechaVencimientoPMatrimonio", rowDemandados.getCell(46));
			celdasVencimientos.put("fechaVencimientoRMatrimonio", rowDemandados.getCell(48));
			celdasVencimientos.put("fechaVencimientoRNacimiento", rowDemandados.getCell(50));
			celdasVencimientos.put("fechaVencimientoRDefuncion", rowDemandados.getCell(52));
			celdasVencimientos.put("fechaVencimientoOtro", rowDemandados.getCell(54));
			// TO DO se debe validar si las fechas de vencimiento son obligatorias 
			//validarVencimientosTareas(celdasVencimientos);
			
		}
		/**
		 * Bloque que asigna caso equipo caso
		 * */
		casoEquipoCaso = asignarCasoEquipoCaso(rowDemandados);
		/**
		 * Fn Bloque que asigna caso equipo caso
		 * */
		/** Documentos Requeridos */
		celdasTareas.put("poder", rowDemandados.getCell(23));//16
		celdasTareas.put("poderProcuraduria", rowDemandados.getCell(25));
		celdasTareas.put("fotocopiaCC", rowDemandados.getCell(27));
		celdasTareas.put("contratoMandato", rowDemandados.getCell(29));
		celdasTareas.put("informativo_juntaMed", rowDemandados.getCell(31));
		celdasTareas.put("copiaProcesoTránsito", rowDemandados.getCell(33));
		celdasTareas.put("preclusionSentencia", rowDemandados.getCell(35));
		celdasTareas.put("boletaLibertad", rowDemandados.getCell(37));
		celdasTareas.put("historiaClinica", rowDemandados.getCell(39));
		celdasTareas.put("relatoHechos", rowDemandados.getCell(41));
		celdasTareas.put("partidaBautismo", rowDemandados.getCell(43));
		celdasTareas.put("partidaMatrimonio", rowDemandados.getCell(45));
		celdasTareas.put("registroMatrimonio", rowDemandados.getCell(47));
		celdasTareas.put("registroCivilNacimiento", rowDemandados.getCell(49));
		celdasTareas.put("registroCivilDefuncion", rowDemandados.getCell(51));
		celdasTareas.put("otros", rowDemandados.getCell(53));
		if (rowDemandados != null) {
			
			if (etiquetaSw.getStringCellValue().equalsIgnoreCase("Esposo(a) Compañero(a)")) {
				celdasResponsables.put("responsablePoder", rowDemandados.getCell(24));
				celdasResponsables.put("responsablePoderProcuraduria", rowDemandados.getCell(26));
				celdasResponsables.put("responsableFotoCopia", rowDemandados.getCell(28));
				celdasResponsables.put("responsableCMandato", rowDemandados.getCell(30));
				celdasResponsables.put("responsableJuntaM", rowDemandados.getCell(32));
				celdasResponsables.put("responsableCPTransito", rowDemandados.getCell(34));
				celdasResponsables.put("responsablePreclusion", rowDemandados.getCell(36));
				celdasResponsables.put("responsableBLibertad", rowDemandados.getCell(38));
				celdasResponsables.put("responsableHClinica", rowDemandados.getCell(40));
				celdasResponsables.put("responsableRHecho", rowDemandados.getCell(42));
				celdasResponsables.put("responsablePBautismo", rowDemandados.getCell(44));
				celdasResponsables.put("responsablePMatrimonio", rowDemandados.getCell(46));
				celdasResponsables.put("responsableRMatrimonio", rowDemandados.getCell(48));
				celdasResponsables.put("responsableRNacimiento", rowDemandados.getCell(50));
				celdasResponsables.put("responsableRDefuncion", rowDemandados.getCell(52));
				celdasResponsables.put("responsableOtro", rowDemandados.getCell(54));
				// TO DO se debe validar si los responsables son obligatorios 
				//validarResponsablesTareas(celdasResponsables);
			}
		}
		if (!etiquetaSw.getStringCellValue().equalsIgnoreCase("Esposo(a) Compañero(a)")
				&& !etiquetaInfoTestigo.equalsIgnoreCase("DEMANDANTES/GRUPO FAMILIAR")
				&& !etiquetaInfoTestigo.equalsIgnoreCase("")
				&& !etiquetaInfoTestigo.equalsIgnoreCase("NOMBRES COMPLETOS *")) {
			TipoMiembro tipoMiembro = new TipoMiembro();
			EquipoCaso equipoCaso = new EquipoCaso();
			XSSFCell nombre = rowDemandados.getCell(0);
			XSSFCell apellido = rowDemandados.getCell(1);
			XSSFCell direccion = rowDemandados.getCell(9);
			
			
			ActividadCaso actividadParticular = new ActividadCaso();
			List<Actividad> listaActividad = new ArrayList<Actividad>();
			Actividad actividadObjeto = new Actividad();
			Date fechaActual = new Date();
			
			actividadObjeto.setDsactividad(Parametros.getActividadDocumentosRequeridos());
			try {
				listaActividad = tareaParticularCasoService.obtenerCodigoActividad(actividadObjeto);
				for (Actividad objActividad : listaActividad) {
					codigoActividadGlobal = objActividad.getCdactividad();
					actividadParticular.setDetalle(objActividad.getDsdetalle());
					actividadParticular.setUsuarioCreacion(cdusuarioLogeado);
					actividadParticular.setUsuarioUltimaModificacion(cdusuarioLogeado);
					actividadParticular.setFinalizada(Parametros.getActividadPendiente());
					actividadParticular.setFechaCreacion(fechaActual);
					actividadParticular.setFechaUltimaModificacion(fechaActual);
					actividadParticular.setNombreActividad(objActividad.getDsactividad());
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = " Verifique la actividad y las tareas correspondientes al formato Original";
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
			
			/** Bloque donde se asigna el tipo del caso en el detalle del caso */

			TipoCaso objTipoCaso = new TipoCaso();
			List<ActividadTipoCaso> listActividad = new ArrayList<ActividadTipoCaso>();
			ActividadTipoCaso objActividadTipoCaso = new ActividadTipoCaso();
			Actividad actividadObjetoAxu = new Actividad();
			objTipoCaso.setCodigo(codigoTipoCasoGlobal);
			actividadObjetoAxu.setCdactividad(codigoActividadGlobal);
			objActividadTipoCaso.setCdactividad(actividadObjetoAxu);
			listActividad.add(objActividadTipoCaso);
			objTipoCaso.setActividadTipoCasoList(listActividad);
			objTipoCaso = tipoCasoService.obtenerActividades(objTipoCaso);

			
			/**
			 * CAMBIO EN METODOS QUE ASIGNAN TAREAS RESPONSABLES A SOLO METODO
			 * Bloque en el que asigno las tareas pasado a asignarTareasResponsables();
			 * */
			List<XSSFCell> tareas = new ArrayList<XSSFCell>();
			List<String> responsablesTareas = new ArrayList<String>();
			List<String> fechasVencimiento = new ArrayList<String>();
			nombresTareas = new ArrayList<String>();
			
			
			Map<String, XSSFCell> documentosRequeridos = getTareasPorParentesco(casoEquipoCaso.getParentesco().getCodigo());
			
			getTareasMiembro(documentosRequeridos, tareas, responsablesTareas, fechasVencimiento);
			
			HashMap<String, Object> bundle = new HashMap<String, Object>();
			
			bundle = asignarTareasResponsables(abogados, abogadosResponsables, abogadosString, actividadParticular, 
					cdusuarioLogeado, fechaActual, fechasVencimiento, nombre, apellido, nombresTareas, responsablesTareas, tareas);
			actividadParticular = (ActividadCaso) bundle.get("actividadParticular");
			bundle.clear();
			/**
			 * Fn Bloque en el que asigno las tareas pasado a asignarTareasResponsablesBloque();
			 * */

			/**
			 * Bloque que asigna equipo caso
			 * */
			equipoCaso = asignarCaso(rowDemandados);
			/**
			 * Fn Bloque que asigna equipo caso
			 * */
						
			CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
			casoPk.setMiembro(Integer.parseInt(Parametros.getDemandante()));
			casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
			if (direccion != null)
				casoEquipoCaso.setDireccion(direccion.getStringCellValue());
			
			casoEquipoCaso.setEquipoCaso(equipoCaso);
			casoEquipoCaso.setActivo(Parametros.getContactoActivo());
			tipoMiembro.setCodigo(Integer.parseInt(Parametros.getDemandante()));
			casoEquipoCaso.setTipoMiembro(tipoMiembro);
			objetoReturn.add(casoEquipoCaso);
			objetoReturn.add(actividadParticular);
		}
		
		return objetoReturn;
	}
	
	
	private void validarResponsablesTareas(Map<String, XSSFCell> celdasResponsables) throws BusinessException {
		
		if (celdasResponsables.get("responsablePoder").getStringCellValue() == null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsablePoderProcuraduria").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableFotoCopia").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableCMandato").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableJuntaM").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableCPTransito").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsablePreclusion").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableBLibertad").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableHClinica").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableRHecho").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsablePBautismo").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsablePMatrimonio").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableRMatrimonio").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableRNacimiento").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableRDefuncion").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		if (celdasResponsables.get("responsableOtro").getStringCellValue() != null)
			new BusinessException(ERROR_RESPONSABLE_TAREA);
		
	}
	
	private void validarVencimientosTareas(Map<String, XSSFCell> celdasVencimiento) throws BusinessException {
		if (celdasVencimiento.get("fechaVencimientoPoder").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoPoderProcuraduria").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoFotoCopia").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoCMandato").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoJuntaM").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoCPTransito").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoPreclusion").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoBLibertad").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoHClinica").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoRHecho").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoPBautismo").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoPMatrimonio").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoRMatrimonio").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoRNacimiento").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoRDefuncion").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		if (celdasVencimiento.get("fechaVencimientoOtro").getStringCellValue() != null)
			new BusinessException(ERROR_FECHA_VENCIMIENTO);
		
	}
	
	/**
	 * 
	 * 
	 * @param documentosRequeridos
	 */
	
	private void getTareasMiembro(Map<String, XSSFCell> documentosRequeridos, List<XSSFCell> tareas, List<String> responsables, List<String> vencimientosTareas) {
		if (documentosRequeridos.get("poder") != null){			
			tareas.add(documentosRequeridos.get("poder"));
			responsables.add(documentosRequeridos.get("responsablePoder").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoPoder").getStringCellValue());
		}
		
		if (documentosRequeridos.get("poderProcuraduria") != null) {			
			tareas.add(documentosRequeridos.get("poderProcuraduria"));
			responsables.add(documentosRequeridos.get("responsablePoderProcuraduria").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoPoderProcuraduria").getStringCellValue());
		}
		
		if (documentosRequeridos.get("fotocopiaCC") != null) {
			tareas.add(documentosRequeridos.get("fotocopiaCC"));
			responsables.add(documentosRequeridos.get("responsableFotoCopia").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoFotoCopia").getStringCellValue());
		}
		
		if (documentosRequeridos.get("contratoMandato") != null) {			
			tareas.add(documentosRequeridos.get("contratoMandato"));
			responsables.add(documentosRequeridos.get("responsableCMandato").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoCMandato").getStringCellValue());
		}
		
		if (documentosRequeridos.get("documentoJuntaMed") != null) {			
			tareas.add(documentosRequeridos.get("documentoJuntaMed"));
			responsables.add(documentosRequeridos.get("responsableJuntaM").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoJuntaM").getStringCellValue());
		}
		
		if (documentosRequeridos.get("procesoTransito") != null) {			
			tareas.add(documentosRequeridos.get("procesoTransito"));
			responsables.add(documentosRequeridos.get("responsableCPTransito").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoCPTransito").getStringCellValue());
		}
		
		if (documentosRequeridos.get("sentencia") != null) {
			tareas.add(documentosRequeridos.get("sentencia"));
			responsables.add(documentosRequeridos.get("responsablePreclusion").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoPreclusion").getStringCellValue());
		}
		
		if (documentosRequeridos.get("boletaLibertad") != null) {
			tareas.add(documentosRequeridos.get("boletaLibertad"));
			responsables.add(documentosRequeridos.get("responsableBLibertad").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoBLibertad").getStringCellValue());
		}
		
		if (documentosRequeridos.get("historiaClinica") != null) {
			tareas.add(documentosRequeridos.get("historiaClinica"));
			responsables.add(documentosRequeridos.get("responsableHClinica").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoHClinica").getStringCellValue());
		}
		
		if (documentosRequeridos.get("relatoHechos") != null) {
			tareas.add(documentosRequeridos.get("relatoHechos"));
			responsables.add(documentosRequeridos.get("responsableRHecho").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoRHecho").getStringCellValue());
		}
		
		if (documentosRequeridos.get("partidaBautismo") != null) {
			tareas.add(documentosRequeridos.get("partidaBautismo"));
			responsables.add(documentosRequeridos.get("responsablePBautismo").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoPBautismo").getStringCellValue());
		}
		
		if (documentosRequeridos.get("partidaMatrimonio") != null) {
			tareas.add(documentosRequeridos.get("partidaMatrimonio"));
			responsables.add(documentosRequeridos.get("responsablePMatrimonio").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoPMatrimonio").getStringCellValue());
		}
		
		if (documentosRequeridos.get("registroMatrimonio") != null) {
			tareas.add(documentosRequeridos.get("registroMatrimonio"));
			responsables.add(documentosRequeridos.get("responsableRMatrimonio").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoRMatrimonio").getStringCellValue());
		}
		
		if (documentosRequeridos.get("registroCivil") != null) {
			tareas.add(documentosRequeridos.get("registroCivil"));
			responsables.add(documentosRequeridos.get("responsableRNacimiento").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoRNacimiento").getStringCellValue());
		}
		
		if (documentosRequeridos.get("registroDefuncion") != null) {
			tareas.add(documentosRequeridos.get("registroDefuncion"));
			responsables.add(documentosRequeridos.get("responsableRDefuncion").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoRDefuncion").getStringCellValue());
		}
		
		if (documentosRequeridos.get("otros") != null) {
			tareas.add(documentosRequeridos.get("otros"));
			responsables.add(documentosRequeridos.get("responsableOtro").getStringCellValue());
			vencimientosTareas.add(documentosRequeridos.get("fechaVencimientoOtro").getStringCellValue());
		}
	}
	
	/**
	 *   @author Diego Blandon
	 *   @param documentosRequeridos String[] con los documentos parametrizados para el tipo de parentesco especifico
	 *   @return map con los documentos que requiere el parentesco y seran evaluados en el documento excel
	 */
	private Map<String, XSSFCell> getMapTareasDocumentosRequeridos(String[] documentosRequeridos) {
		Map<String, XSSFCell> mapDocumentos = new HashMap<String, XSSFCell>();

		
		if (documentosRequeridos.length > 0) {
			for (String documento:documentosRequeridos) {
				if (parametrosDocumentos.getDocumentoPoder().equals(documento)) {
					mapDocumentos.put("poder", celdasTareas.get("poder"));
					mapDocumentos.put("responsablePoder", celdasResponsables.get("responsablePoder"));
					mapDocumentos.put("fechaVencimientoPoder", celdasVencimientos.get("fechaVencimientoPoder"));
					nombresTareas.add(Parametros.getActividadPoder());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoPoderProcuraduria().equals(documento)) {
					mapDocumentos.put("poderProcuraduria", celdasTareas.get("poderProcuraduria"));
					mapDocumentos.put("responsablePoderProcuraduria", celdasResponsables.get("responsablePoderProcuraduria"));
					mapDocumentos.put("fechaVencimientoPoderProcuraduria", celdasVencimientos.get("fechaVencimientoPoderProcuraduria"));
					nombresTareas.add(Parametros.getActividadPoderProcuraduria());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoFotocopiaCC().equals(documento)) {
					mapDocumentos.put("fotocopiaCC", celdasTareas.get("fotocopiaCC"));
					mapDocumentos.put("responsableFotoCopia", celdasResponsables.get("responsableFotoCopia"));
					mapDocumentos.put("fechaVencimientoFotoCopia", celdasVencimientos.get("fechaVencimientoFotoCopia"));
					nombresTareas.add(Parametros.getActividadFotocopiaCC());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoContratoMandato().equals(documento)) {
					mapDocumentos.put("contratoMandato", celdasTareas.get("contratoMandato"));
					mapDocumentos.put("responsableCMandato", celdasResponsables.get("responsableCMandato"));
					mapDocumentos.put("fechaVencimientoCMandato", celdasVencimientos.get("fechaVencimientoCMandato"));
					nombresTareas.add(Parametros.getActividadContratoMandato());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoJuntaMed().equals(documento)) {
					mapDocumentos.put("documentoJuntaMed", celdasTareas.get("informativo_juntaMed"));
					mapDocumentos.put("responsableJuntaM", celdasResponsables.get("responsableJuntaM"));
					mapDocumentos.put("fechaVencimientoJuntaM", celdasVencimientos.get("fechaVencimientoJuntaM"));
					nombresTareas.add(Parametros.getActividadJuntaMedica());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoProcesoTransito().equals(documento)) {
					mapDocumentos.put("procesoTransito", celdasTareas.get("copiaProcesoTránsito"));
					mapDocumentos.put("responsableCPTransito", celdasResponsables.get("responsableCPTransito"));
					mapDocumentos.put("fechaVencimientoCPTransito", celdasVencimientos.get("fechaVencimientoCPTransito"));
					nombresTareas.add(Parametros.getActividadTransito());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoSentencia().equals(documento)) {
					mapDocumentos.put("sentencia", celdasTareas.get("preclusionSentencia"));
					mapDocumentos.put("responsablePreclusion", celdasResponsables.get("responsablePreclusion"));
					mapDocumentos.put("fechaVencimientoPreclusion", celdasVencimientos.get("fechaVencimientoPreclusion"));
					nombresTareas.add(Parametros.getActividadPreclusion());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoBoletaLibertad().equals(documento)) {
					mapDocumentos.put("boletaLibertad", celdasTareas.get("boletaLibertad"));
					mapDocumentos.put("responsableBLibertad", celdasResponsables.get("responsableBLibertad"));
					mapDocumentos.put("fechaVencimientoBLibertad", celdasVencimientos.get("fechaVencimientoBLibertad"));
					nombresTareas.add(Parametros.getActividadBoletaLibertad());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoHistoriaClinica().equals(documento)) {
					mapDocumentos.put("historiaClinica", celdasTareas.get("historiaClinica"));
					mapDocumentos.put("responsableHClinica", celdasResponsables.get("responsableHClinica"));
					mapDocumentos.put("fechaVencimientoHClinica", celdasVencimientos.get("fechaVencimientoHClinica"));
					nombresTareas.add(Parametros.getActividadHistoria());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoRelatoHechos().equals(documento)) {
					mapDocumentos.put("relatoHechos", celdasTareas.get("relatoHechos"));
					mapDocumentos.put("responsableRHecho", celdasResponsables.get("responsableRHecho"));
					mapDocumentos.put("fechaVencimientoRHecho", celdasVencimientos.get("fechaVencimientoRHecho"));
					nombresTareas.add(Parametros.getActividadRelatoHechos());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoPartidaBautismo().equals(documento)) {
					mapDocumentos.put("partidaBautismo", celdasTareas.get("partidaBautismo"));
					mapDocumentos.put("responsablePBautismo", celdasResponsables.get("responsablePBautismo"));
					mapDocumentos.put("fechaVencimientoPBautismo", celdasVencimientos.get("fechaVencimientoPBautismo"));
					nombresTareas.add(Parametros.getActividadPartidaBautismo());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoPartidaMatrimonio().equals(documento)) {
					mapDocumentos.put("partidaMatrimonio", celdasTareas.get("partidaMatrimonio"));
					mapDocumentos.put("responsablePMatrimonio", celdasResponsables.get("responsablePMatrimonio"));
					mapDocumentos.put("fechaVencimientoPMatrimonio", celdasVencimientos.get("fechaVencimientoPMatrimonio"));
					nombresTareas.add(Parametros.getActividadPartidaMatrimonio());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoRegistroMatrimonio().equals(documento)) {
					mapDocumentos.put("registroMatrimonio", celdasTareas.get("registroMatrimonio"));
					mapDocumentos.put("responsableRMatrimonio", celdasResponsables.get("responsableRMatrimonio"));
					mapDocumentos.put("fechaVencimientoRMatrimonio", celdasVencimientos.get("fechaVencimientoRMatrimonio"));
					nombresTareas.add(Parametros.getActividadMatrimonio());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoRegistroCivil().equals(documento)) {
					mapDocumentos.put("registroCivil", celdasTareas.get("registroCivilNacimiento"));
					mapDocumentos.put("responsableRNacimiento", celdasResponsables.get("responsableRNacimiento"));
					mapDocumentos.put("fechaVencimientoRNacimiento", celdasVencimientos.get("fechaVencimientoRNacimiento"));
					nombresTareas.add(Parametros.getActividadRegistroNaciento());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoRegistroDefuncion().equals(documento)) {
					mapDocumentos.put("registroDefuncion", celdasTareas.get("registroCivilDefuncion"));
					mapDocumentos.put("responsableRDefuncion", celdasResponsables.get("responsableRDefuncion"));
					mapDocumentos.put("fechaVencimientoRDefuncion", celdasVencimientos.get("fechaVencimientoRDefuncion"));
					nombresTareas.add(Parametros.getActividadRegistroDefuncion());
					continue;
				}
				
				if (parametrosDocumentos.getDocumentoOtros().equals(documento)) {
					mapDocumentos.put("otros", celdasTareas.get("otros"));
					mapDocumentos.put("responsableOtro", celdasResponsables.get("responsableOtro"));
					mapDocumentos.put("fechaVencimientoOtro", celdasVencimientos.get("fechaVencimientoOtro"));
					nombresTareas.add(Parametros.getActividadOtros());
					continue;
				}
			}
		}
		return mapDocumentos;
	}
	
	private Map<String, XSSFCell> getTareasPorParentesco(Integer codigoParentesco) throws BusinessException {
		
		Map mapDocumentos = null;
		if (parametrosDocumentos.getParentescoEsposo().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoEsposo().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		
		if (parametrosDocumentos.getParentescoHijo().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoHijo().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		
		if (parametrosDocumentos.getParentescoPadre().equals(codigoParentesco.toString()) ||
				parametrosDocumentos.getParentescoMadre().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoMadrePadre().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}

		if (parametrosDocumentos.getParentescoHermano().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoHermano().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		
		if (parametrosDocumentos.getParentescoAbuelo().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoAbuelo().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		
		if (parametrosDocumentos.getParentescoNietos().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoNietos().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		
		if (parametrosDocumentos.getParentescoTios().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoTio().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		
		if (parametrosDocumentos.getParentescoSobrinos().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoSobrinos().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		
		if (parametrosDocumentos.getParentescoBisnietos().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoBisnietos().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		
		if (parametrosDocumentos.getParentescoPrimos().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoPrimos().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}		
		
		if (parametrosDocumentos.getParentescoDanmificados().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentesco3danmificados().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}		
		
		if (parametrosDocumentos.getParentescoBisabuelos().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoBisabuelos().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}		
		
		if (parametrosDocumentos.getParentescoOtro().equals(codigoParentesco.toString())) {
			String documentosRequeridos[] = parametrosDocumentos.getDocumentosParentescoOtro().split(",");
			mapDocumentos = getMapTareasDocumentosRequeridos(documentosRequeridos);
			return mapDocumentos;
		}
		return mapDocumentos;
	}
	
	
	

	/**
	 * */
	private HashMap<String, Object> asignarTareasResponsables(List<CasoEquipoCaso> abogados, 
			List<CasoEquipoCaso> abogadosResponsables,
			String abogadosString, 
			ActividadCaso actividadParticular,
			String cdusuarioLogeado, 
			Date fechaActual, 
			List<String> fechasVencimiento,
			XSSFCell nombre,
			XSSFCell apellido,
			List<String> parametros,
			List<String> responsablesTareas, 
			List<XSSFCell> tareas
			) throws DAOException, BusinessException {
		HashMap<String, Object> bundle = new  HashMap<String, Object>();
		List<TareaParticularCaso> listaSetTarea = new ArrayList<TareaParticularCaso>();
		
		for (int i = 0; i < tareas.size(); i++) {
			List<TareaActividad> listaTareaActividad = new ArrayList<TareaActividad>();
			TareaActividad tareaActividad = new TareaActividad();
			
			if (!tareas.get(i).getStringCellValue().equalsIgnoreCase(caracterMarca)) {
				abogadosResponsables = abogadosResponsablesTareas(abogadosString, responsablesTareas.get(i));				
				asignarTareasResponsables(parametros.get(i), actividadParticular, listaSetTarea,
										abogadosResponsables, cdusuarioLogeado, fechaActual,
										nombre, apellido, fechasVencimiento.get(i), true);
			} else {
				abogadosResponsables = abogadosResponsablesTareas(abogadosString, responsablesTareas.get(i));				
				asignarTareasResponsables(parametros.get(i), actividadParticular, listaSetTarea,
										abogadosResponsables, cdusuarioLogeado, fechaActual,
										nombre, apellido, fechasVencimiento.get(i), false);
			}
		}
		
		bundle.put("actividadParticular", actividadParticular);
		
		return bundle;
	}
	
	/**
	 * METODO PARA ASIGNAR A EQUIPO CASO
	 * */
	private EquipoCaso asignarCaso(XSSFRow rowDemandados) throws BusinessException{
		EquipoCaso equipoCaso = new EquipoCaso();
		Telefono telefonoObjeto = new Telefono();
		Celular celularObjeto = new Celular();
		Correo correoObjeto = new Correo();
		List<Correo> listaCorreo = new ArrayList<Correo>();
		List<Telefono> listaTelefonos = new ArrayList<Telefono>();
		List<Celular> listaCelular = new ArrayList<Celular>();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date fecha = null;
		XSSFCell nombre = rowDemandados.getCell(0);
		XSSFCell apellido = rowDemandados.getCell(1);
		XSSFCell cedula = rowDemandados.getCell(3);
		XSSFCell tipoDocumentoDemandado = rowDemandados.getCell(4);
		XSSFCell fechaNacimiento = rowDemandados.getCell(5);
		XSSFCell telefono = rowDemandados.getCell(6);
		XSSFCell celular = rowDemandados.getCell(7);
		XSSFCell correo = rowDemandados.getCell(8);
		XSSFCell direccion = rowDemandados.getCell(9);
		
		if (nombre != null) {
			if (nombre.getStringCellValue() != null) {
				equipoCaso.setNombre(nombre.getStringCellValue());
			} else {
				infoError = "Los campos marcados con (*) son requeridos.";
			}
		}
		if (cedula != null) {
			try {
				if (cedula.getNumericCellValue() > 0) {
					int datos = (int) cedula.getNumericCellValue();
					equipoCaso.setIdentificacion(Integer.toString(datos));
				}
			} catch (Exception e) {
				if (cedula.getStringCellValue() != null) {
					equipoCaso.setIdentificacion(cedula.getStringCellValue());
				}
			}
		}
		if (apellido != null) {
			if (apellido.getStringCellValue() != null)
				equipoCaso.setApellido(apellido.getStringCellValue());
		}
		
//		if (direccion.getStringCellValue() != null)
//			equipoCaso.setDireccion(direccion.getStringCellValue());
		
		if (tipoDocumentoDemandado.getStringCellValue() != null) {
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setDocumento(tipoDocumentoDemandado.getStringCellValue());
			try {
				tipoDocumento = tipoDocumentoService.obtenerCodigoTipoDocumento(tipoDocumento);
			} catch (DAOException e) {
				
			}
			equipoCaso.setTipoDocumento(tipoDocumento);
		}
		
		if (!fechaNacimiento.getStringCellValue().isEmpty() && fechaNacimiento != null) {
			try {
				if (!fechaNacimiento.getStringCellValue().equals("") && fechaNacimiento.getStringCellValue() != null) {
					fecha = formatoFecha.parse(fechaNacimiento.getStringCellValue());
					equipoCaso.setFechaNacimiento(fecha);
				}
			} catch (ParseException e) {
				LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Inicio del caso. El error es: "
						+ e.getMessage());
				infoError = " Verifique la fecha de Inicio el formato no corresponde";
			}
		}
		
		obtenerTelefonosCeldas(telefono, equipoCaso);
		obtenerCelularesCeldas(celular, equipoCaso);
		obtenerCorreosCeldas(correo, equipoCaso);	
		return equipoCaso;
	}

	/**
	 * METODO PARA ASIGNAR A EQUIPO CASO EQUIPO CASO
	 * */
	private CasoEquipoCaso asignarCasoEquipoCaso(XSSFRow rowDemandados) throws BusinessException {
		CasoEquipoCaso casoEquipoCaso = new CasoEquipoCaso();
		Parentesco parentesco = new Parentesco();
		XSSFCell isContacto = rowDemandados.getCell(2);
		
		
		/** Parentesco*/
		
		XSSFCell esposoCompanero = rowDemandados.getCell(10);
		XSSFCell nieto = rowDemandados.getCell(11);
		XSSFCell tios = rowDemandados.getCell(12);
		XSSFCell sobrinos = rowDemandados.getCell(13);
		XSSFCell bisnietos = rowDemandados.getCell(14);
		XSSFCell primos = rowDemandados.getCell(15);
		XSSFCell damnficados = rowDemandados.getCell(16);
		XSSFCell bisabuelos = rowDemandados.getCell(17);
		XSSFCell hijo = rowDemandados.getCell(18);
		XSSFCell madrePadre = rowDemandados.getCell(19);
		XSSFCell hermano = rowDemandados.getCell(20);
		XSSFCell abuelo = rowDemandados.getCell(21);
		XSSFCell otro = rowDemandados.getCell(22);
		
		if (isContacto != null) {
			if (isContacto.getStringCellValue() != null) {
				if (isContacto.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
					casoEquipoCaso.setContacto(Parametros.getIsContacto());
				}
			} else {
				casoEquipoCaso.setContacto(Parametros.getNoContacto());
			}
		}
		
		if (esposoCompanero.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoEsposo()));
			casoEquipoCaso.setParentesco(parentesco);
		} else if (hijo.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoHijo()));
			casoEquipoCaso.setParentesco(parentesco);
		} else if (madrePadre.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoMadre()));
			casoEquipoCaso.setParentesco(parentesco);
		} else if (hermano.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoHermano()));
			casoEquipoCaso.setParentesco(parentesco);
		} else if (abuelo.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoAbuelo()));
			casoEquipoCaso.setParentesco(parentesco);
		} else if (otro.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoOtro()));
			casoEquipoCaso.setParentesco(parentesco);
		}else if (nieto.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoNietos()));
			casoEquipoCaso.setParentesco(parentesco);
		}else if (tios.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoTios()));
			casoEquipoCaso.setParentesco(parentesco);
		}else if (sobrinos.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoSobrinos()));
			casoEquipoCaso.setParentesco(parentesco);
		}else if (bisnietos.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoBisnietos()));
			casoEquipoCaso.setParentesco(parentesco);
		}else if (primos.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoPrimos()));
			casoEquipoCaso.setParentesco(parentesco);
		}else if (damnficados.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoDanmificados()));
			casoEquipoCaso.setParentesco(parentesco);
		}else if (bisabuelos.getStringCellValue().equalsIgnoreCase(caracterMarca)) {
			parentesco.setCodigo(Integer.parseInt(parametrosDocumentos.getParentescoBisabuelos()));
			casoEquipoCaso.setParentesco(parentesco);
		}

		return casoEquipoCaso;
	}

	/**
	 * */
	private List<CasoEquipoCaso> abogadosResponsablesTareas(String abogados,
			String posiblesResponsables) throws DAOException, BusinessException {
		List<CasoEquipoCaso> abogadosAcargo = new ArrayList<CasoEquipoCaso>();
		Hashtable<String, String> infoAbogados = new Hashtable<String, String>();
		infoAbogados.put("abogados", abogados);
		String abogadoAlmacenar = "";
		if (posiblesResponsables.contains(";")) {
			String responsableString[] = posiblesResponsables.split(";");
			for (int i = 0; i < responsableString.length; i++) {
				if (infoAbogados.get("abogados").contains(responsableString[i])) {
					abogadoAlmacenar += responsableString[i] + ";";
				}
			}
			if (!abogadoAlmacenar.equalsIgnoreCase("")) {
				int posicion = abogadoAlmacenar.length();
				String abogadosAlmacenamiento = abogadoAlmacenar.substring(0, posicion - 1);
				abogadosAcargo = LlenarAbogados(null, abogadosAlmacenamiento);
			}

		} else if (posiblesResponsables != null && !posiblesResponsables.equals("")) {
			if (infoAbogados.get("abogados").contains(posiblesResponsables)) {
				abogadoAlmacenar = posiblesResponsables;
				abogadosAcargo = LlenarAbogados(null, abogadoAlmacenar);
			}
		}
		return abogadosAcargo;
	}

	/**
	 * METODO PARA LEER LA INFORMACION DE LOS TESTIGOS DEL CASO
	 * 
	 * @throws BusinessException
	 */
	public CasoEquipoCaso LlenarTestigos(XSSFRow rowTestigos) throws BusinessException {
		
		TipoMiembro tipoMiembro = new TipoMiembro();
		EquipoCaso equipoCaso = new EquipoCaso();
		CasoEquipoCaso casoEquipoCaso = new CasoEquipoCaso();
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date fecha = null;
		XSSFCell nombreTestigo = rowTestigos.getCell(0);
		XSSFCell apellidoTestigo = rowTestigos.getCell(1);
		XSSFCell cedulaTestigo = rowTestigos.getCell(3);
		XSSFCell tipoDocumentoTestigo = rowTestigos.getCell(4);
		XSSFCell fechaNacimientoTestigo = rowTestigos.getCell(5);
		XSSFCell telefonoTestigo = rowTestigos.getCell(6);
		XSSFCell celularTestigo = rowTestigos.getCell(7);
		XSSFCell hotmailTestigo = rowTestigos.getCell(8);
		XSSFCell direccionTestigo = rowTestigos.getCell(9);
		
		XSSFCell esposoCompanero = rowTestigos.getCell(10);
		XSSFCell nieto = rowTestigos.getCell(11);
		XSSFCell tios = rowTestigos.getCell(12);
		XSSFCell sobrinos = rowTestigos.getCell(13);
		XSSFCell bisnietos = rowTestigos.getCell(14);
		XSSFCell primos = rowTestigos.getCell(15);
		XSSFCell damnficados = rowTestigos.getCell(16);
		XSSFCell bisabuelos = rowTestigos.getCell(17);
		XSSFCell hijo = rowTestigos.getCell(18);
		XSSFCell madrePadre = rowTestigos.getCell(19);
		XSSFCell hermano = rowTestigos.getCell(20);
		XSSFCell abuelo = rowTestigos.getCell(21);
		XSSFCell otro = rowTestigos.getCell(22);
				
		Parentesco parentesco = new Parentesco();
		List<Parentesco> listaParentesco = null;
		if (nombreTestigo != null) {
			if (nombreTestigo.getStringCellValue() != null) {
				equipoCaso.setNombre(nombreTestigo.getStringCellValue());
			} else {
				infoError = "Los campos marcados con (*) son requeridos.";
			}
		}
		if (apellidoTestigo.getStringCellValue() != null && apellidoTestigo != null) {
			equipoCaso.setApellido(apellidoTestigo.getStringCellValue());
		}

		try {
			if (cedulaTestigo.getNumericCellValue() > 0 && cedulaTestigo != null) {
				int datos = (int) cedulaTestigo.getNumericCellValue();
				equipoCaso.setIdentificacion(Integer.toString(datos));
			}
		} catch (Exception e) {
			if (cedulaTestigo.getStringCellValue() != null && cedulaTestigo != null) {

				equipoCaso.setIdentificacion(cedulaTestigo.getStringCellValue());
			}
		}

		obtenerTelefonosCeldas(telefonoTestigo, equipoCaso);
		obtenerCelularesCeldas(celularTestigo, equipoCaso);
		obtenerCorreosCeldas(hotmailTestigo, equipoCaso);
		
		if (direccionTestigo.getStringCellValue() != null
				&& direccionTestigo != null) {
			casoEquipoCaso.setDireccion(direccionTestigo.getStringCellValue());
		}
		
		if (tipoDocumentoTestigo.getStringCellValue() != null) {
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setDocumento(tipoDocumentoTestigo.getStringCellValue());
			try {
				tipoDocumento = tipoDocumentoService.obtenerCodigoTipoDocumento(tipoDocumento);
			} catch (DAOException e) {
				
			}
			equipoCaso.setTipoDocumento(tipoDocumento);
		}
		
		if (!fechaNacimientoTestigo.getStringCellValue().isEmpty() && fechaNacimientoTestigo != null) {
			try {
				if (!fechaNacimientoTestigo.getStringCellValue().equals("") && fechaNacimientoTestigo.getStringCellValue() != null) {
					fecha = formatoFecha.parse(fechaNacimientoTestigo.getStringCellValue());
					equipoCaso.setFechaNacimiento(fecha);
				}
			} catch (ParseException e) {
				LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Inicio del caso. El error es: "
						+ e.getMessage());
				infoError = " Verifique la fecha de Inicio el formato no corresponde";
			}
		}
		
		if (esposoCompanero.getStringCellValue().equalsIgnoreCase(caracterMarca) && esposoCompanero != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoEsposo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoEsposo();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (hijo.getStringCellValue().equalsIgnoreCase(caracterMarca) && hijo != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoHijo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: " + Parametros.getParentescoHijo();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (madrePadre.getStringCellValue().equalsIgnoreCase(caracterMarca) && madrePadre != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoMadre());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoMadre();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (hermano.getStringCellValue().equalsIgnoreCase(caracterMarca) && hermano != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoHermano());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoHermano();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (abuelo.getStringCellValue().equalsIgnoreCase(caracterMarca) && abuelo != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoAbuelo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoAbuelo();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (sobrinos.getStringCellValue().equalsIgnoreCase(caracterMarca) && sobrinos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoPrimos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoSobrinos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (nieto.getStringCellValue().equalsIgnoreCase(caracterMarca) && nieto != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoNieto());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoNieto();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (bisnietos.getStringCellValue().equalsIgnoreCase(caracterMarca) && bisnietos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoBisnietos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoBisnietos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (primos.getStringCellValue().equalsIgnoreCase(caracterMarca) && primos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoPrimos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoPrimos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (damnficados.getStringCellValue().equalsIgnoreCase(caracterMarca) && damnficados != null) {
			try {
				parentesco.setNombre(Parametros.getParentesco3rosDamnificados());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentesco3rosDamnificados();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (bisabuelos.getStringCellValue().equalsIgnoreCase(caracterMarca) && bisabuelos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoBisabuelos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoBisabuelos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}	
		} else if (otro.getStringCellValue().equalsIgnoreCase(caracterMarca) && otro != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoOtro());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoOtro();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}

		}else if (tios.getStringCellValue().equalsIgnoreCase(caracterMarca) && tios != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoTio());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoOtro();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}

		}
		
		

		CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
		casoPk.setMiembro(Integer.parseInt(Parametros.getTestigo()));
		casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
		tipoMiembro.setCodigo(Integer.parseInt(Parametros.getTestigo()));
		casoEquipoCaso.setTipoMiembro(tipoMiembro);
		casoEquipoCaso.setEquipoCaso(equipoCaso);
		casoEquipoCaso.setActivo(Parametros.getContactoActivo());
		
		return casoEquipoCaso;
	}
	
	
	public CasoEquipoCaso LlenarReferenciador(XSSFRow rowReferenciador) throws BusinessException {
		TipoMiembro tipoMiembro = new TipoMiembro();
		EquipoCaso equipoCaso = new EquipoCaso();
		CasoEquipoCaso casoEquipoCaso = new CasoEquipoCaso();
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date fecha = null;
		XSSFCell nombreReferenciador = rowReferenciador.getCell(0);
		XSSFCell apellidoReferenciador = rowReferenciador.getCell(1);
		XSSFCell cedulaReferenciador = rowReferenciador.getCell(3);
		XSSFCell tipoDocumentoReferenciador = rowReferenciador.getCell(4);
		XSSFCell fechaNacimientoReferenciador = rowReferenciador.getCell(5);
		XSSFCell telefonoReferenciador = rowReferenciador.getCell(6);
		XSSFCell celularReferenciador = rowReferenciador.getCell(7);
		XSSFCell hotmailReferenciador = rowReferenciador.getCell(8);
		XSSFCell direccionReferenciador = rowReferenciador.getCell(9);
		
		XSSFCell sobrinos = rowReferenciador.getCell(10);
		XSSFCell nietos = rowReferenciador.getCell(11);
		XSSFCell bisnietos = rowReferenciador.getCell(13);
		XSSFCell primos = rowReferenciador.getCell(14);
		XSSFCell danmificados = rowReferenciador.getCell(15);
		XSSFCell bisabuelos = rowReferenciador.getCell(16);
		
		XSSFCell esposoCompanero = rowReferenciador.getCell(17);
		XSSFCell hijo = rowReferenciador.getCell(18);
		XSSFCell madrePadre = rowReferenciador.getCell(19);
		XSSFCell hermano = rowReferenciador.getCell(20);
		XSSFCell abuelo = rowReferenciador.getCell(21);
		XSSFCell otro = rowReferenciador.getCell(22);
		Parentesco parentesco = new Parentesco();
		List<Parentesco> listaParentesco = null;
		if (nombreReferenciador != null) {
			if (nombreReferenciador.getStringCellValue() != null) {
				equipoCaso.setNombre(nombreReferenciador.getStringCellValue());
			} else {
				infoError = "Los campos marcados con (*) son requeridos.";
			}
		}
		if (apellidoReferenciador.getStringCellValue() != null && apellidoReferenciador != null) {
			equipoCaso.setApellido(apellidoReferenciador.getStringCellValue());
		}
		

		try {
			if (cedulaReferenciador.getNumericCellValue() > 0 && cedulaReferenciador != null) {
				int datos = (int) cedulaReferenciador.getNumericCellValue();
				equipoCaso.setIdentificacion(Integer.toString(datos));
			}
		} catch (Exception e) {
			if (cedulaReferenciador.getStringCellValue() != null && cedulaReferenciador != null) {

				equipoCaso.setIdentificacion(cedulaReferenciador.getStringCellValue());
			}
		}
		
		obtenerTelefonosCeldas(telefonoReferenciador, equipoCaso);
		obtenerCelularesCeldas(celularReferenciador, equipoCaso);
		obtenerCorreosCeldas(hotmailReferenciador, equipoCaso);
		
		if (direccionReferenciador.getStringCellValue() != null
				&& direccionReferenciador != null) {
			casoEquipoCaso.setDireccion(direccionReferenciador.getStringCellValue());
		}
		
		if (tipoDocumentoReferenciador.getStringCellValue() != null) {
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setDocumento(tipoDocumentoReferenciador.getStringCellValue());
			try {
				tipoDocumento = tipoDocumentoService.obtenerCodigoTipoDocumento(tipoDocumento);
			} catch (DAOException e) {
				
			}
			equipoCaso.setTipoDocumento(tipoDocumento);
		}
		
		if (!fechaNacimientoReferenciador.getStringCellValue().isEmpty() && fechaNacimientoReferenciador != null) {
			try {
				if (!fechaNacimientoReferenciador.getStringCellValue().equals("") && fechaNacimientoReferenciador.getStringCellValue() != null) {
					fecha = formatoFecha.parse(fechaNacimientoReferenciador.getStringCellValue());
					equipoCaso.setFechaNacimiento(fecha);
				}
			} catch (ParseException e) {
				LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Inicio del caso. El error es: "
						+ e.getMessage());
				infoError = " Verifique la fecha de Inicio el formato no corresponde";
			}
		}
		
		if (esposoCompanero.getStringCellValue().equalsIgnoreCase(caracterMarca) && esposoCompanero != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoEsposo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoEsposo();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (hijo.getStringCellValue().equalsIgnoreCase(caracterMarca) && hijo != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoHijo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: " + Parametros.getParentescoHijo();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (madrePadre.getStringCellValue().equalsIgnoreCase(caracterMarca) && madrePadre != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoMadre());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoMadre();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (hermano.getStringCellValue().equalsIgnoreCase(caracterMarca) && hermano != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoHermano());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoHermano();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (abuelo.getStringCellValue().equalsIgnoreCase(caracterMarca) && abuelo != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoAbuelo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoAbuelo();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (sobrinos.getStringCellValue().equalsIgnoreCase(caracterMarca) && sobrinos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoPrimos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoSobrinos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (nietos.getStringCellValue().equalsIgnoreCase(caracterMarca) && nietos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoNieto());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoNieto();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (bisnietos.getStringCellValue().equalsIgnoreCase(caracterMarca) && bisnietos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoBisnietos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoBisnietos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (primos.getStringCellValue().equalsIgnoreCase(caracterMarca) && primos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoPrimos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoPrimos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (danmificados.getStringCellValue().equalsIgnoreCase(caracterMarca) && danmificados != null) {
			try {
				parentesco.setNombre(Parametros.getParentesco3rosDamnificados());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentesco3rosDamnificados();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (bisabuelos.getStringCellValue().equalsIgnoreCase(caracterMarca) && bisabuelos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoBisabuelos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoBisabuelos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}			
		} else if (otro.getStringCellValue().equalsIgnoreCase(caracterMarca) && otro != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoOtro());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoOtro();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}

		}

		CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
		casoPk.setMiembro(Integer.parseInt(Parametros.getReferenciador()));
		casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
		tipoMiembro.setCodigo(Integer.parseInt(Parametros.getReferenciador()));
		casoEquipoCaso.setTipoMiembro(tipoMiembro);
		casoEquipoCaso.setEquipoCaso(equipoCaso);
		casoEquipoCaso.setActivo(Parametros.getContactoActivo());
		
		return casoEquipoCaso;
	}	

	/**
	 * METODO PARA LEER LA INFORMACION DE LAS VICTIMAS DEL CASO
	 * 
	 * @throws BusinessException
	 */
	public CasoEquipoCaso LlenarVictimas(XSSFRow rowVictimas) throws BusinessException {
		
		TipoMiembro tipoMiembro = new TipoMiembro();
		EquipoCaso equipoCaso = new EquipoCaso();
		CasoEquipoCaso casoEquipoCaso = new CasoEquipoCaso();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date fecha = null;
		
		XSSFCell nombreVictima = rowVictimas.getCell(0);
		XSSFCell apellidoVictima = rowVictimas.getCell(1);
		XSSFCell cedulaVictima = rowVictimas.getCell(3);
		XSSFCell tipoDocumentoVictima = rowVictimas.getCell(4);
		XSSFCell fehcaNacimientoVictima = rowVictimas.getCell(5);
		XSSFCell telefonoVictima = rowVictimas.getCell(6);
		XSSFCell celularVictima = rowVictimas.getCell(7);
		XSSFCell hotmailVictima = rowVictimas.getCell(8);
		XSSFCell direccionVictima = rowVictimas.getCell(9);
		
		XSSFCell sobrinos = rowVictimas.getCell(10);
		XSSFCell nietos = rowVictimas.getCell(11);
		XSSFCell bisnietos = rowVictimas.getCell(13);
		XSSFCell primos = rowVictimas.getCell(14);
		XSSFCell danmificados = rowVictimas.getCell(15);
		XSSFCell bisabuelos = rowVictimas.getCell(16);
		
		XSSFCell esposoCompanero = rowVictimas.getCell(17);
		XSSFCell hijo = rowVictimas.getCell(18);
		XSSFCell madrePadre = rowVictimas.getCell(19);
		XSSFCell hermano = rowVictimas.getCell(20);
		XSSFCell abuelo = rowVictimas.getCell(21);
		XSSFCell otro = rowVictimas.getCell(22);
		
		
		Parentesco parentesco = new Parentesco();
		List<Parentesco> listaParentesco = null;
		if (nombreVictima != null) {
			if (nombreVictima.getStringCellValue() != null) {
				equipoCaso.setNombre(nombreVictima.getStringCellValue());
			} else {
				infoError = "Los campos marcados con (*) son requeridos.";
			}
		}
		if (apellidoVictima.getStringCellValue() != null && apellidoVictima != null)
			equipoCaso.setApellido(apellidoVictima.getStringCellValue());
		try {
			if (cedulaVictima.getNumericCellValue() > 0 && cedulaVictima != null) {
				int datos = (int) cedulaVictima.getNumericCellValue();
				equipoCaso.setIdentificacion(Integer.toString(datos));
			}
		} catch (Exception e) {
			if (cedulaVictima.getStringCellValue() != null && cedulaVictima != null) {
				equipoCaso.setIdentificacion(cedulaVictima.getStringCellValue());
			}
		}
		obtenerTelefonosCeldas(telefonoVictima, equipoCaso);
		obtenerCelularesCeldas(celularVictima, equipoCaso);
		obtenerCorreosCeldas(hotmailVictima, equipoCaso);

		if (esposoCompanero.getStringCellValue().equalsIgnoreCase(caracterMarca) && esposoCompanero != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoEsposo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoEsposo();

			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (hijo.getStringCellValue().equalsIgnoreCase(caracterMarca) && hijo != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoHijo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoHijo();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (madrePadre.getStringCellValue().equalsIgnoreCase(caracterMarca) && madrePadre != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoMadre());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoMadre();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}

		} else if (hermano.getStringCellValue().equalsIgnoreCase(caracterMarca) && hermano != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoHermano());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoHermano();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}

		} else if (abuelo.getStringCellValue().equalsIgnoreCase(caracterMarca) && abuelo != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoAbuelo());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoAbuelo();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (sobrinos.getStringCellValue().equalsIgnoreCase(caracterMarca) && sobrinos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoPrimos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoSobrinos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (nietos.getStringCellValue().equalsIgnoreCase(caracterMarca) && nietos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoNieto());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoNieto();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (bisnietos.getStringCellValue().equalsIgnoreCase(caracterMarca) && bisnietos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoBisnietos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoBisnietos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (primos.getStringCellValue().equalsIgnoreCase(caracterMarca) && primos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoPrimos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoPrimos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (danmificados.getStringCellValue().equalsIgnoreCase(caracterMarca) && danmificados != null) {
			try {
				parentesco.setNombre(Parametros.getParentesco3rosDamnificados());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentesco3rosDamnificados();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}
		} else if (bisabuelos.getStringCellValue().equalsIgnoreCase(caracterMarca) && bisabuelos != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoBisabuelos());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoBisabuelos();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}	
		} else if (otro.getStringCellValue().equalsIgnoreCase(caracterMarca) && otro != null) {
			try {
				parentesco.setNombre(Parametros.getParentescoOtro());
				listaParentesco = parentescoService.obtenerCodigoParentesco(parentesco);
				for (Parentesco objParentesco : listaParentesco) {
					casoEquipoCaso.setParentesco(objParentesco);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos del parentesco. El error es: "
						+ e.getMessage());
				infoError = "Verifique el tipo de parentesco seleccionado: "
						+ Parametros.getParentescoOtro();
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos del parentesco. El error es: "
						+ e.getMessage());
			}

		}
		
		if (direccionVictima.getStringCellValue() != null)
			casoEquipoCaso.setDireccion(direccionVictima.getStringCellValue());
		
		if (tipoDocumentoVictima.getStringCellValue() != null) {
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setDocumento(tipoDocumentoVictima.getStringCellValue());
			try {
				tipoDocumento = tipoDocumentoService.obtenerCodigoTipoDocumento(tipoDocumento);
			} catch (DAOException e) {
				
			}
			equipoCaso.setTipoDocumento(tipoDocumento);
		}
		
		if (!fehcaNacimientoVictima.getStringCellValue().isEmpty() && fehcaNacimientoVictima != null) {
			try {
				if (!fehcaNacimientoVictima.getStringCellValue().equals("") && fehcaNacimientoVictima.getStringCellValue() != null) {
					fecha = formatoFecha.parse(fehcaNacimientoVictima.getStringCellValue());
					equipoCaso.setFechaNacimiento(fecha);
				}
			} catch (ParseException e) {
				LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Inicio del caso. El error es: "
						+ e.getMessage());
				infoError = " Verifique la fecha de Inicio el formato no corresponde";
			}
		}
		
		CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
		casoPk.setMiembro(Integer.parseInt(Parametros.getVictima()));
		casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
		tipoMiembro.setCodigo(Integer.parseInt(Parametros.getTestigo()));
		casoEquipoCaso.setTipoMiembro(tipoMiembro);
		casoEquipoCaso.setEquipoCaso(equipoCaso);
		casoEquipoCaso.setActivo(Parametros.getContactoActivo());
		return casoEquipoCaso;
	}

	/** METODO PARA LEER LA INFORMACION DE LOS DEMANDADOS DEL CASO 
	 * @throws BusinessException */
	public CasoEquipoCaso LlenarDemandados(XSSFRow rowDemandado) throws BusinessException {
		TipoMiembro tipoMiembro = new TipoMiembro();
		EquipoCaso equipoCaso = new EquipoCaso();
		List<Correo> listaCorreo = new ArrayList<Correo>();
		CasoEquipoCaso casoEquipoCaso = new CasoEquipoCaso();
		Telefono telefonoObjeto = new Telefono();
		Correo correoObjeto = new Correo();
		Celular celular = new Celular();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date fecha = null;
		List<Telefono> listaTelefonos = new ArrayList<Telefono>();
		List<Celular> listaCelulares = new ArrayList<Celular>();
		XSSFCell tipoPersona = rowDemandado.getCell(0);
		XSSFCell nombreDemandado = rowDemandado.getCell(1);
		XSSFCell cedulaDemandado = rowDemandado.getCell(2);
		XSSFCell tipoDocumentoDemandado = rowDemandado.getCell(3);
		XSSFCell fechaNacimientoDemandado = rowDemandado.getCell(4);
		XSSFCell telefonoDemandado = rowDemandado.getCell(5);
		XSSFCell celularDemandado = rowDemandado.getCell(6);
		XSSFCell hotmailDemandado = rowDemandado.getCell(7);
		XSSFCell direccionDemandado = rowDemandado.getCell(8);
		XSSFCell ciudad = rowDemandado.getCell(9);
		XSSFCell departemento = rowDemandado.getCell(10);
		XSSFCell pais = rowDemandado.getCell(11);
		XSSFCell notasDemandado = rowDemandado.getCell(12);
		Ciudad ciudadObjeto = new Ciudad();
		List<Ciudad> listaciudad = null;
		Departamento departamentoObjeto = new Departamento();
		Pais paisObjeto = new Pais();
		Pais objPais = null;
		Departamento objDepartamento = null;

		if (notasDemandado != null && notasDemandado.getStringCellValue() != null) {
			casoEquipoCaso.setObservacion(notasDemandado.getStringCellValue());
		}

		if (nombreDemandado != null) {
			if (nombreDemandado.getStringCellValue() != null) {
				equipoCaso.setNombre(nombreDemandado.getStringCellValue());
			} else {
				infoError = "Los campos marcados con (*) son requeridos.";
			}
		}

		try {
			if (cedulaDemandado.getNumericCellValue() > 0) {
				int datos = (int) cedulaDemandado.getNumericCellValue();
				equipoCaso.setIdentificacion(Integer.toString(datos));
			}
		} catch (Exception e) {
			if (cedulaDemandado.getStringCellValue() != null) {
				equipoCaso.setIdentificacion(cedulaDemandado.getStringCellValue());
			}
		}

		obtenerTelefonosCeldas(telefonoDemandado, equipoCaso);
		obtenerCelularesCeldas(celularDemandado, equipoCaso);
		obtenerCorreosCeldas(hotmailDemandado, equipoCaso);
		

		if (direccionDemandado != null)
			casoEquipoCaso.setDireccion(direccionDemandado.getStringCellValue());
		
		if (tipoDocumentoDemandado.getStringCellValue() != null) {
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setDocumento(tipoDocumentoDemandado.getStringCellValue());
			try {
				tipoDocumento = tipoDocumentoService.obtenerCodigoTipoDocumento(tipoDocumento);
			} catch (DAOException e) {
				
			}
			equipoCaso.setTipoDocumento(tipoDocumento);
		}
		
		if (!fechaNacimientoDemandado.getStringCellValue().isEmpty() && fechaNacimientoDemandado != null) {
			try {
				if (!fechaNacimientoDemandado.getStringCellValue().equals("") && fechaNacimientoDemandado.getStringCellValue() != null) {
					fecha = formatoFecha.parse(fechaNacimientoDemandado.getStringCellValue());
					equipoCaso.setFechaNacimiento(fecha);
				}
			} catch (ParseException e) {
				LOG.error("DAOException: Ocurrio un error convirtiendo la Fecha Inicio del caso. El error es: "
						+ e.getMessage());
				infoError = " Verifique la fecha de Inicio el formato no corresponde";
			}
		}

		if (!ciudad.getStringCellValue().isEmpty() && ciudad != null) {
			ciudadObjeto.setNombre(ciudad.getStringCellValue());
			try {
				listaciudad = ciudadService.consultarCodigoCiudad(ciudadObjeto);
				for (Ciudad obj : listaciudad) {
					casoEquipoCaso.setCiudadEquipoCaso(obj);
				}
			} catch (DAOException e) {
				LOG.error("DAOException: Ocurrio un error consultando codigos de la ciudad. El error es: "
						+ e.getMessage());
				infoError = "Verifique la Ciudad seleccionada ";
			} catch (BusinessException e) {
				LOG.error("BusinessException: Ocurrio un error consultando los codigos de la ciudad . El error es: "
						+ e.getMessage());
			}
		} else {
			if (departemento != null) {
				if (!departemento.getStringCellValue().isEmpty()) {
					departamentoObjeto.setNombre(departemento.getStringCellValue());
					try {
						objDepartamento = DepartamentoService.consultarCodigoDepartamento(departamentoObjeto);
						ciudadObjeto.setDepartamento(objDepartamento);
						casoEquipoCaso.setCiudadEquipoCaso(ciudadObjeto);
					} catch (DAOException e) {
						LOG.error("DAOException: Ocurrio un error consultando codigos del departamento. El error es: "
								+ e.getMessage());
						infoError = "Verifique el Departamento  seleccionado ";
					} catch (BusinessException e) {
						LOG.error("BusinessException: Ocurrio un error consultando los codigos del departamento . El error es: "
								+ e.getMessage());
					}
				}
			} else {
				if (!pais.getStringCellValue().isEmpty() && pais != null) {
					paisObjeto.setNombre(pais.getStringCellValue());
					try {
						objPais = paisService.consultarCodigoPais(paisObjeto);
						departamentoObjeto.setPais(objPais);
						ciudadObjeto.setDepartamento(departamentoObjeto);
						casoEquipoCaso.setCiudadEquipoCaso(ciudadObjeto);
					} catch (DAOException e) {
						LOG.error("DAOException: Ocurrio un error consultando codigos del pais. El error es: "
								+ e.getMessage());
						infoError = "Verifique el Pais  seleccionado ";
					} catch (BusinessException e) {
						LOG.error("BusinessException: Ocurrio un error consultando los codigos del pais. El error es: "
								+ e.getMessage());
					}
				}
			}
		}
		
		CasoEquipoCasoPK casoPk = new CasoEquipoCasoPK();
		casoPk.setMiembro(Integer.parseInt(Parametros.getDemandado()));
		casoEquipoCaso.setCasoEquipoCasoPK(casoPk);
		tipoMiembro.setCodigo(Integer.parseInt(Parametros.getTestigo()));
		casoEquipoCaso.setTipoMiembro(tipoMiembro);
		casoEquipoCaso.setEquipoCaso(equipoCaso);
		casoEquipoCaso.setActivo(Parametros.getContactoActivo());
		if (tipoPersona.getStringCellValue() != null && tipoPersona != null) {
			if (tipoPersona.getStringCellValue().equalsIgnoreCase("Persona Jurídica")) {
				casoEquipoCaso.setPersonajuridica(Parametros.getIsPersonaJuridica());
			} else if (tipoPersona.getStringCellValue().equalsIgnoreCase("Persona Natural")) {
				casoEquipoCaso.setPersonajuridica(Parametros.getNotPersonaJuridica());
			}
		}

		return casoEquipoCaso;
	}

	/** METODO PARA LEER LA INFORMACION DE LOS BIENES AFECTADOS DEL CASO */
	public BienAfectado LlenarBienesAfectado(XSSFRow rowBienAfectado) {
		BienAfectado objBienAfectado = new BienAfectado();
		ClaseBien claseBienObjeto = new ClaseBien();
		XSSFCell claseBien = null;
		XSSFCell tituloBien = null;
		XSSFCell descripcionBien = null;

		if (rowBienAfectado != null) {
			claseBien = rowBienAfectado.getCell(3);
			tituloBien = rowBienAfectado.getCell(7);
			descripcionBien = rowBienAfectado.getCell(10);
			
			if(claseBien.getStringCellValue().isEmpty() && tituloBien.getStringCellValue().isEmpty() && descripcionBien.getStringCellValue().isEmpty())
				objBienAfectado = null;
			else{
				if (claseBien != null) {
					try {
						if (!claseBien.getStringCellValue().equalsIgnoreCase("")
								&& claseBien.getStringCellValue() != null) {
							claseBienObjeto.setNombre(claseBien.getStringCellValue());
							claseBienObjeto = claseBienService.consultarCodigoClaseBien(claseBienObjeto);
							objBienAfectado.setClase(claseBienObjeto);
						} else if (claseBien.getStringCellValue().equalsIgnoreCase("")) {
							claseBien = null;
						}
	
					} catch (DAOException e) {
						LOG.error("DAOException: Ocurrio un error consultando el codigo clase de bien afectado. El error es: "
								+ e.getMessage());
						infoError = "Verifique el Tipo de Bien Afectados no coinciden ";
	
					} catch (BusinessException e) {
						LOG.error("BusinessException: Ocurrio un error consultando el codigo clase de bien afectado. El error es: "
								+ e.getMessage());
					}
				}
	
				if (claseBienObjeto != null) {
	
					if (tituloBien != null) {
						objBienAfectado.setTitulo(tituloBien.getStringCellValue());
					}
	
					if (descripcionBien != null) {
						objBienAfectado.setDetalle(descripcionBien.getStringCellValue());
					}
				}
			}
		}else
			objBienAfectado = null;
		
		return objBienAfectado;
	}

	/** METODO PARA LEER LA INFORMACION DE LOS RADICADOS DEL CASO 
	 * @throws BusinessException 
	 * @throws DAOException */
	private Radicado LlenarRadicados(XSSFRow rowRadicado) throws DAOException, BusinessException {
		Radicado objRadicado = new Radicado();
		RadicadoPK objRadicadoPk = new RadicadoPK();
		Instancia instanciaObjeto = new Instancia();
		XSSFCell numeroRadicado = null;
		XSSFCell intanciaRadicado = null;
		if (rowRadicado != null) {
			numeroRadicado = rowRadicado.getCell(3);
			intanciaRadicado = rowRadicado.getCell(8);
		}
		
		if (intanciaRadicado != null && !intanciaRadicado.getStringCellValue().isEmpty()) {
			instanciaObjeto.setNombre(intanciaRadicado.getStringCellValue());
			instanciaObjeto = instanciaService.consultarCodigoInstancia(instanciaObjeto);
			if (instanciaObjeto != null) {
				objRadicado.setInstancia(instanciaObjeto);
				if (numeroRadicado != null) {
					try {						
						objRadicadoPk.setCodigoRadicado(numeroRadicado.getStringCellValue());
					} catch (Exception e) {
						objRadicadoPk.setCodigoRadicado(String.valueOf(numeroRadicado.getNumericCellValue()));
					}
					objRadicado.setRadicadoPK(objRadicadoPk);
				}
			}
		} else {
			objRadicado=null;
		}

		return objRadicado;
	}

	/**
	 * 
	 * */
	private void asignarTareasResponsables(
			String nombreTarea,
			ActividadCaso actividadParticular,
			List<TareaParticularCaso> listaSetTarea,
			List<CasoEquipoCaso> abogados,
			String cdusuarioLogeado,
			Date fechaActual,
			XSSFCell nombre,
			XSSFCell apellido, 
			String fecha,
			boolean tareaCompletada) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaVencimiento = null;
		if (!fecha.equals("")) {
			try {
				fechaVencimiento = formatoDelTexto.parse(fecha);
			} catch (ParseException e) {
				LOG.error("Error al tratar de realizar la comversion de la fecha de vencimiento a tipo date : "
						+ e.getMessage());
				infoError = "Verifique los formatos de la fecha de vencimiento de la actividad y tareas no coinciden";
			}
		}
		TareaParticularCaso tareasParticulares = new TareaParticularCaso();
		List<ResponsableTarea> responsables = new ArrayList<ResponsableTarea>();
		
		tareasParticulares.setTarea(nombreTarea + " " + nombre.getStringCellValue() + " " + apellido.getStringCellValue());
		tareasParticulares.setActividadParticular(actividadParticular);
		tareasParticulares.setDetalle(nombreTarea);

		if(tareaCompletada)
			tareasParticulares.setFinalizada(Parametros.getActividadPendiente());
		else
			tareasParticulares.setFinalizada(Parametros.getActividadFinalizada());
		
		tareasParticulares.setUsuarioCreacion(cdusuarioLogeado);
		tareasParticulares.setUsuarioUltimaModificacion(cdusuarioLogeado);
		tareasParticulares.setFechaCreacion(fechaActual);
		tareasParticulares.setFechaUltimaModificacion(fechaActual);
		
		if (fechaVencimiento != null)
			tareasParticulares.setFechaLimite(fechaVencimiento);
		
		tareasParticulares.setSnActiva(Parametros.getCodigoActividadActivoSi());
		tareasParticulares.setEsTareaPropia(Parametros.getCodigoActividadPropiaSi());
		
		for (CasoEquipoCaso abogadoResponsable : abogados) {
			ResponsableTarea responsable = new ResponsableTarea();
			ResponsableTareaPK responsableTareaPK = new ResponsableTareaPK();
			
			responsableTareaPK.setCodigoEquipoCaso(abogadoResponsable.getCasoEquipoCasoPK().getCodigoEquipoCaso());
			responsableTareaPK.setCodigoMiembro(abogadoResponsable.getCasoEquipoCasoPK().getMiembro());
			responsable.setResponsableTareaPK(responsableTareaPK);
			responsable.setFechaFinalizacionTarea(fechaVencimiento);
			responsables.add(responsable);
		}
		tareasParticulares.setResponsablesTareas(responsables);
		listaSetTarea.add(tareasParticulares);
		actividadParticular.setTareaParticularCasoSet(listaSetTarea);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/asociarArchivo" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json; charset=utf-8" }, headers = { "Accept=*/*" })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String asociarArchivo(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest context,
			@RequestParam(value = "cdusuarioLogeado", required = false) String cdusuarioLogeado,
			@RequestParam(value = "codigoTarea", required = false) Integer codigoTarea)
			throws IOException, DAOException, BusinessException {
		JSONObject res = new JSONObject();
		String nombreArchivo = "";
//		java.util.Date fecha = new Date();
		Archivo archivoSubir = new Archivo();
		ServletContext servletContext = context.getSession()
				.getServletContext();
		String relativeWebPath = Parametros.getRutaCargarExcel();
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
		try {
			if (!file.isEmpty()) {
				nombreArchivo = file.getOriginalFilename();
//				InputStream inputStream = file.getInputStream();
//				BufferedReader bufferedReader = new BufferedReader(
//						new InputStreamReader(inputStream));
				File archivo = new File(Parametros.getRutaCargaArchivos());
				if (!archivo.exists()) {
					archivo.mkdir();
				}
				File archivoEnAplicativo = new File(absoluteDiskPath);
				if (!archivoEnAplicativo.exists()) {
					archivoEnAplicativo.mkdir();
				}

				archivo = new File(Parametros.getRutaCargarExcel()
						+ codigoTarea + "-" + file.getOriginalFilename());
				archivoEnAplicativo = new File(archivoEnAplicativo
						+ file.getOriginalFilename());
				file.transferTo(archivo);
				String ruta = archivo.toString();
				archivoSubir.setDsruta(ruta);
//				String fileNameWithOutExt = FilenameUtils.getExtension(ruta);
				archivoSubir.setDsarchivo(codigoTarea + "-"
						+ file.getOriginalFilename());
				TareaParticularCaso tareaObjeto = new TareaParticularCaso();
				tareaObjeto.setCodigoTarea(codigoTarea);
				archivoSubir.setCdtareaparticular(tareaObjeto);
				archivoService.guardarArchivo(archivoSubir);
				res.put("STATUS", "SUCCESS");
			}
		} catch (IOException e) {
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/asociarArchivoPrestamo" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json; charset=utf-8" }, headers = { "Accept=*/*" })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String asociarArchivoPrestamo(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest context,
			@RequestParam(value = "cdusuarioLogeado", required = false) String cdusuarioLogeado,
			@RequestParam(value = "codigoPrestamo", required = false) Integer codigoPrestamo)
			throws IOException, DAOException, BusinessException {
		JSONObject res = new JSONObject();
		String nombreArchivo = "";
//		java.util.Date fecha = new Date();

		ServletContext servletContext = context.getSession()
				.getServletContext();
		String relativeWebPath = Parametros.getRutaCargarExcel();
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
		try {
			if (!file.isEmpty()) {
				nombreArchivo = file.getOriginalFilename();
//				InputStream inputStream = file.getInputStream();
//				BufferedReader bufferedReader = new BufferedReader(
//						new InputStreamReader(inputStream));
				File archivo = new File(Parametros.getRutaCargaArchivos());
				if (!archivo.exists()) {
					archivo.mkdir();
				}
				File archivoEnAplicativo = new File(absoluteDiskPath);
				if (!archivoEnAplicativo.exists()) {
					archivoEnAplicativo.mkdir();
				}

				archivo = new File(Parametros.getRutaCargarExcel()
						+ codigoPrestamo + "-" + file.getOriginalFilename());
				archivoEnAplicativo = new File(archivoEnAplicativo
						+ file.getOriginalFilename());
				file.transferTo(archivo);
//				String ruta = archivo.toString();
//				String fileNameWithOutExt = FilenameUtils.getExtension(ruta);
				Prestamo objPrestamo = new Prestamo();
				objPrestamo.setCodigoPrestamo(codigoPrestamo);
				objPrestamo = prestamoService.obtenerPrestamo(objPrestamo);
				objPrestamo.setArchivo(codigoPrestamo + "-"
						+ file.getOriginalFilename());
				objPrestamo = prestamoService.actualizarPrestamo(objPrestamo);
				res.put("STATUS", "SUCCESS");
			}
		} catch (IOException e) {
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/asociarArchivoAbono" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json; charset=utf-8" }, headers = { "Accept=*/*" })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String asociarArchivoAbono(
			@RequestParam("file") MultipartFile[] files,
			HttpServletRequest context,
			@RequestParam(value = "cdusuarioLogeado", required = false) String cdusuarioLogeado,
			@RequestParam(value = "codigoAbono", required = false) Integer codigoAbono)
					throws IOException, DAOException, BusinessException {
		JSONObject res = new JSONObject();
		String nombreArchivo = "";
//		java.util.Date fecha = new Date();
		
		ServletContext servletContext = context.getSession()
				.getServletContext();
		String relativeWebPath = Parametros.getRutaCargarExcel();
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
		try {
			
			
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					nombreArchivo = file.getOriginalFilename();

					File archivo = new File(Parametros.getRutaCargaArchivos());
					if (!archivo.exists()) {
						archivo.mkdir();
					}
					/*	File archivoEnAplicativo = new File(absoluteDiskPath);
						if (!archivoEnAplicativo.exists()) {
							archivoEnAplicativo.mkdir();
						}
						*/

					archivo = new File(
							Parametros.getRutaCargarExcel() + codigoAbono + "-" + file.getOriginalFilename());

					/*	archivoEnAplicativo = new File(archivoEnAplicativo
								+ file.getOriginalFilename());*/

					file.transferTo(archivo);

					//				String ruta = archivo.toString();
					//				String fileNameWithOutExt = FilenameUtils.getExtension(ruta);
					ArchivoAbono archivoAbono = new ArchivoAbono();
					archivoAbono.setAbono(new Abono(codigoAbono));
					archivoAbono.setNombreArchivo(codigoAbono + "-" + file.getOriginalFilename());

					abonoService.guardarArchivoAbono(archivoAbono);
					res.put("STATUS", "SUCCESS");

				} 
			}
		} /*catch (IOException e) {
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		} */catch (Exception e) {
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarArchivosPrestamo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarArchivosPrestamo(CasoFiltro casoFiltro,
			@RequestParam("iDisplayStart") int displayStart,
			@RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0,
			@RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho,
			@ModelAttribute("prestamo") Prestamo prestamo) {
		JSONArray arrayCasosFiltrados = new JSONArray();
		JSONObject res = new JSONObject();
		JSONObject jsO = new JSONObject();
		Prestamo objPrestamo = new Prestamo();
		try {
			objPrestamo = prestamoService.obtenerPrestamo(prestamo);
			if (objPrestamo != null) {
				if (objPrestamo.getArchivo() != null) {
					jsO = new JSONObject();
					jsO.put("codigoCaso", objPrestamo.getCaso().getCodigo());
					jsO.put("codigoPrestamo", objPrestamo.getCodigoPrestamo());
					jsO.put("archivo", objPrestamo.getArchivo());
					jsO.put("descargar",
							"<a href='javascript:void(0);'class='btn btn-circle btn-success' title='Asociar Archivo'><i class='glyphicon glyphicon-download-alt'></i></a>");
					arrayCasosFiltrados.add(jsO);
				}
			}
		} catch (DAOException e) {
			res = new JSONObject();
			LOG.error("DAOException: Ocurrio un error consultando el archivo del prestamo . El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		} catch (BusinessException e) {
			res = new JSONObject();
			LOG.error("BusinessException: Ocurrio un error el archivo del prestamo . El error es: "
					+ e.getMessage());
			res.put("STATUS", estadoError);
		}
		res.put("sEcho", Integer.valueOf(sEcho));
		res.put("iTotalRecords", 1);
		res.put("iTotalDisplayRecords", 1);
		res.put("aaData", arrayCasosFiltrados);
		return res.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarArchivos", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarArchivos(
			CasoFiltro casoFiltro,
			@RequestParam("iDisplayStart") int displayStart,
			@RequestParam("iDisplayLength") int displayLength,
			@RequestParam("iSortCol_0") int sortColumn0,
			@RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho,
			@RequestParam(value = "codigoTarea", required = false) Integer codigoTarea) {

		TareaParticularCaso tareas = new TareaParticularCaso();
		tareas.setCodigoTarea(codigoTarea);
		List<Archivo> archivoFiltrados = new ArrayList<Archivo>();
		Archivo objArchivo = new Archivo();
		objArchivo.setCdtareaparticular(tareas);
		int cantidadArchivos = 0;
		JSONObject res = new JSONObject();

		try {
			archivoFiltrados = archivoService.findByPK(objArchivo);
		} catch (DAOException e) {
			LOG.error("Error al tratar de realizar la consulta de los archivos: "
					+ objArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			LOG.error("Error al tratar de realizar la consulta de los archivos: "
					+ objArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		}
		JSONObject jsO = new JSONObject();
		JSONArray arrayCasosFiltrados = new JSONArray();
		for (Archivo objArchivoLista : archivoFiltrados) {
			jsO = new JSONObject();
			jsO.put("nombreArchivo", objArchivoLista.getDsarchivo());
			jsO.put("codigoArchivo", objArchivoLista.getCdarchivo());
			jsO.put("descargar",
					"<a href='javascript:void(0);'class='btn btn-circle btn-success'	title='Asociar Archivo'><i class='glyphicon glyphicon-download-alt'></i></a>");
			arrayCasosFiltrados.add(jsO);
			cantidadArchivos++;
		}

		res.put("sEcho", Integer.valueOf(sEcho));
		res.put("iTotalRecords", cantidadArchivos);
		res.put("iTotalDisplayRecords", cantidadArchivos);
		res.put("aaData", arrayCasosFiltrados);

		return res.toString();

	}
	
	
	@RequestMapping({ "/downloadArchivo" })
	public void downloadArchivo(HttpServletResponse response,
			@RequestParam("nombreArchivo") String nombreArchivo)
			throws IOException {
		nombreArchivo = Utilidades.decodificarCaracteres(nombreArchivo);
		String fullPath = Parametros.getRutaCargarExcel() + nombreArchivo;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);
		response.setContentType("application/word");
		response.setContentLength((int) downloadFile.length());
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				new Object[] { downloadFile.getName() });
		response.setHeader(headerKey, headerValue);
		ServletOutputStream outStream = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();
	}

	@RequestMapping({ "/downloadArchivoPrestamo" })
	public void downloadArchivoPrestamo(HttpServletResponse response,
			@RequestParam("nombreArchivo") String nombreArchivo)
			throws IOException {
		nombreArchivo = Utilidades.decodificarCaracteres(nombreArchivo);
		String fullPath = Parametros.getRutaCargarExcel() + nombreArchivo;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);
		response.setContentType("application/word");
		response.setContentLength((int) downloadFile.length());
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				new Object[] { downloadFile.getName() });
		response.setHeader(headerKey, headerValue);
		ServletOutputStream outStream = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();
	}
	
	private void obtenerTelefonosCeldas(XSSFCell celdaTelefono, EquipoCaso equipoCaso) throws BusinessException {
		Telefono telefonoObjeto = new Telefono();
		List<Telefono> telefonosList = new ArrayList<Telefono>();
		try {
			if (celdaTelefono.getNumericCellValue() > 0) {
				telefonoObjeto.setNumero(Long.toString((long) celdaTelefono.getNumericCellValue()));
				telefonosList.add(telefonoObjeto);
			} else if (celdaTelefono.getNumericCellValue() == 0.0) {
				infoError = "Los campos marcados con (*) son requeridos.";
				throw new BusinessException("Los campos marcados con (*) son requeridos.");
			}
		} catch (Exception e) {
			if (celdaTelefono.getStringCellValue() != null && !celdaTelefono.getStringCellValue().isEmpty() && celdaTelefono != null) {
				if (celdaTelefono.getStringCellValue().contains(";")) {
					String telefonos[] = celdaTelefono.getStringCellValue().split(";");
					for (int i = 0; i < telefonos.length; i++) {
						telefonoObjeto = new Telefono();
						telefonoObjeto.setNumero(telefonos[i]);
						telefonosList.add(telefonoObjeto);
					}
				} else {
					telefonoObjeto.setNumero(celdaTelefono.getStringCellValue());
					telefonosList.add(telefonoObjeto);
				}
			}
		}
		if (!telefonosList.isEmpty())
			equipoCaso.setTelefonoList(telefonosList);
	}
	
	private void obtenerCelularesCeldas(XSSFCell celdaCelular, EquipoCaso equipoCaso) throws BusinessException {
		Celular celular = new Celular();
		List<Celular> celularesList = new ArrayList<Celular>();
		try {
			if (celdaCelular.getNumericCellValue() > 0 && celdaCelular != null) {
				celular.setNumero(Long.toString((long) celdaCelular.getNumericCellValue()));
				celularesList.add(celular);
			}
		} catch (Exception e) {
			if (celdaCelular.getStringCellValue() != null && !celdaCelular.getStringCellValue().isEmpty() && celdaCelular != null) {
				if (celdaCelular.getStringCellValue().contains(";")) {
					String celulares[] = celdaCelular.getStringCellValue().split(";");
					for (int i = 0; i < celulares.length; i++) {
						celular = new Celular();
						celular.setNumero(celulares[i]);
						celularesList.add(celular);
					}
				} else {
					celular.setNumero(celdaCelular.getStringCellValue());
					celularesList.add(celular);
				}
			}
		}
		if (!celularesList.isEmpty())
			equipoCaso.setCelularList(celularesList);
	}
	
	private void obtenerCorreosCeldas(XSSFCell celdaCorreo, EquipoCaso equipoCaso) throws BusinessException {
		Correo correo = new Correo();
		List<Correo> correosList = new ArrayList<Correo>();
		if (celdaCorreo.getStringCellValue() != null &&  !celdaCorreo.getStringCellValue().isEmpty() && celdaCorreo != null) {
			if (celdaCorreo.getStringCellValue().contains(";")) {
				String correos[] = celdaCorreo.getStringCellValue().split(";");
				for (int i = 0; i < correos.length; i++) {
					correo = new Correo();
					correo.setCorreo(correos[i]);
					correosList.add(correo);
				}
			} else {
				correo.setCorreo(celdaCorreo.getStringCellValue());
				correosList.add(correo);
			}
		}
		if (!correosList.isEmpty())
			equipoCaso.setCorreoList(correosList);
	}
	
	

}
