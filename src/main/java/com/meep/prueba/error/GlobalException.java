package com.meep.prueba.error;

public class GlobalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7843912179982972387L;

	private final int codigo;

	public GlobalException(int codigo, String mensaje, Throwable pilaExcepciones) {
		super(mensaje, pilaExcepciones);
		this.codigo = codigo;
	}

	public GlobalException(int codigo, String mensaje) {
		this(codigo, mensaje, null);
	}

	public GlobalException(int codigo, Throwable pilaExcepciones) {
		this(codigo, null, pilaExcepciones);
	}

	public GlobalException(String mensaje) {
		this(mensaje, null);
	}

	public GlobalException(String mensaje, Throwable pilaExcepciones) {
		this(99, mensaje, pilaExcepciones);
	}

	public GlobalException(Throwable pilaExcepciones) {
		this(null, pilaExcepciones);
	}

	public int getCodigo() {
		return codigo;
	}

}

