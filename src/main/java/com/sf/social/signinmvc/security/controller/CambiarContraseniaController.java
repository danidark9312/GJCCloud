package com.sf.social.signinmvc.security.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gja.gestionCasos.administradores.service.ChangePasswordService;
import com.gja.gestionCasos.maestros.service.MaestroUsuariosService;
import com.sf.social.signinmvc.user.model.ChangePassword;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Controller
@RequestMapping(value = { "/cambiarContrasenia" })
public class CambiarContraseniaController {
	
	private static final Logger LOG = Logger.getLogger(CambiarContraseniaController.class);
	@Autowired
	MaestroUsuariosService maestroUsuariosService; 
	@Autowired
	ChangePasswordService changePasswordService;
	
	private final String errorMessage = "ERROR";

	private final String estadoExito = "SUCCESS";
	private final String estadoError = "ERROR"; 
	
	@RequestMapping()
	public String getCreateForm(Model model, HttpServletRequest httpRequest, Principal principal, String token, String id) {
		ChangePassword change = null;
		try {
			change = changePasswordService.getChangePassword(new ChangePassword(token, id));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (change != null)
			return "seguridad/cambiarContrasenia"; // carpeta y el jsp			
		else
			return "seguridad/errorNoAutorizacion"; // carpeta y el jsp
	}



	@RequestMapping(value = "/cambiarPassword", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String cambiarPassword(ChangePassword changePassword, Principal principal) {
		JSONObject res = new JSONObject();
		User usuario = new User();
		try {
			usuario.setId(maestroUsuariosService.obtenerIdPorEmail(changePassword.getChangePasswordPK().getEmail()));
			maestroUsuariosService.saveUsuario(usuario, changePassword.getNuevaContrasenia());
			res.put("STATUS", estadoExito);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res.toString();
	}
		
}
