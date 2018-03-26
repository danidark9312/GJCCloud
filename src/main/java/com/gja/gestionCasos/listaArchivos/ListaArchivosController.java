package com.gja.gestionCasos.listaArchivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONArray;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.entities.Archivo;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.actividades.service.ArchivoService;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.Correo;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Telefono;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.listaArchivos.entities.ListaArchivos;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.Utilidades;



@Controller
@RequestMapping(value = { "/listaArchivos" })

public class ListaArchivosController {
	
	@Autowired
	private ListaArchivosService listaArchivosService;	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ArchivoService archivoService;

	private static final Logger LOG = Logger.getLogger(ListaArchivosController.class);
	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;

	@RequestMapping()
	public String getCreateForm(Model model, Locale loc, HttpServletRequest httpRequest, Principal principal) {
		MenuVistaPermisosRolesWrapper menuVistaPermisosRolesWrapper = new MenuVistaPermisosRolesWrapper();
		VistaPermisosRolesWrapper vistaPermisosRolesWrapper = new VistaPermisosRolesWrapper();

		menuVistaPermisosRolesWrapper = vistaRolesAccionOpciones.getMenuAutorizaciones(principal);
		vistaPermisosRolesWrapper = vistaRolesAccionOpciones.getAutorizaciones(
				httpRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), principal);

		model.addAttribute("menuPermisos", menuVistaPermisosRolesWrapper);
		model.addAttribute("permisos", vistaPermisosRolesWrapper);
		model.addAttribute("aniosCaducidad", Parametros.getAnosCaducidad());

		return "archivos/listaArchivos"; // carpeta y el jsp
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarArchivos", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarArchivos(
			@RequestParam("iDisplayStart") int displayStart,
			@RequestParam("iDisplayLength") int displayLength,
			@RequestParam("sSearch") String sSearch,
			@RequestParam("iSortCol_0") int sortColumn0,
			@RequestParam("sSortDir_0") String sortDirection0,
			@RequestParam("sEcho") int sEcho,
			String nombreArchivo) {
		
		//Cada vez que se devuelva una fecha en formato json hacer esto
		SimpleDateFormat fechaCreacionFormato = new SimpleDateFormat("dd/MM/yyyy");

		List<ListaArchivos> archivoFiltrados = new ArrayList<ListaArchivos>();
		Long cantidadArchivos = null;
		JSONObject res = new JSONObject();
		JSONArray arrayCasosFiltrados = new JSONArray();
		JSONObject jsO = new JSONObject();

		try {
			archivoFiltrados = listaArchivosService.encontrarArchivosPorFiltro(nombreArchivo, displayStart, displayLength, sSearch);
			cantidadArchivos = listaArchivosService.getCantidadArchivos(nombreArchivo, displayStart, displayLength, sSearch);
			for (ListaArchivos objListaArchivo : archivoFiltrados) {
				jsO = new JSONObject();
				jsO.put("idArchivo", objListaArchivo.getIdArchivo());
				jsO.put("nombreAutor", objListaArchivo.getAutor());
				jsO.put("nombreArchivo", objListaArchivo.getNombreArchivo());
				jsO.put("descripcion", objListaArchivo.getDescripcion());
				jsO.put("fechaCreacion", fechaCreacionFormato.format(objListaArchivo.getFechaCreacion()));
				jsO.put("descargar",
						"<a href='javascript:void(0);'class='btn btn-circle btn-success'	title='Asociar Archivo'><i class='glyphicon glyphicon-download-alt'></i></a>");
				arrayCasosFiltrados.add(jsO);
			}
			
		} catch (DAOException e) {
			LOG.error("Error al tratar de realizar la consulta de los archivos: "
					+ e.getMessage());
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			LOG.error("Error al tratar de realizar la consulta de los archivos: "
					+ e.getMessage());
			res.put("STATUS", "ERROR");
		}
		

		res.put("sEcho", Integer.valueOf(sEcho));
		res.put("iTotalRecords", cantidadArchivos);
		res.put("iTotalDisplayRecords", cantidadArchivos);
		res.put("aaData", arrayCasosFiltrados);

		return res.toString();

	}
	
	@RequestMapping({ "/downloadArchivo" })
	public void downloadArchivoPrestamo(HttpServletResponse response,
			@RequestParam("nombreArchivo") String nombreArchivo)
			throws IOException {
		nombreArchivo = Utilidades.decodificarCaracteres(nombreArchivo);
		String fullPath = Parametros.getRutaArchivoLista() + nombreArchivo;
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
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = { "/saveDescripcionArchivo" }, produces = { "application/json; charset=utf-8" })
	public String saveDescripcionArchivo(HttpServletResponse response,
			@RequestParam("id") int id, @RequestParam("descripcion") String descripcion)
					throws IOException {
		JSONObject res = new JSONObject();
		try {
				ListaArchivos listaArchivosBD = listaArchivosService.find(id);
				listaArchivosBD.setDescripcion(descripcion);
				listaArchivosService.guardarlistaArchivo(listaArchivosBD);
				res.put("STATUS", "SUCCESS");
			}
		 catch (Exception e) {
			e.printStackTrace();
			res.put("STATUS", "ERROR");
		} catch (DAOException e) {
			e.printStackTrace();
			res.put("STATUS", "ERROR");
		} catch (BusinessException e) {
			e.printStackTrace();
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}	
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/asociarArchivo" }, method = {RequestMethod.POST }, produces = { "application/json; charset=utf-8" }, headers = { "Accept=*/*" })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String asociarArchivo(@RequestParam("file") MultipartFile file,HttpServletRequest context, Principal principal)
			throws IOException, DAOException, BusinessException {
		
		
		SocialUserDetails datosUsuario = Utilidades.getRealPrincipal(principal);
		JSONObject res = new JSONObject();
		String nombreArchivo = "";
//		java.util.Date fecha = new Date();
		ListaArchivos archivoSubir = new ListaArchivos();
		ServletContext servletContext = context.getSession()
				.getServletContext();
		String relativeWebPath = Parametros.getRutaArchivoLista();
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

				archivo = new File(Parametros.getRutaArchivoLista()+ "" + file.getOriginalFilename());
				archivoEnAplicativo = new File(archivoEnAplicativo
						+ file.getOriginalFilename());
				file.transferTo(archivo);
				String nombre = archivo.toString();
				archivoSubir.setNombreArchivo(file.getOriginalFilename());
				archivoSubir.setAutor(datosUsuario.getUserId());
				listaArchivosService.guardarlistaArchivo(archivoSubir);
				res.put("STATUS", "SUCCESS");
			}
		} catch (IOException e) {
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error al tratar de realizar la carga de archivos para el documento: "
					+ nombreArchivo + e.getMessage());
			res.put("STATUS", "ERROR");
		}
		return res.toString();
	}
}


