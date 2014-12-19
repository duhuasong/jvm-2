package jvm.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public static class Scope {

		public final static String PUBLIC = "public";

	}
	
	public static class VarType {

		public final static String STRING = "String";
		
		public final static String INTEGER = "Integer";
		
		public final static String STRING_ARRAY = "java.lang.String[]";

		public static final String VOID = "void";

	}
	
	public static class ConstantType {

		public final static String ClassType = "07";
		
		public final static String utf8 = "01";
		
		public final static String field = "09";
		
		public final static String method = "0a";
		
		public final static String nameAndType = "0c";
		

	}
	
	public static class ConstantLinkSymbol {

		public final static String nameAndType = ":";
		
		public final static String methodAndField = ".";
		
	}
	
	public final static Map<String,String> InstructionMap = new HashMap<String,String>();
	
	static{
		InstructionMap.put("04", "iconst_1");
		InstructionMap.put("1a", "iload_0");
		InstructionMap.put("1b", "iload_1");
		InstructionMap.put("1c", "iload_2");
		InstructionMap.put("1d", "iload_3");
		InstructionMap.put("3c", "istore_1");
		InstructionMap.put("3d", "istore_2");
		InstructionMap.put("3e", "istore_3");
		InstructionMap.put("10", "bipush");//后一个字节是整数值
		InstructionMap.put("b8", "invokestatic");//后两个字节是方法名称的index
		InstructionMap.put("b2", "getstatic");//TODO 
		InstructionMap.put("b6", "invokevirtual");//后两个字节是方法名称的index
		InstructionMap.put("60", "iadd");
		InstructionMap.put("b1", "return");
		InstructionMap.put("ac", "ireturn");
	}

}

