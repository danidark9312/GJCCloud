package com.sf.util;

import java.security.Principal;

import org.springframework.security.core.Authentication;

import com.sf.social.signinmvc.security.dto.SocialUserDetails;

public class Utilidades {

	
	
	public static String decodificarCaracteres(String stringCodificado) {
		String stringDecodificado = "";
		try {
			byte[] ptext = stringCodificado.getBytes("ISO-8859-1"); 
			stringDecodificado = new String(ptext, "UTF-8");
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return stringDecodificado;
	}
	
	
	public static SocialUserDetails getRealPrincipal(Principal principal) {
        if (principal instanceof SocialUserDetails) {
            return (SocialUserDetails) principal;
        }
        if (principal instanceof Authentication) {
            Object innerPrincipal = ((Authentication) principal).getPrincipal();
            if (innerPrincipal instanceof SocialUserDetails)
                return (SocialUserDetails) innerPrincipal;
        }
        return null;
    }
	
	public static boolean empty(String value) {
		return (value == null || value.trim() == "");
	}
}
