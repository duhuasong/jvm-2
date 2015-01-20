package jvm.util.exception;

import jvm.util.enums.JvmExceptionEnum;

public class JvmException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public JvmException() {
		
	} 

	public JvmException(String message) {
		super(message); 
	}
	public JvmException(int code) {
		this(JvmExceptionEnum.getDesc(code)); 
	}
}
