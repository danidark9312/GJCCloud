package com.sf.social.signinmvc.user.service;

import com.sf.social.signinmvc.user.dto.RegistrationForm;
import com.sf.social.signinmvc.user.model.User;

/**
 * @author Petri Kainulainen
 */
public interface UserService {

    /**
     * Creates a new user account to the service.
     * @param userAccountData   The information of the created user account.
     * @return  The information of the created user account.
     * @throws DuplicateEmailException Thrown when the email address is found from the database.
     */
    User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException;
}
