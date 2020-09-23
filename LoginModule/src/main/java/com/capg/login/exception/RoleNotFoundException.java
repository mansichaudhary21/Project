package com.capg.login.exception;

public class RoleNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleNotFoundException(String msg)
	{
		super(msg);
	}

}
