package com.gja.gestionCasos.administradores.service;

import com.sf.social.signinmvc.user.model.ChangePassword;
import com.sf.util.DAOException;

public interface ChangePasswordService {

	public ChangePassword save(ChangePassword data) throws DAOException;
	ChangePassword getChangePassword(ChangePassword changePassword) throws DAOException;
}
