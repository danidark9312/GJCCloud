package com.sf.social.signinmvc.security.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gja.gestionCasos.casos.controller.CasoController;
import com.gja.gestionCasos.maestros.service.MaestroUsuariosService;
import com.sf.social.signinmvc.user.model.ChangePassword;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;


@Controller
@RequestMapping(value = { "/sendEmail" })
public class SendEmailController {
	private static final Logger LOG = Logger.getLogger(CasoController.class);
	@Autowired
	MaestroUsuariosService maestroUsuariosService; 
	private final String errorMessage = "ERROR";

	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR";
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping()
	public String getCreateForm(Model model, HttpServletRequest httpRequest, Principal principal) {
		return "seguridad/sendEmail"; // carpeta y el jsp
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validarEmail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody JSONObject validarEmail(User usuario) throws Exception {
		JSONObject res = new JSONObject();
		ChangePassword changePassword= new ChangePassword();
		try {
			changePassword = maestroUsuariosService.existCorreoUsuarioToken(usuario);
			if (changePassword != null){
				res.put("STATUS", estadoExito);
				enviarCorreoAuditoria(changePassword);
			}
		} catch (DAOException e) {
			LOG.error("DAOException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		} catch (BusinessException e) {
			LOG.error("BusinessException: Ocurrio un error guardando el caso . El error es: " + e.getMessage());
			res.put("STATUS", errorMessage);
		}

		return res;
	}
	//Metodo para ennviar del correo del "Olvido su contraseña"	
	@RequestMapping(value = "/enviarCorreo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	private void enviarCorreoAuditoria(ChangePassword changePassword)
			throws MessagingException, BusinessException, DAOException, UnknownHostException {
		InetAddress ip= InetAddress.getLocalHost();
		//Con este capturo la ip y el host
		String url= "http://" + ip.getHostAddress().toString() + Parametros.getUrlToken() + "id=" + changePassword.getChangePasswordPK().getEmail() +
				"&token=" + changePassword.getChangePasswordPK().getToken();
		StringBuilder cuerpoMensaje = new StringBuilder();
		MimeMessage message = null;
		MimeMessageHelper helper = null;
		message = this.mailSender.createMimeMessage();
		helper = new MimeMessageHelper(message, true);
		List to = new ArrayList();
		
		to.add(changePassword.getChangePasswordPK().getEmail());
		
		//Mensaje
		cuerpoMensaje.append("Hola. "+ "\n" + "\n" +"Este es el link de restauraci\u00F3n: " + url + "\n" + "\n" + " se ha enviado al siguiente correo electr\u00F3nico: " + changePassword.getChangePasswordPK().getEmail() + "\n");
		helper.setSubject("Restauraci\u00F3n de contraseña");
				
		helper.setTo((String[]) to.toArray(new String[0]));
		helper.setText(cuerpoMensaje.toString());
		this.mailSender.send(message);
	}

	
}
