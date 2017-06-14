package org.embedded.containers;

public class EmbeddedServletContainerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3459327666659162405L;

	public EmbeddedServletContainerException(String message, Object...in) {
		super(String.format(message, in));
	}

	public EmbeddedServletContainerException(Throwable arg0) {
		super(arg0);
	}
	
	

}
