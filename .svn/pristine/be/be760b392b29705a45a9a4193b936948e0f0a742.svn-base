package com.gja.gestionCasos.administradores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.administradores.repository.ChangePasswordRepository;
import com.sf.social.signinmvc.user.model.ChangePassword;
import com.sf.util.DAOException;
import com.sf.util.Parametros;


@Service("changePasswordService")
@Transactional(readOnly = true)
public class ChangePasswordServiceImpl implements ChangePasswordService {

	@Autowired
	ChangePasswordRepository changePasswordRepository;
	
	public ChangePassword save(ChangePassword data) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	public ChangePassword getChangePassword(ChangePassword changePassword)
			throws DAOException {
		ChangePassword changePasswordReturn = changePasswordRepository.getChangePassword(changePassword);
		if (changePasswordReturn != null) {			
			changePasswordReturn.setUsado(Parametros.getTokenNoUsado());
			changePasswordRepository.save(changePassword);
		}
		return changePasswordReturn;
	}
	
	
}
