package jvm.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public static class Scope {

		public final static String PUBLIC = "public";

	}
	
	public final static Map<String,String> varTypeMap = new HashMap<String,String>();
	
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

		public final static String classType = "07";
		
		public final static String utf8 = "01";
		
		public final static String field = "09";
		
		public final static String method = "0a";
		
		public final static String nameAndType = "0c";
		
		public final static String stringType = "08";

	}
	
	public static class ConstantLinkSymbol {

		public final static String nameAndType = ":";
		
		public final static String methodAndField = ".";
		
	}
	
	//public final static Map<String,String> instructionMap = new HashMap<String,String>();
	
	static{
		/*instructionMap.put("03", "iconst_0");
		instructionMap.put("04", "iconst_1");
		instructionMap.put("05", "iconst_2");
		instructionMap.put("06", "iconst_3");
		instructionMap.put("07", "iconst_4");
		instructionMap.put("08", "iconst_5");
		
		instructionMap.put("1a", "iload_0");
		instructionMap.put("1b", "iload_1");
		instructionMap.put("1c", "iload_2");
		instructionMap.put("1d", "iload_3");
		instructionMap.put("3c", "istore_1");
		instructionMap.put("3d", "istore_2");
		instructionMap.put("3e", "istore_3");
		instructionMap.put("10", "bipush");//后一个字节是整数值
		instructionMap.put("b8", "invokestatic");//后两个字节是方法名称的index
		instructionMap.put("b2", "getstatic");//类的静态属性入栈 
		instructionMap.put("b6", "invokevirtual");//后两个字节是方法名称的index
		instructionMap.put("60", "iadd");
		instructionMap.put("b1", "return");
		instructionMap.put("ac", "ireturn");
		instructionMap.put("bb", "new");*/
		//----------------------------------VarTypeMap----------------------------
		varTypeMap.put(VarType.Integer_Type, "java.lang.Integer");
	}

}

