package com.gja.gestionCasos.general.controller;

import java.security.Principal;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.gja.gestionCasos.actividades.service.ArchivoService;

import com.gja.gestionCasos.casos.service.PrestamoService;
import com.gja.gestionCasos.permisos.service.VistaRolesAccionOpciones;
import com.sf.roles.MenuVistaPermisosRolesWrapper;
import com.sf.roles.VistaPermisosRolesWrapper;
import com.sf.social.signinmvc.user.model.User;
import com.sf.social.signinmvc.user.service.UserService;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Controller
@RequestMapping(value = { "/sendMail" })
public class MailController {

	private static final Logger LOG = Logger.getLogger(MailController.class);
	
	@Autowired
	UserService userRepositoryService;

	@Autowired
	ArchivoService archivoService;
	@Autowired
	PrestamoService prestamoService;
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	VistaRolesAccionOpciones vistaRolesAccionOpciones;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/sendEmail" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = {
					"application/json; charset=utf-8" })
	@ResponseBody
	public String sendEmail(@RequestParam(value = "destinatarios", required = true) String destinatarios,
			@RequestParam(value = "cantidadDestinatarios", required = true) int cantidadDestinatarios,
			@RequestParam(value = "asunto", required = true) String asunto,
			@RequestParam(value = "mensaje", required = true) String mensaje,
			@RequestParam(value = "envioCompleto", required = false) String envioCompleto, HttpServletRequest request)
					throws DAOException, BusinessException {
		List<User> listaUsuarios = new ArrayList<User>();

		MimeMessage message = null;
		MimeMessageHelper helper = null;
		message = this.mailSender.createMimeMessage();
		try {
			helper = new MimeMessageHelper(message, true);
			List to = new ArrayList();
			for (User objUser : listaUsuarios) {
				to.add(objUser.getEmail());
			}
			helper.setTo((String[]) to.toArray(new String[0]));
			helper.setSubject(Parametros.getAsuntoActualizacion());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// helper.setText(Parametros.getMensajeCorreoContratoPorFinalizar());
		return envioCompleto;

	}

}
