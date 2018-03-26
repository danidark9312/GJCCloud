package com.gja.gestionCasos.clientes.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONArray;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.Correo;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Telefono;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.util.Parametros;



@Controller
@RequestMapping(value = { "/listaClientes" })
public class ListaClientesController {
	
	@Autowired
	ListaClienteService listaClienteService;

	private static final Logger LOG = Logger.getLogger(ListaClientesController.class);
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

		return "clientes/listaClientes"; // carpeta y el jsp
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarClientes", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String mostrarClientes(CasoFiltro casoFiltro, @RequestParam("iDisplayStart") int displayStart,
			@RequestParam("iDisplayLength") int displayLength, @RequestParam("iSortCol_0") int sortColumn0,
			@RequestParam("sSortDir_0") String sortDirection0, @RequestParam("sEcho") int sEcho) {

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		List<CasoEquipoCaso> listaClientes = null;
		String accion = null;
		JSONObject res = new JSONObject();
		listaClientes = listaClienteService.encontrarClientesPorFiltro(casoFiltro, false);
		Long cantidad = listaClienteService.contarClientesPorFiltro(casoFiltro, true);
		JSONObject jsO = new JSONObject();
		JSONArray arrayClientes = new JSONArray();
		for (CasoEquipoCaso casoEquipoCaso : listaClientes) {
			jsO = new JSONObject();
			EquipoCaso equipoCaso = casoEquipoCaso.getEquipoCaso();
			jsO.put("nombre", equipoCaso.getNombre() + (equipoCaso.getApellido() != null ? " " + equipoCaso.getApellido():""));
			jsO.put("cedula", equipoCaso.getIdentificacion());
			jsO.put("tipoMiembro", casoEquipoCaso.getTipoMiembro().getNombre());
			jsO.put("direccion", casoEquipoCaso.getDireccion());
			jsO.put("codigoCaso", casoEquipoCaso.getCaso().getCodigo());
			jsO.put("codigoEquipocaso", equipoCaso.getCodigoEquipoCaso());
			jsO.put("telefonos", crearStringTelefonos(casoEquipoCaso));
			jsO.put("correos", crearStringCorreos(casoEquipoCaso));
			jsO.put("nombreCaso", casoEquipoCaso.getCaso().getNombre());
			jsO.put("codigoMiembro", casoEquipoCaso.getTipoMiembro().getCodigo());

			accion = "";
			accion = accion
					+ "<SPAN title='Ver detalles'><a name='verDetalleTarea'><i  id='verDetalle' class='glyphicon glyphicon-chevron-down'></i></a></SPAN>";
			jsO.put("accionDetalleTarea", accion);


			arrayClientes.add(jsO);
		}

		res.put("sEcho", Integer.valueOf(sEcho));
		res.put("iTotalRecords", cantidad);
		res.put("iTotalDisplayRecords", cantidad);
		res.put("aaData", arrayClientes);

		return res.toString();
	}

	//Metodo para setear los telefonos
	private String crearStringTelefonos(CasoEquipoCaso casoEquipoCaso) {		
		List<Telefono> telefonos = casoEquipoCaso.getEquipoCaso().getTelefonoList();
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < telefonos.size(); i++) {
			strBuilder.append(telefonos.get(i).getNumero());
			if (!((i + 1) == telefonos.size()))
				strBuilder.append(", ");
		}
		return strBuilder.toString();
	}

	//Metodo para setear los correos
	private String crearStringCorreos(CasoEquipoCaso casoEquipoCaso) {
		List<Correo> correos = casoEquipoCaso.getEquipoCaso().getCorreoList();
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < correos.size(); i++) {
			strBuilder.append(correos.get(i).getCorreo());
			if (!((i + 1) == correos.size()))
				strBuilder.append(", ");
		}
		return strBuilder.toString();
	}

}
