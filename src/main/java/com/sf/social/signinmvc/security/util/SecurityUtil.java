package com.sf.social.signinmvc.security.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.social.signinmvc.user.model.User;

/**
 * @author Petri Kainulainen
 */
public class SecurityUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {
    	
    }
    
    public static void logInUser(final User user) {
        LOGGER.info("Logging in user: {}", user);

        SocialUserDetails userDetails = SocialUserDetails.getBuilder()
                .nombre(user.getNombre())
                .id(user.getId())
                .apellido(user.getApellido())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())
                .username(user.getEmail())
                .activo( user.getActivo())
                .foto(user.getFoto())
                .numeroTarjetaProfesional(user.getNumeroTarjetaProfesional())
                .numeroTelefono(user.getNumeroTelefono())
                .direccion(user.getDireccion())
                
                
                .build();
        LOGGER.debug("Logging in principal: {}", userDetails);

        Authentication authentication = 
        		new UsernamePasswordAuthenticationToken(userDetails, 
        				null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LOGGER.info("User: {} has been logged in.", userDetails);
    }
}
