package jvm.util.exception;

import java.util.HashMap;
import java.util.Map;

public class JvmException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private static final Map<Integer,String> jvmExceptionEnum= new HashMap<Integer,String>();
	
	public static final int METHOD_NOT_IMPLEMENT= 1;
	
	static{
		jvmExceptionEnum.put(METHOD_NOT_IMPLEMENT, "此方法还未实现。");
	}

	public JvmException() {
		
	} 

	public JvmException(String message) {
		super(message); 
	}
	public JvmException(int code) {
		super(jvmExceptionEnum.get(code));
	}
}
