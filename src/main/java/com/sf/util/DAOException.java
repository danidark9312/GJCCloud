package com.sf.util;

/**
 * @author Alejandro
 * @version 1.0
 * @created 17-jun-2012 8:17:50 a.m.
 */

public class DAOException extends Throwable {
	
	private static final long serialVersionUID = 7370828136376445282L;

	public DAOException() {
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable t) {
		super(message, t);
	}

	public DAOException(Throwable t) {
		super(t);
	}
}