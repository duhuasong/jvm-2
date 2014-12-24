package jvm.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public static class Scope {

		public final static String PUBLIC = "public";

	}
	
	public final static Map<String,String> VarTypeMap = new HashMap<String,String>();
	
	public static class VarType {

		public final static String Object_Type = "L";
		
		public final static String Integer_Type= "I";
		
		public final static String Byte_Type= "B";
		
		public final static String Char_Type= "C";
		
		public final static String Double_Type= "D";
		
		public final static String Float_Type= "F";
		
		public final static String Long_Type= "J";
		
		public final static String Short_Type= "S";
		
		public final static String Boolean_Type= "Z";
		
		public final static String Void_Type= "V";

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
		InstructionMap.put("b2", "getstatic");//类的静态属性入栈 
		InstructionMap.put("b6", "invokevirtual");//后两个字节是方法名称的index
		InstructionMap.put("60", "iadd");
		InstructionMap.put("b1", "return");
		InstructionMap.put("ac", "ireturn");
		
		VarTypeMap.put(VarType.Integer_Type, "java.lang.Integer");
	}

}

