package com.sf.social.signinmvc.security.controller;

import java.security.SecureRandom;
import java.math.BigInteger;

	public final class RandomToken {
	  public SecureRandom random = new SecureRandom();

	  public String nextSessionId() {
	    return new BigInteger(130, random).toString(32);
	  }
	}
