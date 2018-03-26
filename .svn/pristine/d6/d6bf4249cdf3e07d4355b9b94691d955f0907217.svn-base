package com.gja.gestionCasos.administradores.repository;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.sf.social.signinmvc.user.model.ChangePassword;
import com.sf.util.DAOException;
import com.sf.util.Parametros;
import com.sf.util.repository.AbstractRepository;

@Repository("changePasswordRepository")
public class ChangePasswordRepositoryImpl  extends AbstractRepository<ChangePassword> implements ChangePasswordRepository{

	public ChangePassword save(ChangePassword data) throws DAOException {
			data = entityManager.merge(data);
			return data;
	}
	
	public ChangePassword getChangePassword(ChangePassword changePassword) throws DAOException {
	try {		
		changePassword = (ChangePassword)entityManager.createQuery("select c from ChangePassword c where c.changePasswordPK.token= :pToken AND c.changePasswordPK.email =:pEmail AND c.usado = :pUsado")
				.setParameter("pToken", changePassword.getChangePasswordPK().getToken())
				.setParameter("pUsado", Parametros.getTokenNoUsado())
				.setParameter("pEmail", changePassword.getChangePasswordPK().getEmail())
				.getSingleResult();
	} catch (NoResultException e) {
		changePassword = null;
	}
	

	return changePassword;
}
	
}
