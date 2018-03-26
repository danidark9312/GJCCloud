package com.sf.util;

/**
 * @author Alejandro
 * @version 1.0
 * @created 17-jun-2012.
 */

public class BusinessException extends Throwable {
	  private static final long serialVersionUID = -495120564577807967L;

	  public BusinessException() {
	  }

	  public BusinessException(String message) {
	    super(message);
	  }

	  public BusinessException(String message, Throwable t) {
	    super(message, t);
	  }

	  public BusinessException(Throwable t) {
	    super(t);
	  }
}
