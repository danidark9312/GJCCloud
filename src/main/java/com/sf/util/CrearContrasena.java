package com.sf.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CrearContrasena {

	private PasswordEncoder passwordEncoder;
	
	
	
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String password="125";
		String encodedPassword =  passwordEncoder.encode(password);
		System.out.println(encodedPassword);

	}
	
	

}
