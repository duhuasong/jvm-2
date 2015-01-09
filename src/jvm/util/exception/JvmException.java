package jvm.util.exception;

public class JvmException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public JvmException() {
		
	} 

	public JvmException(String message) {
		super(message); 
	}
}
